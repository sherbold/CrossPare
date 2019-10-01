package de.ugoe.cs.cpdp.loader;

import static org.junit.Assert.*;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

// TODO update test
public class MynbouFolderLoaderTest {

	@Test
	public void testLoadVersions() {
		MynbouFolderLoader loader = new MynbouFolderLoader();
		loader.setLocation("testdata/mynbou");
		//loader.setLocation("C:\\temp\\MYNBOU");
		List<SoftwareVersion> versions = loader.load();
		for( SoftwareVersion version : versions ) {
			System.out.println(version.getProject()+"\t"+version.getVersion()+"\t"+version.getReleaseDate());
		}
		for (MemoryPoolMXBean mpBean: ManagementFactory.getMemoryPoolMXBeans()) {
		    if (mpBean.getType() == MemoryType.HEAP) {
		        System.out.printf(
		            "Name: %s: %s\n",
		            mpBean.getName(), mpBean.getUsage()
		        );
		    }
		}
		assertEquals(4, versions.size());
	}

}
