package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

// TODO comment
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


	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		for( Instances traindata : traindataSet ) {
			apply(testdata, traindata);
		}
	}

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
