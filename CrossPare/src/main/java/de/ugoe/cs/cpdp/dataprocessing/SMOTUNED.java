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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Instance;



/**
 * Implements SMOTUNED filter (Synthetic Minority Over-sampling Technique - with parameter Tuning)
 * <p>
 * The implementation follows the paper of Agrawal and Menzies from 2018 (doi: 10.1145/3180155.3180197),
 * except differences in the core SMOTE implementation, which is oriented at Chawla et al. (doi.org/10.1613/jair.953).
 *
 * @author Steffen Tunkel
 */
public class SMOTUNED implements IProcessesingStrategy, ISetWiseProcessingStrategy {

    /**
     * frontier size in a generation
     */
    final int sizeFrontier = 10;

    /**
     * crossover probability for the differential evolution
     */
    final double crossoverProbability = 0.3;

    /**
     * differential weight for the differential evolution
     */
    final double differentialWeight = 0.7;

    /**
     * minimum number of neighbors for SMOTE
     */
    final int minNumNeighbors = 1;

    /**
     * maximum number of neighbors for SMOTE
     */
    final int maxNumNeighbors = 20;

    /**
     * set of percentages of additional instances to create with SMOTE
     */
    final int[] percSmoteToGenerate = {50,100,200,400};

    /**
     * maximum Minkowski order for the distance calculation in SMOTE
     */
    final int maxMinkowskiOrder = 5;

    /**
     * maximal ratio of the final amount of majority class instances to the overall instances (1.0 = no undersampling)
     */
    final double undersampleRate = 0.5;

    /**
     * Parameters are fixed. String is ignored.
     *
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        for (SoftwareVersion trainversion : trainversionSet) {
            apply(testversion, trainversion);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances traindata = trainversion.getInstances();
        SMOTE smote = differentialEvolution(new Instances(traindata));
        Instances indexedData = smote.applySMOTE(new Instances(traindata));
        updateSoftwareVersion(trainversion, indexedData);
    }

    /**
     * Updates the training software version.
     *
     * @param version original training software version
     * @param indexedData new training data with indices
     */
    private static void updateSoftwareVersion(SoftwareVersion version, Instances indexedData){
        final int numIndexAttr = 2;
        Instances newData = new Instances(indexedData);
        for (int i=0; i< numIndexAttr; i++){
            newData.deleteAttributeAt(0);
        }
        Random rand = new Random();
        SoftwareVersion versionCopy = new SoftwareVersion(version);
        Instances traindata = version.getInstances();
        Instances bugmatrix = version.getBugMatrix();
        List<Double> efforts = version.getEfforts();
        List<Double> numBugs = version.getNumBugs();

        traindata.clear();
        if (bugmatrix != null) {
            bugmatrix.clear();
        }
        if (efforts != null) {
            efforts.clear();
        }
        if (numBugs != null) {
            numBugs.clear();
        }

        for (int i = 0; i < indexedData.size(); i++) {
            int origIdx = (int)indexedData.get(i).value(0);
            int secondIdx = (int)indexedData.get(i).value(1);
            if(secondIdx < 0){
                traindata.add(versionCopy.getInstances().get(origIdx));
                if (bugmatrix != null) {
                    bugmatrix.add(versionCopy.getBugMatrix().get(origIdx));
                }
                if (efforts != null) {
                    efforts.add(versionCopy.getEfforts().get(origIdx));
                }
                if (numBugs != null) {
                    numBugs.add(versionCopy.getNumBugs().get(origIdx));
                }
            }
            else{
                traindata.add(newData.get(i));
                int randomPick;
                if(rand.nextInt(2) == 0){
                    randomPick = origIdx;
                }
                else{
                    randomPick = secondIdx;;
                }
                if (bugmatrix != null) {
                    bugmatrix.add(versionCopy.getBugMatrix().get(randomPick));
                }
                if (efforts != null) {
                    double diffEffort = versionCopy.getEfforts().get(secondIdx) - versionCopy.getEfforts().get(origIdx);
                    double synthEffort = versionCopy.getEfforts().get(origIdx) + Math.round(diffEffort / 2.0);
                    efforts.add(synthEffort);
                }
                if (numBugs != null) {
                    numBugs.add(versionCopy.getNumBugs().get(randomPick));
                }
            }
        }
    }

