package de.ugoe.cs.cpdp.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.Normalization;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class NormalizationPreprocessorTest {

	@Test
	public void testApply() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("attr1"));
		attributes.add(new Attribute("class"));
		attributes.add(new Attribute("attr2"));
		Instances instances = new Instances("test", attributes, 0);
		instances.setClassIndex(1);
		
		double[] value1 = new double[]{1.5, 0.0,  3.0};
		double[] value2 = new double[]{1.4, 1.0,  6.0};
		double[] value3 = new double[]{1.6, 0.0, 15.0};
		
		instances.add(new DenseInstance(1.0, value1));
		instances.add(new DenseInstance(1.0, value2));
		instances.add(new DenseInstance(1.0, value3));
		
		Normalization processor = new Normalization();
		processor.apply(instances, SetUniqueList.setUniqueList(new LinkedList<Instances>()) );
		
		double[] expected1 = new double[]{0.5, 0.0, 0.0};
		double[] expected2 = new double[]{0.0, 1.0, 0.25};
		double[] expected3 = new double[]{1.0, 0.0, 1.0};
				
		assertArrayEquals(expected1, instances.instance(0).toDoubleArray(), 0.0001);
		assertArrayEquals(expected2, instances.instance(1).toDoubleArray(), 0.0001);
		assertArrayEquals(expected3, instances.instance(2).toDoubleArray(), 0.0001);
	}

	@Test
	public void testSetParameter() {
		// just a simple crash test
		new Normalization().setParameter("somestring");
	}

}
