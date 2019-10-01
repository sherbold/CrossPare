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
     * @see ISetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
    	testdata.insertAttributeAt(new Attribute("instance_index"), 0);
    	for (int i=0; i<testdata.size(); i++) {
    		testdata.get(i).setValue(0, i);
    	}
        for (Instances traindata : traindataSet) {
        	traindata.insertAttributeAt(new Attribute("instance_index"), 0);
        	for (int i=0; i<traindata.size(); i++) {
        		traindata.get(i).setValue(0, i);
        	}
        }
    }

    /**
     * @see IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
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