    /**
     * Executes the differential evolution (tuning of SMOTE)
     * <p>
     * Differential evolution is the technique used in SMOTUNED to update the smote parameter.
     *
     * @param data original trainings data
     * @return best performing SMOTE filter object
     */
    private SMOTE differentialEvolution(Instances data){
        List<SMOTE> frontier = randomFrontier();
        SmoteScorePair bestSMOTE = new SmoteScorePair(new SMOTE(frontier.get(0)), data);
        Random rand = new Random();
        int lives = 1;
        while(lives > 0){
            lives -= 1;
            List<SMOTE> tempFrontier = new ArrayList<>();
            for(int i=0; i<frontier.size(); i++){
                SmoteScorePair oldSMOTE = new SmoteScorePair(new SMOTE(frontier.get(i)), data);
                SMOTE x = frontier.get(rand.nextInt(frontier.size()));
                SMOTE y = frontier.get(rand.nextInt(frontier.size()));
                SMOTE z = frontier.get(rand.nextInt(frontier.size()));
                int tempNumNeighbors;
                if(Math.random() < this.crossoverProbability){
                    tempNumNeighbors = (int) Math.round(x.getK() + this.differentialWeight * (y.getK() - z.getK()));
                    if(tempNumNeighbors < 1 || tempNumNeighbors > 20){
                        tempNumNeighbors = oldSMOTE.getSmote().getK();
                    }
                }
                else{
                    tempNumNeighbors = oldSMOTE.getSmote().getK();
                }

                int tempSmotePercentage;
                if(Math.random() < this.crossoverProbability){
                    tempSmotePercentage = (int) Math.round(x.getM() + this.differentialWeight * (y.getM() - z.getM()));
                    int diffPercentage = 1000;
                    int pickedPercentage = 0;
                    for(int possiblePercentage:percSmoteToGenerate){
                        if(Math.abs(tempSmotePercentage - possiblePercentage) < diffPercentage){
                            diffPercentage = Math.abs(tempSmotePercentage - possiblePercentage);
                            pickedPercentage = possiblePercentage;
                        }
                    }
                    tempSmotePercentage = pickedPercentage;
                    if(tempSmotePercentage <= 0){
                        tempSmotePercentage = oldSMOTE.getSmote().getM();
                    }
                }
                else{
                    tempSmotePercentage = oldSMOTE.getSmote().getM();
                }

                double tempMinkowskiOrder;
                if(Math.random() < this.crossoverProbability){
                    tempMinkowskiOrder = Math.round((x.getMinkowskiOrder() + this.differentialWeight *
                            (y.getMinkowskiOrder() - z.getMinkowskiOrder()))*10)/10.0;
                    if(tempMinkowskiOrder <= 0){
                        tempMinkowskiOrder = oldSMOTE.getSmote().getMinkowskiOrder();
                    }
                }
                else{
                    tempMinkowskiOrder = oldSMOTE.getSmote().getMinkowskiOrder();
                }
                SmoteScorePair newSMOTE = new SmoteScorePair(new SMOTE(tempNumNeighbors, tempSmotePercentage,
                        tempMinkowskiOrder, this.undersampleRate), data);
                if (newSMOTE.getScore() < oldSMOTE.getScore()){
                    newSMOTE = oldSMOTE;
                }
                tempFrontier.add(newSMOTE.getSmote());
                if (newSMOTE.getScore() > bestSMOTE.getScore()){
                    bestSMOTE = newSMOTE;
                    lives += 1;
                }
            }
            frontier = tempFrontier;
        }
        return bestSMOTE.getSmote();
    }

