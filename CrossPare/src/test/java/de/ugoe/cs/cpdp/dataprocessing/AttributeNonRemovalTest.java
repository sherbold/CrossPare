package de.ugoe.cs.cpdp.dataprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.Instances;

public class AttributeNonRemovalTest {

	@Test
	public void testKeepSingle() {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(2);
                
        Instances traindata = new Instances("traindata", attributes, 0);
        traindata.setClassIndex(2);
        
        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "2.0", testdata, null, null, null, null, null);
        SoftwareVersion trainversion = new SoftwareVersion("foo", "bar", "1.0", traindata, null, null, null, null, null);

        AttributeNonRemoval attributeNonRemoval = new AttributeNonRemoval();
        attributeNonRemoval.setParameter("test class");
        attributeNonRemoval.apply(testversion, trainversion);
        
        assertNotNull(testdata.attribute("test"));
        assertNotNull(testdata.attribute("class"));
        assertNull(testdata.attribute("another"));
        
        assertNotNull(traindata.attribute("test"));
        assertNotNull(traindata.attribute("class"));
        assertNull(traindata.attribute("another"));
	}
	
	@Test
	public void testKeepSingle_trainset() {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(2);

        Instances traindata1 = new Instances("traindata1", attributes, 0);
        traindata1.setClassIndex(2);
        Instances traindata2 = new Instances("traindata2", attributes, 0);
        traindata1.setClassIndex(2);

        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "3.0", testdata, null, null, null, null, null);
        SetUniqueList<SoftwareVersion> trainversionSet =
                SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
        trainversionSet.add(new SoftwareVersion("foo", "bar", "1.0", traindata1, null, null, null, null, null));
        trainversionSet.add(new SoftwareVersion("foo", "bar", "2.0", traindata2, null, null, null, null, null));       
        
        AttributeNonRemoval attributeNonRemoval = new AttributeNonRemoval();
        attributeNonRemoval.setParameter("test class");
        attributeNonRemoval.apply(testversion, trainversionSet);
        
        assertNotNull(testdata.attribute("test"));
        assertNotNull(testdata.attribute("class"));
        assertNull(testdata.attribute("another"));
        
        for(SoftwareVersion trainversion : trainversionSet) {
                Instances traindata = trainversion.getInstances();
	        assertNotNull(traindata.attribute("test"));
	        assertNotNull(traindata.attribute("class"));
	        assertNull(traindata.attribute("another"));
        }
	}
	
	@Test(expected = Exception.class)
	public void testDeleteClass() {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(2);
                
        Instances traindata = new Instances("traindata", attributes, 0);
        traindata.setClassIndex(2);
        
        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "2.0", testdata, null, null, null, null, null);
        SoftwareVersion trainversion = new SoftwareVersion("foo", "bar", "1.0", traindata, null, null, null, null, null);

        AttributeNonRemoval attributeNonRemoval = new AttributeNonRemoval();
        attributeNonRemoval.setParameter("test");
        attributeNonRemoval.apply(testversion, trainversion);
	}
	
	@Test
	public void testKeepRegex() {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("test2"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(3);
        
        Instances traindata = new Instances("traindata", attributes, 0);
        traindata.setClassIndex(3);
        
        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "2.0", testdata, null, null, null, null, null);
        SoftwareVersion trainversion = new SoftwareVersion("foo", "bar", "1.0", traindata, null, null, null, null, null);

        AttributeNonRemoval attributeNonRemoval = new AttributeNonRemoval();
        attributeNonRemoval.setParameter("test.* class");
        attributeNonRemoval.apply(testversion, trainversion);
        
        assertNotNull(testdata.attribute("test"));
        assertNotNull(testdata.attribute("test2"));
        assertNotNull(testdata.attribute("class"));
        assertNull(testdata.attribute("another"));
        
        assertNotNull(traindata.attribute("test"));
        assertNotNull(traindata.attribute("test2"));
        assertNotNull(traindata.attribute("class"));
        assertNull(traindata.attribute("another"));
	}
	
	@Test
	public void testKeepRegex_trainset() {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("test"));
        attributes.add(new Attribute("test2"));
        attributes.add(new Attribute("another"));
        attributes.add(new Attribute("class"));
        
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(3);
        
        Instances traindata1 = new Instances("traindata1", attributes, 0);
        traindata1.setClassIndex(3);
        Instances traindata2 = new Instances("traindata2", attributes, 0);
        traindata1.setClassIndex(3);
        
        SoftwareVersion testversion = new SoftwareVersion("foo", "bar", "3.0", testdata, null, null, null, null, null);
        SetUniqueList<SoftwareVersion> trainversionSet =
                SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
        trainversionSet.add(new SoftwareVersion("foo", "bar", "1.0", traindata1, null, null, null, null, null));
        trainversionSet.add(new SoftwareVersion("foo", "bar", "2.0", traindata2, null, null, null, null, null));     
        
        AttributeNonRemoval attributeNonRemoval = new AttributeNonRemoval();
        attributeNonRemoval.setParameter("test.* class");
        attributeNonRemoval.apply(testversion, trainversionSet);
        
        assertNotNull(testdata.attribute("test"));
        assertNotNull(testdata.attribute("test2"));
        assertNotNull(testdata.attribute("class"));
        assertNull(testdata.attribute("another"));
        for(SoftwareVersion trainversion : trainversionSet) {
                Instances traindata = trainversion.getInstances();
	        assertNotNull(traindata.attribute("test"));
	        assertNotNull(traindata.attribute("test2"));
	        assertNotNull(traindata.attribute("class"));
	        assertNull(traindata.attribute("another"));
        }
        }

}
