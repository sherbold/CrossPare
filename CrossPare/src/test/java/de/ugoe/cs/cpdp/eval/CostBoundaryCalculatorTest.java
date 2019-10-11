package de.ugoe.cs.cpdp.eval;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.ugoe.cs.cpdp.loader.MynbouDataLoader;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class CostBoundaryCalculatorTest {

	@SuppressWarnings("serial")
	@Test
	public void testRealistic() throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("another"));
        List<String> classAttVals = new ArrayList<>();
		classAttVals.add("0");
		classAttVals.add("1");
        attributes.add(new Attribute("bug", classAttVals));
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(1);
        testdata.add(new DenseInstance(1.0, new double[] {1.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {2.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {3.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[] {4.0, 1.0}));

        ArrayList<Attribute> bugAttributes = new ArrayList<>();
        bugAttributes.add(new Attribute("MRM-797_major_2007-05-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2007-06-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2008-05-26 08:42:10"));
        Instances bugMatrix = new Instances("bugmatrix", bugAttributes, 0);
        bugMatrix.add(new DenseInstance(1.0, new double[] {1.0, 0.0, 1.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 1.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 1.0}));
		
		Classifier classifier = new AbstractClassifier() {
			@Override
			public void buildClassifier(Instances data) throws Exception {
				// empty
			}
			@Override
			public double classifyInstance(Instance instance) throws Exception {
				return instance.value(0)>2.0 ? 0.0 : 1.0;
			}
		};
		List<Double> efforts = Arrays.asList(new Double[]{1.0, 2.0, 3.0, 4.0});
		
		Evaluation eval = new Evaluation(testdata);
        eval.evaluateModel(classifier, testdata);
		double probQAFailure = 0.0;
		
		CostBoundaryCalculator costCalc = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, probQAFailure);
		
		assertEquals(1.0, costCalc.getLowerConst1to1(), 0.00001);
		assertEquals(2.0, costCalc.getUpperConst1to1(), 0.00001);
		assertEquals(1.5, costCalc.getLowerSizeNtoM(), 0.00001);
		assertEquals(7.0, costCalc.getUpperSizeNtoM(), 0.00001);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testAllNegative() throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("another"));
        List<String> classAttVals = new ArrayList<>();
		classAttVals.add("0");
		classAttVals.add("1");
        attributes.add(new Attribute("bug", classAttVals));
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(1);
        testdata.add(new DenseInstance(1.0, new double[] {1.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {2.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {3.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[] {4.0, 1.0}));

        ArrayList<Attribute> bugAttributes = new ArrayList<>();
        bugAttributes.add(new Attribute("MRM-797_major_2007-05-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2007-06-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2008-05-26 08:42:10"));
        Instances bugMatrix = new Instances("bugmatrix", bugAttributes, 0);
        bugMatrix.add(new DenseInstance(1.0, new double[] {1.0, 0.0, 1.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 1.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 1.0}));
		
		Classifier classifier = new AbstractClassifier() {
			@Override
			public void buildClassifier(Instances data) throws Exception {
				// empty
			}
			@Override
			public double classifyInstance(Instance instance) throws Exception {
				return 0.0;
			}
		};
		List<Double> efforts = Arrays.asList(new Double[]{1.0, 2.0, 3.0, 4.0});
		
		Evaluation eval = new Evaluation(testdata);
        eval.evaluateModel(classifier, testdata);
		double probQAFailure = 0.0;
		
		CostBoundaryCalculator costCalc = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, probQAFailure);
		
		assertEquals(-1.0, costCalc.getLowerConst1to1(), 0.00001);
		assertEquals(4.0/3.0, costCalc.getUpperConst1to1(), 0.00001);
		assertEquals(-1.0, costCalc.getLowerSizeNtoM(), 0.00001);
		assertEquals(10.0/3.0, costCalc.getUpperSizeNtoM(), 0.00001);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testAllPositive() throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("another"));
        List<String> classAttVals = new ArrayList<>();
		classAttVals.add("0");
		classAttVals.add("1");
        attributes.add(new Attribute("bug", classAttVals));
        Instances testdata = new Instances("testdata", attributes, 0);
        testdata.setClassIndex(1);
        testdata.add(new DenseInstance(1.0, new double[] {1.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {2.0, 1.0}));
        testdata.add(new DenseInstance(1.0, new double[] {3.0, 0.0}));
        testdata.add(new DenseInstance(1.0, new double[] {4.0, 1.0}));

        ArrayList<Attribute> bugAttributes = new ArrayList<>();
        bugAttributes.add(new Attribute("MRM-797_major_2007-05-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2007-06-26 08:42:10"));
        bugAttributes.add(new Attribute("MRM-797_major_2008-05-26 08:42:10"));
        Instances bugMatrix = new Instances("bugmatrix", bugAttributes, 0);
        bugMatrix.add(new DenseInstance(1.0, new double[] {1.0, 0.0, 1.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 1.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 0.0}));
        bugMatrix.add(new DenseInstance(1.0, new double[] {0.0, 0.0, 1.0}));
		
		Classifier classifier = new AbstractClassifier() {
			@Override
			public void buildClassifier(Instances data) throws Exception {
				// empty
			}
			@Override
			public double classifyInstance(Instance instance) throws Exception {
				return 1.0;
			}
		};
		List<Double> efforts = Arrays.asList(new Double[]{1.0, 2.0, 3.0, 4.0});
		
		Evaluation eval = new Evaluation(testdata);
        eval.evaluateModel(classifier, testdata);
		double probQAFailure = 0.0;
		
		CostBoundaryCalculator costCalc = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, probQAFailure);
		
		assertEquals(4.0/3.0, costCalc.getLowerConst1to1(), 0.00001);
		assertEquals(-1.0, costCalc.getUpperConst1to1(), 0.00001);
		assertEquals(10.0/3.0, costCalc.getLowerSizeNtoM(), 0.00001);
		assertEquals(-1.0, costCalc.getUpperSizeNtoM(), 0.00001);
	}

	@SuppressWarnings("serial")
	@Test
	public void testAntIvy() throws Exception {
		MynbouDataLoader loader = new MynbouDataLoader();
		Instances testdata = loader.load(new File("testdata/mynbou/ant-ivy/ant-ivy-1.4.1_aggregated.csv"), true);
		Instances bugMatrix = loader.getBugMatrix();
		Attribute effortAtt = testdata.attribute("SM_file_lloc");
		List<Double> efforts = new ArrayList<>(testdata.size());
        for (int i = 0; i < testdata.size(); i++) {
            if(effortAtt!=null) {
                efforts.add(testdata.get(i).value(effortAtt));
            } else {
                // add constant effort per instance (default)
                efforts.add(1.0);
            }
        }
		
		Classifier classifier = new AbstractClassifier() {
			@Override
			public void buildClassifier(Instances data) throws Exception {
				// empty
			}
			@Override
			public double classifyInstance(Instance instance) throws Exception {
				return 0.0;
			}
		};
		
		
		Evaluation eval = new Evaluation(testdata);
        eval.evaluateModel(classifier, testdata);
		double probQAFailure = 0.0;
		
		CostBoundaryCalculator costCalc = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, probQAFailure);
		
		assertEquals(-1.0, costCalc.getLowerConst1to1(), 0.00001);
		assertEquals(-1.0, costCalc.getLowerSizeNtoM(), 0.00001);
	}
}