    /**
     * Creates a new frontier for SMOTE parameters.
     *
     * @return list of SMOTE objects
     */
    private List<SMOTE> randomFrontier(){
        List<SMOTE> frontier = new ArrayList<>();
        Random rand = new Random();
        for(int i=0; i<this.sizeFrontier; i++){
            int numNeighbors = rand.nextInt(this.maxNumNeighbors) + this.minNumNeighbors;
            int smotePercentage = this.percSmoteToGenerate[rand.nextInt(this.percSmoteToGenerate.length)];
            double minkowskiOrder = (rand.nextInt(maxMinkowskiOrder*10)/10.0) + 0.1;
            frontier.add(new SMOTE(numNeighbors, smotePercentage, minkowskiOrder, this.undersampleRate));
        }
        return frontier;
    }

    /**
     * Helper class for the assessment of SMOTE filter objects.
     */
    private static class SmoteScorePair implements Comparable<SMOTUNED.SmoteScorePair> {

        /**
         * score of the SMOTE filter
         */
        private final double score;

        /**
         * SMOTE filter instance
         */
        private final SMOTE smote;


        @SuppressWarnings("hiding")
        public SmoteScorePair(SMOTE smote, Instances data) {
            this.smote = smote;
            this.score = evaluateSMOTE(smote, data);
        }

        @SuppressWarnings("hiding")
        public SmoteScorePair(SMOTE smote, double score) {
            this.score = score;
            this.smote = smote;
        }

        /**
         * @return the score of the SMOTE filter
         */
        public double getScore() { return score; }

        /**
         * @return the SMOTE instance
         */
        public SMOTE getSmote() { return smote; }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(SMOTUNED.SmoteScorePair other) {
            return Double.compare(score, other.score);
        }

        /**
         * Evaluates the performance of SMOTE filter a specific data set.
         * <p>
         * The SMOTE filter is applied on the given data set. Afterwards the performance is assessed
         * with a 3-fold cross validation using a random forest classifier. The measured metric is the
         * Matthews Correlation Coefficient (MCC).
         *
         * @param smote SMOTE filter object to assess
         * @param data trainings data
         * @return score on the smote data (MCC)
         */
        private static double evaluateSMOTE(SMOTE smote, Instances data){
            final int ignoredAttributes = 2; // 'instance_index' and 'synthetic_index'
            Instances smoteData = smote.applySMOTE(new Instances(data));
            for(int i=0; i < ignoredAttributes; i++){
                smoteData.deleteAttributeAt(0);
            }
            Classifier clf = new RandomForest();
            try {
                clf.buildClassifier(smoteData);
            } catch (Exception e) {
                throw new RuntimeException("error building a classifier for the evaluation of a SMOTE filter in SMOTUNED: " + e);
            }
            Evaluation eval;
            try {
                eval = new Evaluation(smoteData);
                eval.crossValidateModel(clf, smoteData, 3, new Random(42));
            } catch (Exception e) {
                throw new RuntimeException("error evaluating a classifier for the evaluation of a SMOTE filter in SMOTUNED: " + e);
            }
            return eval.matthewsCorrelationCoefficient(1);
        }
    }

    /**
     * Implements the SMOTE (Synthetic Minority Over-sampling Technique) algorithm.
     * <p>
     * Should lead to better class balance by synthesizing new elements for the minority class (defective artifacts).
     * Additionally to pure SMOTE an undersampling of the majority class can be applied by setting
     * the undersampleRatio smaller than 1.
     * This implementation follows the description by Chawla et al. from 2002 (doi.org/10.1613/jair.953).
     */
    private static class SMOTE{

        /**
         * Number of nearest neighbors regarded by the algorithm
         */
        private int numNeighbors; // numNeighbors

        /**
         * Percentage of synthetic instances to create. Also ratio between minority and majority instances.
         */
        private final int smotePercentage; // smotePercentage

        /**
         * Minkowski order for the distance calculation
         */
        private final double minkowskiOrder; // minkowskiOrder

        /**
         * maximal ratio of the final amount of majority class instances to the overall instances (1.0 = no undersampling)
         */
        private final double undersampleRatio;

