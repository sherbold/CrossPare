package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.IParameterizable;

import weka.core.Instances;

/**
 * A data processing strategy that is applied to the test data and a multiple sets of training data. 
 * @author Steffen Herbold
 */
public interface ISetWiseProcessingStrategy extends IParameterizable {

	/**
	 * Applies the processing strategy. 
	 * @param testdata test data
	 * @param traindataSet training data sets
	 */
	void apply(Instances testdata, SetUniqueList<Instances> traindataSet);
	
}
