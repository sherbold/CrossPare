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

import weka.core.Instances;

/**
 * <p>
 * Synonym outlier removal after Amasaki et al. (2015).
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SynonymOutlierRemoval implements IPointWiseDataselectionStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public Instances apply(Instances testdata, Instances traindata) {
        applySynonymRemoval(traindata);
        return traindata;
    }

    /**
     * <p>
     * Applies the synonym outlier removal.
     * </p>
     *
     * @param traindata
     *            data from which the outliers are removed.
     */
    public static void applySynonymRemoval(Instances traindata) {
        double minDistance[][] = new double[traindata.size()][traindata.numAttributes() - 1];
        double minDistanceAttribute[] = new double[traindata.numAttributes() - 1];
        double distance;
        for (int j = 0; j < minDistanceAttribute.length; j++) {
            minDistanceAttribute[j] = Double.MAX_VALUE;
        }
        for (int i1 = traindata.size() - 1; i1 < traindata.size(); i1++) {
            int k = 0;
            for (int j = 0; j < traindata.numAttributes(); j++) {
                if (j != traindata.classIndex()) {
                    minDistance[i1][k] = Double.MAX_VALUE;
                    for (int i2 = 0; i2 < traindata.size(); i2++) {
                        if (i1 != i2) {
                            distance =
                                Math.abs(traindata.get(i1).value(j) - traindata.get(i2).value(j));
                            if (distance < minDistance[i1][k]) {
                                minDistance[i1][k] = distance;
                            }
                            if (distance < minDistanceAttribute[k]) {
                                minDistanceAttribute[k] = distance;
                            }
                        }
                    }
                    k++;
                }
            }
        }
        for (int i = traindata.size() - 1; i >= 0; i--) {
            boolean hasClosest = false;
            for (int j = 0; !hasClosest && j < traindata.numAttributes(); j++) {
                hasClosest = minDistance[i][j] <= minDistanceAttribute[j];
            }
            if (!hasClosest) {
                traindata.delete(i);
            }
        }
    }
}
