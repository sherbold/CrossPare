package de.ugoe.cs.cpdp.training;

import java.util.Arrays;
import java.util.logging.Level;

import de.ugoe.cs.util.console.Console;

import weka.core.OptionHandler;
import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;

/**
 * WekaBaseTraining2
 * 
 * Allows specification of the Weka classifier and its params in the XML experiment configuration.
 * 
 * Important conventions of the XML format: 
 * Cross Validation params always come last and are prepended with -CVPARAM
 * Example: <trainer name="WekaTraining" param="RandomForestLocal weka.classifiers.trees.RandomForest -CVPARAM I 5 25 5"/>
 */
public abstract class WekaBaseTraining implements IWekaCompatibleTrainer {
	
	protected Classifier classifier = null;
	protected String classifierClassName;
	protected String classifierName;
	protected String[] classifierParams;
	
	@Override
	public void setParameter(String parameters) {
		String[] params = parameters.split(" ");

		// first part of the params is the classifierName (e.g. SMORBF)
		classifierName = params[0];
		
		// the following parameters can be copied from weka!
		
		// second param is classifierClassName (e.g. weka.classifiers.functions.SMO)
		classifierClassName = params[1];
	
		// rest are params to the specified classifier (e.g. -K weka.classifiers.functions.supportVector.RBFKernel)
		classifierParams = Arrays.copyOfRange(params, 2, params.length);
		
		classifier = setupClassifier();
	}

	@Override
	public Classifier getClassifier() {
		return classifier;
	}

	public Classifier setupClassifier() {
		Classifier cl = null;
		try{
			@SuppressWarnings("rawtypes")
			Class c = Class.forName(classifierClassName);
			Classifier obj = (Classifier) c.newInstance();
			
			// Filter out -CVPARAM, these are special because they do not belong to the Weka classifier class as parameters
			String[] param = Arrays.copyOf(classifierParams, classifierParams.length);
			String[] cvparam = {};
			boolean cv = false;
			for ( int i=0; i < classifierParams.length; i++ ) {
				if(classifierParams[i].equals("-CVPARAM")) {
					// rest of array are cvparam
					cvparam = Arrays.copyOfRange(classifierParams, i+1, classifierParams.length);
					
					// before this we have normal params
					param = Arrays.copyOfRange(classifierParams, 0, i);
					
					cv = true;
					break;
				}
			}
			
			// set classifier params
			((OptionHandler)obj).setOptions(param);
			cl = obj;
			
			// we have cross val params
			// cant check on cvparam.length here, it may not be initialized			
			if(cv) {
				final CVParameterSelection ps = new CVParameterSelection();
				ps.setClassifier(obj);
				ps.setNumFolds(5);
				//ps.addCVParameter("I 5 25 5");
				for( int i=1 ; i<cvparam.length/4 ; i++ ) {
					ps.addCVParameter(Arrays.asList(Arrays.copyOfRange(cvparam, 0, 4*i)).toString().replaceAll(", ", " ").replaceAll("^\\[|\\]$", ""));
				}
				
				cl = ps;
			}

		}catch(ClassNotFoundException e) {
			Console.traceln(Level.WARNING, String.format("class not found: %s", e.toString()));
			e.printStackTrace();
		} catch (InstantiationException e) {
			Console.traceln(Level.WARNING, String.format("Instantiation Exception: %s", e.toString()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Console.traceln(Level.WARNING, String.format("Illegal Access Exception: %s", e.toString()));
			e.printStackTrace();
		} catch (Exception e) {
			Console.traceln(Level.WARNING, String.format("Exception: %s", e.toString()));
			e.printStackTrace();
		}
		
		return cl;
	}

	@Override
	public String getName() {
		return classifierName;
	}
	
}
