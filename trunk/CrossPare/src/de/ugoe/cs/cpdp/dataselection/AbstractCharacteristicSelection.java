package de.ugoe.cs.cpdp.dataselection;

import java.util.ArrayList;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.Stats;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 * Abstract class that implements the foundation of setwise data selection strategies using distributional characteristics.
 * This class provides the means to transform the data sets into their characteristic vectors.  
 * @author Steffen Herbold
 */
public abstract class AbstractCharacteristicSelection implements
		ISetWiseDataselectionStrategy {

	/**
	 * vector with the distributional characteristics 
	 */
	private String[] characteristics = new String[]{"mean","stddev"};
	
	/**
	 * Sets the distributional characteristics. The names of the characteristics are separated by blanks. 
	 */
	@Override
	public void setParameter(String parameters) {
		if( !"".equals(parameters) ) {
			characteristics = parameters.split(" ");
		}
	}
	
	/**
	 * Transforms the data into the distributional characteristics. The first instance is the test data, followed by the training data. 
	 * @param testdata test data
	 * @param traindataSet training data sets
	 * @return distributional characteristics of the data
	 */
	protected Instances characteristicInstances(Instances testdata, SetUniqueList<Instances> traindataSet) {
		// setup weka Instances for clustering
		final ArrayList<Attribute> atts = new ArrayList<Attribute>();
		
		final Attribute classAtt = testdata.classAttribute();
		for( int i=0 ; i<testdata.numAttributes() ; i++ ) {
			Attribute dataAtt = testdata.attribute(i);
			if( !dataAtt.equals(classAtt) ) {
				for( String characteristic : characteristics ) {
					atts.add(new Attribute(dataAtt.name() + "_" + characteristic));
				}
			}
		}
		final Instances data = new Instances("distributional_characteristics", atts, 0);
		
		// setup data for clustering
		double[] instanceValues = new double[atts.size()];
		for( int i=0 ; i<testdata.numAttributes() ; i++ ) {
			Attribute dataAtt = testdata.attribute(i);
			if( !dataAtt.equals(classAtt) ) {
				Stats stats = testdata.attributeStats(i).numericStats;
				for( int j=0; j<characteristics.length; j++ ) {
					if( "mean".equals(characteristics[j]) ) {
						instanceValues[i*characteristics.length+j] = stats.mean;
					} else if( "stddev".equals(characteristics[j])) {
						instanceValues[i*characteristics.length+j] = stats.stdDev;
					} else if( "var".equals(characteristics[j])) {
						instanceValues[i*characteristics.length+j] = testdata.variance(j);
					} else {
						throw new RuntimeException("Unkown distributional characteristic: " + characteristics[j]);
					}
				}
			}
		}		
		data.add(new DenseInstance(1.0, instanceValues));
		
		for( Instances traindata : traindataSet ) {
			instanceValues = new double[atts.size()];
			for( int i=0 ; i<traindata.numAttributes() ; i++ ) {
				Attribute dataAtt = traindata.attribute(i);
				if( !dataAtt.equals(classAtt) ) {
					Stats stats = traindata.attributeStats(i).numericStats;
					for( int j=0; j<characteristics.length; j++ ) {
						if( "mean".equals(characteristics[j]) ) {
							instanceValues[i*characteristics.length+j] = stats.mean;
						} else if( "stddev".equals(characteristics[j])) {
							instanceValues[i*characteristics.length+j] = stats.stdDev;
						} else if( "var".equals(characteristics[j])) {
							instanceValues[i*characteristics.length+j] = testdata.variance(j);
						} else {
							throw new RuntimeException("Unkown distributional characteristic: " + characteristics[j]);
						}
					}
				}
			}		
			Instance instance = new DenseInstance(1.0, instanceValues);
			
			data.add(instance);
		}
		return data;
	}
	
	/**
	 * Returns the normalized distributional characteristics of the training data. 
	 * @param testdata test data
	 * @param traindataSet training data sets
	 * @return normalized distributional characteristics of the data
	 */
	protected Instances normalizedCharacteristicInstances(Instances testdata, SetUniqueList<Instances> traindataSet) {
		Instances data = characteristicInstances(testdata, traindataSet);
		try {
			final Normalize normalizer = new Normalize();
			normalizer.setInputFormat(data);
			data = Filter.useFilter(data, normalizer);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception during normalization of distributional characteristics.", e);
		}
		return data;
	}
}
