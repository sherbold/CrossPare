package de.ugoe.cs.cpdp.dataprocessing;

import weka.core.Instances;
import de.ugoe.cs.cpdp.IParameterizable;

/**
 * A data processing strategy that is applied to the test data and a single set of training data. 
 * @author Steffen Herbold
 */
public interface IProcessesingStrategy extends IParameterizable {
	
	/**
	 * Applies the processing strategy. 
	 * @param testdata test data
	 * @param traindata training data
	 */
	void apply(Instances testdata, Instances traindata);
}
