package de.ugoe.cs.cpdp;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExperimentConfigurationTest {

	@Test
	public void validateConfigurations() {
		/*validateConfigurations("config_shared");
		validateConfigurations("config_singleclassifier");
		validateConfigurations("config_bagging");*/
		validateConfigurations("exp-java/config");
		validateConfigurations("exp-nasa/config");
	}
	
	public void validateConfigurations(String folder) {
		File configFolder = new File(folder);
		boolean error = false;
		for( File configFile : configFolder.listFiles() ) {
			try {
				if( configFile.isFile() ) {
					new ExperimentConfiguration(configFile);
				}
			} catch (Exception e) {
				System.err.println("Failure initializing the experiment configuration for configuration file " + configFile);
				e.printStackTrace();
				error = true;
			}
		}
		if(error) fail();
	}

}
