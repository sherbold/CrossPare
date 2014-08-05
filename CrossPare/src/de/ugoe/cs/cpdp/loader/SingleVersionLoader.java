package de.ugoe.cs.cpdp.loader;

import java.io.File;

import weka.core.Instances;

/**
 * Interface for version loaders, i.e., loading of a set of instances from a
 * file
 * 
 * @author Steffen Herbold
 */
public interface SingleVersionLoader {

	/**
	 * Loads the instances.
	 * 
	 * @param file
	 *            handle to the file of the instances
	 * @return the instances
	 */
	Instances load(File file);

	/**
	 * Defines a filter for the files to be loaded; only strings that end with
	 * the filter are considered.
	 * 
	 * @param filename
	 *            string defining the filename filter
	 * @return true if a filename shall be considered
	 */
	boolean filenameFilter(String endsWith);
}