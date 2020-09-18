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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.util.MathArrays;

import de.ugoe.cs.cpdp.util.ArrayUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filter according to B. Turhan, T. Menzies, A. Bener, and J. Die Stefano: On the relative value of
 * cross-company and within company defect prediction
 * 
 * @author Steffen Herbold
 */
public class TurhanFilter implements IPointWiseDataselectionStrategy {

    /**
     * number of neighbors that are selected
     */
    private int k = 10;

    /**
     * Sets the number of neighbors.
     * 
     * @param parameters
     *            number of neighbors
     */
    @Override
    public void setParameter(String parameters) {
        this.k = Integer.parseInt(parameters);
    }

    /**
     * @see IPointWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @SuppressWarnings("boxing")
    @Override
    public SoftwareVersion apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances traindata = trainversion.getInstances();
        Instances testdata = testversion.getInstances();
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
        final Attribute classAttribute = testdata.classAttribute();

        final List<Integer> selectedIndex = SetUniqueList.setUniqueList(new LinkedList<Integer>());

        final double[][] trainDoubles =
            new double[traindata.numInstances()][testdata.numAttributes()];

        for (int i = 0; i < traindata.numInstances(); i++) {
            Instance instance = traindata.instance(i);
            int tmp = 0;
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAttribute) {
                    trainDoubles[i][tmp++] = instance.value(j);
                }
            }
        }

        for (int i = 0; i < testdata.numInstances(); i++) {
            Instance testIntance = testdata.instance(i);
            double[] targetVector = new double[testdata.numAttributes() - 1];
            int tmp = 0;
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAttribute) {
                    targetVector[tmp++] = testIntance.value(j);
                }
            }

            double farthestClosestDistance = Double.MAX_VALUE;
            int farthestClosestIndex = 0;
            double[] closestDistances = new double[this.k];
            for (int m = 0; m < closestDistances.length; m++) {
                closestDistances[m] = Double.MAX_VALUE;
            }
            int[] closestIndex = new int[this.k];

            for (int n = 0; n < traindata.numInstances(); n++) {
                double distance = MathArrays.distance(targetVector, trainDoubles[n]);

                if (distance < farthestClosestDistance) {
                    closestIndex[farthestClosestIndex] = n;
                    closestDistances[farthestClosestIndex] = distance;

                    farthestClosestIndex = ArrayUtils.findMax(closestDistances);
                    farthestClosestDistance = closestDistances[farthestClosestIndex];
                }
            }
            for (int index : closestIndex) {
                selectedIndex.add(index);
            }
        }

        final Instances selected = new Instances(testdata);
        selected.delete();
        for (Integer i : selectedIndex) {
            selected.add(traindata.instance(i));
            if (bugMatrix != null) {
                bugMatrix.add(trainversion.getBugMatrix().instance(i));
            }
            if (efforts != null) {
                efforts.add(trainversion.getEfforts().get(i));
            }
            if (numBugs != null) {
                numBugs.add(trainversion.getNumBugs().get(i));
            }
        }
        return new SoftwareVersion(trainversion.getDataset(), trainversion.getProject(), trainversion.getVersion(),
                selected, bugMatrix, efforts, numBugs, trainversion.getReleaseDate(), null);
    }

}
