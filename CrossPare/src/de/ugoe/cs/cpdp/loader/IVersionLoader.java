package de.ugoe.cs.cpdp.loader;

import java.util.List;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * Implements the interface for loading software versions from a data source.
 * 
 * @author Steffen Herbold
 */
public interface IVersionLoader {

	/**
	 * Sets the location of the data.
	 * 
	 * @param location
	 *            location of the data
	 */
	public void setLocation(String location);

	/**
	 * Loads the data.
	 * 
	 * @return the data
	 */
	public List<SoftwareVersion> load();

}
