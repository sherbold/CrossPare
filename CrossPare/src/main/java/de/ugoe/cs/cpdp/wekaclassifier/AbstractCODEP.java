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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.ADTree;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Implements CODEP proposed by Panichella et al. (2014).
 * </p>
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractCODEP extends AbstractClassifier {

    /**
     * Default serialization ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * List of classifiers that is internally used.
     */
    private List<Classifier> internalClassifiers = null;

    /**
     * List of attributes that is internally used.
     */
    private ArrayList<Attribute> internalAttributes = null;

    /**
     * Trained CODEP classifier.
     */
    private Classifier codepClassifier = null;

    /**
     * Map that store attributes for upscaling for each classifier
     */
    private Map<Integer, Integer> upscaleIndex = null;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
     */
    @Override
    public double classifyInstance(Instance instance) throws Exception {
        if (this.codepClassifier == null) {
            throw new RuntimeException("classifier must be trained first, call to buildClassifier missing");
        }
        Instances tmp = new Instances("tmp", this.internalAttributes, 1);
        tmp.setClass(this.internalAttributes.get(this.internalAttributes.size() - 1));
        tmp.add(createInternalInstance(instance));
        return this.codepClassifier.classifyInstance(tmp.firstInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @SuppressWarnings("boxing")
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        setupInternalClassifiers();
        setupInternalAttributes();
        this.upscaleIndex = new HashMap<>();

        int classifierIndex = 0;
        boolean secondAttempt = false;
        Instances traindataCopy = null;
        for (Classifier classifier : this.internalClassifiers) {
            boolean trainingSuccessfull = false;
            do {
                LOGGER.debug("internally training " + classifier.getClass().getName());
                try {
                    if (secondAttempt) {
                        classifier.buildClassifier(traindataCopy);
                        trainingSuccessfull = true;
                    }
                    else {
                        classifier.buildClassifier(traindata);
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

                    this.upscaleIndex.put(classifierIndex, attrIndex);
                    LOGGER.info("upscaled attribute " + attributeName + "; restarting training");
                    secondAttempt = true;
                    continue;
                }
            }
            while (!trainingSuccessfull); // dummy loop for internal continue
            classifierIndex++;
            secondAttempt = false;
        }

        Instances internalTraindata =
            new Instances("internal instances", this.internalAttributes, traindata.size());
        internalTraindata.setClass(this.internalAttributes.get(this.internalAttributes.size() - 1));

        for (Instance instance : traindata) {
            internalTraindata.add(createInternalInstance(instance));
        }

        this.codepClassifier = getCodepClassifier();
        this.codepClassifier.buildClassifier(internalTraindata);
    }

    /**
     * <p>
     * Creates a CODEP instance using the classifications of the internal classifiers.
     * </p>
     *
     * @param instance
     *            instance for which the CODEP instance is created
     * @return CODEP instance
     * @throws Exception
     *             thrown if an exception occurs during classification with an internal classifier
     */
    @SuppressWarnings("boxing")
    private Instance createInternalInstance(Instance instance) throws Exception {
        double[] values = new double[this.internalAttributes.size()];
        Instances traindataCopy;
        for (int j = 0; j < this.internalClassifiers.size(); j++) {
            if (this.upscaleIndex.containsKey(j)) {
                // instance value must be upscaled
                int attrIndex = this.upscaleIndex.get(j);
                double upscaledVal = instance.value(attrIndex) * WekaUtils.SCALER;
                traindataCopy = new Instances(instance.dataset());
                instance = new DenseInstance(instance.weight(), instance.toDoubleArray());
                instance.setValue(attrIndex, upscaledVal);
                traindataCopy.add(instance);
                instance.setDataset(traindataCopy);
            }
            values[j] = this.internalClassifiers.get(j).classifyInstance(instance);
        }
        values[this.internalAttributes.size() - 1] = instance.classValue();
        return new DenseInstance(1.0, values);
    }

    /**
     * <p>
     * Sets up the attributes array.
     * </p>
     */
    private void setupInternalAttributes() {
        this.internalAttributes = new ArrayList<>();
        for (Classifier classifier : this.internalClassifiers) {
            this.internalAttributes.add(new Attribute(classifier.getClass().getName()));
        }
        final ArrayList<String> classAttVals = new ArrayList<>();
        classAttVals.add("0");
        classAttVals.add("1");
        final Attribute classAtt = new Attribute("bug", classAttVals);
        this.internalAttributes.add(classAtt);
    }

    /**
     * <p>
     * Sets up the classifier array.
     * </p>
     */
    private void setupInternalClassifiers() {
        this.internalClassifiers = new ArrayList<>(6);
        // create training data with prediction labels

        this.internalClassifiers.add(new ADTree());
        this.internalClassifiers.add(new BayesNet());
        this.internalClassifiers.add(new DecisionTable());
        this.internalClassifiers.add(new Logistic());
        this.internalClassifiers.add(new MultilayerPerceptron());
        this.internalClassifiers.add(new RBFNetwork());
    }

    /**
     * <p>
     * Abstract method through which implementing classes define which classifier is used for the
     * CODEP.
     * </p>
     *
     * @return classifier for CODEP
     */
    abstract protected Classifier getCodepClassifier();
}
