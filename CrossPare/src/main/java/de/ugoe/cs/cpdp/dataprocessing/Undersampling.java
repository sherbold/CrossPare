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

import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * Implements undersampling, a strategy for handling bias in data. In case there are less positive
 * samples (i.e. defect-prone) samples in the data than negative samples (i.e. non-defect-prone),
 * the non-defect-prone entities are sampled such thatthe number of defect-prone and
 * non-defect-prone instances is the same afterwards.
 * 
 * @author Steffen Herbold
 */
public class Undersampling implements IProcessesingStrategy, ISetWiseProcessingStrategy {

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
     * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        for (SoftwareVersion trainversion : trainversionSet) {
            apply(testversion, trainversion);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances traindata = trainversion.getInstances();
        final int[] counts = traindata.attributeStats(traindata.classIndex()).nominalCounts;

        if (counts[1] < counts[0]) {
            SoftwareVersion trainversioncopy = new SoftwareVersion(trainversion);
            Instances bugmatrix = trainversion.getBugMatrix();
            List<Double> efforts = trainversion.getEfforts();
            List<Double> numBugs = trainversion.getNumBugs();

            Instances negatives = new Instances(traindata);
            Instances positives = new Instances(traindata);

            negatives.insertAttributeAt(new Attribute("instance_index"), 0);
            positives.insertAttributeAt(new Attribute("instance_index"), 0);
            for (int i=0; i<negatives.size(); i++) {
                negatives.get(i).setValue(0, i);
                positives.get(i).setValue(0, i);
            }

            for (int i = traindata.size() - 1; i >= 0; i--) {
                if (Double.compare(1.0, negatives.get(i).classValue()) == 0) {
                    negatives.remove(i);
                }
                if (Double.compare(0.0, positives.get(i).classValue()) == 0) {
                    positives.remove(i);
                }
            }

            Resample resample = new Resample();
            resample.setSampleSizePercent((100.0 * counts[1]) / counts[0]);
            try {
                resample.setInputFormat(negatives);
                negatives = Filter.useFilter(negatives, resample);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            traindata.clear();
            if (bugmatrix != null) {
                bugmatrix.clear();
            }
            if (efforts != null) {
                efforts.clear();
            }
            if (numBugs != null) {
                numBugs.clear();
            }
            for (int i = 0; i < negatives.size(); i++) {
                int index = (int)negatives.get(i).value(0);
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
            for (int i = 0; i < positives.size(); i++) {
                int index = (int)positives.get(i).value(0);
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

}
