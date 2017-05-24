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

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.util.console.Console;
import weka.classifiers.bayes.BayesNet;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Wrapper to max BayesNet to deal with a problem with Discretize
 * </p>
 * 
 * @author Steffen Herbold
 */
public class BayesNetWrapper extends BayesNet {

    /**
     * generated ID
     */
    private static final long serialVersionUID = -4835134612921456157L;

    /**
     * Map that store attributes for upscaling for each classifier
     */
    private Set<Integer> upscaleIndex = new HashSet<>();

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.bayes.BayesNet#buildClassifier(weka.core.Instances)
     */
    @SuppressWarnings("boxing")
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        boolean trainingSuccessfull = false;
        boolean secondAttempt = false;
        Instances traindataCopy = null;
        do {
            try {
                if (secondAttempt) {
                    super.buildClassifier(traindataCopy);
                    trainingSuccessfull = true;
                }
                else {
                    super.buildClassifier(traindata);
                    trainingSuccessfull = true;
                }
            }
            catch (IllegalArgumentException e) {
                String regex = "A nominal attribute \\((.*)\\) cannot have duplicate labels.*";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(e.getMessage());
                if (!m.find()) {
                    // cannot treat problem, rethrow exception
                    throw e;
                }
                String attributeName = m.group(1);
                int attrIndex = traindata.attribute(attributeName).index();
                if (secondAttempt) {
                    throw new RuntimeException("cannot be handled correctly yet, because upscaleIndex is a Map");
                    // traindataCopy = upscaleAttribute(traindataCopy, attrIndex);
                }
                traindataCopy = WekaUtils.upscaleAttribute(traindata, attrIndex);

                this.upscaleIndex.add(attrIndex);
                Console.traceln(Level.FINE, "upscaled attribute " + attributeName +
                    "; restarting training of BayesNet");
                secondAttempt = true;
                continue;
            }
        }
        while (!trainingSuccessfull); // dummy loop for internal continue
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.bayes.BayesNet#distributionForInstance(weka.core.Instance)
     */
    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        Instances traindataCopy;
        for (int attrIndex : this.upscaleIndex) {
            // instance value must be upscaled
            double upscaledVal = instance.value(attrIndex) * WekaUtils.SCALER;
            traindataCopy = new Instances(instance.dataset());
            instance = new DenseInstance(instance.weight(), instance.toDoubleArray());
            instance.setValue(attrIndex, upscaledVal);
            traindataCopy.add(instance);
            instance.setDataset(traindataCopy);
        }
        return super.distributionForInstance(instance);
    }
}
