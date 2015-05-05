package de.ugoe.cs.cpdp.loader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import weka.core.Instances;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * Implements the {@link AbstractFolderLoader}
 * 
 * @author Fabian Trautsch
 */
public class DecentFolderLoader extends AbstractFolderLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
	 */
	@Override
	protected SingleVersionLoader getSingleLoader() {
		return new DecentDataLoader();
	}
	
	/**
	 * @see de.ugoe.cs.cpdp.loader.IVersionLoader#load()
	 */
	@Override
	public List<SoftwareVersion> load() {
		final List<SoftwareVersion> versions = new LinkedList<SoftwareVersion>();

		final File dataDir = new File(path);
		final SingleVersionLoader instancesLoader = getSingleLoader();

		String projectName = dataDir.getName();
		
		
		/*
		 * The following lines make it possible, that we can have two different possibilities
		 * to load data:
		 * 1) From one project (e.g. /decent/input/project1)
		 * 2) From more than one project (e.g. /decent/input/)
		 * 
		 * Requirement is, that we have a folder structure like this:
		 * "/decent/input/project1/model.decent, /decent/input/project2/model.decent, ..."
		 * 
		 * In the first one the "else" is executed, therefore it will just search the folder "project1"
		 * for a "model.decent" file. In the second one, it will look into each folder and searches for
		 * "model.decent" files.
		 */
		for (File projectDir : dataDir.listFiles()) {
			if (projectDir.isDirectory()) {
				projectName = projectDir.getName();
				for (File versionFile : projectDir.listFiles()) {
					loadDataFromFile(versionFile,instancesLoader, projectName, versions);
				}
			} else {
				loadDataFromFile(projectDir, instancesLoader, projectName, versions);
			}
		}
		return versions;
	}
	
	/**
	 * Loads data from a file and adds the instances from the load method to the 
	 * versions List.
	 * 
	 * @param versionFile file to load from
	 * @param instancesLoader loader that should be used
	 * @param projectName name of the project which was loaded
	 * @param versions list, where the weka instances are added to
	 */
	
	private void loadDataFromFile(File versionFile, 
			SingleVersionLoader instancesLoader, String projectName, List<SoftwareVersion> versions) {
		if (versionFile.isFile()
				&& instancesLoader.filenameFilter(versionFile
						.getName())) {
			String versionName = versionFile.getName();
			Instances data = instancesLoader.load(versionFile);
			versions.add(new SoftwareVersion(projectName,
					versionName, data));
		}
	}

}
