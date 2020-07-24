package de.ugoe.cs.cpdp.loader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instance;

public class JitFolderLoaderTest {

	@Test
	public void testLoadVersions() {
		JitFolderLoader loader = new JitFolderLoader();
		loader.setLocation("testdata/jit");
		//loader.setParameter("adhoc);
		loader.setParameter("jira");
		List<SoftwareVersion> versions = loader.load();
		List<Integer> numberOfAttributes = new ArrayList<Integer>();
		for( SoftwareVersion version : versions ) {
			numberOfAttributes.add(version.getInstances().numAttributes());
			System.out.println(version.getProject()+"\t"+"First commit: "+version.getCommitterDates().get(0)+"\t"+"Number of instances: "+version.getInstances().size());
			assertEquals(version.getInstances().size(), version.getBugMatrix().size());
			assertEquals(version.getInstances().size(), version.getCommitterDates().size());
			for(Instance inst : version.getBugMatrix()) {
				for(int i=0; i<inst.numAttributes(); ++i) {
					String keyword = inst.attribute(i).name().substring(0,5);
					//assertEquals("adhoc", keyword);
					assertNotEquals("adhoc", keyword);
				}
			}
		}
		System.out.println("Number of Attributes:"+'\t'+numberOfAttributes.get(0));
		assertEquals(numberOfAttributes.get(0), numberOfAttributes.get(1));
		assertEquals(2, versions.size());
    }
    
}