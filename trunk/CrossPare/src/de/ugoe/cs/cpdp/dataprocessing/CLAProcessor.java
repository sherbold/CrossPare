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

import org.apache.commons.math3.stat.descriptive.rank.Median;

import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * This processor implements the CLA strategy from the CLAMI paper at ASE 2014 be Nam et al. With
 * CLA, the original classification of the data is removed and instead a new classification is
 * created based on metric values that are higher than the median of the metric.
 * </p>
 * <p>
 * This can also be done for the test data (i.e., TestAsTraining data selection), as the original
 * classification is completely ignored. Hence, CLA is an approach for unsupervised learning.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class CLAProcessor implements IProcessesingStrategy {

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
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core. Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        applyCLA(traindata);
    }

    /**
     * Applies the CLA processor the the data.
     * 
     * @param data
     *            data to which the processor is applied
     */
    public void applyCLA(Instances data) {
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

        // finally modify the instances
        // drop the unclean instances
        for (int i = data.numInstances() - 1; i >= 0; i--) {
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
