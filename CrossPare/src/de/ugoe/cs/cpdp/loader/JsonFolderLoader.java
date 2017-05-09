package de.ugoe.cs.cpdp.loader;

/**
 * Implements the {@link AbstractFolderLoader} for data from the SmartSHARK stored as JSON. 
 * 
 * @author Steffen Herbold
 */
public class JsonFolderLoader extends AbstractFolderLoader {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
     */
    @Override
    protected SingleVersionLoader getSingleLoader() {
        return new JsonDataLoader();
    }

}