        public SMOTE(int numNeighbors, int smotePercentage, double minkowskiOrder, double undersampleRatio) {
            if(numNeighbors <= 0 || smotePercentage <= 0 || minkowskiOrder <= 0 || undersampleRatio <= 0){
                throw new RuntimeException("Error creating a SMOTE object. A parameter is not positive.");
            }
            if(smotePercentage > 100 && smotePercentage % 100 != 0){
                throw new RuntimeException("Error creating a SMOTE object. The SMOTE ratio is not completely divisible by 100 and not smaller than 100.");
            }
            this.numNeighbors = numNeighbors;
            this.smotePercentage = smotePercentage;
            this.minkowskiOrder = minkowskiOrder;
            this.undersampleRatio = undersampleRatio;
        }

        public SMOTE(SMOTE other) {
            this.numNeighbors = other.numNeighbors;
            this.smotePercentage = other.smotePercentage;
            this.minkowskiOrder = other.minkowskiOrder;
            this.undersampleRatio = other.undersampleRatio;
        }

        /**
         * @return the number of neighbors
         */
        public int getK() { return numNeighbors; }

        /**
         * @return the number of synthetic examples / desired ratio between minority and majority instances
         */
        public int getM() { return smotePercentage; }

        /**
         * @return the Minkowski order
         */
        public double getMinkowskiOrder() { return minkowskiOrder; }

        /**
         * Applies the SMOTE algorithm to the data set.
         * <p>
         * Additionally introduces two index attributes to the instances.
         * In the case of an unchanged minority or majority element the 'instance_index' is the index
         * in the original data set and the 'synthetic_index' is set to -1.
         * For synthesized elements the 'instance_index' and 'synthetic_index' are the indices of its two
         * root elements in the original data set.
         *
         * @param data original training data
         * @return training data manipulated by SMOTE with additional indices
         */
        private Instances applySMOTE(Instances data){
            Random rand = new Random();

            Instances indexed = new Instances(data);
            indexed.insertAttributeAt(new Attribute("instance_index"), 0);
            for (int i=0; i<indexed.size(); i++) {
                indexed.get(i).setValue(0, i);
            }
            indexed.insertAttributeAt(new Attribute("synthetic_index"), 1);
            for (int i=0; i<indexed.size(); i++) {
                indexed.get(i).setValue(1, -1);
            }
            Instances minority = new Instances(indexed, 0);
            Instances majority = new Instances(indexed, 0);
            for(int i=0; i<indexed.size(); i++){
                if (indexed.get(i).classValue() > 0.0) {
                    minority.add(indexed.get(i));
                } else {
                    majority.add(indexed.get(i));
                }
            }
            final int sizeMinority = minority.size();
            if (sizeMinority < 2){
                throw new RuntimeException("error applying SMOTE: SMOTE needs at least 2 defective Instances.");
            }
            // reduce amount of neighbors if numNeighbors bigger than minority size
            if (this.numNeighbors >= sizeMinority){
                this.numNeighbors = sizeMinority - 1;
            }
            List<Integer> synthList = new ArrayList<>();
            for(int i=0; i<sizeMinority; i++){
                synthList.add(i);
            }
            if(this.smotePercentage < 100){
                while(this.smotePercentage/100.0 * sizeMinority < synthList.size()){
                    synthList.remove(rand.nextInt(synthList.size()));
                }
            }
            Instances newSamples = new Instances(indexed, 0);
            for(Integer synthIndex: synthList){
                Instances syntheticInstances = createSyntheticInstances(minority, minority.get(synthIndex));
                newSamples.addAll(syntheticInstances);
            }
            minority.addAll(newSamples);
            while(majority.size() > (majority.size()+minority.size())*this.undersampleRatio){
                majority.remove(rand.nextInt(majority.size()));
            }

            indexed.clear();
            indexed.addAll(minority);
            indexed.addAll(majority);
            return indexed;
        }

