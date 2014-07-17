package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Standardization procedure after Watanabe et al.: Adapting a Fault Prediction Model to Allow Inter Language Reuse.
 * <br><br>
 * In comparison to Watanabe et al., we transform training data instead of the test data. Otherwise, this approach would not be feasible with multiple projects.
 * @author Steffen Herbold
 */
public class AverageStandardization implements ISetWiseProcessingStrategy, IProcessesingStrategy {

	/**
	 * Does not have parameters. String is ignored.
	 * @param parameters ignored
	 */
	@Override
	public void setParameter(String parameters) {
		// dummy
	}

	/**
	 * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		final Attribute classAttribute = testdata.classAttribute();
		
		final double[] meanTest = new double[testdata.numAttributes()];
		
		// get means of testdata
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				meanTest[j] = testdata.meanOrMode(j);
			}
		}
		
		// preprocess training data
		for( Instances traindata : traindataSet ) {
			double[] meanTrain = new double[testdata.numAttributes()];
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					meanTrain[j] = traindata.meanOrMode(j);
				}
			}
			
			for( int i=0 ; i<traindata.numInstances() ; i++ ) {
				Instance instance = traindata.instance(i);
				for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
					if( testdata.attribute(j)!=classAttribute ) {
						instance.setValue(j, instance.value(j)*meanTest[j]/meanTrain[j]);
					}
				}
			}
		}
	}

	/**
	 * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
	 */
	@Override
	public void apply(Instances testdata, Instances traindata) {
		final Attribute classAttribute = testdata.classAttribute();
		
		final double[] meanTest = new double[testdata.numAttributes()];
		
		// get means of testdata
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				meanTest[j] = testdata.meanOrMode(j);
			}
		}
		
		// preprocess training data
		final double[] meanTrain = new double[testdata.numAttributes()];
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				meanTrain[j] = traindata.meanOrMode(j);
			}
		}
		
		for( int i=0 ; i<traindata.numInstances() ; i++ ) {
			Instance instance = traindata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)*meanTest[j]/meanTrain[j]);
				}
			}
		}
	}

}
