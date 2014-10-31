package de.ugoe.cs.cpdp.training;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.io.output.NullOutputStream;

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
 * WekaClusterTraining2
 * 
 * Currently supports only EM Clustering.
 * 
 * 1. Cluster training data
 * 2. for each cluster train a classifier with training data from cluster
 * 3. match test data instance to a cluster, then classify with classifier from the cluster
 * 
 * XML configuration:
 * <!-- because of clustering -->
 * <preprocessor name="Normalization" param=""/>
 * 
 * <!-- cluster trainer -->
 * <trainer name="WekaClusterTraining2" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 */
public class WekaClusterTraining2 extends WekaBaseTraining2 implements ITrainingStrategy {

	private final TraindatasetCluster classifier = new TraindatasetCluster();
	
	@Override
	public Classifier getClassifier() {
		return classifier;
	}
	
	@Override
	public void apply(Instances traindata) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			classifier.buildClassifier(traindata);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}
	

	public class TraindatasetCluster extends AbstractClassifier {
		
		private static final long serialVersionUID = 1L;

		private EM clusterer = null;

		private HashMap<Integer, Classifier> cclassifier;
		private HashMap<Integer, Instances> ctraindata; 
		
		
		/**
		 * Helper method that gives us a clean instance copy with 
		 * the values of the instancelist of the first parameter. 
		 * 
		 * @param instancelist with attributes
		 * @param instance with only values
		 * @return copy of the instance
		 */
		private Instance createInstance(Instances instances, Instance instance) {
			// attributes for feeding instance to classifier
			Set<String> attributeNames = new HashSet<>();
			for( int j=0; j<instances.numAttributes(); j++ ) {
				attributeNames.add(instances.attribute(j).name());
			}
			
			double[] values = new double[instances.numAttributes()];
			int index = 0;
			for( int j=0; j<instance.numAttributes(); j++ ) {
				if( attributeNames.contains(instance.attribute(j).name())) {
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
				
				// 5. classify instance with class attribute to the classifier of that cluster number
				ret = cclassifier.get(cnum).classifyInstance(classInstance);
				
			}catch( Exception e ) {
				Console.traceln(Level.INFO, String.format("ERROR matching instance to cluster!"));
				throw new RuntimeException(e);
			}
			return ret;
		}

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
			
			// 3. cluster data
			// use standard params for now
			clusterer = new EM();
			// we can set options like so:
			//String[] params = {"-N", "100"};
			//clusterer.setOptions(params);
			
			// set max num of clusters to train data size (although we do not want that)
			clusterer.setMaximumNumberOfClusters(train.size());
						
			// build clusterer
			clusterer.buildClusterer(train);
			
			Instances ctrain = new Instances(train);
			
			// get train data per cluster
			int cnumber;
			for ( int j=0; j < ctrain.numInstances(); j++ ) {
				cnumber = clusterer.clusterInstance(ctrain.get(j));
				
				// add training data to list of instances for this cluster number
				if ( !ctraindata.containsKey(cnumber) ) {
					ctraindata.put(cnumber, new Instances(traindata));
					ctraindata.get(cnumber).delete();
				}
				ctraindata.get(cnumber).add(traindata.get(j));
			}
			
			// train one classifier per cluster, we get the cluster number from the training data
			Iterator<Integer> clusternumber = ctraindata.keySet().iterator();
			while ( clusternumber.hasNext() ) {
				cnumber = clusternumber.next();			
				cclassifier.put(cnumber,setupClassifier());
				cclassifier.get(cnumber).buildClassifier(ctraindata.get(cnumber));
				
				//Console.traceln(Level.INFO, String.format("classifier in cluster "+cnumber));
			}
		}
	}
}