        /**
         * Creates synthetic instances for the SMOTE algorithm.
         * <p>
         * First finds the nearest neighbors of the picked reference instance. The used distance order
         * and the amount of nearest neighbors are set by the attributes of the SMOTE class.
         * Then synthesizes one or multiple times a new minority class instance by randomly choosing a nearest neighbor
         * and setting all attributes of the new synthetic instance as a random value between the reference instance
         * attribute's value and the neighbor instance attribute's value.
         *
         * @param minority instances of the minority class (defective artifacts)
         * @param  pickedInstance instance is one specific member of the minority class
         * @return synthetic instances
         */
        private Instances createSyntheticInstances(Instances minority, Instance pickedInstance){
            Random rand = new Random();
            List<NeighborDistancePair> neighbors = new ArrayList<>();
            for (Instance instance : minority) {
                double distance = minkowskiDistance(pickedInstance, instance);
                neighbors.add(new NeighborDistancePair(distance, instance));
            }
            Collections.sort(neighbors);
            neighbors.remove(0); // closest neighbor is the picked instance itself

            int synthCounter;
            if(this.smotePercentage < 100){
                synthCounter = 1;
            }
            else{
                synthCounter = this.smotePercentage / 100;
            }
            Instances syntheticInstances;
            if(minority.size() >= synthCounter){
                syntheticInstances = new Instances(minority, 0, synthCounter);
            }
            else{
                syntheticInstances = new Instances(minority);
                while(syntheticInstances.size() < synthCounter){
                    syntheticInstances.add((Instance) syntheticInstances.get(0).copy());
                }
            }
            while(synthCounter > 0){
                synthCounter -= 1;
                int randNeighborIndex = rand.nextInt(this.numNeighbors);
                List<Double> diff = diffInstances(pickedInstance, neighbors.get(randNeighborIndex).getInstance());
                syntheticInstances.get(synthCounter).setValue(0, pickedInstance.value(0));
                syntheticInstances.get(synthCounter).setValue(1,
                        neighbors.get(randNeighborIndex).getInstance().value(0));
                for(int attributeIndex=2; attributeIndex<diff.size(); attributeIndex++){
                    double newValue = pickedInstance.value(attributeIndex) + Math.random() * diff.get(attributeIndex);
                    syntheticInstances.get(synthCounter).setValue(attributeIndex, newValue);
                }
            }
            return syntheticInstances;
        }

        /**
         * Calculates the Minkowski distance between two instances.
         *
         * @param firstInstance instance containing index attributes
         * @param  secondInstance instance containing index attributes
         * @return Minkowski distance
         */
        private double minkowskiDistance(Instance firstInstance, Instance secondInstance){
            final int ignoredAttributes = 2; // 'instance_index' and 'synthetic_index'
            double distance = 0.0;
            for (int i=ignoredAttributes; i< firstInstance.numAttributes(); i++){
                distance += Math.pow(Math.abs(firstInstance.value(i) - secondInstance.value(i)), this.minkowskiOrder);
            }
            return Math.pow(distance, 1.0/this.minkowskiOrder);
        }

        /**
         * Calculates the differences of all attributes for two instances.
         *
         * @param firstInstance Instance whose attribute values are used as reference
         * @param  secondInstance Instance whose attribute values are subtracted from the other
         * @return List with difference of each attribute
         */
        private List<Double> diffInstances(Instance firstInstance, Instance secondInstance){
            List<Double> difference = new ArrayList<>();
            for (int i=0; i< firstInstance.numAttributes(); i++){
                difference.add(firstInstance.value(i) - secondInstance.value(i));
            }
            return difference;
        }

        /**
         * Helper class for the distance comparison of neighbors.
         */
        private static class NeighborDistancePair implements Comparable<NeighborDistancePair> {

            /**
             * Distance of the instance to the reference instance
             */
            private final double distance;

            /**
             * Instance for which the distance to the reference instance is stored
             */
            private final Instance instance;

            @SuppressWarnings("hiding")
            public NeighborDistancePair(double distance, Instance instance) {
                this.distance = distance;
                this.instance = instance;
            }

            /**
             * @return the distance
             */
            public double getDistance() {
                return distance;
            }

            /**
             * @return the instance
             */
            public Instance getInstance() {
                return instance;
            }

            /*
             * (non-Javadoc)
             *
             * @see java.lang.Comparable#compareTo(java.lang.Object)
             */
            @Override
            public int compareTo(NeighborDistancePair other) {
                if (distance > other.distance) {
                    return 1;
                }
                if (distance < other.distance) {
                    return -1;
                }
                return 0;
            }
        }
    }
}
