package de.ugoe.cs.cpdp.versions;

import weka.core.Instances;

/**
 * Removes unbalanced data sets in terms of classification. All data sets that are outside of the quantil defined
 * by setParameter (default=0.1) are removed. 
 * @author Steffen Herbold
 */
public class UnbalancedFilter extends AbstractVersionFilter {

	/**
	 * quantil where outside lying versions are removed
	 */
	private double quantil = 0.1;
	
	/**
	 * Sets the quantil.
	 * @param parameters the quantil as string
	 */
	@Override
	public void setParameter(String parameters) {
		quantil = Double.parseDouble(parameters);
	}

	/**
	 * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
	 */
	@Override
	public boolean apply(SoftwareVersion version) {
		final Instances instances = version.getInstances();
		
		final int[] counts = instances.attributeStats(instances.classIndex()).nominalCounts;
		return ((double) counts[0])/instances.numInstances() >= (1-quantil) ||
			((double) counts[0])/instances.numInstances() <= (quantil);
	}

}
