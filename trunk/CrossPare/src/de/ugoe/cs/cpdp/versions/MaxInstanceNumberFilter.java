package de.ugoe.cs.cpdp.versions;

/**
 * Applies to large data sets. All data sets that have more than the required maximum number of instances are removed. 
 * @author Steffen Herbold
 */
public class MaxInstanceNumberFilter extends AbstractVersionFilter {

	/**
	 * maximum number of instances required
	 */
	private int maxInstances = 0;
	
	/**
	 * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
	 */
	@Override
	public boolean apply(SoftwareVersion version) {
		return version.getInstances().numInstances()>maxInstances;
	}

	/**
	 * Sets the minimal number of instances.
	 * @param parameters number of instances
	 */
	@Override
	public void setParameter(String parameters) {
		maxInstances = Integer.parseInt(parameters);
	}

}
