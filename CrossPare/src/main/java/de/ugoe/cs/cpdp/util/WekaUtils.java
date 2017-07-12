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
import java.util.logging.Level;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

import de.ugoe.cs.util.console.Console;
import weka.classifiers.Classifier;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.ZeroR;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Collections of helper functions to work with Weka.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class WekaUtils {

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
                    inst1[l] = data.instance(i).value(k);
                }
            }
            for (int j = 0; j < data.numInstances(); j++) {
                if (j != i) {
                    l = 0;
                    for (int k = 0; k < data.numAttributes(); k++) {
                        if (k != data.classIndex()) {
                            inst2[l] = data.instance(j).value(k);
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
                Console.traceln(Level.WARNING, String.format("classifier null!"));
            }
            classifier.buildClassifier(traindata);
        }
        catch (IllegalArgumentException e) {
            if (classifier instanceof CVParameterSelection) {
                // in case of parameter selection, check if internal cross validation loop
                // is the problem
                Console.traceln(Level.WARNING, "error with CVParameterSelection training");
                Console.traceln(Level.WARNING, "trying without parameter selection...");
                Classifier internalClassifier = ((CVParameterSelection) classifier).getClassifier();
                classifier = buildClassifier(internalClassifier, traindata);
                Console.traceln(Level.WARNING, "...success");
            }
            else if (classifier instanceof RBFNetwork) {
                Console
                    .traceln(Level.WARNING,
                             "Failure in RBFNetwork training. Checking if this is due to too small and skewed training data.");
                int countNoBug = traindata.attributeStats(traindata.classIndex()).nominalCounts[0];
                int countBug = traindata.attributeStats(traindata.classIndex()).nominalCounts[1];
                Console.traceln(Level.WARNING, "trainsize: " + traindata.size() + "; numNoBug: " +
                    countNoBug + "; numBug: " + countBug);
                if (traindata.size() <= 10 && (countNoBug <= 1 || countBug <= 1)) {
                    Console
                        .traceln(Level.WARNING,
                                 "only one instance in minority class and less than 10 instances");
                    Console.traceln(Level.WARNING, "using ZeroR instead");
                    classifier = new ZeroR();
                    classifier = buildClassifier(classifier, traindata);
                }
                else {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (Exception e) {
            if (e.getMessage() != null &&
                e.getMessage().contains("Not enough training instances with class labels"))
            {
                Console.traceln(Level.SEVERE,
                                "failure due to lack of instances: " + e.getMessage());
                Console.traceln(Level.SEVERE, "training ZeroR classifier instead");
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
            Console.traceln(Level.WARNING, "data only binary, i.e., numeric attribute will not be real counts, but still just 0, 1");
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
}
