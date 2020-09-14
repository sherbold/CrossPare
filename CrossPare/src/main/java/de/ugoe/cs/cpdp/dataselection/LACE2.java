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
import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.util.MathArrays;

import de.ugoe.cs.cpdp.dataprocessing.MORPH;
import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Resample;

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
            this.percentage = Double.parseDouble(parameters);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances selectedData = new Instances(testversion.getInstances());
        selectedData.clear();

        LinkedList<SoftwareVersion> trainversionCopy = new LinkedList<>(trainversionSet);
        Collections.shuffle(trainversionCopy);

        CLIFF cliff = new CLIFF();
        cliff.setParameter(Double.toString(this.percentage));
        MORPH morph = new MORPH();
        Median median = new Median();
        double minDist = Double.MIN_VALUE;

        for (SoftwareVersion trainversion : trainversionCopy) {
            Instances traindata = trainversion.getInstances();
            Instances cliffedData = cliff.applyCLIFF(trainversion).getInstances();
            if (minDist == Double.MIN_VALUE) {
                // determine distance for leader-follower algorithm
                Instances sample;
                if (traindata.size() > 100) {
                    int countNoBug;
                    int countBug;
                    Random rand = new Random();
                    do {
                        Resample resample = new Resample();
                        resample.setSampleSizePercent(100.0 / traindata.size() * 100.0);
                        resample.setNoReplacement(true);
                        resample.setRandomSeed(rand.nextInt()); // otherwise seed not random
                        try {
                            resample.setInputFormat(traindata);
                            sample = Filter.useFilter(traindata, resample);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        countNoBug = sample.attributeStats(sample.classIndex()).nominalCounts[0];
                        countBug = sample.attributeStats(sample.classIndex()).nominalCounts[1];
                    }
                    while (countNoBug < 1 || countBug < 1); // check if both classes are present
                }
                else {
                    sample = new Instances(traindata);
                }
                double[] distances = new double[sample.size()];
                for (int i = 0; i < sample.size(); i++) {
                    Instance unlikeNeighbor = MORPH.getNearestUnlikeNeighbor(sample.get(i), sample);
                    distances[i] = MathArrays.distance(WekaUtils.instanceValues(sample.get(i)),
                                                       WekaUtils.instanceValues(unlikeNeighbor));
                }
                minDist = median.evaluate(distances);
            }
            for (int i = 0; i < cliffedData.size(); i++) {
                Instance unlikeNeighbor =
                    MORPH.getNearestUnlikeNeighbor(cliffedData.get(i), selectedData);
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
