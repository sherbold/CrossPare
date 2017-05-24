package de.ugoe.cs.cpdp.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.AverageStandardization;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class AverageStandardizationTest {

	@Test
	public void testSetParameter() {
		new AverageStandardization().setParameter("somestring");
	}

	@Test
	public void testApply() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("attr1"));
		attributes.add(new Attribute("class"));
		attributes.add(new Attribute("attr2"));
			
		double[] value1 = new double[]{1.5, 0.0,  3.0};
		double[] value2 = new double[]{1.4, 1.0,  6.0};
		double[] value3 = new double[]{1.6, 0.0, 15.0};
		double[] value4 = new double[]{ 3.0, 0.0, 1.5};
		double[] value5 = new double[]{ 6.0, 1.0, 1.4 };
		double[] value6 = new double[]{15.0, 0.0, 1.6};
		
		Instances instances = new Instances("test", attributes, 0);
		instances.setClassIndex(1);
		
		instances.add(new DenseInstance(1.0, value1));
		instances.add(new DenseInstance(1.0, value2));
		instances.add(new DenseInstance(1.0, value3));
				
		Instances instTest = new Instances("foo", attributes, 0);
		instTest.add(new DenseInstance(1.0, value4));
		instTest.add(new DenseInstance(1.0, value5));
		instTest.add(new DenseInstance(1.0, value6));
		
		SetUniqueList<Instances> instSet = SetUniqueList.setUniqueList(new LinkedList<Instances>()); 
		instSet.add(instTest);
		
		AverageStandardization processor = new AverageStandardization();
		processor.apply(instances, instSet);
		
		double[] expected1 = new double[]{0.5625, 0.0, 8.0};
		double[] expected2 = new double[]{1.125 , 1.0, 7.466666666666};
		double[] expected3 = new double[]{2.8125, 0.0, 8.533333333333};
		
		assertArrayEquals(expected1, instSet.get(0).instance(0).toDoubleArray(), 0.0001);
		assertArrayEquals(expected2, instSet.get(0).instance(1).toDoubleArray(), 0.0001);
		assertArrayEquals(expected3, instSet.get(0).instance(2).toDoubleArray(), 0.0001);
	}

}
