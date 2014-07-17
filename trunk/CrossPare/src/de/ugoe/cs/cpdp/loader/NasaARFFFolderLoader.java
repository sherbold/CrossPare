package de.ugoe.cs.cpdp.loader;

public class NasaARFFFolderLoader extends AbstractFolderLoader {

	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new NasaARFFLoader();
	}

}
