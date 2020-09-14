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

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Median as reference transformation after Carmargo Cruz and Ochimizu: Towards Logistic Regression
 * Models for Predicting Fault-prone Code across Software Projects <br>
 * <br>
 * For each attribute value x, the new value is x + (median of the test data - median of the current
 * project)
 * 
 * @author Steffen Herbold
 */
public class MedianAsReference implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(weka.core.de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances testdata = testversion.getInstances();
        final Attribute classAttribute = testdata.classAttribute();
        final double[] median = new double[testdata.numAttributes()];

        // test and train have the same number of attributes
        Attribute traindataClassAttribute;
        double[] currentmedian = new double[testdata.numAttributes()];

        // get medians
        for (int j = 0; j < testdata.numAttributes(); j++) {
            if (testdata.attribute(j) != classAttribute) {
                median[j] = testdata.kthSmallestValue(j, (testdata.numInstances() + 1) >> 1); // (>>2
                                                                                              // ->
                                                                                              // /2)
            }
        }

        // preprocess training data
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
            // get median of current training set
            traindataClassAttribute = traindata.classAttribute();
            for (int j = 0; j < traindata.numAttributes(); j++) {
                if (traindata.attribute(j) != traindataClassAttribute &&
                    traindata.attribute(j).isNumeric())
                {
                    currentmedian[j] =
                        traindata.kthSmallestValue(j, (traindata.numInstances() + 1) >> 1); // (>>2
                                                                                            // ->
                                                                                            // /2)
                }
            }
            for (int i = 0; i < traindata.numInstances(); i++) {
                Instance instance = traindata.instance(i);
                for (int j = 0; j < traindata.numAttributes(); j++) {
                    if (traindata.attribute(j) != classAttribute &&
                        traindata.attribute(j).isNumeric())
                    {
                        instance.setValue(j, instance.value(j) + (median[j] - currentmedian[j]));
                    }
                }
            }
        }
    }

    /**
     * @see IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances testdata = testversion.getInstances();
        Instances traindata = trainversion.getInstances();
        final Attribute classAttribute = testdata.classAttribute();
        final Attribute traindataClassAttribute = traindata.classAttribute();
        final double[] median = new double[testdata.numAttributes()];

        // test and train have the same number of attributes
        double[] currentmedian = new double[testdata.numAttributes()];

        // get medians
        for (int j = 0; j < testdata.numAttributes(); j++) {
            if (testdata.attribute(j) != classAttribute) {
                median[j] = testdata.kthSmallestValue(j, (testdata.numInstances() + 1) >> 1); // (>>2
                                                                                              // ->
                                                                                              // /2)
            }
        }

        // get median of current training set
        for (int j = 0; j < traindata.numAttributes(); j++) {
            if (traindata.attribute(j) != traindataClassAttribute &&
                traindata.attribute(j).isNumeric())
            {
                currentmedian[j] =
                    traindata.kthSmallestValue(j, (traindata.numInstances() + 1) >> 1); // (>>2 ->
                                                                                        // /2)
            }
        }

        // preprocess training data
        for (int i = 0; i < traindata.numInstances(); i++) {
            Instance instance = traindata.instance(i);
            for (int j = 0; j < traindata.numAttributes(); j++) {
                if (traindata.attribute(j) != classAttribute &&
                    traindata.attribute(j).isNumeric())
                {
                    instance.setValue(j, instance.value(j) + (median[j] - currentmedian[j]));
                }
            }
        }
    }

}
