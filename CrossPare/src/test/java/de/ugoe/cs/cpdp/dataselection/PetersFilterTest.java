package de.ugoe.cs.cpdp.dataselection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class PetersFilterTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testSetParameter() {
		new PetersFilter().setParameter("somestring");
	}
	
	@SuppressWarnings({ "deprecation", "boxing" })
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
		traindata.add(new DenseInstance(1.0, new double[]{6.6, 0.0}));
		
		PetersFilter filter = new PetersFilter();
		Instances selected = filter.apply(testdata, traindata);
		
		Set<Double> selectedSet = new HashSet<>();
		for( int i=0 ; i<selected.numInstances() ; i++ ) {
			selectedSet.add(selected.instance(i).toDoubleArray()[0]);
		}
		
		assertTrue(selectedSet.contains(3.05));
		assertTrue(selectedSet.contains(8.0));
	}

}
