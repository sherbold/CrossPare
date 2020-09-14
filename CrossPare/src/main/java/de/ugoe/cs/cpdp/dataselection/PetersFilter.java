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

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filter according to F. Peters, T. Menzies, and A. Marcus: Better Cross Company Defect Prediction
 * <br>
 * <br>
 * This filter does not work, the paper has been withdrawn.
 * 
 * @author Steffen Herbold
 */
@Deprecated
public class PetersFilter implements IPointWiseDataselectionStrategy {

    /*
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /*
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
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
            bugMatrix.delete();
        }
        final Attribute classAttribute = testdata.classAttribute();

        final double[][] testDoubles =
            new double[testdata.numInstances()][testdata.numAttributes()];
        for (int i = 0; i < testdata.numInstances(); i++) {
            Instance instance = testdata.instance(i);
            int tmp = 0;
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAttribute) {
                    testDoubles[i][tmp++] = instance.value(j);
                }
            }
        }

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

        final List<List<Integer>> fanList = new ArrayList<>(testdata.numInstances());
        for (int i = 0; i < testdata.numInstances(); i++) {
            fanList.add(new LinkedList<Integer>());
        }

        for (int i = 0; i < traindata.numInstances(); i++) {
            double minDistance = Double.MAX_VALUE;
            int minIndex = 0;
            for (int j = 0; j < testdata.numInstances(); j++) {
                double distance = MathArrays.distance(trainDoubles[i], testDoubles[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    minIndex = j;
                }
            }
            fanList.get(minIndex).add(i);
        }

        final SetUniqueList<Integer> selectedIndex =
            SetUniqueList.setUniqueList(new LinkedList<Integer>());
        for (int i = 0; i < testdata.numInstances(); i++) {
            double minDistance = Double.MAX_VALUE;
            int minIndex = -1;
            for (Integer j : fanList.get(i)) {
                double distance = MathArrays.distance(testDoubles[i], trainDoubles[j]);
                if (distance < minDistance && distance > 0.0d) {
                    minDistance = distance;
                    minIndex = j;
                }
            }
            if (minIndex != -1) {
                selectedIndex.add(minIndex);
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
