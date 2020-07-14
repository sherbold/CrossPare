package de.ugoe.cs.cpdp.loader;

/**
 * Loader for just in time data
 * @author jvdmosel
 */
public class JitFolderLoader extends AbstractFolderLoader {

	/*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
     */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new JitDataLoader();
	}
}
