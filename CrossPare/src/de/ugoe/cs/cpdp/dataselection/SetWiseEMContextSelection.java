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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.util.console.Console;
import weka.clusterers.EM;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 * Selects training data by clustering project context factors.
 * 
 * The project context factors used for the clustering are configured in the XML param attribute,
 * Example: <setwiseselector name="SetWiseEMContextSelection" param="AFS TND TNC" />
 */
public class SetWiseEMContextSelection implements ISetWiseDataselectionStrategy {

    /**
     * context factors
     */
    private String[] project_context_factors; // = new String[]{"TND", "TNC", "TNF", "TLOC"};

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null) {
            project_context_factors = parameters.split(" ");
        }
    }

    /**
     * Uses the Weka EM-Clustering algorithm to cluster the projects by their project context
     * factors. The project context factors are first normalized and then used for clustering. They
     * can be configured in the configuration param.
     * 
     * @param testdata
     * @param traindataSet
     */
    protected void cluster(Instances testdata, SetUniqueList<Instances> traindataSet) {
        // now do the clustering, normalizedCharacteristicInstances ruft getContextFactors auf
        final Instances data = this.normalizedCharacteristicInstances(testdata, traindataSet);

        final Instance targetInstance = data.instance(0);
        final List<Instance> candidateInstances = new LinkedList<Instance>();
        for (int i = 1; i < data.numInstances(); i++) {
            candidateInstances.add(data.instance(i));
        }

        // cluster and select
        try {
            final EM emeans = new EM();
            boolean onlyTarget = true;
            int targetCluster;
            int maxNumClusters = candidateInstances.size();

            do { // while(onlyTarget)
                emeans.setMaximumNumberOfClusters(maxNumClusters);
                emeans.buildClusterer(data);

                targetCluster = emeans.clusterInstance(targetInstance);

                // check if cluster only contains target project
                for (int i = 0; i < candidateInstances.size() && onlyTarget; i++) {
                    onlyTarget &=
                        !(emeans.clusterInstance(candidateInstances.get(i)) == targetCluster);
                }
                maxNumClusters = emeans.numberOfClusters() - 1;

                // Console.traceln(Level.INFO, "number of clusters: " + emeans.numberOfClusters());
            }
            while (onlyTarget);

            Console.traceln(Level.INFO, "clusters: " + maxNumClusters);
            Console.traceln(Level.INFO, "instances vor dem clustern: " + traindataSet.size());
            int numRemoved = 0;
            for (int i = 0; i < candidateInstances.size(); i++) {
                if (emeans.clusterInstance(candidateInstances.get(i)) != targetCluster) {
                    traindataSet.remove(i - numRemoved++);
                }
            }
            Console.traceln(Level.INFO, "instances nach dem clustern: " + traindataSet.size());
        }
        catch (Exception e) {
            throw new RuntimeException("error applying setwise EM clustering training data selection",
                                       e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        // issuetracking und pl muss passen
        /*
         * int s = traindataSet.size(); Console.traceln(Level.INFO,
         * "remove non matching PL and IssueTracking projects, size now: " + s);
         * this.removeWrongContext(testdata, traindataSet, "PL"); this.removeWrongContext(testdata,
         * traindataSet, "IssueTracking"); s = traindataSet.size(); Console.traceln(Level.INFO,
         * "size after removal: " + s);
         */
        // now cluster
        this.cluster(testdata, traindataSet);
    }

    /**
     * Returns test- and training data with only the project context factors which were chosen in
     * the configuration. This is later used for clustering.
     * 
     * @param testdata
     * @param traindataSet
     * @return
     */
    protected Instances getContextFactors(Instances testdata,
                                          SetUniqueList<Instances> traindataSet)
    {
        // setup weka Instances for clustering
        final ArrayList<Attribute> atts = new ArrayList<Attribute>();

        // we only want the project context factors
        for (String pcf : this.project_context_factors) {
            atts.add(new Attribute(pcf));
        }

        // set up the data
        final Instances data = new Instances("project_context_factors", atts, 0);
        double[] instanceValues = new double[atts.size()];

        // only project context factors + only one instance per project needed
        int i = 0;
        for (String pcf : this.project_context_factors) {
            instanceValues[i] = testdata.instance(0).value(testdata.attribute(pcf));
            // Console.traceln(Level.INFO, "adding attribute: " + pcf + " value: " +
            // instanceValues[i]);
            i++;
        }
        data.add(new DenseInstance(1.0, instanceValues));

        // now for the projects of the training stet
        for (Instances traindata : traindataSet) {
            instanceValues = new double[atts.size()]; // ohne das hier immer dieselben werte?!
            i = 0;
            for (String pcf : this.project_context_factors) {
                instanceValues[i] = traindata.instance(0).value(traindata.attribute(pcf));
                // Console.traceln(Level.INFO, "adding attribute: " + pcf + " value: " +
                // instanceValues[i]);
                i++;
            }

            data.add(new DenseInstance(1.0, instanceValues));
        }

        return data;
    }

    /**
     * Delete projects where the project context does not match the training project
     * 
     * @param testdata
     * @param traindataSet
     * @param attribute
     */
    protected void removeWrongContext(Instances testdata,
                                      SetUniqueList<Instances> traindataSet,
                                      String attribute)
    {
        Set<Instances> remove = new HashSet<Instances>();
        for (Instances traindata : traindataSet) {
            if (traindata.firstInstance().value(traindata.attribute(attribute)) != testdata
                .firstInstance().value(testdata.attribute(attribute)))
            {
                remove.add(traindata);
                // Console.traceln(Level.WARNING,
                // "rmove attribute "+attribute+" test:
                // "+testdata.firstInstance().value(testdata.attribute(attribute))+" train:
                // "+traindata.firstInstance().value(traindata.attribute(attribute)));
            }
        }

        // now delete the projects from set
        for (Instances i : remove) {
            traindataSet.remove(i);
            // Console.traceln(Level.INFO, "removing training project from set");
        }
    }

    /**
     * Normalizes the data before it gets used for clustering
     * 
     * @param testdata
     * @param traindataSet
     * @return
     */
    protected Instances normalizedCharacteristicInstances(Instances testdata,
                                                          SetUniqueList<Instances> traindataSet)
    {
        Instances data = this.getContextFactors(testdata, traindataSet);
        try {
            final Normalize normalizer = new Normalize();
            normalizer.setInputFormat(data);
            data = Filter.useFilter(data, normalizer);
        }
        catch (Exception e) {
            throw new RuntimeException("Unexpected exception during normalization of distributional characteristics.",
                                       e);
        }
        return data;
    }
}
