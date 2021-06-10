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

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;

import weka.classifiers.sklearn.ScikitLearnClassifier;
import weka.core.Instances;

/**
 * <p>
 * Wrapper to always remove the model from Python after training to prevent memory leakages.
 * </p>
 *
 * @author Steffen Tunkel
 */
public class SklearnWrapper extends ScikitLearnClassifier {

    /** Default serial ID */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     *
     * @see weka.classifiers.bayes.BayesNet#buildClassifier(weka.core.Instances)
     */
    @SuppressWarnings("boxing")
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        Field f1 = this.m_learner.getClass().getDeclaredField("m_removeModelFromPyPostTraining");
        FieldUtils.writeField(f1, this.m_learner, true, true);
        super.buildClassifier(traindata);
    }
}
