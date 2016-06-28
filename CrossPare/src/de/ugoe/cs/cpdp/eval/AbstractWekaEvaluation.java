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

package de.ugoe.cs.cpdp.eval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.util.StringTools;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Base class for the evaluation of results of classifiers compatible with the {@link Classifier}
 * interface. For each classifier, the following metrics are calculated:
 * <ul>
 * <li>succHe: Success with recall>0.7, precision>0.5</li>
 * <li>succZi: Success with recall>0.7, precision>0.7</li>
 * <li>succG75: Success with gscore>0.75</li>
 * <li>succG60: Success with gscore>0.6</li>
 * <li>error</li>
 * <li>recall</li>
 * <li>precision</li>
 * <li>fscore</li>
 * <li>gscore</li>
 * <li>MCC</li>
 * <li>AUC</li>
 * <li>AUCEC (weighted by LOC, if applicable; 0.0 if LOC not available)</li>
 * <li>tpr: true positive rate</li>
 * <li>tnr: true negative rate</li>
 * <li>fpr: false positive rate</li>
 * <li>fnr: false negative rate</li>
 * <li>tp: true positives</li>
 * <li>fp: false positives</li>
 * <li>tn: true negatives</li>
 * <li>fn: false negatives</li>
 * </ul>
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractWekaEvaluation implements IEvaluationStrategy {

    /**
     * writer for the evaluation results
     */
    private PrintWriter output = new PrintWriter(System.out);

    private boolean outputIsSystemOut = true;
    
    private String configurationName = "default";

    /**
     * Creates the Weka evaluator. Allows the creation of the evaluator in different ways, e.g., for
     * cross-validation or evaluation on the test data.
     * 
     * @param testdata
     *            test data
     * @param classifier
     *            classifier used
     * @return evaluator
     */
    protected abstract Evaluation createEvaluator(Instances testdata, Classifier classifier);

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.EvaluationStrategy#apply(weka.core.Instances, weka.core.Instances,
     * java.util.List, boolean)
     */
    @Override
    public void apply(Instances testdata,
                      Instances traindata,
                      List<ITrainer> trainers,
                      List<Double> efforts, 
                      boolean writeHeader,
                      List<IResultStorage> storages)
    {
        final List<Classifier> classifiers = new LinkedList<>();
        final List<ExperimentResult> experimentResults = new LinkedList<>();
        String productName = testdata.relationName();
        
        for (ITrainer trainer : trainers) {
            if (trainer instanceof IWekaCompatibleTrainer) {
                classifiers.add(((IWekaCompatibleTrainer) trainer).getClassifier());
                experimentResults.add(new ExperimentResult(configurationName, productName, ((IWekaCompatibleTrainer) trainer).getName()));
            }
            else {
                throw new RuntimeException("The selected evaluator only support Weka classifiers");
            }
        }

        if (writeHeader) {
            output.append("version,size_test,size_training");
            for (ITrainer trainer : trainers) {
                output.append(",succHe_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",succZi_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",succG75_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",succG60_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",error_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",recall_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",precision_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",fscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",gscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",mcc_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",auc_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",aucec_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",tpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",tnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",fpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",fnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",tp_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",fn_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",tn_" + ((IWekaCompatibleTrainer) trainer).getName());
                output.append(",fp_" + ((IWekaCompatibleTrainer) trainer).getName());
            }
            output.append(StringTools.ENDLINE);
        }

        output.append(productName);
        output.append("," + testdata.numInstances());
        output.append("," + traindata.numInstances());

        Evaluation eval = null;
        Iterator<Classifier> classifierIter = classifiers.iterator();
        Iterator<ExperimentResult> resultIter = experimentResults.iterator();
        while (classifierIter.hasNext()) {
            Classifier classifier = classifierIter.next();
            eval = createEvaluator(testdata, classifier);

            double pf =
                eval.numFalsePositives(1) / (eval.numFalsePositives(1) + eval.numTrueNegatives(1));
            double gmeasure = 2 * eval.recall(1) * (1.0 - pf) / (eval.recall(1) + (1.0 - pf));
            double aucec = calculateReviewEffort(testdata, classifier, efforts);
            double succHe = eval.recall(1) >= 0.7 && eval.precision(1) >= 0.5 ? 1.0 : 0.0;
            double succZi = eval.recall(1) >= 0.7 && eval.precision(1) >= 0.7 ? 1.0 : 0.0;
            double succG75 = gmeasure > 0.75 ? 1.0 : 0.0;
            double succG60 = gmeasure > 0.6 ? 1.0 : 0.0;
            
            output.append("," + succHe);
            output.append("," + succZi);
            output.append("," + succG75);
            output.append("," + succG60);            
            output.append("," + eval.errorRate());
            output.append("," + eval.recall(1));
            output.append("," + eval.precision(1));
            output.append("," + eval.fMeasure(1));
            output.append("," + gmeasure);
            output.append("," + eval.matthewsCorrelationCoefficient(1));
            output.append("," + eval.areaUnderROC(1));
            output.append("," + aucec);
            output.append("," + eval.truePositiveRate(1));
            output.append("," + eval.trueNegativeRate(1));
            output.append("," + eval.falsePositiveRate(1));
            output.append("," + eval.falseNegativeRate(1));
            output.append("," + eval.numTruePositives(1));
            output.append("," + eval.numFalseNegatives(1));
            output.append("," + eval.numTrueNegatives(1));
            output.append("," + eval.numFalsePositives(1));
            
            ExperimentResult result = resultIter.next();
            result.setSizeTestData(testdata.numInstances());
            result.setSizeTrainingData(traindata.numInstances());
            result.setSuccHe(succHe);
            result.setSuccZi(succZi);
            result.setSuccG75(succG75);
            result.setSuccG60(succG60);
            result.setError(eval.errorRate());
            result.setRecall(eval.recall(1));
            result.setPrecision(eval.precision(1));
            result.setFscore(eval.fMeasure(1));
            result.setGscore(gmeasure);
            result.setMcc(eval.matthewsCorrelationCoefficient(1));
            result.setAuc(eval.areaUnderROC(1));
            result.setAucec(aucec);
            result.setTpr(eval.truePositiveRate(1));
            result.setTnr(eval.trueNegativeRate(1));
            result.setFpr(eval.falsePositiveRate(1));
            result.setFnr(eval.falseNegativeRate(1));
            result.setTp(eval.numTruePositives(1));
            result.setFn(eval.numFalseNegatives(1));
            result.setTn(eval.numTrueNegatives(1));
            result.setFp(eval.numFalsePositives(1));
            for( IResultStorage storage : storages ) {
                storage.addResult(result);
            }
        }

        output.append(StringTools.ENDLINE);
        output.flush();
    }
    
    private double calculateReviewEffort(Instances testdata, Classifier classifier, List<Double> efforts) {
        if( efforts==null ) {
            return 0;
        }
        
        final List<Integer> bugPredicted = new ArrayList<>();
        final List<Integer> nobugPredicted = new ArrayList<>();
        double totalLoc = 0.0d;
        int totalBugs = 0;
        for (int i = 0; i < testdata.numInstances(); i++) {
            try {
                if (Double.compare(classifier.classifyInstance(testdata.instance(i)), 0.0d) == 0) {
                    nobugPredicted.add(i);
                }
                else {
                    bugPredicted.add(i);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(
                                           "unexpected error during the evaluation of the review effort",
                                           e);
            }
            if (Double.compare(testdata.instance(i).classValue(), 1.0d) == 0) {
                totalBugs++;
            }
            totalLoc += efforts.get(i);
        }

        final List<Double> reviewLoc = new ArrayList<>(testdata.numInstances());
        final List<Double> bugsFound = new ArrayList<>(testdata.numInstances());

        double currentBugsFound = 0;

        while (!bugPredicted.isEmpty()) {
            double minLoc = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < bugPredicted.size(); i++) {
                double currentLoc = efforts.get(bugPredicted.get(i));
                if (currentLoc < minLoc) {
                    minIndex = i;
                    minLoc = currentLoc;
                }
            }
            if (minIndex != -1) {
                reviewLoc.add(minLoc / totalLoc);

                currentBugsFound += testdata.instance(bugPredicted.get(minIndex)).classValue();
                bugsFound.add(currentBugsFound);

                bugPredicted.remove(minIndex);
            }
            else {
                throw new RuntimeException("Shouldn't happen!");
            }
        }

        while (!nobugPredicted.isEmpty()) {
            double minLoc = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < nobugPredicted.size(); i++) {
                double currentLoc = efforts.get(nobugPredicted.get(i));
                if (currentLoc < minLoc) {
                    minIndex = i;
                    minLoc = currentLoc;
                }
            }
            if (minIndex != -1) {
                reviewLoc.add(minLoc / totalLoc);

                currentBugsFound += testdata.instance(nobugPredicted.get(minIndex)).classValue();
                bugsFound.add(currentBugsFound);
                nobugPredicted.remove(minIndex);
            }
            else {
                throw new RuntimeException("Shouldn't happen!");
            }
        }

        double auc = 0.0;
        for (int i = 0; i < bugsFound.size(); i++) {
            auc += reviewLoc.get(i) * bugsFound.get(i) / totalBugs;
        }

        return auc;
    }

    @SuppressWarnings("unused")
    @Deprecated
    private double calculateReviewEffort(Instances testdata, Classifier classifier) {

        // attribute in the JURECZKO data and default
        Attribute loc = testdata.attribute("loc");
        if (loc == null) {
            // attribute in the NASA/SOFTMINE/MDP data
            loc = testdata.attribute("LOC_EXECUTABLE");
        }
        if (loc == null) {
            // attribute in the AEEEM data
            loc = testdata.attribute("numberOfLinesOfCode");
        }
        if (loc == null) {
            // attribute in the RELINK data
            loc = testdata.attribute("CountLineCodeExe");
        }
        if( loc == null ) {
            return 0.0;
        }

        final List<Integer> bugPredicted = new ArrayList<>();
        final List<Integer> nobugPredicted = new ArrayList<>();
        double totalLoc = 0.0d;
        int totalBugs = 0;
        for (int i = 0; i < testdata.numInstances(); i++) {
            try {
                if (Double.compare(classifier.classifyInstance(testdata.instance(i)), 0.0d) == 0) {
                    nobugPredicted.add(i);
                }
                else {
                    bugPredicted.add(i);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(
                                           "unexpected error during the evaluation of the review effort",
                                           e);
            }
            if (Double.compare(testdata.instance(i).classValue(), 1.0d) == 0) {
                totalBugs++;
            }
            totalLoc += testdata.instance(i).value(loc);
        }

        final List<Double> reviewLoc = new ArrayList<>(testdata.numInstances());
        final List<Double> bugsFound = new ArrayList<>(testdata.numInstances());

        double currentBugsFound = 0;

        while (!bugPredicted.isEmpty()) {
            double minLoc = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < bugPredicted.size(); i++) {
                double currentLoc = testdata.instance(bugPredicted.get(i)).value(loc);
                if (currentLoc < minLoc) {
                    minIndex = i;
                    minLoc = currentLoc;
                }
            }
            if (minIndex != -1) {
                reviewLoc.add(minLoc / totalLoc);

                currentBugsFound += testdata.instance(bugPredicted.get(minIndex)).classValue();
                bugsFound.add(currentBugsFound);

                bugPredicted.remove(minIndex);
            }
            else {
                throw new RuntimeException("Shouldn't happen!");
            }
        }

        while (!nobugPredicted.isEmpty()) {
            double minLoc = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < nobugPredicted.size(); i++) {
                double currentLoc = testdata.instance(nobugPredicted.get(i)).value(loc);
                if (currentLoc < minLoc) {
                    minIndex = i;
                    minLoc = currentLoc;
                }
            }
            if (minIndex != -1) {
                reviewLoc.add(minLoc / totalLoc);

                currentBugsFound += testdata.instance(nobugPredicted.get(minIndex)).classValue();
                bugsFound.add(currentBugsFound);
                nobugPredicted.remove(minIndex);
            }
            else {
                throw new RuntimeException("Shouldn't happen!");
            }
        }

        double auc = 0.0;
        for (int i = 0; i < bugsFound.size(); i++) {
            auc += reviewLoc.get(i) * bugsFound.get(i) / totalBugs;
        }

        return auc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.Parameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (output != null && !outputIsSystemOut) {
            output.close();
        }
        if ("system.out".equals(parameters) || "".equals(parameters)) {
            output = new PrintWriter(System.out);
            outputIsSystemOut = true;
        }
        else {
            try {
                output = new PrintWriter(new FileOutputStream(parameters));
                outputIsSystemOut = false;
                int filenameStart = parameters.lastIndexOf('/')+1;
                int filenameEnd = parameters.lastIndexOf('.');
                configurationName = parameters.substring(filenameStart, filenameEnd);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
