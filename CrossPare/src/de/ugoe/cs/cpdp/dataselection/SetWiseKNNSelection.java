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
     * @see de.ugoe.cs.cpdp.dataselection.SetWiseDataselectionStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final Instances data = normalizedCharacteristicInstances(testdata, traindataSet);

        final Set<Integer> selected = new HashSet<Integer>();
        for (int i = 0; i < k; i++) {
            int closestIndex = getClosest(data);

            selected.add(closestIndex);
            data.delete(closestIndex);
        }

        for (int i = traindataSet.size() - 1; i >= 0; i--) {
            if (selected.contains(i)) {
                traindataSet.remove(i);
            }
        }
    }

    /**
     * Helper method that determines the index of the instance with the smallest distance to the
     * first instance (index 0).
     * 
     * @param data
     *            data set
     * @return index of the closest instance
     */
    private int getClosest(Instances data) {
        double closestDistance = Double.MAX_VALUE;
        int closestIndex = 1;
        for (int i = 1; i < data.numInstances(); i++) {
            double distance =
                MathArrays.distance(data.instance(0).toDoubleArray(), data.instance(i)
                    .toDoubleArray());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestIndex = i;
            }
        }
        return closestIndex;
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
            k = Integer.parseInt(split[0]);
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
