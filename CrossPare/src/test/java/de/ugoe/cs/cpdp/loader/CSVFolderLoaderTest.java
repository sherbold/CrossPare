package de.ugoe.cs.cpdp.loader;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

// TODO update test
public class CSVFolderLoaderTest {

	@Test
	public void testLoadVersions() {
		CSVFolderLoader loader = new CSVFolderLoader();
		loader.setLocation("testdata/JURECZKO");
		List<SoftwareVersion> versions = loader.load();
		
		assertEquals(65, versions.size());
	}

}
