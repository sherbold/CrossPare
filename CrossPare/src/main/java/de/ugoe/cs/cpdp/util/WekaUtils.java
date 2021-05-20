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

package de.ugoe.cs.cpdp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.ZeroR;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.WekaException;

/**
 * <p>
 * Collections of helper functions to work with Weka.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class WekaUtils {
	
	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * <p>
     * Data class for distance between instances within a data set based on their distributional
     * characteristics.
     * </p>
     * 
     * @author Steffen Herbold
     */
    public static class DistChar {

        /**
         * mean distance
         */
        public final double mean;

        /**
         * standard deviation
         */
        public final double std;

        /**
         * minimal value
         */
        public final double min;

        /**
         * maximal value
         */
        public final double max;

        /**
         * number of instances
         */
        public final int num;

        /**
         * <p>
         * Constructor. Creates a new DistChar object.
         * </p>
         *
         * @param mean
         *            mean distance between instances
         * @param std
         *            standard deviation of distances between instances
         * @param min
         *            minimal distance between instances
         * @param max
         *            maximal distance between instances
         * @param num
         *            number of instance
         */
        @SuppressWarnings("hiding")
        DistChar(double mean, double std, double min, double max, int num) {
            this.mean = mean;
            this.std = std;
            this.min = min;
            this.max = max;
            this.num = num;
        }
    }

    /**
     * Scaling value that moves the decimal point by 5 digets.
     */
    public final static double SCALER = 10000.0d;

    /**
     * <p>
     * Adoption of the Hamming difference to numerical values, i.e., basically a count of different
     * metric values.
     * </p>
     *
     * @param inst1
     *            first instance to be compared
     * @param inst2
     *            second instance to be compared
     * @return the distance
     */
    public static double hammingDistance(Instance inst1, Instance inst2) {
        double distance = 0.0;
        for (int j = 0; j < inst1.numAttributes(); j++) {
            if (j != inst1.classIndex()) {
                if (inst1.value(j) != inst2.value(j)) {
                    distance += 1.0;
                }
            }
        }
        return distance;
    }

    /**
     * <p>
     * Returns a double array of the values without the classification.
     * </p>
     *
     * @param instance
     *            the instance
     * @return double array
     */
    public static double[] instanceValues(Instance instance) {
        double[] values = new double[instance.numAttributes() - 1];
        int k = 0;
        for (int j = 0; j < instance.numAttributes(); j++) {
            if (j != instance.classIndex()) {
                values[k] = instance.value(j);
                k++;
            }
        }
        return values;
    }

    /**
     * <p>
     * Calculates the distributional characteristics of the distances the instances within a data
     * set have to each other.
     * </p>
     *
     * @param data
     *            data for which the instances are characterized
     * @return characteristics
     */
    public static DistChar datasetDistance(Instances data) {
        double distance;
        double sumAll = 0.0;
        double sumAllQ = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int numCmp = 0;
        int l = 0;
        double[] inst1 = new double[data.numAttributes() - 1];
        double[] inst2 = new double[data.numAttributes() - 1];
        EuclideanDistance euclideanDistance = new EuclideanDistance();
        for (int i = 0; i < data.numInstances(); i++) {
            l = 0;
            for (int k = 0; k < data.numAttributes(); k++) {
                if (k != data.classIndex()) {
                    inst1[l++] = data.instance(i).value(k);
                }
            }
            for (int j = 0; j < data.numInstances(); j++) {
                if (j != i) {
                    l = 0;
                    for (int k = 0; k < data.numAttributes(); k++) {
                        if (k != data.classIndex()) {
                            inst2[l++] = data.instance(j).value(k);
                        }
                    }
                    distance = euclideanDistance.compute(inst1, inst2);
                    sumAll += distance;
                    sumAllQ += distance * distance;
                    numCmp++;
                    if (distance < min) {
                        min = distance;
                    }
                    if (distance > max) {
                        max = distance;
                    }
                }
            }
        }
        double mean = sumAll / numCmp;
        double std = Math.sqrt((sumAllQ - (sumAll * sumAll) / numCmp) * (1.0d / (numCmp - 1)));
        return new DistChar(mean, std, min, max, data.numInstances());
    }

    /**
     * <p>
     * Calculates the distributional characteristics of the distances of a single attribute the
     * instances within a data set have to each other.
     * </p>
     *
     * @param data
     *            data for which the instances are characterized
     * @param index
     *            attribute for which the distances are characterized
     * @return characteristics
     */
    public static DistChar attributeDistance(Instances data, int index) {
        double distance;
        double sumAll = 0.0;
        double sumAllQ = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int numCmp = 0;
        double value1, value2;
        for (int i = 0; i < data.numInstances(); i++) {
            value1 = data.instance(i).value(index);
            for (int j = 0; j < data.numInstances(); j++) {
                if (j != i) {
                    value2 = data.instance(j).value(index);
                    distance = Math.abs(value1 - value2);
                    sumAll += distance;
                    sumAllQ += distance * distance;
                    numCmp++;
                    if (distance < min) {
                        min = distance;
                    }
                    if (distance > max) {
                        max = distance;
                    }
                }
            }
        }
        double mean = sumAll / numCmp;
        double std = Math.sqrt((sumAllQ - (sumAll * sumAll) / numCmp) * (1.0d / (numCmp - 1)));
        return new DistChar(mean, std, min, max, data.numInstances());
    }

    /**
     * <p>
     * Upscales the value of a single attribute. This is a workaround to get BayesNet running for
     * all data. Works on a copy of the training data, i.e., leaves the original data untouched.
     * </p>
     *
     * @param traindata
     *            data from which the attribute is upscaled.
     * @param attributeIndex
     *            index of the attribute
     * @return data with upscaled attribute
     */
    public static Instances upscaleAttribute(Instances traindata, int attributeIndex) {
        Instances traindataCopy = new Instances(traindata);
        for (int i = 0; i < traindata.size(); i++) {
            traindataCopy.get(i).setValue(attributeIndex,
                                          traindata.get(i).value(attributeIndex) * SCALER);
        }
        return traindataCopy;
    }

    /**
     * <p>
     * Wrapper function around the buildClassifier method of WEKA. The intend is to collect
     * workarounds required to get some WEKA classifiers running here.
     * </p>
     *
     * @param classifier
     *            the classifier that is trained
     * @param traindata
     *            the training data
     */
    public static Classifier buildClassifier(Classifier classifier, Instances traindata) {
        try {
            if (classifier == null) {
                LOGGER.warn(String.format("classifier null!"));
            }
            classifier.buildClassifier(traindata);
        }
        catch (IllegalArgumentException e) {
            if (classifier instanceof CVParameterSelection) {
                // in case of parameter selection, check if internal cross validation loop
                // is the problem
            	LOGGER.warn("error with CVParameterSelection training");
            	LOGGER.warn("trying without parameter selection...");
                Classifier internalClassifier = ((CVParameterSelection) classifier).getClassifier();
                classifier = buildClassifier(internalClassifier, traindata);
                LOGGER.warn("...success");
            }
            else if (classifier instanceof RBFNetwork) {
            	LOGGER.warn("Failure in RBFNetwork training. Checking if this is due to skewed training data with less than two instances in the minority class.");
                int countNoBug = traindata.attributeStats(traindata.classIndex()).nominalCounts[0];
                int countBug = traindata.attributeStats(traindata.classIndex()).nominalCounts[1];
                LOGGER.warn("trainsize: " + traindata.size() + "; numNoBug: " +
                    countNoBug + "; numBug: " + countBug);
                if (countNoBug <= 1 || countBug <= 1) {
                	LOGGER.warn("less than two instances in minority class");
                	LOGGER.warn("using ZeroR instead");
                    classifier = new ZeroR();
                    classifier = buildClassifier(classifier, traindata);
                }
                else {
                    throw new RuntimeException(e);
                }
            }
        }
        catch( WekaException e) {
        	if (e.getMessage() != null &&
        		e.getMessage().contains("Some instance weights are not equal to 1")) {
        		Instances traindataCopy = new Instances(traindata);
        		for(int i=0; i<traindataCopy.size(); i++ ) {
        			traindataCopy.get(i).setWeight(1.0d);
        		}
        		classifier = buildClassifier(classifier, traindataCopy);
        	}
        }
        catch (Exception e) {
            if (e.getMessage() != null &&
                e.getMessage().contains("Not enough training instances with class labels"))
            {
            	LOGGER.warn("failure due to lack of instances: " + e.getMessage());
            	LOGGER.warn("training ZeroR classifier instead");
                classifier = new ZeroR();
                try {
                    classifier.buildClassifier(traindata);
                }
                catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            else {
                throw new RuntimeException(e);
            }
        }
        return classifier;
    }

    /**
     * <p>
     * Makes the class attribute binary, in case it is currently numeric.
     * </p>
     *
     * @param data
     *            the data
     */
    public static void makeClassBinary(Instances data) {
        if (data.classAttribute().isNumeric()) {
            // create new nominal attribute
            final ArrayList<String> classAttVals = new ArrayList<>();
            classAttVals.add("0");
            classAttVals.add("1");
            final Attribute classAtt =
                new Attribute(data.classAttribute().name() + "Cls", classAttVals);
            data.insertAttributeAt(classAtt, data.numAttributes());
            for (Instance instance : data) {
                double value = instance.classValue() < 1.0 ? 0 : 1;
                instance.setValue(data.numAttributes() - 1, value);
            }
            // update class index and delete old class attribute
            int oldClassIndex = data.classIndex();
            data.setClassIndex(data.numAttributes() - 1);
            data.deleteAttributeAt(oldClassIndex);
        }
        else if (!data.classAttribute().isNominal()) {
            throw new RuntimeException("class attribute invalid: neither numeric nor nominal");
        }
    }
    
    /**
     * <p>
     * Makes a nominal class attribute numeric. The numeric values will just be an enumeration of the nominal classes, no real counts.
     * </p>
     *
     * @param data
     *            the data
     */
    public static void makeClassNumeric(Instances data) {
        if (data.classAttribute().isNominal()) {
        	LOGGER.warn("data only binary, i.e., numeric attribute will not be real counts, but still just 0, 1");
            // create new numeric attribute
            data.insertAttributeAt(new Attribute(data.classAttribute().name()+"Num"), data.numAttributes());
            for (Instance instance : data) {
                instance.setValue(data.numAttributes() - 1, instance.classValue());
            }
            // update class index and delete old class attribute
            int oldClassIndex = data.classIndex();
            data.setClassIndex(data.numAttributes() - 1);
            data.deleteAttributeAt(oldClassIndex);
        }
        else if (!data.classAttribute().isNumeric()) {
            throw new RuntimeException("class attribute invalid: neither numeric nor nominal");
        }
    }

    /**
     * Optimization of a classifiers' hyperparameters with differential evolution
     * <p>
     * Differential evolution is a genetic algorithm in which the hyperparameters get optimized by mutation, i.e.
     * they are manipulated in a random process and if the mutated version performs better it 'survives'.
     * The parameters of this function consists of the data to optimize on, the classifier and hyperparameter descriptions,
     * and control parameter for the optimization.
     *
     * @param traindata data set on which the optimization is done
     * @param clfClass weka class name of the classifier to train
     * @param fixedParamString String with fixed hyperparameters
     * @param tuneParams list of hyperparameters, which are to be tuned
     * @param mutationConstant differentiation constant (control parameter)
     * @param crossoverConstant probability of hyperparameters to mutate (control parameter)
     * @param populationSize size of population (control parameter)
     * @param numGenerations number of iterations (control parameter)
     * @return trained classifier with optimized hyperparameters
     */
    public static Classifier differentialEvolution(Instances traindata, String clfClass, String fixedParamString,
                                                   List<Hyperparameter> tuneParams, double mutationConstant,
                                                   double crossoverConstant, int populationSize, int numGenerations) {
        if(populationSize < 4){
            throw new RuntimeException(String.format("Error: The differential evolution algorithm needs at least a population of size 4, currently set to %d", populationSize));
        }
        if(numGenerations <= 0){
            throw new RuntimeException(String.format("Error: The set number of generations = %d is not a valid for the algorithm",numGenerations));
        }
        if(mutationConstant < 0.0 || mutationConstant > 1.0){
            throw new RuntimeException(String.format("Error: The set mutation constant = %f is not a valid for the algorithm",mutationConstant));
        }
        if(crossoverConstant < 0.0 || crossoverConstant > 1.0){
            throw new RuntimeException(String.format("Error: The set crossover constant = %f is not a valid for the algorithm",crossoverConstant));
        }
        Random rand = new Random();
        List<ClassifierScorePair> population = new ArrayList<>();
        for (int j = 0; j < populationSize; j++) {
            List<Hyperparameter> tuneParamsRand = new ArrayList<>();
            for (Hyperparameter hyperparam : tuneParams) {
                Hyperparameter copyHyperparam = new Hyperparameter(hyperparam);
                copyHyperparam.pickRandom();
                tuneParamsRand.add(copyHyperparam);
            }
            population.add(new ClassifierScorePair(clfClass, fixedParamString, tuneParamsRand, traindata));
        }
        int indexBest = 0;
        for(int j = 0; j < populationSize; j++){
            if((population.get(j).getScore() > population.get(indexBest).getScore())
                    || Double.isNaN(population.get(indexBest).getScore())){
                indexBest = j;
            }
        }
        for (int gen = 0; gen < numGenerations; gen++) {
            for (int j = 0; j < populationSize; j++) {
                int[] r = {0, 0, 0};
                r[0] = rand.nextInt(populationSize);
                while (r[0] == j) {
                    r[0] = rand.nextInt(populationSize);
                }
                r[1] = rand.nextInt(populationSize);
                while (r[1] == j || r[1] == r[0]) {
                    r[1] = rand.nextInt(populationSize);
                }
                r[2] = rand.nextInt(populationSize);
                while (r[2] == j || r[2] == r[0] || r[2] == r[1]) {
                    r[2] = rand.nextInt(populationSize);
                }
                int randomFixedMutation = rand.nextInt(tuneParams.size());
                List<Hyperparameter> mutatedParams = new ArrayList<>();
                for (int i = 0; i < tuneParams.size(); i++) {
                    if (rand.nextFloat() < crossoverConstant || randomFixedMutation == i) {
                        double value = population.get(r[2]).getParams().get(i).getValue() +
                                mutationConstant * (population.get(r[0]).getParams().get(i).getValue() -
                                        population.get(r[1]).getParams().get(i).getValue());
                        mutatedParams.add(new Hyperparameter(population.get(j).getParams().get(i), value));
                        if (mutatedParams.get(i).checkOutOfBound()) {
                            mutatedParams.get(i).pickRandom();
                        }
                    }
                    else {
                        mutatedParams.add(new Hyperparameter(population.get(j).getParams().get(i)));
                    }
                }
                ClassifierScorePair mutation = new ClassifierScorePair(population.get(j), mutatedParams, traindata);
                if(!Double.isNaN(mutation.getScore())){
                    if ((mutation.getScore() >= population.get(j).getScore()) || Double.isNaN(population.get(j).getScore())){
                        population.set(j, mutation);
                        if ((mutation.getScore() >= population.get(indexBest).getScore()) || (Double.isNaN(population.get(indexBest).getScore()))){
                            indexBest = j;
                        }
                    }
                }
            }
        }
        if(Double.isNaN(population.get(indexBest).getScore())){
            List<Hyperparameter> emptyTuneParams = new ArrayList<>();
            ClassifierScorePair defaultClassifier = new ClassifierScorePair(clfClass, fixedParamString, emptyTuneParams, traindata);
            return defaultClassifier.getClf();
        }
        else{
            return population.get(indexBest).getClf();
        }
    }

    /**
     * Contains the description of a hyperparameter and is a helper class for the differential evolution
     * <p>
     * Currently only supports numeric parameters (integer or float).
     * */
    public static class Hyperparameter {

        /**
         * Name of the parameter (has to match with the weka/sklearn keyword)
         */
        private final String name;

        /**
         * true: Parameter is treated as integer | false: Parameter is treated as float
         */
        private final boolean isInt;

        /**
         * Lower bound of the parameter for hyperparameter tuning
         */
        private final Double min;

        /**
         * Upper bound of the parameter for hyperparameter tuning
         */
        private final Double max;

        /**
         * Value of the parameter
         */
        private Double value;

        /**
         * Constructor for tunable parameters including the boundary values.
         */
        public Hyperparameter(String name, boolean isInt, double min, double max) {
            this.name = name;
            this.isInt = isInt;
            this.min = min;
            this.max = max;
            this.value = null;
        }

        /**
         * Copy constructor.
         */
        public Hyperparameter(Hyperparameter other) {
            this.name = other.name;
            this.isInt = other.isInt;
            this.min = other.min;
            this.max = other.max;
            this.value = other.value;
        }

        /**
         * Copy constructor with a new value setter.
         */
        public Hyperparameter(Hyperparameter other, double newValue) {
            this.name = other.name;
            this.isInt = other.isInt;
            this.min = other.min;
            this.max = other.max;
            this.setValue(newValue);
        }

        /**
         * @return the value of the hyperparameter
         */
        public Double getValue() {
            return this.value;
        }

        /**
         * sets the value of the hyperparameter, sensitive to integer or float type parameters.
         */
        public void setValue(double value) {
            if(this.isInt){
                this.value = (double) Math.round(value);
            }
            else {
                this.value = value;
            }
        }

        /**
         * @return the string representation of the hyperparameter as needed by the weka interface.
         */
        public String toString() {
            if (this.isInt) {
                return String.format("%s %d", this.name, Math.round(this.value));
            }
            return String.format("%s %.6f", this.name, this.value).replace(",", ".");
        }

        /**
         * sets the value to a random value between the boundaries
         */
        public void pickRandom() {
            Random rand = new Random();
            setValue(this.min + (this.max - this.min) * rand.nextFloat());
        }

        /**
         * @return true if the current value is out of the boundary values
         */
        public boolean checkOutOfBound() {
            return (this.value < this.min || this.value > this.max);
        }
    }

    /**
     * Helper class for the differential evolution algorithm.
     * Keeps a weka classifier object and additional information about the classifier,
     * including: class name, parameters for hyperparameter tuning, defined fixed parameters and performance score.
     */
    private static class ClassifierScorePair {

        /**
         * Weka class name of the classifier
         */
        private final String name;

        /**
         * true: if classifier is: ScikitLearnClassifier | false: otherwise
         */
        private final boolean isSklearn;

        /**
         * Weka classifier
         */
        private Classifier clf;

        /**
         * List of hyperparameters for hyperparameter tuning
         */
        private final List<Hyperparameter> tuneParams;

        /**
         * String with the fixed hyperparameters
         */
        private final String fixedParamsString;

        /**
         * Performance score of the classifier
         */
        private final double score;

        /**
         * Constructor which also sets up, evaluates and builds the classifier according to the
         * given hyperparameters on the given data set.
         */
        public ClassifierScorePair(String name, String fixedParamsString, List<Hyperparameter> tuneParams,
                                   Instances data) {
            this.name = name;
            this.isSklearn = this.name.equals("weka.classifiers.sklearn.ScikitLearnClassifier");
            this.tuneParams = tuneParams;
            this.fixedParamsString = fixedParamsString;
            this.clf = setupClassifier();
            this.score = evaluateClassifier(data);
            this.clf = buildClassifier(this.clf, data);
        }

        /**
         * Copy constructor with updated tunable parameters which sets up, evaluates and builds the classifier
         * according to the given hyperparameters on the given data set.
         */
        public ClassifierScorePair(ClassifierScorePair other, List<Hyperparameter> newTuneParameters, Instances data) {
            this.name = other.name;
            this.isSklearn = other.isSklearn;
            this.tuneParams = newTuneParameters;
            this.fixedParamsString = other.fixedParamsString;
            this.clf = setupClassifier();
            this.score = evaluateClassifier(data);
            this.clf = buildClassifier(this.clf, data);
        }

        /**
         * @return the classifier object
         */
        public Classifier getClf() {
            return clf;
        }

        /**
         * @return the performance score
         */
        public double getScore() {
            return score;
        }

        /**
         * @return the list of tuning hyperparameters
         */
        public List<Hyperparameter> getParams() {
            return tuneParams;
        }

        /**
         * Sets up a weka classifier for differential evolution
         *
         * @return classifier object
         */
        public Classifier setupClassifier() {
            List<String> allParamStrings = new ArrayList<>(Arrays.asList(this.fixedParamsString.split(" ")));
            for (Hyperparameter hyperparameter : this.tuneParams){
                if(isSklearn){
                    allParamStrings.add(hyperparameter.toString().replace(" ", "="));
                }
                else{
                    allParamStrings.addAll(Arrays.asList(hyperparameter.toString().split(" ")));
                }
            }
            String[] optionStrings;
            if(isSklearn){
                List<String> wekaParams = new ArrayList<>();
                List<String> sklearnParams = new ArrayList<>();
                for(String param : allParamStrings) {
                    if (param.contains("=")) {
                        sklearnParams.add(param);
                    } else {
                        wekaParams.add(param);
                    }
                }
                if(!sklearnParams.isEmpty()){
                    wekaParams.add("-parameters");
                    wekaParams.add(String.join(",", sklearnParams));
                }
                optionStrings = wekaParams.toArray(new String[0]);
            }
            else{
                optionStrings = allParamStrings.toArray(new String[0]);
            }
            Classifier clf = null;
            try {
                @SuppressWarnings("unchecked")
                Class<Classifier> c = (Class<Classifier>) Class.forName(this.name);
                clf = c.getDeclaredConstructor().newInstance();
                ((OptionHandler) clf).setOptions(optionStrings);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(String.format("Error: class not found: %s", e.toString()));
            } catch (InstantiationException e) {
                throw new RuntimeException(String.format("Error: Instantiation Exception: %s", e.toString()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("Error: Illegal Access Exception: %s", e.toString()));
            } catch (Exception e) {
                throw new RuntimeException(String.format("Error: Exception: %s", e.toString()));
            }
            return clf;
        }

        /**
         * Evaluates the classifier
         * <p>
         * Using 3-fold cross validation and Matthews Correlation Coefficient as performance measure.
         *
         * @param data data set to evaluate the classifier on
         * @return performance score (MCC)
         */
        private double evaluateClassifier(Instances data) {
            Evaluation eval;
            try {
                eval = new Evaluation(data);
                eval.crossValidateModel(this.clf, data, 3, new Random());
            } catch (Exception e) {
                throw new RuntimeException("error evaluating a classifier: " + e);
            }
            return eval.matthewsCorrelationCoefficient(1);
        }
    }
}
