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
     * @param classifierName
     *            name of the first classifier in the configuration
     * @return number of contained resultsfor the given product and experiment
     */
    public int containsResult(String experimentName, String productName, String classifierName);

    /**
     * <p>
     * Checks if a heterogeneous result is already contained in the storage.
     * </p>
     *
     * @param experimentName
     *            name of the experiment
     * @param productName
     *            name of the product
     * @param classifierName
     *            name of the first classifier in the configuration
     * @param trainProductName
     *            name of the trainProduct
     *
     * @return number of contained resultsfor the given product and experiment
     */
    public int containsHeterogeneousResult(String experimentName,
                                           String productName,
                                           String classifierName,
                                           String trainProductName);
}
