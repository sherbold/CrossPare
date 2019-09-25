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

package de.ugoe.cs.cpdp.dataselection;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddCluster;
import weka.filters.unsupervised.attribute.Remove;

/**
 * Use in Config:
 * 
 * Specify number of clusters -N = Num Clusters
 * <pointwiseselector name="PointWiseEMClusterSelection" param="-N 10"/>
 * 
 * Try to determine the number of clusters: -I 10 = max iterations -X 5 = 5 folds for cross
 * evaluation -max = max number of clusters
 * <pointwiseselector name="PointWiseEMClusterSelection" param="-I 10 -X 5 -max 300"/>
 * 
 * Don't forget to add: <preprocessor name="Normalization" param=""/>
 */
public class PointWiseEMClusterSelection implements IPointWiseDataselectionStrategy {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
    /**
     * paramters passed to the selection
     */
    private String[] params;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        this.params = parameters.split(" ");
    }

    /**
     * 1. Cluster the traindata 2. for each instance in the testdata find the assigned cluster 3.
     * select only traindata from the clusters we found in our testdata
     * 
     * @returns the selected training data
     */
    @SuppressWarnings("boxing")
    @Override
    public Instances apply(Instances testdata, Instances traindata) {
        // final Attribute classAttribute = testdata.classAttribute();

        final List<Integer> selectedCluster =
            SetUniqueList.setUniqueList(new LinkedList<Integer>());

        // 1. copy train- and testdata
        Instances train = new Instances(traindata);
        Instances test = new Instances(testdata);

        Instances selected = null;

        try {
            // remove class attribute from traindata
            Remove filter = new Remove();
            filter.setAttributeIndices("" + (train.classIndex() + 1));
            filter.setInputFormat(train);
            train = Filter.useFilter(train, filter);

            LOGGER.debug(String.format("starting clustering"));

            // 3. cluster data
            EM clusterer = new EM();
            clusterer.setOptions(this.params);
            clusterer.buildClusterer(train);
            int numClusters = clusterer.getNumClusters();
            if (numClusters == -1) {
            	LOGGER.debug(String.format("we have unlimited clusters"));
            }
            else {
            	LOGGER.debug(String.format("we have: " + numClusters + " clusters"));
            }

            // 4. classify testdata, save cluster int

            // remove class attribute from testdata?
            Remove filter2 = new Remove();
            filter2.setAttributeIndices("" + (test.classIndex() + 1));
            filter2.setInputFormat(test);
            test = Filter.useFilter(test, filter2);

            int cnum;
            for (int i = 0; i < test.numInstances(); i++) {
                cnum = clusterer.clusterInstance(test.get(i));

                // we dont want doubles (maybe use a hashset instead of list?)
                if (!selectedCluster.contains(cnum)) {
                    selectedCluster.add(cnum);
                    // Console.traceln(Level.INFO, String.format("assigned to cluster: "+cnum));
                }
            }

            LOGGER.debug(String.format("our testdata is in: " + selectedCluster.size() + " different clusters"));

            // 5. get cluster membership of our traindata
            AddCluster cfilter = new AddCluster();
            cfilter.setClusterer(clusterer);
            cfilter.setInputFormat(train);
            Instances ctrain = Filter.useFilter(train, cfilter);

            // 6. for all traindata get the cluster int, if it is in our list of testdata cluster
            // int add the traindata
            // of this cluster to our returned traindata
            int cnumber;
            selected = new Instances(traindata);
            selected.delete();

            for (int j = 0; j < ctrain.numInstances(); j++) {
                // get the cluster number from the attributes
                cnumber = Integer.parseInt(ctrain.get(j)
                    .stringValue(ctrain.get(j).numAttributes() - 1).replace("cluster", ""));

                // Console.traceln(Level.INFO,
                // String.format("instance "+j+" is in cluster: "+cnumber));
                if (selectedCluster.contains(cnumber)) {
                    // this only works if the index does not change
                    selected.add(traindata.get(j));
                    // check for differences, just one attribute, we are pretty sure the index does
                    // not change
                    if (traindata.get(j).value(3) != ctrain.get(j).value(3)) {
                    	LOGGER.warn(String
                            .format("we have a difference between train an ctrain!"));
                    }
                }
            }

            LOGGER.debug(String.format("that leaves us with: " +
                selected.numInstances() + " traindata instances from " + traindata.numInstances()));
        }
        catch (Exception e) {
            throw new RuntimeException("error in pointwise em", e);
        }

        return selected;
    }

}
