package de.ugoe.cs.cpdp.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.LogarithmTransform;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class LogarithmTranformTest {

	@Test
	public void testSetParameter() {
		new LogarithmTransform().setParameter("somestring");
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
		
		LogarithmTransform processor = new LogarithmTransform();
		processor.apply(instances, SetUniqueList.setUniqueList(new LinkedList<Instances>()) );
		
		double[] expected1 = new double[]{Math.log(1.5+1), 0.0, Math.log(3.0+1)};
		double[] expected2 = new double[]{Math.log(1.4+1), 1.0, Math.log(6.0+1)};
		double[] expected3 = new double[]{Math.log(1.6+1), 0.0, Math.log(15.0+1)};
				
		assertArrayEquals(expected1, instances.instance(0).toDoubleArray(), 0.0001);
		assertArrayEquals(expected2, instances.instance(1).toDoubleArray(), 0.0001);
		assertArrayEquals(expected3, instances.instance(2).toDoubleArray(), 0.0001);
	}

}
