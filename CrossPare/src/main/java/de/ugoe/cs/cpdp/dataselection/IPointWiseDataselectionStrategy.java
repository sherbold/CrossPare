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

import de.ugoe.cs.cpdp.IParameterizable;

import weka.core.Instances;

/**
 * Interface for pointwise data selection strategies.
 * 
 * @author Steffen Herbold
 */
public interface IPointWiseDataselectionStrategy extends IParameterizable {

    /**
     * Applies the data selection strategy.
     * 
     * @param testdata
     *            test data
     * @param traindata
     *            candidate training data
     * @return the selected training data
     */
    Instances apply(Instances testdata, Instances traindata);
}
