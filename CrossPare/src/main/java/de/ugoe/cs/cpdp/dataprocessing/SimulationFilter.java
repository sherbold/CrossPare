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

package de.ugoe.cs.cpdp.dataprocessing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filter for the Repast Simulation of Software Projects.
 * 
 * Filters the training dataset in the following way: If 0 is no bug and 1 means there is a bug in
 * this artifact, then this filter filters the dataset in this way:
 * 
 * 10010111000101110101111011101 x--x-x-----x-x---x-x----x---x
 * 
 * The instances, which are marked with x in this graphic are included in the newly created dataset
 * and form the trainingsdataset.
 * 
 * @author Fabian Trautsch
 * 
 */

public class SimulationFilter implements IProcessesingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @SuppressWarnings("boxing")
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        SoftwareVersion trainversioncopy = new SoftwareVersion(trainversion);
        Instances traindata = trainversion.getInstances();
        Instances bugmatrix = trainversion.getBugMatrix();
        List<Double> efforts = trainversion.getEfforts();
        List<Double> numBugs = trainversion.getNumBugs();
        Instances newDataSet = new Instances(traindata);
        newDataSet.insertAttributeAt(new Attribute("instance_index"), 0);
        for (int i=0; i<newDataSet.size(); i++) {
            newDataSet.get(i).setValue(0, i);
        }
        traindata.delete();
        if (bugmatrix != null) {
            bugmatrix.delete();
        }
        if (efforts != null) {
            efforts.clear();
        }
        if (numBugs != null) {
            numBugs.clear();
        }

        HashMap<Double, Instance> artifactNames = new HashMap<>();

        // This is to add all data, where the first occurence of the file has a bug
        ArrayList<Double> firstOccurenceArtifactNames = new ArrayList<>();

        // Sort dataset (StateID is connected to the date of commit: Lower StateID
        // means earlier commit than a higher stateID)
        Attribute wekaAttribute = newDataSet.attribute("Artifact.Target.StateID");
        newDataSet.sort(wekaAttribute);

        /*
         * Logical summary: If there is an instance that dont have a bug, put it into the hashmap
         * (only unique values in there)
         * 
         * If there is an instance, that hava a bug look up if it is in the hashmap already (this
         * means: it does not had a bug before!): If this is true add it to a new dataset and remove
         * it from the hashmap, so that new changes from "nonBug" -> "bug" for this file can be
         * found.
         * 
         * If the instance has a bug and is not in the hashmap (this means: The file has a bug with
         * its first occurence or this file only has bugs and not an instance with no bug), then (if
         * it is not in the arrayList above) add it to the new dataset. This way it is possible to
         * get the first occurence of a file, which has a bug
         */
        for (int i = 0; i < newDataSet.numInstances(); i++) {
            Instance wekaInstance = newDataSet.instance(i);
            int index = (int)wekaInstance.value(0);

            double newBugLabel = wekaInstance.classValue();
            Attribute wekaArtifactName = newDataSet.attribute("Artifact.Name");
            Double artifactName = wekaInstance.value(wekaArtifactName);

            if (newBugLabel == 0.0 && artifactNames.keySet().contains(artifactName)) {
                artifactNames.put(artifactName, wekaInstance);
            }
            else if (newBugLabel == 0.0 && !artifactNames.keySet().contains(artifactName)) {
                artifactNames.put(artifactName, wekaInstance);
            }
            else if (newBugLabel == 1.0 && artifactNames.keySet().contains(artifactName)) {
                traindata.add(trainversioncopy.getInstances().get(index));
                if (bugmatrix != null) {
                    bugmatrix.add(trainversioncopy.getBugMatrix().get(index));
                }
                if (efforts != null) {
                    efforts.add(trainversioncopy.getEfforts().get(index));
                }
                if (numBugs != null) {
                    numBugs.add(trainversioncopy.getNumBugs().get(index));
                }
                artifactNames.remove(artifactName);
            }
            else if (newBugLabel == 1.0 && !artifactNames.keySet().contains(artifactName)) {
                if (!firstOccurenceArtifactNames.contains(artifactName)) {
                    traindata.add(trainversioncopy.getInstances().get(index));
                    if (bugmatrix != null) {
                        bugmatrix.add(trainversioncopy.getBugMatrix().get(index));
                    }
                    if (efforts != null) {
                        efforts.add(trainversioncopy.getEfforts().get(index));
                    }
                    if (numBugs != null) {
                        numBugs.add(trainversioncopy.getNumBugs().get(index));
                    }
                    firstOccurenceArtifactNames.add(artifactName);
                }
            }
        }

        // If we have a file, that never had a bug (this is, when it is NOT in the
        // new created dataset, but it is in the HashMap from above) add it to
        // the new dataset

        double[] artifactNamesinNewDataSet = traindata.attributeToDoubleArray(0);
        HashMap<Double, Instance> artifactNamesCopy = new HashMap<>(artifactNames);

        for (Double artifactName : artifactNames.keySet()) {

            for (int i = 0; i < artifactNamesinNewDataSet.length; i++) {
                if (artifactNamesinNewDataSet[i] == artifactName) {
                    artifactNamesCopy.remove(artifactName);
                }
            }
        }

        for (Double artifact : artifactNamesCopy.keySet()) {
            int index = (int)artifactNamesCopy.get(artifact).value(0);
            traindata.add(trainversioncopy.getInstances().get(index));
            if (bugmatrix != null) {
                bugmatrix.add(trainversioncopy.getBugMatrix().get(index));
            }
            if (efforts != null) {
                efforts.add(trainversioncopy.getEfforts().get(index));
            }
            if (numBugs != null) {
                numBugs.add(trainversioncopy.getNumBugs().get(index));
            }
        }

    }

}
