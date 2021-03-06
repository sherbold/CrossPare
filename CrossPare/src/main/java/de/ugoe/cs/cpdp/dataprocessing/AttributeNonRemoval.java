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

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Removes attributes from all data sets, except the one defined, using their name. 
 * 
 * @author Fabian Trautsch
 */
public class AttributeNonRemoval implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * names of the attributes to be kept (determined by {@link #setParameter(String)})
     */
    private ArrayList<String> attributeNames = new ArrayList<>();

    /**
     * Sets that attributes that will be kept. The string contains the blank-separated regular expressions of the
     * attributes to be kept. <br>
     * <br>
     * Note, that the regular expressions currently must not contain whitespaces.
     * 
     * @param parameters
     *            string with the blank-separated attribute names
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null) {
            String[] attributeNamesArray = parameters.split(" ");
            for (String attributeName : attributeNamesArray) {
                this.attributeNames.add(attributeName);
            }
        }
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances testdata = testversion.getInstances();
    	for (int i = testdata.numAttributes() - 1; i >= 0; i--) {
        	String curAttribute = testdata.attribute(i).name();
        	boolean hasMatch = false;
        	for( String regex : attributeNames ) {
        		if( curAttribute.matches(regex) ) {
        			hasMatch = true;
        		}
        	}
        	if( !hasMatch ) {
                testdata.deleteAttributeAt(i);
                for( SoftwareVersion trainversion : trainversionSet ) {
                    Instances traindata = trainversion.getInstances();
                	traindata.deleteAttributeAt(i);
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
        for (int i = testdata.numAttributes() - 1; i >= 0; i--) {
        	String curAttribute = testdata.attribute(i).name();
        	boolean hasMatch = false;
        	for( String regex : attributeNames ) {
        		if( curAttribute.matches(regex) ) {
        			hasMatch = true;
        		}
        	}
        	if( !hasMatch ) {
                testdata.deleteAttributeAt(i);
                traindata.deleteAttributeAt(i);
            }
        }
    }
}
