package de.ugoe.cs.cpdp.loader;

public class CSVMockusFolderLoader extends AbstractFolderLoader {

	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new CSVMockusDataLoader();
	}
}
