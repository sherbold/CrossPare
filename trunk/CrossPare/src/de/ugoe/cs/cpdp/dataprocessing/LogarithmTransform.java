package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Logarithm transformation after Carmargo Cruz and Ochimizu: Towards Logistic Regression Models for Predicting Fault-prone Code across Software Projects.
 * <br><br>
 * Transform each attribute value x into log(x+1). 
 * @author Steffen Herbold
 */
public class LogarithmTransform implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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
		
		// preprocess testdata
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, Math.log(1+instance.value(j)));
				}
			}
		}
		
		// preprocess training data
		for( Instances traindata : traindataSet ) {
			for( int i=0 ; i<traindata.numInstances() ; i++ ) {
				Instance instance = traindata.instance(i);
				for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
					if( testdata.attribute(j)!=classAttribute ) {
						instance.setValue(j, Math.log(1+instance.value(j)));
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
		
		// preprocess testdata
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, Math.log(1+instance.value(j)));
				}
			}
		}
		
		// preprocess training data
		for( int i=0 ; i<traindata.numInstances() ; i++ ) {
			Instance instance = traindata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, Math.log(1+instance.value(j)));
				}
			}
		}
	}
}
