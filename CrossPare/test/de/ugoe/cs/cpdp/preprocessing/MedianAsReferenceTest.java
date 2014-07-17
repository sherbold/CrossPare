package de.ugoe.cs.cpdp.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.MedianAsReference;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class MedianAsReferenceTest {

	@Test
	public void testSetParameter() {
		// simple crashstest
		new MedianAsReference().setParameter("somestring");
	}

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
		
		MedianAsReference processor = new MedianAsReference();
		processor.apply(instances, SetUniqueList.setUniqueList(new LinkedList<Instances>()) );
		
		double[] expected1 = new double[]{0.0, 0.0, -3.0};
		double[] expected2 = new double[]{-0.1,1.0,  0.0};
		double[] expected3 = new double[]{0.1, 0.0,  9.0};
				
		assertArrayEquals(expected1, instances.instance(0).toDoubleArray(), 0.0001);
		assertArrayEquals(expected2, instances.instance(1).toDoubleArray(), 0.0001);
		assertArrayEquals(expected3, instances.instance(2).toDoubleArray(), 0.0001);
	}

}
