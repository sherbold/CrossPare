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

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Helper class for normalization of data sets.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class NormalizationUtil {

    /**
     * <p>
     * Min-Max normalization to scale all data to the interval [0,1] (N1 in Transfer Defect Learning
     * by Nam et al.).
     * </p>
     *
     * @param data
     *            data that is normalized
     */
    public static void minMax(Instances data) {
        for (int j = 0; j < data.numAttributes(); j++) {
            if (data.classIndex() != j) {
                double min = data.attributeStats(j).numericStats.min;
                double max = data.attributeStats(j).numericStats.max;

                for (int i = 0; i < data.numInstances(); i++) {
                    Instance inst = data.instance(i);
                    double newValue = (inst.value(j) - min) / (max - min);
                    inst.setValue(j, newValue);
                }
            }
        }
    }

    /**
     * <p>
     * Z-Score normalization (N2 in Transfer Defect Learning by Nam et al.).
     * </p>
     *
     * @param data
     *            data that is normalized
     */
    public static void zScore(Instances data) {
        final double[] mean = new double[data.numAttributes()];
        final double[] std = new double[data.numAttributes()];

        // get means and stddevs of data
        for (int j = 0; j < data.numAttributes(); j++) {
            if (data.classIndex() != j) {
                mean[j] = data.meanOrMode(j);
                std[j] = Math.sqrt(data.variance(j));
            }
        }
        applyZScore(data, mean, std);
    }

    /**
     * <p>
     * Z-Score normalization using the mean and std of the training data (N3 in Transfer Defect
     * Learning by Nam et al.).
     * </p>
     *
     * @param testdata
     *            test data of the target product
     * @param traindata
     *            training data
     */
    public static void zScoreTraining(Instances testdata, Instances traindata) {
        final double[] mean = new double[testdata.numAttributes()];
        final double[] std = new double[testdata.numAttributes()];

        // get means of training
        for (int j = 0; j < traindata.numAttributes(); j++) {
            if (traindata.classIndex() != j) {
                mean[j] = traindata.meanOrMode(j);
                std[j] = Math.sqrt(traindata.variance(j));
            }
        }

        applyZScore(testdata, mean, std);
        applyZScore(traindata, mean, std);
    }

    /**
     * <p>
     * Z-Score normalization using the mean and std of the test data (N4 in Transfer Defect Learning
     * by Nam et al.).
     * </p>
     *
     * @param testdata
     *            test data of the target product
     * @param traindata
     *            training data
     */
    public static void zScoreTarget(Instances testdata, Instances traindata) {
        final double[] mean = new double[testdata.numAttributes()];
        final double[] std = new double[testdata.numAttributes()];

        // get means of testdata
        for (int j = 0; j < testdata.numAttributes(); j++) {
            if (testdata.classIndex() != j) {
                mean[j] = testdata.meanOrMode(j);
                std[j] = Math.sqrt(testdata.variance(j));
            }
        }

        applyZScore(testdata, mean, std);
        applyZScore(traindata, mean, std);
    }

    /**
     * <p>
     * Z-Score normalization using the mean and std of the test data (N4 in Transfer Defect Learning
     * by Nam et al.).
     * </p>
     *
     * @param testdata
     *            test data of the target product
     * @param traindataSet
     *            training data
     */
    public static void zScoreTarget(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final double[] mean = new double[testdata.numAttributes()];
        final double[] std = new double[testdata.numAttributes()];

        // get means of testdata
        for (int j = 0; j < testdata.numAttributes(); j++) {
            if (testdata.classIndex() != j) {
                mean[j] = testdata.meanOrMode(j);
                std[j] = Math.sqrt(testdata.variance(j));
            }
        }

        applyZScore(testdata, mean, std);
        for (Instances traindata : traindataSet) {
            applyZScore(traindata, mean, std);
        }
    }

    /**
     * <p>
     * Internal helper function
     * </p>
     */
    private static void applyZScore(Instances data, double[] mean, double[] std) {
        for (int i = 0; i < data.numInstances(); i++) {
            Instance instance = data.instance(i);
            for (int j = 0; j < data.numAttributes(); j++) {
                if (data.classIndex() != j) {
                    instance.setValue(j, instance.value(j) - mean[j] / std[j]);
                }
            }
        }
    }
}
