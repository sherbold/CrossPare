package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * N4 after "Transfer Defect Learning" by Jaechang Nam, Sinno Jialin Pan, and Sunghun Kim.
 * 
 * @author Steffen Herbold
 */
public class ZScoreTargetNormalization implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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
		final double[] stddevTest = new double[testdata.numAttributes()];
		
		// get means of testdata
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				meanTest[j] = testdata.meanOrMode(j);
				stddevTest[j] = Math.sqrt(testdata.variance(j));
			}
		}
		
		// preprocess test data
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-meanTest[j]/stddevTest[j]);
				}
			}
		}
		
		// preprocess training data
		for( Instances traindata : traindataSet ) {			
			for( int i=0 ; i<traindata.numInstances() ; i++ ) {
				Instance instance = traindata.instance(i);
				for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
					if( testdata.attribute(j)!=classAttribute ) {
						instance.setValue(j, instance.value(j)-meanTest[j]/stddevTest[j]);
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
		final double[] stddevTest = new double[testdata.numAttributes()];
		
		// get means of testdata
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				meanTest[j] = testdata.meanOrMode(j);
				stddevTest[j] = Math.sqrt(testdata.variance(j));
			}
		}
		
		// preprocess test data
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-meanTest[j]/stddevTest[j]);
				}
			}
		}
		
		// preprocess training data
		for( int i=0 ; i<traindata.numInstances() ; i++ ) {
			Instance instance = traindata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-meanTest[j]/stddevTest[j]);
				}
			}
		}
	}
}
