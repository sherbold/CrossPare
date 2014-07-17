package de.ugoe.cs.cpdp.loader;

/**
 * Implements a {@link IVersionLoader} for data from // TODO data reference
 * Each folder contained in the defined location ({@link #setLocation(String)}) represents a project, the data files
 * within the versions.  
 * @author Steffen Herbold
 */
public class CSVFolderLoader extends AbstractFolderLoader {

	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new CSVDataLoader();
	}

	
}
