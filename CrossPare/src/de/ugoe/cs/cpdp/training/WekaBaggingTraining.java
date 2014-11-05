package de.ugoe.cs.cpdp.training;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.io.output.NullOutputStream;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Programmatic WekaBaggingTraining
 *
 * first parameter is Trainer Name.
 * second parameter is class name
 * 
 * all subsequent parameters are configuration params (for example for trees)
 * Cross Validation params always come last and are prepended with -CVPARAM
 * 
 * XML Configurations for Weka Classifiers:
 * <pre>
 * {@code
 * <!-- examples -->
 * <setwisetrainer name="WekaBaggingTraining" param="NaiveBayesBagging weka.classifiers.bayes.NaiveBayes" />
 * <setwisetrainer name="WekaBaggingTraining" param="LogisticBagging weka.classifiers.functions.Logistic -R 1.0E-8 -M -1" />
 * }
 * </pre>
 * 
 */
public class WekaBaggingTraining extends WekaBaseTraining implements ISetWiseTrainingStrategy {

	private final TraindatasetBagging classifier = new TraindatasetBagging();
	
	@Override
	public Classifier getClassifier() {
		return classifier;
	}
	
	@Override
	public void apply(SetUniqueList<Instances> traindataSet) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			classifier.buildClassifier(traindataSet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}
	
	public class TraindatasetBagging extends AbstractClassifier {
		
		private static final long serialVersionUID = 1L;

		private List<Instances> trainingData = null;
		
		private List<Classifier> classifiers = null;
	
		@Override
		public double classifyInstance(Instance instance) {
			if( classifiers==null ) {
				return 0.0;
			}
			
			double classification = 0.0;
			for( int i=0 ; i<classifiers.size(); i++ ) {
				Classifier classifier = classifiers.get(i);
				Instances traindata = trainingData.get(i);
				
				Set<String> attributeNames = new HashSet<>();
				for( int j=0; j<traindata.numAttributes(); j++ ) {
					attributeNames.add(traindata.attribute(j).name());
				}
				
				double[] values = new double[traindata.numAttributes()];
				int index = 0;
				for( int j=0; j<instance.numAttributes(); j++ ) {
					if( attributeNames.contains(instance.attribute(j).name())) {
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
				} catch (Exception e) {
					throw new RuntimeException("bagging classifier could not classify an instance", e);
				}
			}
			classification /= classifiers.size();
			return (classification>=0.5) ? 1.0 : 0.0;
		}
		
		public void buildClassifier(SetUniqueList<Instances> traindataSet) throws Exception {
			classifiers = new LinkedList<>();
			trainingData = new LinkedList<>();
			for( Instances traindata : traindataSet ) {
				Classifier classifier = setupClassifier();
				classifier.buildClassifier(traindata);
				classifiers.add(classifier);
				trainingData.add(new Instances(traindata));
			}
		}
	
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
