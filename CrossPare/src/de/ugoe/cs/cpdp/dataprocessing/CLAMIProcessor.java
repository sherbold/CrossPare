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

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

import org.apache.commons.math3.stat.descriptive.rank.Median;

import de.ugoe.cs.util.console.Console;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * This processor implements the CLAMI strategy from the CLAMI paper at ASE 2014 be Nam et al. With
 * CLAMI, the original classification of the data is removed and instead a new classification is
 * created based on metric values that are higher than the median of the metric. Afterwards, a
 * subset of the metrics is selected, where the violations of this median threshold is minimal.
 * Finally, all instances where the metric violations are not correct are dropped, leaving
 * noise-free data regarding the median threshold classification.
 * </p>
 * <p>
 * This can also be done for the test data (i.e., TestAsTraining data selection), as the original
 * classification is completely ignored. Hence, CLAMI is an approach for unsupervised learning.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class CLAMIProcessor implements IProcessesingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        applyCLAMI(testdata, traindata);
    }

    /**
     * <p>
     * Applies the CLAMI processor to the data. The test data is also required, in order to
     * guarantee a consistent metric set.
     * </p>
     *
     * @param testdata
     *            test data; the data is not modified, only metrics are dropped
     * @param data
     *            data to which the CLAMI processor is applied
     */
    public void applyCLAMI(Instances testdata, Instances data) {

        // first determine medians
        double[] medians = new double[data.numAttributes()];
        // get medians
        for (int j = 0; j < data.numAttributes(); j++) {
            if (j != data.classIndex()) {
                medians[j] = data.kthSmallestValue(j, (data.numInstances() + 1) >> 1);
            }
        }
        // now determine cluster number for each instance
        double[] clusterNumber = new double[data.numInstances()];
        for (int i = 0; i < data.numInstances(); i++) {
            int countHighValues = 0;
            Instance currentInstance = data.get(i);
            for (int j = 0; j < data.numAttributes(); j++) {
                if (j != data.classIndex()) {
                    if (currentInstance.value(j) > medians[j]) {
                        countHighValues++;
                    }
                }
            }
            clusterNumber[i] = countHighValues;
        }

        // determine median of cluster number
        Median m = new Median();
        double medianClusterNumber = m.evaluate(clusterNumber);

        // now we filter the metrics
        int[] numMetricViolations = new int[data.numAttributes()];
        for (int j = 0; j < data.numAttributes(); j++) {
            int currentViolations = 0;
            for (int i = 0; i < data.numInstances(); i++) {
                Instance currentInstance = data.get(i);
                if (j != data.classIndex()) {
                    if (clusterNumber[i] > medianClusterNumber) {
                        // "buggy"
                        if (currentInstance.value(j) <= medians[j]) {
                            currentViolations++;
                        }
                    }
                    else {
                        // "not buggy"
                        if (currentInstance.value(j) > medians[j]) {
                            currentViolations++;
                        }
                    }
                }
            }
            numMetricViolations[j] = currentViolations;
        }

        SortedSet<Integer> distinctViolationCounts = new TreeSet<>();
        for (int currentViolations : numMetricViolations) {
            distinctViolationCounts.add(currentViolations);
        }
        Iterator<Integer> violationCountInterator = distinctViolationCounts.iterator();

        int violationCutoff = violationCountInterator.next();
        // now we filter the data;
        // this is first tried with the metrics with fewest violations. if no buggy/bugfree
        // instances remain, this is repeated with the next metrics with second fewest violations,
        // and so on.
        // this part is a bit unclear from the description in the paper, but I confirmed with the
        // author that this is how they implemented it
        boolean[] cleanInstances = new boolean[data.numInstances()];
        int numCleanBuggyInstances = 0;
        int numCleanBugfreeInstances = 0;
        do {
            violationCutoff = violationCountInterator.next();
            cleanInstances = new boolean[data.numInstances()];
            numCleanBuggyInstances = 0;
            numCleanBugfreeInstances = 0;
            for (int i = 0; i < data.numInstances(); i++) {
                int currentViolations = 0;
                Instance currentInstance = data.get(i);
                for (int j = 0; j < data.numAttributes(); j++) {
                    if (j != data.classIndex() && numMetricViolations[j] == violationCutoff) {
                        if (clusterNumber[i] > medianClusterNumber) {
                            // "buggy"
                            if (currentInstance.value(j) <= medians[j]) {
                                currentViolations++;
                            }
                        }
                        else {
                            // "not buggy"
                            if (currentInstance.value(j) > medians[j]) {
                                currentViolations++;
                            }
                        }
                    }
                }
                if (currentViolations == 0) {
                    cleanInstances[i] = true;
                    if (clusterNumber[i] > medianClusterNumber) {
                        numCleanBuggyInstances++;
                    }
                    else {
                        numCleanBugfreeInstances++;
                    }
                }
                else {
                    cleanInstances[i] = false;
                }
            }
        }
        while (numCleanBuggyInstances == 0 || numCleanBugfreeInstances == 0);

        // output some interesting information to provide insights into the CLAMI model
        Console.traceln(Level.FINE, "Selected Metrics and Median-threshold: ");
        for (int j = 0; j < data.numAttributes(); j++) {
            if (j != data.classIndex() && numMetricViolations[j] == violationCutoff) {
                Console.traceln(Level.FINE, "\t" + data.attribute(j).name() + ": " + medians[j]);
            }
        }

        // finally modify the instances
        // drop the metrics (also from the testdata)
        for (int j = data.numAttributes() - 1; j >= 0; j--) {
            if (j != data.classIndex() && numMetricViolations[j] != violationCutoff) {
                data.deleteAttributeAt(j);
                testdata.deleteAttributeAt(j);
            }
        }
        // drop the unclean instances
        for (int i = data.numInstances() - 1; i >= 0; i--) {
            if (!cleanInstances[i]) {
                data.delete(i);
            }
            else {
                // set the classification
                if (clusterNumber[i] > medianClusterNumber) {
                    data.get(i).setClassValue(1.0d);
                }
                else {
                    data.get(i).setClassValue(0.0d);
                }
            }
        }
    }

}
