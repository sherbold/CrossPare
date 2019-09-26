package de.ugoe.cs.cpdp.versions;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.loader.CSVFolderLoader;
import de.ugoe.cs.cpdp.util.CrosspareUtils;

// TODO update test
public class InstanceNumberFilterTest {

	@Test
	public void testApply_1() {
		CSVFolderLoader loader = new CSVFolderLoader();
		loader.setLocation("testdata/JURECZKO");
		List<SoftwareVersion> versions = loader.load();
		
		int sizeVersions = versions.size();
		
		MinInstanceNumberFilter filter = new MinInstanceNumberFilter();
		List<IVersionFilter> filters = new LinkedList<>();
		filters.add(filter);
        CrosspareUtils.filterVersions(versions, filters);
		
		assertEquals(sizeVersions, versions.size());
	}
	
	@Test
	public void testApply_2() {
		CSVFolderLoader loader = new CSVFolderLoader();
		loader.setLocation("testdata/JURECZKO");
		List<SoftwareVersion> versions = loader.load();
		
		int expected = 0;
		for( SoftwareVersion version : versions ) {
			if(version.getInstances().numInstances() >= 100) expected++;
		}
		
		MinInstanceNumberFilter filter = new MinInstanceNumberFilter();
		filter.setParameter("100");
		List<IVersionFilter> filters = new LinkedList<>();
		filters.add(filter);
        CrosspareUtils.filterVersions(versions, filters);
		
		assertEquals(expected, versions.size());
	}
}
