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
 * 1. Cluster traindata
 * 2. for each cluster train a classifier with traindata from cluster
 * 3. match testdata instance to a cluster, then classify with classifier from the cluster
 * 
 * XML config:
 * <!-- because of clustering -->
 * <preprocessor name="Normalization" param=""/>
 * 
 * <!-- cluster trainer -->
 * <trainer name="WekaClusterTraining2" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 * 
 * Questions:
 * - how do we configure the clustering params?
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

		private HashMap<Integer, Classifier> cclassifier = new HashMap<Integer, Classifier>();
		private HashMap<Integer, Instances> ctraindata = new HashMap<Integer, Instances>(); 
		
		
		
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
				Instances traindata = ctraindata.get(0);
				Instance classInstance = createInstance(traindata, instance);
				
				// remove class attribute before clustering
				Remove filter = new Remove();
				filter.setAttributeIndices("" + (traindata.classIndex() + 1));
				filter.setInputFormat(traindata);
				traindata = Filter.useFilter(traindata, filter);
				
				Instance clusterInstance = createInstance(traindata, instance);
				
				// 1. classify testdata instance to a cluster number
				int cnum = clusterer.clusterInstance(clusterInstance);
				
				// 2. classify testata instance to the classifier
				ret = cclassifier.get(cnum).classifyInstance(classInstance);
				
			}catch( Exception e ) {
				Console.traceln(Level.INFO, String.format("ERROR matching instance to cluster!"));
				throw new RuntimeException(e);
			}
			return ret;
		}

		
		
		@Override
		public void buildClassifier(Instances traindata) throws Exception {
			
			// 1. copy traindata
			Instances train = new Instances(traindata);
			
			// 2. remove class attribute for clustering
			Remove filter = new Remove();
			filter.setAttributeIndices("" + (train.classIndex() + 1));
			filter.setInputFormat(train);
			train = Filter.useFilter(train, filter);
			
			// 3. cluster data
			//Console.traceln(Level.INFO, String.format("starting clustering"));
			
			// use standard params for now
			clusterer = new EM();
			//String[] params = {"-N", "100"};
			//clusterer.setOptions(params);
			clusterer.buildClusterer(train);
			// set max num to traindata size
			clusterer.setMaximumNumberOfClusters(train.size());
			
			// 4. get cluster membership of our traindata
			//AddCluster cfilter = new AddCluster();
			//cfilter.setClusterer(clusterer);
			//cfilter.setInputFormat(train);
			//Instances ctrain = Filter.useFilter(train, cfilter);
			
			Instances ctrain = new Instances(train);
			
			// get traindata per cluster
			int cnumber;
			for ( int j=0; j < ctrain.numInstances(); j++ ) {
				// get the cluster number from the attributes, subract 1 because if we clusterInstance we get 0-n, and this is 1-n
				//cnumber = Integer.parseInt(ctrain.get(j).stringValue(ctrain.get(j).numAttributes()-1).replace("cluster", "")) - 1;
				
				cnumber = clusterer.clusterInstance(ctrain.get(j));
				// add training data to list of instances for this cluster number
				if ( !ctraindata.containsKey(cnumber) ) {
					ctraindata.put(cnumber, new Instances(traindata));
					ctraindata.get(cnumber).delete();
				}
				ctraindata.get(cnumber).add(traindata.get(j));
			}
			
			// train one classifier per cluster, we get the clusternumber from the traindata
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
