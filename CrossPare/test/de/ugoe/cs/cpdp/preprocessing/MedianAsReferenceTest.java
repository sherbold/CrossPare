package de.ugoe.cs.cpdp.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		double[] value3 = new double[]{1.6, 1.0, 15.0};
			
		instances.add(new DenseInstance(1.0, value1));
		instances.add(new DenseInstance(1.0, value2));
		instances.add(new DenseInstance(1.0, value3));
		
		Instances trainInstances = new Instances("train", attributes, 0);
		trainInstances.setClassIndex(1);
                trainInstances.add(new DenseInstance(1.0, new double[]{1.6, -1.0, 2.0}));
                trainInstances.add(new DenseInstance(1.0, new double[]{1.4, 1.0, 3.0}));
                trainInstances.add(new DenseInstance(1.0, new double[]{1.7, 2.0, 5.0}));
                
                List<Instances> trainList = new LinkedList<>();
                trainList.add(trainInstances);
                
		MedianAsReference processor = new MedianAsReference();
		processor.apply(instances, SetUniqueList.setUniqueList(trainList) );
		
		double[] expected1 = new double[]{1.5,-1.0, 5.0};
		double[] expected2 = new double[]{1.3, 1.0, 6.0};
		double[] expected3 = new double[]{1.6, 2.0, 8.0};
		
		// assert test stays the same
		assertArrayEquals(value1, instances.instance(0).toDoubleArray(), 0.0001);
		assertArrayEquals(value2, instances.instance(1).toDoubleArray(), 0.0001);
		assertArrayEquals(value3, instances.instance(2).toDoubleArray(), 0.0001);
		
		// assert expected values for train instances
		assertArrayEquals(expected1, trainInstances.instance(0).toDoubleArray(), 0.0001);
                assertArrayEquals(expected2, trainInstances.instance(1).toDoubleArray(), 0.0001);
                assertArrayEquals(expected3, trainInstances.instance(2).toDoubleArray(), 0.0001);
	}

}
