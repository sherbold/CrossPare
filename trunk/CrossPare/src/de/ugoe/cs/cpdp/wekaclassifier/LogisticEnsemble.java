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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Logistic Ensemble Classifier after Uchigaki et al. with some assumptions. It is unclear if these
 * assumptions are true.
 *
 * @author Steffen Herbold
 */
public class LogisticEnsemble extends AbstractClassifier {

    /**
     * default id
     */
    private static final long serialVersionUID = 1L;

    /**
     * list with classifiers
     */
    private List<Classifier> classifiers = null;

    /**
     * list with weights for each classifier
     */
    private List<Double> weights = null;

    /**
     * local copy of the options to be passed to the ensemble of logistic classifiers
     */
    private String[] options;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#setOptions(java.lang.String[])
     */
    @Override
    public void setOptions(String[] options) throws Exception {
        this.options = options;
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#distributionForInstance(weka.core.Instance)
     */
    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        Iterator<Classifier> classifierIter = classifiers.iterator();
        Iterator<Double> weightIter = weights.iterator();
        double[] result = new double[2];
        while (classifierIter.hasNext()) {
            for (int j = 0; j < instance.numAttributes(); j++) {
                if (j != instance.classIndex()) {
                    Instance copy = new DenseInstance(instance);
                    for (int k = instance.numAttributes() - 1; k >= 0; k--) {
                        if (j != k && k != instance.classIndex()) {
                            copy.deleteAttributeAt(k);
                        }
                    }
                    double[] localResult = classifierIter.next().distributionForInstance(copy);
                    double currentWeight = weightIter.next();
                    for (int i = 0; i < localResult.length; i++) {
                        result[i] = result[i] + localResult[i] * currentWeight;
                    }
                }
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        classifiers = new LinkedList<>();
        weights = new LinkedList<>();
        List<Double> weightsTmp = new LinkedList<>();
        double sumWeights = 0.0;
        for (int j = 0; j < traindata.numAttributes(); j++) {
            if (j != traindata.classIndex()) {
                final Logistic classifier = new Logistic();
                classifier.setOptions(options);
                final Instances copy = new Instances(traindata);
                for (int k = traindata.numAttributes() - 1; k >= 0; k--) {
                    if (j != k && k != traindata.classIndex()) {
                        copy.deleteAttributeAt(k);
                    }
                }
                classifier.buildClassifier(copy);
                classifiers.add(classifier);
                Evaluation eval = new Evaluation(copy);
                eval.evaluateModel(classifier, copy);
                weightsTmp.add(eval.matthewsCorrelationCoefficient(1));
                sumWeights += eval.matthewsCorrelationCoefficient(1);
            }
        }
        for (double tmp : weightsTmp) {
            weights.add(tmp / sumWeights);
        }
    }
}
