package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.Stats;

/**
 * Normalizes each attribute of each data set separately. 
 * @author Steffen Herbold
 */
public class Normalization implements ISetWiseProcessingStrategy, IProcessesingStrategy {

	/**
	 * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		final Attribute classAtt = testdata.classAttribute();
		
		for( int i=0 ; i<testdata.numAttributes(); i++) {
			if( !testdata.attribute(i).equals(classAtt) ) {
				Stats teststats = testdata.attributeStats(i).numericStats;
				
				double minVal = teststats.min;
				double maxVal = teststats.max;
				
				for( Instances traindata : traindataSet ) {
					Stats trainstats = traindata.attributeStats(i).numericStats;
					if( minVal>trainstats.min ) {
						minVal = trainstats.min;
					}
					if( maxVal<trainstats.max ) {
						maxVal = trainstats.max;
					}
				}
	
				for( int j=0 ; j<testdata.numInstances() ; j++ ) {
					Instance inst = testdata.instance(j);
					double newValue = (inst.value(i)-minVal)/(maxVal-minVal);
					inst.setValue(i, newValue);
				}
				
				for( Instances traindata : traindataSet ) {
					for( int j=0 ; j<traindata.numInstances() ; j++ ) {
						Instance inst = traindata.instance(j);
						double newValue = (inst.value(i)-minVal)/(maxVal-minVal);
						inst.setValue(i, newValue);
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
		final Attribute classAtt = testdata.classAttribute();
		
		for( int i=0 ; i<testdata.numAttributes(); i++) {
			if( !testdata.attribute(i).equals(classAtt) ) {
				Stats teststats = testdata.attributeStats(i).numericStats;
				
				double minVal = teststats.min;
				double maxVal = teststats.max;
				
				Stats trainstats = traindata.attributeStats(i).numericStats;
				if( minVal>trainstats.min ) {
					minVal = trainstats.min;
				}
				if( maxVal<trainstats.max ) {
					maxVal = trainstats.max;
				}
	
				for( int j=0 ; j<testdata.numInstances() ; j++ ) {
					Instance inst = testdata.instance(j);
					double newValue = (inst.value(i)-minVal)/(maxVal-minVal);
					inst.setValue(i, newValue);
				}
				
				for( int j=0 ; j<traindata.numInstances() ; j++ ) {
					Instance inst = traindata.instance(j);
					double newValue = (inst.value(i)-minVal)/(maxVal-minVal);
					inst.setValue(i, newValue);
				}
			}
		}
	}

	/**
	 * Does not have parameters. String is ignored.
	 * @param parameters ignored
	 */
	@Override
	public void setParameter(String parameters) {
		// no parameters
	}
}
