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

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * Uses the test data as training data.
 * 
 * @author Steffen Herbold
 * 
 */
public class TestAsTraining implements ISetWiseDataselectionStrategy {

    /**
     * no parameters
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        traindataSet.clear();
        traindataSet.add(new Instances(testdata));
    }

}
