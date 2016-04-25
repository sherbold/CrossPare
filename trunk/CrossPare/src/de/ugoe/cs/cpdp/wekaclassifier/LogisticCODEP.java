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

package de.ugoe.cs.cpdp.wekaclassifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

/**
 * <p>
 * Implements COPEP with logistic regression.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class LogisticCODEP extends AbstractCODEP {

    /**
     * Default serialization ID.
     */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.wekaclassifier.AbstractCODEP#getCodepClassifier()
     */
    @Override
    protected Classifier getCodepClassifier() {
        return new Logistic();
    }

}
