package de.ugoe.cs.cpdp.dataselection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class TestAsTrainingTest {

	@Test
	public void testApply() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("attr1"));
		attributes.add(new Attribute("class"));
		
		Instances testdata = new Instances("test", attributes, 0);
		testdata.setClassIndex(1);
		testdata.add(new DenseInstance(1.0, new double[]{3.0, 0.0}));
		testdata.add(new DenseInstance(1.0, new double[]{6.6, 0.0}));
		testdata.add(new DenseInstance(1.0, new double[]{3.1, 0.0}));
				
		Instances traindata = new Instances("train", attributes, 0);
		traindata.setClassIndex(1);
		traindata.add(new DenseInstance(1.0, new double[]{2.9, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{2.8, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{3.2, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{3.05, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{10.0, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{9.0, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{8.0, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{1.0, 0.0}));
		traindata.add(new DenseInstance(1.0, new double[]{5.0, 0.0}));
		
        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "2.0", testdata, null, null, null, null, null);
        SoftwareVersion trainversion = new SoftwareVersion("foo", "bar", "1.0", traindata, null, null, null, null, null);
		SetUniqueList<SoftwareVersion> trainversionSet = SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
		trainversionSet.add(trainversion);
		
		TestAsTraining filter = new TestAsTraining();
		filter.apply(testversion, trainversionSet);
		
		assertEquals(1, trainversionSet.size());
	
		traindata = trainversionSet.get(0).getInstances();
		assertNotSame(testdata, traindata);
		assertEquals(testdata.numInstances(), traindata.numInstances());
		for( int i=0; i<testdata.numInstances(); i++ ) {
			assertArrayEquals(testdata.instance(i).toDoubleArray(), traindata.instance(i).toDoubleArray(), 0.000000001);
		}
	}

}
