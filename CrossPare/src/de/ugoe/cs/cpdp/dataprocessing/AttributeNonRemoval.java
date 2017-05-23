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
    private ArrayList<String> attributeNames = new ArrayList<String>();

    /**
     * Sets that attributes that will be kept. The string contains the blank-separated names of the
     * attributes to be kept. <br>
     * <br>
     * Note, that keeping of attributes with blanks is currently not supported!
     * 
     * @param parameters
     *            string with the blank-separated attribute names
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null) {
            String[] attributeNamesArray = parameters.split(" ");
            for (String attributeName : attributeNamesArray) {
                attributeNames.add(attributeName);
            }
        }
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for (String attributeName : attributeNames) {
            for (int i = 0; i < testdata.numAttributes(); i++) {
                if (!attributeName.equals(testdata.attribute(i).name())) {
                    testdata.deleteAttributeAt(i);
                    for (Instances traindata : traindataSet) {
                        traindata.deleteAttributeAt(i);
                    }
                }
            }
        }
    }

    /**
     * @see IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        for (int i = testdata.numAttributes() - 1; i >= 0; i--) {
            if (!attributeNames.contains(testdata.attribute(i).name())) {
                testdata.deleteAttributeAt(i);
                traindata.deleteAttributeAt(i);
            }
        }
    }
}
