package de.ugoe.cs.cpdp.dataprocessing;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class BugDateFilterTest {

	@Test
	public void test() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		// setup test version
		SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "2.0", null, null, null, null, LocalDateTime.parse("2008-01-01 12:12:12", formatter), null);
		
		// setup train version
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        Instances traindataSetup = new Instances("traindata", attributes, 0);
        traindataSetup.setClassIndex(1);
        traindataSetup.add(new DenseInstance(1.0, new double[] {1.0, 2.0}));
        traindataSetup.add(new DenseInstance(1.0, new double[] {2.0, 1.0}));
        traindataSetup.add(new DenseInstance(1.0, new double[] {3.0, 0.0}));
        traindataSetup.add(new DenseInstance(1.0, new double[] {4.0, 1.0}));
        
        ArrayList<Attribute> bugAttributes = new ArrayList<>();
        bugAttributes.add(new Attribute("MRM-797_major_2007-05-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2007-06-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2008-05-26 08:42:10"));
        Instances bugMatrix = new Instances("bugmatrix", bugAttributes, 0);
        bugMatrix.add(new DenseInstance(1.0, new double[] {1.0, 0.0, 1.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 1.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 1.0}));
		
        SoftwareVersion trainversion = new SoftwareVersion("foo", "bar", "1.0", traindataSetup, bugMatrix, null, null, null, null);
        Instances traindata = trainversion.getInstances();
        
		BugDateFilter filter = new BugDateFilter();
		filter.apply(testversion, trainversion);
		
		assertEquals(1.0d, traindata.get(0).classValue(), 0.00001);
		assertEquals(1.0d, traindata.get(1).classValue(), 0.00001);
		assertEquals(0.0d, traindata.get(2).classValue(), 0.00001);
		assertEquals(0.0d, traindata.get(3).classValue(), 0.00001);
	}

}
