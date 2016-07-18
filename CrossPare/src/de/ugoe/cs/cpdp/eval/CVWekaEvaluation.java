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

import java.io.PrintStream;
import java.util.Random;

import org.apache.commons.io.output.NullOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Implements the {@link AbstractWekaEvaluation} for 10-fold cross validation.
 * 
 * @author Steffen Herbold
 */
public class CVWekaEvaluation extends AbstractWekaEvaluation {

    /*
     * @see de.ugoe.cs.cpdp.eval.AbstractWekaEvaluation#createEvaluator(weka.core.Instances,
     *      weka.classifiers.Classifier)
     */
    @Override
    protected Evaluation createEvaluator(Instances testdata, Classifier classifier) {
        PrintStream errStr = System.err;
        System.setErr(new PrintStream(new NullOutputStream()));
        try {
            final Evaluation eval = new Evaluation(testdata);
            eval.crossValidateModel(classifier, testdata, 10, new Random(1));
            return eval;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            System.setErr(errStr);
        }
    }

}
