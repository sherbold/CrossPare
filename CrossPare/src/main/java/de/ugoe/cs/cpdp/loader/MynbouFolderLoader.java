package de.ugoe.cs.cpdp.loader;

/**
 * Loader for data generated with mynbou.
 * @author sherbold
 */
public class MynbouFolderLoader extends AbstractFolderLoader {

	/*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
     */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new MynbouDataLoader();
	}

}
