package de.ugoe.cs.cpdp.versions;

import java.util.List;

import de.ugoe.cs.cpdp.IParameterizable;

/**
 * Implements the interface for a {@link SoftwareVersion} filter. 
 * @author Steffen Herbold
 */
public interface IVersionFilter extends IParameterizable {

	/**
	 * Applies the filter to a single version. 
	 * @param version the version
	 * @return true if filter applies to version, false otherwise
	 */
	boolean apply(SoftwareVersion version);
	
	/**
	 * Applies the filter a a list of versions. Versions were the filter applies are automatically removed from the list. 
	 * @param versions list of versions
	 * @return number of removed versions
	 */
	int apply(List<SoftwareVersion> versions);
}
