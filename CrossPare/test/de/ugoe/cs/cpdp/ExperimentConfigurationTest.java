package de.ugoe.cs.cpdp;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 * Tests the ExperimentConfiguration
 * </p>
 * 
 * @author Steffen Herbold
 */
public class ExperimentConfigurationTest {

	@Test
	public void validateConfigurations() {
		validateConfigurations("testdata/configurations");
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
