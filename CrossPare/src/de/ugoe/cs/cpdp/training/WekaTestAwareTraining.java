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

import java.util.logging.Level;

import de.ugoe.cs.cpdp.wekaclassifier.ITestAwareClassifier;
import de.ugoe.cs.util.console.Console;
import weka.classifiers.rules.ZeroR;
import weka.core.Instances;

/**
 * <p>
 * Trainer that allows classifiers access to the training data. Classifiers need to make sure that
 * they do not use the classification.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class WekaTestAwareTraining extends WekaBaseTraining implements ITestAwareTrainingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        this.classifier = setupClassifier();
        if (!(this.classifier instanceof ITestAwareClassifier)) {
            throw new RuntimeException("classifier must implement the ITestAwareClassifier interface in order to be used as TestAwareTrainingStrategy");
        }
        ((ITestAwareClassifier) this.classifier).setTestdata(testdata);
        try {
            if (this.classifier == null) {
                Console.traceln(Level.WARNING, String.format("classifier null!"));
            }
            this.classifier.buildClassifier(traindata);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Not enough training instances with class labels")) {
                Console.traceln(Level.SEVERE,
                                "failure due to lack of instances: " + e.getMessage());
                Console.traceln(Level.SEVERE, "training ZeroR classifier instead");
                this.classifier = new ZeroR();
                try {
                    this.classifier.buildClassifier(traindata);
                }
                catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            else {
                throw new RuntimeException(e);
            }
        }
    }
}
