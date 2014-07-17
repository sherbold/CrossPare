package de.ugoe.cs.cpdp.dataselection;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * Uses the test data as training data.
 * @author Steffen Herbold
 *
 */
public class TestAsTraining implements ISetWiseDataselectionStrategy {

	/**
	 * no parameters
	 */
	@Override
	public void setParameter(String parameters) {
		// dummy
	}

	/**(non-Javadoc)
	 * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		traindataSet.clear();
		traindataSet.add(new Instances(testdata));
	}

}
