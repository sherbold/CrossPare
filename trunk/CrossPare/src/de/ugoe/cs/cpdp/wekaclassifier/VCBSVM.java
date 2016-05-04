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

package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import de.lmu.ifi.dbs.elki.logging.Logging.Level;
import de.ugoe.cs.cpdp.util.SortUtils;
import de.ugoe.cs.util.console.Console;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * <p>
 * VCBSVM after Ryu et al. (2014)
 * </p>
 * 
 * @author Steffen Herbold
 */
public class VCBSVM extends AbstractClassifier implements ITestAwareClassifier {

    /**
     * Default id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Test data. CLASSIFICATION MUST BE IGNORED!
     */
    private Instances testdata = null;

    /**
     * Number of boosting iterations
     */
    private int boostingIterations = 5;

    /**
     * Penalty parameter lamda
     */
    private double lamda = 0.5;

    /**
     * Classifier trained in each boosting iteration
     */
    private List<Classifier> boostingClassifiers;

    /**
     * Weights for each boosting iteration
     */
    private List<Double> classifierWeights;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#getCapabilities()
     */
    @Override
    public Capabilities getCapabilities() {
        return new SMO().getCapabilities();
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#setOptions(java.lang.String[])
     */
    @Override
    public void setOptions(String[] options) throws Exception {
        String lamdaString = Utils.getOption('L', options);
        String boostingIterString = Utils.getOption('B', options);
        if (!boostingIterString.equals("")) {
            boostingIterations = Integer.parseInt(boostingIterString);
        }
        if (lamdaString.equals("")) {
            lamda = Double.parseDouble(lamdaString);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.wekaclassifier.ITestAwareClassifier#setTestdata(weka.core.Instances)
     */
    @Override
    public void setTestdata(Instances testdata) {
        this.testdata = testdata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
     */
    @Override
    public double classifyInstance(Instance instance) throws Exception {
        double classification = 0.0;
        Iterator<Classifier> classifierIter = boostingClassifiers.iterator();
        Iterator<Double> weightIter = classifierWeights.iterator();
        while (classifierIter.hasNext()) {
            Classifier classifier = classifierIter.next();
            Double weight = weightIter.next();
            if (classifier.classifyInstance(instance) > 0.5d) {
                classification += weight;
            }
            else {
                classification -= weight;
            }
        }
        return classification >= 0 ? 1.0d : 0.0d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances data) throws Exception {
        // get validation set
        Resample resample = new Resample();
        resample.setSampleSizePercent(50);
        Instances validationCandidates;
        try {
            resample.setInputFormat(data);
            validationCandidates = Filter.useFilter(data, resample);
        }
        catch (Exception e) {
            Console.traceln(Level.SEVERE, "failure during validation set selection of VCBSVM");
            throw new RuntimeException(e);
        }
        Double[] validationCandidateWeights = calculateSimilarityWeights(validationCandidates);
        int[] indexSet = new int[validationCandidateWeights.length];
        IntStream.range(0, indexSet.length).forEach(val -> indexSet[val] = val);
        SortUtils.quicksort(validationCandidateWeights, indexSet, true);
        Instances validationdata = new Instances(validationCandidates);
        validationdata.clear();
        int numValidationInstances = (int) Math.ceil(indexSet.length * 0.2);
        for (int i = 0; i < numValidationInstances; i++) {
            validationdata.add(validationCandidates.get(indexSet[i]));
        }

        // setup training data (data-validationdata)
        Instances traindata = new Instances(data);
        traindata.removeAll(validationdata);
        Double[] similarityWeights = calculateSimilarityWeights(traindata);

        double[] boostingWeights = new double[traindata.size()];
        for (int i = 0; i < boostingWeights.length; i++) {
            boostingWeights[i] = 1.0d;
        }
        double bestAuc = 0.0;
        boostingClassifiers = new LinkedList<>();
        classifierWeights = new LinkedList<>();
        for (int boostingIter = 0; boostingIter < boostingIterations; boostingIter++) {
            for (int i = 0; i < boostingWeights.length; i++) {
                traindata.get(i).setWeight(boostingWeights[i]);
            }

            Instances traindataCurrentLoop;
            if (boostingIter > 0) {
                traindataCurrentLoop = sampleData(traindata, similarityWeights);
            }
            else {
                traindataCurrentLoop = traindata;
            }

            SMO internalClassifier = new SMO();
            internalClassifier.buildClassifier(traindataCurrentLoop);

            double sumWeightedMisclassifications = 0.0d;
            double sumWeights = 0.0d;
            for (int i = 0; i < traindataCurrentLoop.size(); i++) {
                Instance inst = traindataCurrentLoop.get(i);
                if (inst.classValue() != internalClassifier.classifyInstance(inst)) {
                    sumWeightedMisclassifications += inst.weight();
                }
                sumWeights += inst.weight();
            }
            double epsilon = sumWeightedMisclassifications / sumWeights;
            double alpha = lamda * Math.log((1.0d - epsilon) / epsilon);
            for (int i = 0; i < traindata.size(); i++) {
                Instance inst = traindata.get(i);
                if (inst.classValue() != internalClassifier.classifyInstance(inst)) {
                    boostingWeights[i] *= boostingWeights[i] * Math.exp(alpha);
                }
                else {
                    boostingWeights[i] *= boostingWeights[i] * Math.exp(-alpha);
                }
            }
            classifierWeights.add(alpha);
            boostingClassifiers.add(internalClassifier);

            final Evaluation eval = new Evaluation(validationdata);
            eval.evaluateModel(this, validationdata);
            double currentAuc = eval.areaUnderROC(1);
            final Evaluation eval2 = new Evaluation(validationdata);
            eval2.evaluateModel(internalClassifier, validationdata);

            if (currentAuc >= bestAuc) {
                bestAuc = currentAuc;
            }
            else {
                // performance drop, abort boosting, classifier of current iteration is dropped
                Console.traceln(Level.INFO, "no gain for boosting iteration " + (boostingIter + 1) +
                    "; aborting boosting");
                classifierWeights.remove(classifierWeights.size() - 1);
                boostingClassifiers.remove(boostingClassifiers.size() - 1);
                return;
            }
        }
    }

    /**
     * <p>
     * Calculates the similarity weights for the training data
     * </p>
     *
     * @param data
     *            training data
     * @return vector with similarity weights
     */
    private Double[] calculateSimilarityWeights(Instances data) {
        double[] minAttValues = new double[data.numAttributes()];
        double[] maxAttValues = new double[data.numAttributes()];
        Double[] weights = new Double[data.numInstances()];

        for (int j = 0; j < data.numAttributes(); j++) {
            if (j != data.classIndex()) {
                minAttValues[j] = testdata.attributeStats(j).numericStats.min;
                maxAttValues[j] = testdata.attributeStats(j).numericStats.max;
            }
        }

        for (int i = 0; i < data.numInstances(); i++) {
            Instance inst = data.instance(i);
            int similar = 0;
            for (int j = 0; j < data.numAttributes(); j++) {
                if (j != data.classIndex()) {
                    if (inst.value(j) >= minAttValues[j] && inst.value(j) <= maxAttValues[j]) {
                        similar++;
                    }
                }
            }
            weights[i] = similar / (data.numAttributes() - 1.0d);
        }
        return weights;
    }

    /**
     * 
     * <p>
     * Samples data according to the similarity weights. This sampling
     * </p>
     *
     * @param data
     * @param similarityWeights
     * @return sampled data
     */
    private Instances sampleData(Instances data, Double[] similarityWeights) {
        // split data into four sets;
        Instances similarPositive = new Instances(data);
        similarPositive.clear();
        Instances similarNegative = new Instances(data);
        similarNegative.clear();
        Instances notsimiPositive = new Instances(data);
        notsimiPositive.clear();
        Instances notsimiNegative = new Instances(data);
        notsimiNegative.clear();
        for (int i = 0; i < data.numInstances(); i++) {
            if (data.get(i).classValue() == 1.0) {
                if (similarityWeights[i] == 1.0) {
                    similarPositive.add(data.get(i));
                }
                else {
                    notsimiPositive.add(data.get(i));
                }
            }
            else {
                if (similarityWeights[i] == 1.0) {
                    similarNegative.add(data.get(i));
                }
                else {
                    notsimiNegative.add(data.get(i));
                }
            }
        }

        int sampleSizes = (similarPositive.size() + notsimiPositive.size()) / 2;

        similarPositive = weightedResample(similarPositive, sampleSizes);
        notsimiPositive = weightedResample(notsimiPositive, sampleSizes);
        similarNegative = weightedResample(similarNegative, sampleSizes);
        notsimiNegative = weightedResample(notsimiNegative, sampleSizes);
        similarPositive.addAll(similarNegative);
        similarPositive.addAll(notsimiPositive);
        similarPositive.addAll(notsimiNegative);
        return similarPositive;
    }

    /**
     * <p>
     * This is just my interpretation of the resampling. Details are missing from the paper.
     * </p>
     *
     * @param data
     *            data that is sampled
     * @param size
     *            desired size of the sample
     * @return sampled data
     */
    private Instances weightedResample(final Instances data, final int size) {
        final Instances resampledData = new Instances(data);
        resampledData.clear();
        double sumOfWeights = data.sumOfWeights();
        Random rand = new Random();
        while (resampledData.size() < size) {
            double randVal = rand.nextDouble() * sumOfWeights;
            double currentWeightSum = 0.0;
            for (int i = 0; i < data.size(); i++) {
                currentWeightSum += data.get(i).weight();
                if (currentWeightSum >= randVal) {
                    resampledData.add(data.get(i));
                    break;
                }
            }
        }

        return resampledData;
    }
}
