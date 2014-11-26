package de.ugoe.cs.cpdp.versions;

import weka.core.Instances;

/**
 * Applies to small data sets. All data sets that do not have the required minimal number of instances in each class (i.e., positive, negative) are removed. 
 * @author Steffen Herbold
 */
public class MinClassNumberFilter extends AbstractVersionFilter {

	/**
	 * minimal number of instances required
	 */
	private int minInstances = 0;
	
	/**
	 * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
	 */
	@Override
	public boolean apply(SoftwareVersion version) {
		Instances instances = version.getInstances();
		int[] counts = instances.attributeStats(instances.classIndex()).nominalCounts;
		boolean toSmall = false;
		for( int count : counts ) {
			toSmall |= count<minInstances;
		}
		return toSmall;
	}

	/**
	 * Sets the minimal number of instances for each class.
	 * @param parameters number of instances
	 */
	@Override
	public void setParameter(String parameters) {
		minInstances = Integer.parseInt(parameters);
	}

}
