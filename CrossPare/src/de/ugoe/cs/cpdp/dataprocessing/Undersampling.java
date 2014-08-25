package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * Implements undersampling, a strategy for handling bias in data. In case there are less positive samples (i.e. defect-prone) samples in the
 * data than negative samples (i.e. non-defect-prone), the non-defect-prone entities are sampled such thatthe number of defect-prone and non-defect-prone instances is the same afterwards.  
 * @author Steffen Herbold
 */
public class Undersampling implements IProcessesingStrategy,
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
		
		final int[] counts = traindata.attributeStats(traindata.classIndex()).nominalCounts;
		
		if( counts[1]<counts[0] ) {
			Instances negatives = new Instances(traindata);
			Instances positives = new Instances(traindata);
			
			for( int i=traindata.size()-1 ; i>=0 ; i-- ) {
				if( Double.compare(1.0, negatives.get(i).classValue())==0 ) {
					negatives.remove(i);
				}
				if( Double.compare(0.0, positives.get(i).classValue())==0 ) {
					positives.remove(i);
				}
			}
			
			Resample resample = new Resample();
			resample.setSampleSizePercent((100.0* counts[1])/counts[0]);
			try {
				resample.setInputFormat(traindata);
				negatives = Filter.useFilter(negatives, resample);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			traindata.clear();
			for( int i=0 ; i<negatives.size() ; i++ ) {
				traindata.add(negatives.get(i));
			}
			for( int i=0 ; i<positives.size() ; i++ ) {
				traindata.add(positives.get(i));
			}
		}
	}

}
