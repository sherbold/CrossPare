package de.ugoe.cs.cpdp.dataprocessing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import de.ugoe.cs.cpdp.dataprocessing.InstanceRemoval;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class InstanceRemovalTest {

    @Test
    public void testSetParameter() {
        new InstanceRemoval().setParameter("test:0,another:1");
    }
    
    @Test
    public void testApply() {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(2);
        testdata.add(new DenseInstance(1.0, new double[]{1.0, 0.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[]{0.0, 0.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[]{0.0, 1.0, 0.0}));
                
        Instances traindata = new Instances("traindata", attributes, 0);
        traindata.setClassIndex(2);
        traindata.add(new DenseInstance(1.0, new double[]{1.0, 0.0, 0.0}));
        traindata.add(new DenseInstance(1.0, new double[]{0.0, 0.0, 0.0}));
        traindata.add(new DenseInstance(1.0, new double[]{0.0, 1.0, 0.0}));
        
        InstanceRemoval filter = new InstanceRemoval();
        filter.setParameter("test:0,another:1");
        filter.apply(testdata, traindata);

        assertTrue(traindata.numInstances() == 2);
        assertTrue(traindata.get(0).value(traindata.attribute("test")) == 1.0);
        assertTrue(traindata.get(0).value(traindata.attribute("another")) == 0.0);
        assertTrue(traindata.get(1).value(traindata.attribute("test")) == 0.0);
        assertTrue(traindata.get(1).value(traindata.attribute("another")) == 0.0);
        
        assertTrue(testdata.numInstances() == 2);
        assertTrue(testdata.get(0).value(testdata.attribute("test")) == 1.0);
        assertTrue(testdata.get(0).value(testdata.attribute("another")) == 0.0);
        assertTrue(testdata.get(1).value(testdata.attribute("test")) == 0.0);
        assertTrue(testdata.get(1).value(testdata.attribute("another")) == 0.0);
    }
}
