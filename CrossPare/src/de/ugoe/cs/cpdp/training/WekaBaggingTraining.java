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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * The first parameter is the trainer name, second parameter is class name. All subsequent
 * parameters are configuration parameters of the algorithms. Cross validation parameters always
 * come last and are prepended with -CVPARAM
 * </p>
 * <p>
 * XML Configurations for Weka Classifiers:
 * 
 * <pre>
 * {@code
 * <!-- examples -->
 * <setwisetrainer name="WekaBaggingTraining" param="NaiveBayesBagging weka.classifiers.bayes.NaiveBayes" />
 * <setwisetrainer name="WekaBaggingTraining" param="LogisticBagging weka.classifiers.functions.Logistic -R 1.0E-8 -M -1" />
 * }
 * </pre>
 * </p>
 * 
 * @author Alexander Trautsch
 */
public class WekaBaggingTraining extends WekaBaseTraining implements ISetWiseTrainingStrategy {

    /**
     * the classifier
     */
    private final TraindatasetBagging classifier = new TraindatasetBagging();

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.training.WekaBaseTraining#getClassifier()
     */
    @Override
    public Classifier getClassifier() {
        return classifier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy#apply(org.apache.commons.collections4.list.
     * SetUniqueList)
     */
    @Override
    public void apply(SetUniqueList<Instances> traindataSet) {
        try {
            classifier.buildClassifier(traindataSet);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * Helper class for bagging classifiers.
     * </p>
     * 
     * @author Steffen Herbold
     */
    public class TraindatasetBagging extends AbstractClassifier {

        /**
         * default serialization ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * internal storage of the training data
         */
        private List<Instances> trainingData = null;

        /**
         * bagging classifier for each training data set
         */
        private List<Classifier> classifiers = null;

        /*
         * (non-Javadoc)
         * 
         * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
         */
        @Override
        public double classifyInstance(Instance instance) {
            if (classifiers == null) {
                return 0.0;
            }

            double classification = 0.0;
            for (int i = 0; i < classifiers.size(); i++) {
                Classifier classifier = classifiers.get(i);
                Instances traindata = trainingData.get(i);

                Set<String> attributeNames = new HashSet<>();
                for (int j = 0; j < traindata.numAttributes(); j++) {
                    attributeNames.add(traindata.attribute(j).name());
                }

                double[] values = new double[traindata.numAttributes()];
                int index = 0;
                for (int j = 0; j < instance.numAttributes(); j++) {
                    if (attributeNames.contains(instance.attribute(j).name())) {
                        values[index] = instance.value(j);
                        index++;
                    }
                }

                Instances tmp = new Instances(traindata);
                tmp.clear();
                Instance instCopy = new DenseInstance(instance.weight(), values);
                instCopy.setDataset(tmp);
                try {
                    classification += classifier.classifyInstance(instCopy);
                }
                catch (Exception e) {
                    throw new RuntimeException("bagging classifier could not classify an instance",
                                               e);
                }
            }
            classification /= classifiers.size();
            return (classification >= 0.5) ? 1.0 : 0.0;
        }

        /**
         * <p>
         * trains a new dataset wise bagging classifier
         * </p>
         *
         * @param traindataSet
         *            the training data per prodcut
         * @throws Exception
         *             thrown if an error occurs during the training of the classifiers for any
         *             product
         */
        public void buildClassifier(SetUniqueList<Instances> traindataSet) throws Exception {
            classifiers = new LinkedList<>();
            trainingData = new LinkedList<>();
            for (Instances traindata : traindataSet) {
                Classifier classifier = setupClassifier();
                classifier.buildClassifier(traindata);
                classifiers.add(classifier);
                trainingData.add(new Instances(traindata));
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
         */
        @Override
        public void buildClassifier(Instances traindata) throws Exception {
            classifiers = new LinkedList<>();
            trainingData = new LinkedList<>();
            final Classifier classifier = setupClassifier();
            classifier.buildClassifier(traindata);
            classifiers.add(classifier);
            trainingData.add(new Instances(traindata));
        }
    }
}
