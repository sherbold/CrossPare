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

import java.io.PrintStream;
import java.util.logging.Level;

import org.apache.commons.io.output.NullOutputStream;

import de.ugoe.cs.util.console.Console;
import weka.core.Instances;

/**
 * Programmatic WekaTraining
 * 
 * first parameter is Trainer Name. second parameter is class name
 * 
 * all subsequent parameters are configuration params (for example for trees) Cross Validation
 * params always come last and are prepended with -CVPARAM
 * 
 * XML Configurations for Weka Classifiers:
 * 
 * <pre>
 * {@code
 * <!-- examples -->
 * <trainer name="WekaTraining" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 * <trainer name="WekaTraining" param="Logistic weka.classifiers.functions.Logistic -R 1.0E-8 -M -1" />
 * }
 * </pre>
 * 
 */
public class WekaTraining extends WekaBaseTraining implements ITrainingStrategy {

    @Override
    public void apply(Instances traindata) {
        PrintStream errStr = System.err;
        System.setErr(new PrintStream(new NullOutputStream()));
        try {
            if (classifier == null) {
                Console.traceln(Level.WARNING, String.format("classifier null!"));
            }
            classifier.buildClassifier(traindata);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            System.setErr(errStr);
        }
    }
}
