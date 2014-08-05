package de.ugoe.cs.cpdp.loader;

/**
 * Implements the {@link AbstractFolderLoader} for standard ARFF files.
 * 
 * @author Steffen Herbold
 * 
 */
public class ARFFFolderLoader extends AbstractFolderLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
	 */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new ARFFLoader();
	}
}
