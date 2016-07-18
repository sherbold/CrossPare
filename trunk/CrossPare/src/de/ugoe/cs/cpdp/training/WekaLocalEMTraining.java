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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import de.ugoe.cs.util.console.Console;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.clusterers.EM;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * <p>
 * Local Trainer with EM Clustering for data partitioning. Currently supports only EM Clustering.
 * </p>
 * <ol>
 * <li>Cluster training data</li>
 * <li>for each cluster train a classifier with training data from cluster</li>
 * <li>match test data instance to a cluster, then classify with classifier from the cluster</li>
 * </ol>
 * 
 * XML configuration:
 * 
 * <pre>
 * {@code
 * <trainer name="WekaLocalEMTraining" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 * }
 * </pre>
 */
public class WekaLocalEMTraining extends WekaBaseTraining implements ITrainingStrategy {

    /**
     * the classifier
     */
    private final TraindatasetCluster classifier = new TraindatasetCluster();

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
     * @see de.ugoe.cs.cpdp.training.ITrainingStrategy#apply(weka.core.Instances)
     */
    @Override
    public void apply(Instances traindata) {
        try {
            classifier.buildClassifier(traindata);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * Weka classifier for the local model with EM clustering.
     * </p>
     * 
     * @author Alexander Trautsch
     */
    public class TraindatasetCluster extends AbstractClassifier {

        /**
         * default serializtion ID
         */
        private static final long serialVersionUID = 1L;

        /**
         * EM clusterer used
         */
        private EM clusterer = null;

        /**
         * classifiers for each cluster
         */
        private HashMap<Integer, Classifier> cclassifier;

        /**
         * training data for each cluster
         */
        private HashMap<Integer, Instances> ctraindata;

        /**
         * Helper method that gives us a clean instance copy with the values of the instancelist of
         * the first parameter.
         * 
         * @param instancelist
         *            with attributes
         * @param instance
         *            with only values
         * @return copy of the instance
         */
        private Instance createInstance(Instances instances, Instance instance) {
            // attributes for feeding instance to classifier
            Set<String> attributeNames = new HashSet<>();
            for (int j = 0; j < instances.numAttributes(); j++) {
                attributeNames.add(instances.attribute(j).name());
            }

            double[] values = new double[instances.numAttributes()];
            int index = 0;
            for (int j = 0; j < instance.numAttributes(); j++) {
                if (attributeNames.contains(instance.attribute(j).name())) {
                    values[index] = instance.value(j);
                    index++;
                }
            }

            Instances tmp = new Instances(instances);
            tmp.clear();
            Instance instCopy = new DenseInstance(instance.weight(), values);
            instCopy.setDataset(tmp);

            return instCopy;
        }

        /*
         * (non-Javadoc)
         * 
         * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
         */
        @Override
        public double classifyInstance(Instance instance) {
            double ret = 0;
            try {
                // 1. copy the instance (keep the class attribute)
                Instances traindata = ctraindata.get(0);
                Instance classInstance = createInstance(traindata, instance);

                // 2. remove class attribute before clustering
                Remove filter = new Remove();
                filter.setAttributeIndices("" + (traindata.classIndex() + 1));
                filter.setInputFormat(traindata);
                traindata = Filter.useFilter(traindata, filter);

                // 3. copy the instance (without the class attribute) for clustering
                Instance clusterInstance = createInstance(traindata, instance);

                // 4. match instance without class attribute to a cluster number
                int cnum = clusterer.clusterInstance(clusterInstance);

                // 5. classify instance with class attribute to the classifier of that cluster
                // number
                ret = cclassifier.get(cnum).classifyInstance(classInstance);

            }
            catch (Exception e) {
                Console.traceln(Level.INFO, String.format("ERROR matching instance to cluster!"));
                throw new RuntimeException(e);
            }
            return ret;
        }

        /*
         * (non-Javadoc)
         * 
         * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
         */
        @Override
        public void buildClassifier(Instances traindata) throws Exception {

            // 1. copy training data
            Instances train = new Instances(traindata);

            // 2. remove class attribute for clustering
            Remove filter = new Remove();
            filter.setAttributeIndices("" + (train.classIndex() + 1));
            filter.setInputFormat(train);
            train = Filter.useFilter(train, filter);

            // new objects
            cclassifier = new HashMap<Integer, Classifier>();
            ctraindata = new HashMap<Integer, Instances>();

            Instances ctrain;
            int maxNumClusters = train.size();
            boolean sufficientInstancesInEachCluster;
            do { // while(onlyTarget)
                sufficientInstancesInEachCluster = true;
                clusterer = new EM();
                clusterer.setMaximumNumberOfClusters(maxNumClusters);
                clusterer.buildClusterer(train);

                // 4. get cluster membership of our traindata
                // AddCluster cfilter = new AddCluster();
                // cfilter.setClusterer(clusterer);
                // cfilter.setInputFormat(train);
                // Instances ctrain = Filter.useFilter(train, cfilter);

                ctrain = new Instances(train);
                ctraindata = new HashMap<>();

                // get traindata per cluster
                for (int j = 0; j < ctrain.numInstances(); j++) {
                    // get the cluster number from the attributes, subract 1 because if we
                    // clusterInstance we get 0-n, and this is 1-n
                    // cnumber =
                    // Integer.parseInt(ctrain.get(j).stringValue(ctrain.get(j).numAttributes()-1).replace("cluster",
                    // "")) - 1;

                    int cnumber = clusterer.clusterInstance(ctrain.get(j));
                    // add training data to list of instances for this cluster number
                    if (!ctraindata.containsKey(cnumber)) {
                        ctraindata.put(cnumber, new Instances(traindata));
                        ctraindata.get(cnumber).delete();
                    }
                    ctraindata.get(cnumber).add(traindata.get(j));
                }

                for (Entry<Integer, Instances> entry : ctraindata.entrySet()) {
                    Instances instances = entry.getValue();
                    int[] counts = instances.attributeStats(instances.classIndex()).nominalCounts;
                    for (int count : counts) {
                        sufficientInstancesInEachCluster &= count > 0;
                    }
                    sufficientInstancesInEachCluster &= instances.numInstances() >= 5;
                }
                maxNumClusters = clusterer.numberOfClusters() - 1;
            }
            while (!sufficientInstancesInEachCluster);

            // train one classifier per cluster, we get the cluster number from the training data
            Iterator<Integer> clusternumber = ctraindata.keySet().iterator();
            while (clusternumber.hasNext()) {
                int cnumber = clusternumber.next();
                cclassifier.put(cnumber, setupClassifier());
                cclassifier.get(cnumber).buildClassifier(ctraindata.get(cnumber));

                // Console.traceln(Level.INFO, String.format("classifier in cluster "+cnumber));
            }
        }
    }
}
