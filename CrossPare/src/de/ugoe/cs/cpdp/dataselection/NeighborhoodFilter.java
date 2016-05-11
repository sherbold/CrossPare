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

import java.util.TreeSet;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.core.Instances;

/**
 * <p>
 * Relevancy filter after Ryu et al., 2015b.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class NeighborhoodFilter implements IPointWiseDataselectionStrategy {

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
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public Instances apply(Instances testdata, Instances traindata) {
        return applyNeighborhoodFilter(testdata, traindata);
    }

    /**
     * <p>
     * Applies the relevancy filter after Ryu et al.
     * </p>
     *
     * @param testdata test data 
     * @param traindata training data
     * @return filtered trainind data
     */
    private Instances applyNeighborhoodFilter(Instances testdata, Instances traindata) {
        TreeSet<Integer> selectedInstances = new TreeSet<>();
        for (int i = 0; i < testdata.size(); i++) {
            double minHam = Double.MAX_VALUE;
            for (int j = 0; j < traindata.size(); j++) {
                double distance = WekaUtils.hammingDistance(testdata.get(i), traindata.get(j));
                if (distance < minHam) {
                    minHam = distance;
                }
            }
            for (int j = 0; j < traindata.size(); j++) {
                double distance = WekaUtils.hammingDistance(testdata.get(i), traindata.get(j));
                if (distance <= minHam) {
                    selectedInstances.add(j);
                }
            }
        }
        Instances selectedTraindata = new Instances(testdata);
        selectedTraindata.clear();
        for (Integer index : selectedInstances) {
            selectedTraindata.add(traindata.instance(index));
        }
        return selectedTraindata;
    }
}
