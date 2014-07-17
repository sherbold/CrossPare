package de.ugoe.cs.cpdp.dataselection;

import de.ugoe.cs.cpdp.IParameterizable;

import weka.core.Instances;

/**
 * Interface for pointwise data selection strategies. 
 * @author Steffen Herbold
 */
public interface IPointWiseDataselectionStrategy extends IParameterizable {

	/**
	 * Applies the data selection strategy. 
	 * @param testdata test data
	 * @param traindata candidate training data
	 * @return the selected training data
	 */
	Instances apply(Instances testdata, Instances traindata);
}
