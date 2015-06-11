package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * Resamples the data with WEKA {@link Resample} to have a uniform distribution among all classes.   
 * @author Steffen Herbold
 */
public class Resampling implements IProcessesingStrategy,
		ISetWiseProcessingStrategy {

	
	/**
	 * Does not have parameters. String is ignored.
	 * @param parameters ignored
	 */
	@Override
	public void setParameter(String parameters) {
		// dummy
	}

	/*
	 * (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		for( Instances traindata : traindataSet ) {
			apply(testdata, traindata);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
	 */
	@Override
	public void apply(Instances testdata, Instances traindata) {
		Resample resample = new Resample();
		resample.setSampleSizePercent(100);
		resample.setBiasToUniformClass(1.0);
		
		Instances traindataSample;
		try {
			resample.setInputFormat(traindata);
			traindataSample = Filter.useFilter(traindata, resample);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		traindata.clear();
		for( int i=0 ; i<traindataSample.size() ; i++ ) {
			traindata.add(traindataSample.get(i));
		}
	}

}
