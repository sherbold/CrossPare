// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.dataprocessing;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import com.google.common.collect.Sets;

import de.ugoe.cs.cpdp.util.SortUtils;
import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.util.console.Console;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * <p>
 * Implements the OPTTOPk filter after P. He et al. (2015).
 * </p>
 * 
 * @author Steffen Herbold
 */
public class TopMetricFilter implements ISetWiseProcessingStrategy {

    /**
     * Internally used correlation threshold.
     */
    double correlationThreshold = 0.5;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.equals("")) {
            this.correlationThreshold = Double.parseDouble(parameters);
        }
    }

    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        try {
            determineTopKAttributes(testdata, traindataSet);
        }
        catch (Exception e) {
            Console.printerr("Failure during metric selection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("boxing")
    private void determineTopKAttributes(Instances testdata, SetUniqueList<Instances> traindataSet)
        throws Exception
    {
        Integer[] counts = new Integer[traindataSet.get(0).numAttributes() - 1];
        IntStream.range(0, counts.length).forEach(val -> counts[val] = 0);
        for (Instances traindata : traindataSet) {
            J48 decisionTree = new J48();
            decisionTree.buildClassifier(traindata);
            int k = 0;
            for (int j = 0; j < traindata.numAttributes(); j++) {
                if (j != traindata.classIndex()) {
                    if (decisionTree.toString().contains(traindata.attribute(j).name())) {
                        counts[k] = counts[k] + 1;
                    }
                    k++;
                }
            }
        }
        int[] topkIndex = new int[counts.length];
        IntStream.range(0, counts.length).forEach(val -> topkIndex[val] = val);
        SortUtils.quicksort(counts, topkIndex, true);

        // get CFSs for each training set
        List<Set<Integer>> cfsSets = new LinkedList<>();
        for (Instances traindata : traindataSet) {
            boolean selectionSuccessful = false;
            boolean secondAttempt = false;
            Instances traindataCopy = null;
            do {
                try {
                    if (secondAttempt) {
                        AttributeSelection attsel = new AttributeSelection();
                        CfsSubsetEval eval = new CfsSubsetEval();
                        GreedyStepwise search = new GreedyStepwise();
                        search.setSearchBackwards(true);
                        attsel.setEvaluator(eval);
                        attsel.setSearch(search);
                        attsel.SelectAttributes(traindataCopy);
                        Set<Integer> cfsSet = new HashSet<>();
                        for (int attr : attsel.selectedAttributes()) {
                            cfsSet.add(attr);
                        }
                        cfsSets.add(cfsSet);
                        selectionSuccessful = true;
                    }
                    else {
                        AttributeSelection attsel = new AttributeSelection();
                        CfsSubsetEval eval = new CfsSubsetEval();
                        GreedyStepwise search = new GreedyStepwise();
                        search.setSearchBackwards(true);
                        attsel.setEvaluator(eval);
                        attsel.setSearch(search);
                        attsel.SelectAttributes(traindata);
                        Set<Integer> cfsSet = new HashSet<>();
                        for (int attr : attsel.selectedAttributes()) {
                            cfsSet.add(attr);
                        }
                        cfsSets.add(cfsSet);
                        selectionSuccessful = true;
                    }
                }
                catch (IllegalArgumentException e) {
                    String regex = "A nominal attribute \\((.*)\\) cannot have duplicate labels.*";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(e.getMessage());
                    if (!m.find()) {
                        // cannot treat problem, rethrow exception
                        throw e;
                    }
                    String attributeName = m.group(1);
                    int attrIndex = traindata.attribute(attributeName).index();
                    if (secondAttempt) {
                        traindataCopy = WekaUtils.upscaleAttribute(traindataCopy, attrIndex);
                    }
                    else {
                        traindataCopy = WekaUtils.upscaleAttribute(traindata, attrIndex);
                    }
                    Console
                        .traceln(Level.FINE,
                                 "upscaled attribute " + attributeName + "; restarting training");
                    secondAttempt = true;
                    continue;
                }
            }
            while (!selectionSuccessful); // dummy loop for internal continue
        }

        double[] coverages = new double[topkIndex.length];
        for (Set<Integer> cfsSet : cfsSets) {
            Set<Integer> topkSet = new HashSet<>();
            for (int k = 0; k < topkIndex.length; k++) {
                topkSet.add(topkIndex[k]);
                coverages[k] += (coverage(topkSet, cfsSet) / traindataSet.size());
            }
        }
        double bestCoverageValue = Double.MIN_VALUE;
        int bestCoverageIndex = 0;
        for (int i = 0; i < coverages.length; i++) {
            if (coverages[i] > bestCoverageValue) {
                bestCoverageValue = coverages[i];
                bestCoverageIndex = i;
            }
        }
        // build correlation matrix
        SpearmansCorrelation corr = new SpearmansCorrelation();
        double[][] correlationMatrix = new double[bestCoverageIndex][bestCoverageIndex];
        for (Instances traindata : traindataSet) {
            double[][] vectors = new double[bestCoverageIndex][traindata.size()];
            for (int i = 0; i < traindata.size(); i++) {
                for (int j = 0; j < bestCoverageIndex; j++) {
                    vectors[j][i] = traindata.get(i).value(topkIndex[j]);
                }
            }
            for (int j = 0; j < bestCoverageIndex; j++) {
                for (int k = j + 1; k < bestCoverageIndex; k++) {
                    correlationMatrix[j][k] = Math.abs(corr.correlation(vectors[j], vectors[k]));
                }
            }
        }
        Set<Integer> topkSetIndexSet = new TreeSet<>();
        // j<30 ensures that the computational time does not explode since the powerset is 2^n in
        // complexity
        for (int j = 0; j < bestCoverageIndex && j < 30; j++) {
            topkSetIndexSet.add(j);
        }
        Set<Set<Integer>> allCombinations = Sets.powerSet(topkSetIndexSet);
        double bestOptCoverage = Double.MIN_VALUE;
        Set<Integer> opttopkSetIndexSet = null;
        for (Set<Integer> combination : allCombinations) {
            if (isUncorrelated(correlationMatrix, combination)) {
                double currentCoverage = 0.0;
                Set<Integer> topkCombination = new TreeSet<>();
                for (Integer index : combination) {
                    topkCombination.add(topkIndex[index]);
                }
                for (Set<Integer> cfsSet : cfsSets) {
                    currentCoverage += (coverage(topkCombination, cfsSet) / traindataSet.size());
                }
                if (currentCoverage > bestOptCoverage) {
                    bestOptCoverage = currentCoverage;
                    opttopkSetIndexSet = combination;
                }
            }
        }
        if( opttopkSetIndexSet==null ) {
            throw new RuntimeException("Could not determine a best top-k set with optimal coverage. This means that the top-k set and the subset determined by CFS are disjunctive.");
        }
        
        Set<Integer> opttopkIndex = new TreeSet<>();
        for (Integer index : opttopkSetIndexSet) {
            opttopkIndex.add(topkIndex[index]);
        }
        Console.traceln(Level.FINE, "selected the following metrics:");
        for (Integer index : opttopkIndex) {
            Console.traceln(Level.FINE, traindataSet.get(0).attribute(index).name());
        }
        // finally remove attributes
        for (int j = testdata.numAttributes() - 1; j >= 0; j--) {
            if (j != testdata.classIndex() && !opttopkIndex.contains(j)) {
                testdata.deleteAttributeAt(j);
                for (Instances traindata : traindataSet) {
                    traindata.deleteAttributeAt(j);
                }
            }
        }
    }

    @SuppressWarnings("boxing")
    private boolean isUncorrelated(double[][] correlationMatrix, Set<Integer> combination) {
        Integer[] intCombination = combination.toArray(new Integer[0]);
        boolean areUncorrelated = true;
        for (int i = 0; areUncorrelated && i < intCombination.length; i++) {
            for (int j = i + 1; areUncorrelated && j < intCombination.length; j++) {
                areUncorrelated &=
                    correlationMatrix[intCombination[i]][intCombination[j]] > this.correlationThreshold;
            }
        }
        return areUncorrelated;
    }

    private static double coverage(Set<Integer> topkSet, Set<Integer> cfsSet) {
        Set<Integer> topkSetCopy1 = new HashSet<>(topkSet);
        topkSetCopy1.retainAll(cfsSet);
        Set<Integer> topkSetCopy2 = new HashSet<>(topkSet);
        topkSetCopy2.addAll(cfsSet);
        return ((double) topkSetCopy1.size()) / topkSetCopy2.size();
    }
}
