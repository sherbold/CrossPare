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

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.cpdp.wekaclassifier.ITestAwareClassifier;

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
     * @see de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        this.classifier = setupClassifier();
        if (!(this.classifier instanceof ITestAwareClassifier)) {
            throw new RuntimeException("classifier must implement the ITestAwareClassifier interface in order to be used as TestAwareTrainingStrategy");
        }
        ((ITestAwareClassifier) this.classifier).setTestdata(testversion.getInstances());
        this.classifier = WekaUtils.buildClassifier(this.classifier, trainversion.getInstances());
    }
}
