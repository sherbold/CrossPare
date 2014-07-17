package de.ugoe.cs.cpdp.dataselection;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.IParameterizable;

import weka.core.Instances;

/**
 * Interface for setwise data selection strategies.
 * @author Steffen Herbold
 */
public interface ISetWiseDataselectionStrategy extends IParameterizable {

	/**
	 * Applies a setwise data selection strategy. 
	 * @param testdata test data for which the training data is selected
	 * @param traindataSet candidate training data
	 */
	void apply(Instances testdata, SetUniqueList<Instances> traindataSet);
}
