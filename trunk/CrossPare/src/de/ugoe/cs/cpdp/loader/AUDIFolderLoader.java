package de.ugoe.cs.cpdp.loader;

public class AUDIFolderLoader extends AbstractFolderLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
	 */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new AUDIDataLoader();
	}
}
