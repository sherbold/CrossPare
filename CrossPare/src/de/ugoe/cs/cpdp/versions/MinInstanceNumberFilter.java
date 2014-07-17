package de.ugoe.cs.cpdp.versions;

/**
 * Applies to small data sets. All data sets that do not have the required minimal number of instances are removed. 
 * @author Steffen Herbold
 */
public class MinInstanceNumberFilter extends AbstractVersionFilter {

	/**
	 * minimal number of instances required
	 */
	private int minInstances = 0;
	
	/**
	 * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
	 */
	@Override
	public boolean apply(SoftwareVersion version) {
		return version.getInstances().numInstances()<minInstances;
	}

	/**
	 * Sets the minimal number of instances.
	 * @param parameters number of instances
	 */
	@Override
	public void setParameter(String parameters) {
		minInstances = Integer.parseInt(parameters);
	}

}
