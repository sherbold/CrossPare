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

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Removes an attributes from all data sets using their name.
 * 
 * @author Steffen Herbold
 */
public class AttributeRemoval implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * names of the attributes to be removed (determined by {@link #setParameter(String)})
     */
    private String[] attributeNames = new String[] { };

    /**
     * Sets that attributes that will be removed. The string contains the blank-separated names of
     * the attributes to be removed. <br>
     * <br>
     * Note, that removal of attributes with blanks is currently not supported!
     * 
     * @param parameters
     *            string with the blank-separated attribute names
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null) {
            this.attributeNames = parameters.split(" ");
        }
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances testdata = testversion.getInstances();
        for (String attributeName : this.attributeNames) {
            for (int i = 0; i < testdata.numAttributes(); i++) {
                if (attributeName.equals(testdata.attribute(i).name())) {
                    testdata.deleteAttributeAt(i);
                    for (SoftwareVersion trainversion : trainversionSet) {
                        Instances traindata = trainversion.getInstances();
                        traindata.deleteAttributeAt(i);
                    }
                }
            }
        }
    }

    /**
     * @see IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances testdata = testversion.getInstances();
        Instances traindata = trainversion.getInstances();
        for (String attributeName : this.attributeNames) {
            for (int i = 0; i < testdata.numAttributes(); i++) {
                if (attributeName.equals(testdata.attribute(i).name())) {
                    testdata.deleteAttributeAt(i);
                    traindata.deleteAttributeAt(i);
                }
            }
        }
    }
}
