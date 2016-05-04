// Copyright 2015 Georg-August-Universit�t G�ttingen, Germany
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

package de.ugoe.cs.cpdp.eval;

/**
 * <p>
 * Interface for result storages.
 * </p>
 * 
 * @author Steffen Herbold
 */
public interface IResultStorage {

    /**
     * <p>
     * Stores a new experiment result
     * </p>
     *
     * @param result
     *            result to be stored
     */
    public void addResult(ExperimentResult result);

    /**
     * <p>
     * Checks if a result is already contained in the storage.
     * </p>
     *
     * @param experimentName
     *            name of the experiment
     * @param productName
     *            name of the product
     * @return true of the results ofr the given product and experiment are contained in the result
     *         storage
     */
    public boolean containsResult(String experimentName, String productName);
}
