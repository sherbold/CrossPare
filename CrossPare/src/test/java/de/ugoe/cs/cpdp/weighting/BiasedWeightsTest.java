package de.ugoe.cs.cpdp.weighting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.BiasedWeights;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class BiasedWeightsTest {

	@Test
	public void testApply_1() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("attr1"));
		List<String> classAttVals = new ArrayList<>();
		classAttVals.add("0");
		classAttVals.add("1");
		attributes.add(new Attribute("bug", classAttVals));
		attributes.add(new Attribute("attr2"));
			
		double[] value1 = new double[]{1.5, 0.0,  3.0};
		double[] value2 = new double[]{1.4, 1.0,  6.0};
		double[] value3 = new double[]{1.6, 0.0, 15.0};
		double[] value4 = new double[]{ 3.0, 0.0, 1.5};
		double[] value5 = new double[]{ 6.0, 1.0, 1.4 };
		double[] value6 = new double[]{15.0, 0.0, 1.6};
		double[] value7 = new double[]{ 6.0, 0.0, 1.4 };
		double[] value8 = new double[]{15.0, 0.0, 1.6};
		
		Instances instances = new Instances("test", attributes, 0);
		instances.setClassIndex(1);
		
		instances.add(new DenseInstance(1.0, value1));
		instances.add(new DenseInstance(1.0, value2));
		instances.add(new DenseInstance(1.0, value3));
		instances.add(new DenseInstance(1.0, value4));
		instances.add(new DenseInstance(1.0, value5));
		instances.add(new DenseInstance(1.0, value6));
		instances.add(new DenseInstance(1.0, value7));
		instances.add(new DenseInstance(1.0, value8));
		
		BiasedWeights processor = new BiasedWeights();
		processor.apply(new Instances(instances), instances);
		
		assertEquals(0.6666666d, instances.instance(0).weight(), 0.00001);
		assertEquals(2.0d, instances.instance(1).weight(), 0.00001);
		assertEquals(0.6666666d, instances.instance(2).weight(), 0.00001);
		assertEquals(0.6666666d, instances.instance(3).weight(), 0.00001);
		assertEquals(2.0d, instances.instance(4).weight(), 0.00001);
		assertEquals(0.6666666d, instances.instance(5).weight(), 0.00001);
		assertEquals(0.6666666d, instances.instance(6).weight(), 0.00001);
		assertEquals(0.6666666d, instances.instance(7).weight(), 0.00001);
		assertEquals(instances.numInstances(), instances.sumOfWeights(), 0.0001);
	}
	
	@Test
	public void testApply_2() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("attr1"));
		List<String> classAttVals = new ArrayList<>();
		classAttVals.add("0");
		classAttVals.add("1");
		attributes.add(new Attribute("bug", classAttVals));
		attributes.add(new Attribute("attr2"));
			
		double[] value1 = new double[]{1.5, 0.0,  3.0};
		double[] value2 = new double[]{1.4, 1.0,  6.0};
		double[] value3 = new double[]{1.6, 0.0, 15.0};
		double[] value4 = new double[]{ 3.0, 0.0, 1.5};
		double[] value5 = new double[]{ 6.0, 1.0, 1.4 };
		double[] value6 = new double[]{15.0, 0.0, 1.6};
		double[] value7 = new double[]{ 6.0, 0.0, 1.4 };
		double[] value8 = new double[]{15.0, 0.0, 1.6};
		
		Instances instances = new Instances("test", attributes, 0);
		instances.setClassIndex(1);
		
		instances.add(new DenseInstance(1.0, value1));
		instances.add(new DenseInstance(1.0, value2));
		instances.add(new DenseInstance(1.0, value3));
		instances.add(new DenseInstance(1.0, value4));
		instances.add(new DenseInstance(1.0, value5));
		instances.add(new DenseInstance(1.0, value6));
		instances.add(new DenseInstance(1.0, value7));
		instances.add(new DenseInstance(1.0, value8));
		
		
		BiasedWeights processor = new BiasedWeights();
		processor.setParameter("0.7");
		processor.apply(new Instances(instances), instances);
		
		assertEquals(0.4, instances.instance(0).weight(), 0.00001);
		assertEquals(2.8, instances.instance(1).weight(), 0.00001);
		assertEquals(0.4, instances.instance(2).weight(), 0.00001);
		assertEquals(0.4, instances.instance(3).weight(), 0.00001);
		assertEquals(2.8, instances.instance(4).weight(), 0.00001);
		assertEquals(0.4, instances.instance(5).weight(), 0.00001);
		assertEquals(0.4, instances.instance(6).weight(), 0.00001);
		assertEquals(0.4, instances.instance(7).weight(), 0.00001);
		assertEquals(instances.numInstances(), instances.sumOfWeights(), 0.0001);
	}

}
