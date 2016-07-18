// Copyright 2016 Georg-August-Universität Göttingen, Germany
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

import java.util.Collections;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.util.MathArrays;

import de.ugoe.cs.cpdp.dataprocessing.MORPH;
import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * <p>
 * Implements LACE2 data privacy filter after Peters et al.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class LACE2 implements ISetWiseDataselectionStrategy {

    /**
     * percentage of data selected by the internal CLIFF.
     */
    private double percentage = 0.10;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            percentage = Double.parseDouble(parameters);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        Instances selectedData = new Instances(testdata);
        selectedData.clear();

        LinkedList<Instances> traindataCopy = new LinkedList<>(traindataSet);
        Collections.shuffle(traindataCopy);

        CLIFF cliff = new CLIFF();
        cliff.setParameter(Double.toString(percentage));
        MORPH morph = new MORPH();
        Median median = new Median();
        double minDist = Double.MIN_VALUE;

        for (Instances traindata : traindataCopy) {
            Instances cliffedData = cliff.applyCLIFF(traindata);
            if (minDist == Double.MIN_VALUE) {
                // determine distance for leader-follower algorithm
                Instances sample;
                if (traindata.size() > 100) {
                    Resample resample = new Resample();
                    resample.setSampleSizePercent(100.0 / traindata.size() * 100.0);
                    resample.setBiasToUniformClass(0.0);
                    resample.setNoReplacement(true);
                    try {
                        resample.setInputFormat(traindata);
                        sample = Filter.useFilter(traindata, resample);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    sample = new Instances(traindata);
                }
                double[] distances = new double[sample.size()];
                for (int i = 0; i < sample.size(); i++) {
                    Instance unlikeNeighbor = morph.getNearestUnlikeNeighbor(sample.get(i), sample);
                    distances[i] = MathArrays.distance(WekaUtils.instanceValues(sample.get(i)),
                                                       WekaUtils.instanceValues(unlikeNeighbor));
                }
                minDist = median.evaluate(distances);
            }
            for (int i = 0; i < cliffedData.size(); i++) {
                Instance unlikeNeighbor =
                    morph.getNearestUnlikeNeighbor(cliffedData.get(i), selectedData);
                if (unlikeNeighbor == null) {
                    selectedData.add(cliffedData.get(i));
                }
                else {
                    double distance =
                        MathArrays.distance(WekaUtils.instanceValues(cliffedData.get(i)),
                                            WekaUtils.instanceValues(unlikeNeighbor));
                    if (distance > minDist) {
                        morph.morphInstance(cliffedData.get(i), cliffedData);
                        selectedData.add(cliffedData.get(i));
                    }
                }
            }
        }
    }

}
