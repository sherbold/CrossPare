package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Median as reference transformation after Carmargo Cruz and Ochimizu: Towards Logistic Regression Models for Predicting Fault-prone Code across Software Projects
 * <br><br>
 * For each attribute value x, the new value is x-median of the test data
 * @author Steffen Herbold
 */
public class MedianAsReference implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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
		
		final double[] median = new double[testdata.numAttributes()];
		
		// get medians
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				median[j] = testdata.kthSmallestValue(j, (testdata.numInstances()+1)>>1); // (>>2 -> /2)
			}
		}
		
		// update testdata
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-median[j]);
				}
			}
		}
		
		// preprocess training data
		for( Instances traindata : traindataSet ) {
			for( int i=0 ; i<traindata.numInstances() ; i++ ) {
				Instance instance = traindata.instance(i);
				for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
					if( testdata.attribute(j)!=classAttribute ) {
						instance.setValue(j, instance.value(j)-median[j]);
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
		
		final double[] median = new double[testdata.numAttributes()];
		
		// get medians
		for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
			if( testdata.attribute(j)!=classAttribute ) {
				median[j] = testdata.kthSmallestValue(j, (testdata.numInstances()+1)>>1); // (>>2 -> /2)
			}
		}
		
		// update testdata
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			Instance instance = testdata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-median[j]);
				}
			}
		}
		
		// preprocess training data
		for( int i=0 ; i<traindata.numInstances() ; i++ ) {
			Instance instance = traindata.instance(i);
			for( int j=0 ; j<testdata.numAttributes() ; j++ ) {
				if( testdata.attribute(j)!=classAttribute ) {
					instance.setValue(j, instance.value(j)-median[j]);
				}
			}
		}
	}

}
