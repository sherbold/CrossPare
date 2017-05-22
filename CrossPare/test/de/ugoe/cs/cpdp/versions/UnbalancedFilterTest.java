package de.ugoe.cs.cpdp.versions;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.loader.CSVFolderLoader;


// TODO update test
public class UnbalancedFilterTest {

	@Test
	public void testApply() {
		CSVFolderLoader loader = new CSVFolderLoader();
		loader.setLocation("testdata/JURECZKO");
		List<SoftwareVersion> versions = loader.load();
		
		UnbalancedFilter filter = new UnbalancedFilter();
		filter.apply(versions);
		
		assertEquals(57, versions.size());
	}

}
