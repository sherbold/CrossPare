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

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements an attribute filter that is based on the information gain of each attribute after Z.
 * He, F. Peters, T. Menzies, Y. Yang: Learning from Open-Source Projects: An Empirical Study on
 * Defect Prediction. A logistic classifier is trained to separate a random sample of the training
 * data from a random sample of the test data. As standard, the best 50% of attributes are retained.
 * This ratio can be adjusted using the parameter of the filter (0.5 = 50%). <br>
 * <br>
 * Best means the least information gain, because this means that the attribute is similar in both
 * test and training data.
 * 
 * @author Steffen Herbold
 */
public class InformationGainFilter implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * size of the random sample that is drawn from both test data and training data
     */
    private final int sampleSize = 500;

    /**
     * ratio of features that is kept
     */
    private double featureRatio = 0.5;

    /**
     * Sets the feature ratio.
     * 
     * @param parameters
     *            feature ratio
     */
    @Override
    public void setParameter(String parameters) {
        if (!"".equals(parameters)) {
            featureRatio = Double.parseDouble(parameters);
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for (Instances traindata : traindataSet) {
            apply(testdata, traindata, false);
        }

    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        apply(testdata, traindata, true);
    }

    /**
     * Internal helper function for the application of the filter to both all data set as well as a
     * single data set.
     * 
     * @param testdata
     *            data of the target product
     * @param traindata
     *            data of the training product
     * @param removeFromTest
     *            defines whether the attributes shall be removed from the test data as well or not
     */
    private void apply(Instances testdata, Instances traindata, boolean removeFromTest) {
        final Random rand = new Random(1);
        final int removalNumber = (int) (featureRatio * (testdata.numAttributes() - 1));

        final int classIndex = testdata.classIndex();

        // sample instances
        final Instances sample = new Instances(testdata);
        for (int j = 0; j < sampleSize; j++) {
            Instance inst =
                new DenseInstance(testdata.instance(rand.nextInt(testdata.numInstances())));
            inst.setDataset(sample);
            inst.setClassValue(1.0);
            sample.add(inst);
            inst = new DenseInstance(traindata.instance(rand.nextInt(traindata.numInstances())));
            inst.setDataset(sample);
            inst.setClassValue(0.0);
            sample.add(inst);
        }

        final double[] gain = new double[sample.numAttributes()];

        final InfoGainAttributeEval gainEval = new InfoGainAttributeEval();
        try {
            gainEval.buildEvaluator(sample);
            for (int i = 0; i < testdata.numAttributes(); i++) {
                // if( sample.classAttribute().equals(sample.attribute(i)) ) {
                // gain[i] = 0.0;
                // } else {
                if (!sample.classAttribute().equals(sample.attribute(i))) {
                    gain[i] = gainEval.evaluateAttribute(i);
                }
            }
        }
        catch (Exception e) {
            // throw new RuntimeException("could not determine information gain for all attributes",
            // e);
            // ignore exception; it is caused by attributes that are extremely
        }

        // select best attributes
        final double[] gainCopy = Arrays.copyOf(gain, gain.length);
        Arrays.sort(gainCopy);
        final double cutoffGain = gainCopy[testdata.numAttributes() - removalNumber];

        for (int i = testdata.numAttributes() - 1; i >= 0; i--) {
            if (gain[i] >= cutoffGain && i != classIndex) {
                traindata.deleteAttributeAt(i);
                if (removeFromTest) {
                    testdata.deleteAttributeAt(i);
                }
            }
        }
    }

}
