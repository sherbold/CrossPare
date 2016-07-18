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

package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;

/**
 * <p>
 * Common interface for all training strategies that internally use the {@link Classifier} from WEKA. 
 * </p>
 * 
 * @author Steffen Herbold
 */
public interface IWekaCompatibleTrainer extends ITrainer {

    /**
     * <p>
     * returns the WEKA classifier
     * </p>
     *
     * @return the classifier
     */
    Classifier getClassifier();

    /**
     * <p>
     * returns the name of the training strategy
     * </p>
     *
     * @return the name
     */
    String getName();
}
