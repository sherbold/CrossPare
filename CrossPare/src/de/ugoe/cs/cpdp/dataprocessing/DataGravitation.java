package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

// TODO comment
public class DataGravitation implements IProcessesingStrategy, ISetWiseProcessingStrategy {

	/**
	 * Does not have parameters. String is ignored.
	 * @param parameters ignored
	 */
	@Override
	public void setParameter(String parameters) {
		// dummy
	}
	
	/* (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		for( Instances traindata : traindataSet ) {
			apply(testdata, traindata);
		}
	}

	/* (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
	 */
	@Override
	public void apply(Instances testdata, Instances traindata) {
		Attribute classAtt = testdata.classAttribute();
		
		double[] minAttValues = new double[testdata.numAttributes()];
		double[] maxAttValues = new double[testdata.numAttributes()];
		double[] weights = new double[traindata.numInstances()];
		double weightsum = 0.0;
		
		for( int j=0; j<testdata.numAttributes(); j++) {
			if( testdata.attribute(j)!=classAtt ) {
				minAttValues[j] = testdata.attributeStats(j).numericStats.min;
				maxAttValues[j] = testdata.attributeStats(j).numericStats.max;
			}
		}
		
		for( int i=0; i<traindata.numInstances(); i++ ) {
			Instance inst = traindata.instance(i);
			int similar = 0;
			for( int j=0; j<testdata.numAttributes(); j++ ) {
				if( testdata.attribute(j)!=classAtt ) {
					if( inst.value(j)>=minAttValues[j] && inst.value(j)<=maxAttValues[j] )  {
						similar++;
					}
				}
			}
			weights[i] = similar/Math.sqrt(testdata.numAttributes()-similar);
			weightsum += weights[i];
		}
		for( int i=0; i<traindata.numInstances(); i++ ) {
			traindata.instance(i).setWeight(weights[i]*traindata.numInstances()/weightsum);
		}
	}

}
