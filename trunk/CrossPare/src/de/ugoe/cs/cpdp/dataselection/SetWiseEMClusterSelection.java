package de.ugoe.cs.cpdp.dataselection;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filter based on EM clustering after S. Herbold: Training data selection for cross-project defect prediction
 * @author Steffen Herbold
 */
public class SetWiseEMClusterSelection extends AbstractCharacteristicSelection {
	
	/**
	 * @see de.ugoe.cs.cpdp.dataselection.SetWiseDataselectionStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		final Instances data = normalizedCharacteristicInstances(testdata, traindataSet); 
		final Instance targetInstance = data.instance(0);
		final List<Instance> candidateInstances = new LinkedList<Instance>();
		for( int i=1; i<data.numInstances(); i++ ) {
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
				for( int i=0 ; i<candidateInstances.size() && onlyTarget; i++ ) {
					onlyTarget &= !(emeans.clusterInstance(candidateInstances.get(i))==targetCluster);
				}
				maxNumClusters = emeans.numberOfClusters()-1;
			} while(onlyTarget);
			
			int numRemoved = 0;
			for( int i=0 ; i<candidateInstances.size() ; i++ ) {
				if( emeans.clusterInstance(candidateInstances.get(i))!=targetCluster ) {
					traindataSet.remove(i-numRemoved++);
				}
			}
		} catch(Exception e) {
			throw new RuntimeException("error applying setwise EM clustering training data selection", e);
		}
	}	
}
