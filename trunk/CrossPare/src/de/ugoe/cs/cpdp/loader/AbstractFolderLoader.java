package de.ugoe.cs.cpdp.loader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import weka.core.Instances;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;


public abstract class AbstractFolderLoader implements IVersionLoader {
	
	// TODO
	interface SingleVersionLoader {
		Instances load(File file);
		boolean filenameFilter(String filename);
	}

	/**
	 * Path of the data.
	 */
	private String path = "";
	
	/**
	 * @see de.ugoe.cs.cpdp.loader.IVersionLoader#setLocation(java.lang.String)
	 */
	@Override
	public void setLocation(String location) {
		path=location;
	}
	
	/**
	 * @see de.ugoe.cs.cpdp.loader.IVersionLoader#load()
	 */
	@Override
	public List<SoftwareVersion> load() {
		final List<SoftwareVersion> versions = new LinkedList<SoftwareVersion>();
		
		final File dataDir = new File(path);
		final SingleVersionLoader instancesLoader = getSingleLoader();
		
		for( File projectDir : dataDir.listFiles() ) {
			if( projectDir.isDirectory() ) {
				String projectName = projectDir.getName();
				for( File versionFile : projectDir.listFiles() ) {
					if( versionFile.isFile() && instancesLoader.filenameFilter(versionFile.getName()) ) {
						String versionName = versionFile.getName();
						Instances data = instancesLoader.load(versionFile);
						versions.add(new SoftwareVersion(projectName, versionName, data));
					}
				}
			}
		}
		return versions;
	}
	
	abstract protected SingleVersionLoader getSingleLoader();
}
