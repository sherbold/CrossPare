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
import java.util.List;
import java.util.logging.Level;

import de.ugoe.cs.util.console.Console;
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

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
     */
    @Override
    public double classifyInstance(Instance instance) throws Exception {
        if (codepClassifier == null) {
            throw new RuntimeException("classifier must be trained first, call to buildClassifier missing");
        }
        Instances tmp = new Instances("tmp", internalAttributes, 1);
        tmp.setClass(internalAttributes.get(internalAttributes.size() - 1));
        tmp.add(createInternalInstance(instance));
        return codepClassifier.classifyInstance(tmp.firstInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        setupInternalClassifiers();
        setupInternalAttributes();

        for (Classifier classifier : internalClassifiers) {
            Console.traceln(Level.FINE, "internally training " + classifier.getClass().getName());
            classifier.buildClassifier(traindata);
        }

        Instances internalTraindata =
            new Instances("internal instances", internalAttributes, traindata.size());
        internalTraindata.setClass(internalAttributes.get(internalAttributes.size() - 1));

        for (Instance instance : traindata) {
            internalTraindata.add(createInternalInstance(instance));
        }

        codepClassifier = getCodepClassifier();
        codepClassifier.buildClassifier(internalTraindata);
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
    private Instance createInternalInstance(Instance instance) throws Exception {
        double[] values = new double[internalAttributes.size()];
        for (int j = 0; j < internalClassifiers.size(); j++) {
            values[j] = internalClassifiers.get(j).classifyInstance(instance);
        }
        values[internalAttributes.size() - 1] = instance.classValue();
        return new DenseInstance(1.0, values);
    }

    /**
     * <p>
     * Sets up the attributes array.
     * </p>
     */
    private void setupInternalAttributes() {
        internalAttributes = new ArrayList<>();
        for (Classifier classifier : internalClassifiers) {
            internalAttributes.add(new Attribute(classifier.getClass().getName()));
        }
        final ArrayList<String> classAttVals = new ArrayList<String>();
        classAttVals.add("0");
        classAttVals.add("1");
        final Attribute classAtt = new Attribute("bug", classAttVals);
        internalAttributes.add(classAtt);
    }

    /**
     * <p>
     * Sets up the classifier array.
     * </p>
     */
    private void setupInternalClassifiers() {
        internalClassifiers = new ArrayList<>(6);
        // create training data with prediction labels

        internalClassifiers.add(new ADTree());
        internalClassifiers.add(new BayesNet());
        internalClassifiers.add(new DecisionTable());
        internalClassifiers.add(new Logistic());
        internalClassifiers.add(new MultilayerPerceptron());
        internalClassifiers.add(new RBFNetwork());
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
