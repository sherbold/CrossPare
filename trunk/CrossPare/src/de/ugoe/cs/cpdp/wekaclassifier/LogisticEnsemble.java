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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Logistic Ensemble Classifier after Uchigaki et al. 
 *
 * TODO comment class
 * @author Steffen Herbold
 */
public class LogisticEnsemble extends AbstractClassifier {

    private static final long serialVersionUID = 1L;

    private List<Instances> trainingData = null;

    private List<Classifier> classifiers = null;
    
    private String[] options; 

    @Override
    public void setOptions(String[] options) throws Exception {
        this.options = options;
    }
    
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
                throw new RuntimeException("bagging classifier could not classify an instance", e);
            }
        }
        classification /= classifiers.size();
        return (classification >= 0.5) ? 1.0 : 0.0;
    }

    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        classifiers = new LinkedList<>();
        for( int j=0 ; j<traindata.numAttributes() ; j++) {
            final Logistic classifier = new Logistic();
            classifier.setOptions(options);
            final Instances copy = new Instances(traindata);
            for( int k=traindata.numAttributes()-1; k>=0 ; k-- ) {
                if( j!=k && traindata.classIndex()!=k ) {
                    copy.deleteAttributeAt(k);
                }
            }
            classifier.buildClassifier(copy);
            classifiers.add(classifier);
        }
    }
}
