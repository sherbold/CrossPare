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
 * Resamples the data with WEKA {@link Resample} to have a uniform distribution among all classes.
 * 
 * @author Steffen Herbold
 */
public class Resampling implements IProcessesingStrategy, ISetWiseProcessingStrategy {

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
        SoftwareVersion trainversioncopy = new SoftwareVersion(trainversion);
        Instances traindata = trainversion.getInstances();
        Instances bugmatrix = trainversion.getBugMatrix();
        List<Double> efforts = trainversion.getEfforts();
        List<Double> numBugs = trainversion.getNumBugs();
        Instances indexed = new Instances(traindata);
        indexed.insertAttributeAt(new Attribute("instance_index"), 0);
        for (int i=0; i<indexed.size(); i++) {
            indexed.get(i).setValue(0, i);
        }
        Resample resample = new Resample();
        resample.setSampleSizePercent(100);
        resample.setBiasToUniformClass(1.0);

        Instances traindataSample;
        try {
            resample.setInputFormat(indexed);
            traindataSample = Filter.useFilter(indexed, resample);
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
        for (int i = 0; i < traindataSample.size(); i++) {
            int index = (int)traindataSample.get(i).value(0);
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
