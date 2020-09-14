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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
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
        // dummy, parameters not used
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public SoftwareVersion apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        return applyNeighborhoodFilter(testversion, trainversion);
    }

    /**
     * <p>
     * Applies the relevancy filter after Ryu et al.
     * </p>
     *
     * @param testversion
     *            version of the test data
     * @param traindata
     *            version of the training data
     * @return filtered version of the training data
     */
    @SuppressWarnings("boxing")
    private static SoftwareVersion applyNeighborhoodFilter(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances testdata = testversion.getInstances();
        Instances traindata = trainversion.getInstances();
        Instances bugMatrix = null;
        List<Double> efforts = null;
        List<Double> numBugs = null;
        if (trainversion.getEfforts() != null) {
            efforts = new ArrayList<>();
        }
        if (trainversion.getNumBugs() != null) {
            numBugs = new ArrayList<>();
        }
        if (trainversion.getBugMatrix() != null) {
            bugMatrix = new Instances(trainversion.getBugMatrix());
            bugMatrix.clear();
        }
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
            if (bugMatrix != null) {
                bugMatrix.add(trainversion.getBugMatrix().instance(index));
            }
            if (efforts != null) {
                efforts.add(trainversion.getEfforts().get(index));
            }
            if (numBugs != null) {
                numBugs.add(trainversion.getNumBugs().get(index));
            }

        }
        return new SoftwareVersion(trainversion.getDataset(), trainversion.getProject(), trainversion.getVersion(),
                selectedTraindata, bugMatrix, efforts, numBugs, trainversion.getReleaseDate(), null);
    }
}
