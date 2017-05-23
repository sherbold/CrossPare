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

import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filter based on EM clustering after S. Herbold: Training data selection for cross-project defect
 * prediction
 * 
 * @author Steffen Herbold
 */
public class SetWiseEMClusterSelection extends AbstractCharacteristicSelection {

    /**
     * @see ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final Instances data = normalizedCharacteristicInstances(testdata, traindataSet);
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
            }
            while (onlyTarget);

            int numRemoved = 0;
            for (int i = 0; i < candidateInstances.size(); i++) {
                if (emeans.clusterInstance(candidateInstances.get(i)) != targetCluster) {
                    traindataSet.remove(i - numRemoved++);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("error applying setwise EM clustering training data selection",
                                       e);
        }
    }
}
