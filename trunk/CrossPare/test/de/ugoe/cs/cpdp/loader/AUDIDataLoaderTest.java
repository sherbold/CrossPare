package de.ugoe.cs.cpdp.loader;

import java.io.File;

import org.junit.Test;

public class AUDIDataLoaderTest {

	@Test
	public void load() {
		AUDIDataLoader loader = new AUDIDataLoader();	
		File file = new File(ClassLoader.getSystemResource("A_mask_metric_src.csv").getFile());
		loader.load(file);
	}

}
