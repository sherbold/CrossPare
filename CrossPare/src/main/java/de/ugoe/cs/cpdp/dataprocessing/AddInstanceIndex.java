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
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Adds a new {@link Attribute} called instance_index that is the numerical index of the instance in the data.
 * 
 * This should never be used with a regular classifier!
 * 
 * @author Steffen Herbold
 */
public class AddInstanceIndex implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances testdata = testversion.getInstances();
        testdata.insertAttributeAt(new Attribute("instance_index"), 0);
    	for (int i=0; i<testdata.size(); i++) {
    		testdata.get(i).setValue(0, i);
    	}
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
        	traindata.insertAttributeAt(new Attribute("instance_index"), 0);
        	for (int i=0; i<traindata.size(); i++) {
        		traindata.get(i).setValue(0, i);
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
    	testdata.insertAttributeAt(new Attribute("instance_index"), 0);
    	for (int i=0; i<testdata.size(); i++) {
    		testdata.get(i).setValue(0, i);
    	}
    	traindata.insertAttributeAt(new Attribute("instance_index"), 0);
    	for (int i=0; i<traindata.size(); i++) {
    		traindata.get(i).setValue(0, i);
    	}
    }
}
