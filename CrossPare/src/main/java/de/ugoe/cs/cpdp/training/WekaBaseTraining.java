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

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.core.OptionHandler;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.Vote;

/**
 * <p>
 * Allows specification of the Weka classifier and its params in the XML experiment configuration.
 * </p>
 * <p>
 * Important conventions of the XML format: Cross Validation params always come last and are
 * prepended with -CVPARAM.<br>
 * Example:
 * 
 * <pre>
 * {@code
 * <trainer name="WekaTraining" param="RandomForestLocal weka.classifiers.trees.RandomForest -CVPARAM I 5 25 5"/>
 * }
 * </pre>
 * 
 * @author Alexander Trautsch
 */
public abstract class WekaBaseTraining implements IWekaCompatibleTrainer {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
    /**
     * reference to the Weka classifier
     */
    protected Classifier classifier = null;

    /**
     * qualified class name of the weka classifier
     */
    protected String classifierClassName;

    /**
     * name of the classifier
     */
    protected String classifierName;

    /**
     * parameters of the training
     */
    protected String[] classifierParams;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        String[] params = parameters.split(" ");

        // first part of the params is the classifierName (e.g. SMORBF)
        this.classifierName = params[0];

        // the following parameters can be copied from weka!

        // second param is classifierClassName (e.g. weka.classifiers.functions.SMO)
        this.classifierClassName = params[1];

        // rest are params to the specified classifier (e.g. -K
        // weka.classifiers.functions.supportVector.RBFKernel)
        this.classifierParams = Arrays.copyOfRange(params, 2, params.length);

        // classifier = setupClassifier();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer#getClassifier()
     */
    @Override
    public Classifier getClassifier() {
        return this.classifier;
    }

    /**
     * <p>
     * helper function that sets up the Weka classifier including its parameters
     * </p>
     *
     * @return
     */
    protected Classifier setupClassifier() {
        Classifier cl = null;
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName(this.classifierClassName);
            Classifier obj = (Classifier) c.newInstance();

            // Filter out -CVPARAM, these are special because they do not belong to the Weka
            // classifier class as parameters
            String[] param = Arrays.copyOf(this.classifierParams, this.classifierParams.length);
            String[] cvparam = { };
            boolean cv = false;
            for (int i = 0; i < this.classifierParams.length; i++) {
                if (this.classifierParams[i].equals("-CVPARAM")) {
                    // rest of array are cvparam
                    cvparam = Arrays.copyOfRange(this.classifierParams, i + 1, this.classifierParams.length);

                    // before this we have normal params
                    param = Arrays.copyOfRange(this.classifierParams, 0, i);

                    cv = true;
                    break;
                }
            }

            // set classifier params
            ((OptionHandler) obj).setOptions(param);
            cl = obj;

            if (cl instanceof Vote) {
                Vote votingClassifier = (Vote) cl;
                for (Classifier clf : votingClassifier.getClassifiers()) {
                    if (clf instanceof BayesNet) {
                        ((BayesNet) clf).setUseADTree(false);
                    }
                }
            }
            // we have cross val params
            // cant check on cvparam.length here, it may not be initialized
            if (cv) {
                final CVParameterSelection ps = new CVParameterSelection();
                ps.setClassifier(obj);
                ps.setNumFolds(5);
                // ps.addCVParameter("I 5 25 5");
                for (int i = 1; i < cvparam.length / 4; i++) {
                    ps.addCVParameter(Arrays.asList(Arrays.copyOfRange(cvparam, 0, 4 * i))
                        .toString().replaceAll(", ", " ").replaceAll("^\\[|\\]$", ""));
                }

                cl = ps;
            }

        }
        catch (ClassNotFoundException e) {
            LOGGER.warn(String.format("class not found: %s", e.toString()));
            e.printStackTrace();
        }
        catch (InstantiationException e) {
        	LOGGER.warn(String.format("Instantiation Exception: %s", e.toString()));
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
        	LOGGER.warn(String.format("Illegal Access Exception: %s", e.toString()));
            e.printStackTrace();
        }
        catch (Exception e) {
        	LOGGER.warn(String.format("Exception: %s", e.toString()));
            e.printStackTrace();
        }

        return cl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer#getName()
     */
    @Override
    public String getName() {
        return this.classifierName;
    }

}
