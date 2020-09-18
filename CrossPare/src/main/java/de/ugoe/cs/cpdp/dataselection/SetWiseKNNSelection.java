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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.util.MathArrays;

import de.ugoe.cs.cpdp.util.ArrayUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Filter based on the k-nearest neighbor (KNN) algorithm S. Herbold: Training data selection for
 * cross-project defect prediction
 * 
 * @author Steffen Herbold
 */
public class SetWiseKNNSelection extends AbstractCharacteristicSelection {

    /**
     * number of neighbors selected
     */
    private int k = 1;

    /**
     * @see ISetWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @SuppressWarnings("boxing")
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        final Instances data = normalizedCharacteristicInstances(testversion, trainversionSet);

        final Set<Integer> selected = getClosestK(data, this.k);
        for (int i = trainversionSet.size() - 1; i >= 0; i--) {
            if (!selected.contains(i + 1)) {  // traindata starts from data[1] but from trainversionSet[0]
                trainversionSet.remove(i);
            }
        }
    }

    /**
     * Helper method that determines the indices of the k instances with the smallest distance to the
     * first instance (index 0).
     * 
     * @param data
     *            data set
     * @return index of the closest instance
     */
    private static Set<Integer> getClosestK(Instances data, int k) {
        double farthestClosestDistance = Double.MAX_VALUE;
        int farthestClosestIndex = 0;
        double[] closestDistances = new double[k];
        for (int m = 0; m < closestDistances.length; m++) {
            closestDistances[m] = Double.MAX_VALUE;
        }
        int[] closestIndex = new int[k];

        for (int n = 1; n < data.numInstances(); n++) {
            double distance = MathArrays.distance(data.instance(0).toDoubleArray(), data.instance(n).toDoubleArray());

            if (distance < farthestClosestDistance) {
                closestIndex[farthestClosestIndex] = n;
                closestDistances[farthestClosestIndex] = distance;

                farthestClosestIndex = ArrayUtils.findMax(closestDistances);
                farthestClosestDistance = closestDistances[farthestClosestIndex];
            }
        }

        final Set<Integer> selected = new HashSet<>();
        for (int index : closestIndex) {
            selected.add(index);
        }
        return selected;
    }

    /**
     * Sets the number of neighbors followed by the distributional characteristics, the values are
     * separated by blanks.
     * 
     * @see AbstractCharacteristicSelection#setParameter(String)
     */
    @Override
    public void setParameter(String parameters) {
        if (!"".equals(parameters)) {
            final String[] split = parameters.split(" ");
            this.k = Integer.parseInt(split[0]);
            String str = "";
            for (int i = 1; i < split.length; i++) {
                str += split[i];
                if (i < split.length - 1) {
                    str += " ";
                }
            }
            super.setParameter(str);
        }
    }
}
