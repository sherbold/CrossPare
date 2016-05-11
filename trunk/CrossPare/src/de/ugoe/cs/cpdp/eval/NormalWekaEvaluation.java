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

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Implements the {@link AbstractWekaEvaluation} for evaluation on the test data.
 * 
 * @author Steffen Herbold
 * 
 */
public class NormalWekaEvaluation extends AbstractWekaEvaluation {

    /**
     * @see de.ugoe.cs.cpdp.eval.AbstractWekaEvaluation#createEvaluator(weka.core.Instances,
     *      weka.classifiers.Classifier)
     */
    @Override
    protected Evaluation createEvaluator(Instances testdata, Classifier classifier) {
        try {
            final Evaluation eval = new Evaluation(testdata);
            eval.evaluateModel(classifier, testdata);
            return eval;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
