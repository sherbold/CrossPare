package de.ugoe.cs.cpdp.dataselection;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddCluster;
import weka.filters.unsupervised.attribute.Remove;
import de.ugoe.cs.util.console.Console;


/**
 * Use in Config:
 * 
 * Specify number of clusters
 * -N = Num Clusters
 * <pointwiseselector name="PointWiseEMClusterSelection" param="-N 10"/>
 *
 * Try to determine the number of clusters:
 * -I 10 = max iterations
 * -X 5 = 5 folds for cross evaluation
 * -max = max number of clusters
 * <pointwiseselector name="PointWiseEMClusterSelection" param="-I 10 -X 5 -max 300"/>
 * 
 * Don't forget to add:
 * <preprocessor name="Normalization" param=""/>
 */
public class PointWiseEMClusterSelection implements IPointWiseDataselectionStrategy {
	
	private String[] params; 
	
	@Override
	public void setParameter(String parameters) {
		params = parameters.split(" ");
	}

	
	/**
	 * 1. Cluster the traindata
	 * 2. for each instance in the testdata find the assigned cluster
	 * 3. select only traindata from the clusters we found in our testdata
	 * 
	 * @returns the selected training data
	 */
	@Override
	public Instances apply(Instances testdata, Instances traindata) {
		//final Attribute classAttribute = testdata.classAttribute();
		
		final List<Integer> selectedCluster = SetUniqueList.setUniqueList(new LinkedList<Integer>());

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
			
			Console.traceln(Level.INFO, String.format("starting clustering"));
			
			// 3. cluster data
			EM clusterer = new EM();
			clusterer.setOptions(params);
			clusterer.buildClusterer(train);
			int numClusters = clusterer.getNumClusters();
			if ( numClusters == -1) {
				Console.traceln(Level.INFO, String.format("we have unlimited clusters"));
			}else {
				Console.traceln(Level.INFO, String.format("we have: "+numClusters+" clusters"));
			}
			
			
			// 4. classify testdata, save cluster int
			
			// remove class attribute from testdata?
			Remove filter2 = new Remove();
			filter2.setAttributeIndices("" + (test.classIndex() + 1));
			filter2.setInputFormat(test);
			test = Filter.useFilter(test, filter2);
			
			int cnum;
			for( int i=0; i < test.numInstances(); i++ ) {
				cnum = ((EM)clusterer).clusterInstance(test.get(i));

				// we dont want doubles (maybe use a hashset instead of list?)
				if ( !selectedCluster.contains(cnum) ) {
					selectedCluster.add(cnum);
					//Console.traceln(Level.INFO, String.format("assigned to cluster: "+cnum));
				}
			}
			
			Console.traceln(Level.INFO, String.format("our testdata is in: "+selectedCluster.size()+" different clusters"));
			
			// 5. get cluster membership of our traindata
			AddCluster cfilter = new AddCluster();
			cfilter.setClusterer(clusterer);
			cfilter.setInputFormat(train);
			Instances ctrain = Filter.useFilter(train, cfilter);
			
			
			// 6. for all traindata get the cluster int, if it is in our list of testdata cluster int add the traindata
			// of this cluster to our returned traindata
			int cnumber;
			selected = new Instances(traindata);
			selected.delete();
			
			for ( int j=0; j < ctrain.numInstances(); j++ ) {
				// get the cluster number from the attributes
				cnumber = Integer.parseInt(ctrain.get(j).stringValue(ctrain.get(j).numAttributes()-1).replace("cluster", ""));
				
				//Console.traceln(Level.INFO, String.format("instance "+j+" is in cluster: "+cnumber));
				if ( selectedCluster.contains(cnumber) ) {
					// this only works if the index does not change
					selected.add(traindata.get(j));
					// check for differences, just one attribute, we are pretty sure the index does not change
					if ( traindata.get(j).value(3) != ctrain.get(j).value(3) ) {
						Console.traceln(Level.WARNING, String.format("we have a difference between train an ctrain!"));
					}
				}
			}
			
			Console.traceln(Level.INFO, String.format("that leaves us with: "+selected.numInstances()+" traindata instances from "+traindata.numInstances()));
		}catch( Exception e ) {
			Console.traceln(Level.WARNING, String.format("ERROR"));
			throw new RuntimeException("error in pointwise em", e);
		}
	
		return selected;
	}

}
