package de.ugoe.cs.cpdp.wekaclassifier;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ManualUpTest {

	@Test
	public void test() throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("size"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("instance_index"));
        final ArrayList<String> classAttVals = new ArrayList<>();
        classAttVals.add("0");
        classAttVals.add("1");
        attributes.add(new Attribute("class", classAttVals));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(3);
        testdata.add(new DenseInstance(1.0, new double[]{15.0, 0.0, 0.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[]{10.0, 0.0, 1.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[]{10.0, 0.0, 2.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[]{1.0, 1.0, 3.0, 0.0}));
        
        Classifier manualUp = new ManualUp();
        manualUp.buildClassifier(testdata);
        
        assertEquals("value 1 distribution incorrect", 0.0, manualUp.distributionForInstance(testdata.get(0))[1], 0.00001);
        assertEquals("value 2 distribution incorrect", 0.3333333, manualUp.distributionForInstance(testdata.get(1))[1], 0.00001);
        assertEquals("value 3 distribution incorrect", 0.6666666, manualUp.distributionForInstance(testdata.get(2))[1], 0.00001);
        assertEquals("value 4 distribution incorrect", 1.0, manualUp.distributionForInstance(testdata.get(3))[1], 0.00001);
        
        assertEquals("value 1 class incorrect", 0.0, manualUp.classifyInstance(testdata.get(0)), 0.00001);
        assertEquals("value 1 class incorrect", 0.0, manualUp.classifyInstance(testdata.get(1)), 0.00001);
        assertEquals("value 1 class incorrect", 1.0, manualUp.classifyInstance(testdata.get(2)), 0.00001);
        assertEquals("value 1 class incorrect", 1.0, manualUp.classifyInstance(testdata.get(3)), 0.00001);
	}

}
