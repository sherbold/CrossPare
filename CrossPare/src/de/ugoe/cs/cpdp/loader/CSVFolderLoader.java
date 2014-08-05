package de.ugoe.cs.cpdp.loader;

/**
 * Implements the {@link AbstractFolderLoader} for data from the PROMISE
 * repository mined by Jurezko and Madeyski.
 * 
 * @author Steffen Herbold
 */
public class CSVFolderLoader extends AbstractFolderLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
	 */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new CSVDataLoader();
	}

}
