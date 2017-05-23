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

package de.ugoe.cs.cpdp.dataselection;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * A setwise data selection strategy based on the separatability of the training data from the test
 * data after Z. He, F. Peters, T. Menzies, Y. Yang: Learning from Open-Source Projects: An
 * Empirical Study on Defect Prediction. <br>
 * <br>
 * This is calculated through the error of a logistic regression classifier that tries to separate
 * the sets.
 * 
 * @author Steffen Herbold
 */
public class SeparatabilitySelection implements ISetWiseDataselectionStrategy {

    /**
     * size of the random sample that is drawn from both test data and training data
     */
    private final int sampleSize = 500;

    /**
     * number of repetitions of the sample drawing
     */
    private final int maxRep = 10;

    /**
     * number of neighbors that are selected
     */
    private int neighbors = 10;

    /**
     * Sets the number of neighbors that are selected.
     */
    @Override
    public void setParameter(String parameters) {
        if (!"".equals(parameters)) {
            neighbors = Integer.parseInt(parameters);
        }
    }

    /**
     * @see ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final Random rand = new Random(1);

        // calculate distances between testdata and traindata
        final double[] distances = new double[traindataSet.size()];

        int i = 0;
        for (Instances traindata : traindataSet) {
            double distance = 0.0;
            for (int rep = 0; rep < maxRep; rep++) {
                // sample instances
                Instances sample = new Instances(testdata);
                for (int j = 0; j < sampleSize; j++) {
                    Instance inst =
                        new DenseInstance(testdata.instance(rand.nextInt(testdata.numInstances())));
                    inst.setDataset(sample);
                    inst.setClassValue(1.0);
                    sample.add(inst);
                    inst = new DenseInstance(traindata
                        .instance(rand.nextInt(traindata.numInstances())));
                    inst.setDataset(sample);
                    inst.setClassValue(0.0);
                    sample.add(inst);
                }

                // calculate separation
                Evaluation eval;
                try {
                    eval = new Evaluation(sample);
                    eval.crossValidateModel(new Logistic(), sample, 5, rand);
                }
                catch (Exception e) {
                    throw new RuntimeException("cross-validation during calculation of separatability failed",
                                               e);
                }
                distance += eval.pctCorrect() / 100.0;
            }
            distances[i++] = 2 * ((distance / maxRep) - 0.5);
        }

        // select closest neighbors
        final double[] distancesCopy = Arrays.copyOf(distances, distances.length);
        Arrays.sort(distancesCopy);
        final double cutoffDistance = distancesCopy[neighbors];

        for (i = traindataSet.size() - 1; i >= 0; i--) {
            if (distances[i] > cutoffDistance) {
                traindataSet.remove(i);
            }
        }
    }
}
