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
import java.util.Arrays;
import java.util.Collections;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filters the given dataset for an nominal attribute. Every instance that has a value of the
 * defined values of the given nominal attribute is removed.
 * 
 * 
 * (e.g. param="CONFIDECNE low middle"; all instances where the "CONFIDENCE" attribute value is
 * "low" or "middle" are removed from the dataset)
 */

public class NominalAttributeFilter implements IProcessesingStrategy {

    private String nominalAttributeName = "";
    private String[] nominalAttributeValues = new String[] { };

    /**
     * Sets the nominal attribute name (first parameter) and the nominal attribute values (other
     * parameters), which should be removed from the dataset.
     * 
     * @param parameters
     *            string with the blank-separated parameters (first parameter is the name of the
     *            nominal attribute, everything else are the values)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null) {
            String[] parameter = parameters.split(" ");
            nominalAttributeName = parameter[0];
            nominalAttributeValues = Arrays.copyOfRange(parameter, 1, parameter.length);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        int indexOfConfidenceAttribute = -1;

        // Find index of the named confidence attribute to filter for
        for (int i = 0; i < traindata.numAttributes(); i++) {
            if (traindata.attribute(i).name().equals(nominalAttributeName)) {
                indexOfConfidenceAttribute = i;
            }
        }

        // if it was not found return
        if (indexOfConfidenceAttribute == -1) {
            return;
        }

        // Find index of nominal values
        Attribute confidenceAttribute = traindata.attribute(indexOfConfidenceAttribute);
        ArrayList<Object> nominalValuesOfConfidenceAttribute =
            Collections.list(confidenceAttribute.enumerateValues());
        ArrayList<Double> indexOfnominalAttributeValues = new ArrayList<Double>();

        for (int k = 0; k < nominalValuesOfConfidenceAttribute.size(); k++) {
            for (String attributeValue : nominalAttributeValues) {
                if (((String) nominalValuesOfConfidenceAttribute.get(k)).equals(attributeValue)) {
                    indexOfnominalAttributeValues.add((double) k);
                }
            }
        }

        // Go through all instances and check if nominal attribute equals
        for (int j = traindata.numInstances() - 1; j >= 0; j--) {
            Instance wekaInstance = traindata.get(j);

            // delete all instances where nominal attribute has the value of one of the parameter
            if (indexOfnominalAttributeValues.contains(wekaInstance
                .value(indexOfConfidenceAttribute)))
            {
                traindata.delete(j);
            }
        }
    }

}
