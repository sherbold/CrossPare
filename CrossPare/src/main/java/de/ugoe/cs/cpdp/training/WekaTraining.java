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




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * <p>
 * The first parameter is the trainer name, second parameter is class name. All subsequent
 * parameters are configuration parameters of the algorithms. Cross validation parameters always
 * come last and are prepended with -CVPARAM
 * </p>
 * XML Configurations for Weka Classifiers:
 * 
 * <pre>
 * {@code
 * <!-- examples -->
 * <trainer name="WekaTraining" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 * <trainer name="WekaTraining" param=
"Logistic weka.classifiers.functions.Logistic -R 1.0E-8 -M -1" />
 * }
 * </pre>
 * 
 */
public class WekaTraining extends WekaBaseTraining implements ITrainingStrategy {
	
	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    @Override
    public void apply(SoftwareVersion trainversion) {
        this.classifier = setupClassifier();
        if (this.classifier == null) {
            LOGGER.error("classifier of WekaTraining is null");
            throw new RuntimeException("classifier of WekaTraining is null");
        }
        this.classifier = WekaUtils.buildClassifier(this.classifier, trainversion.getInstances());
    }
}
