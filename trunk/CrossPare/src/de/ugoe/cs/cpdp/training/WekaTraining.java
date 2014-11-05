package de.ugoe.cs.cpdp.training;

import java.io.PrintStream;
import java.util.logging.Level;

import org.apache.commons.io.output.NullOutputStream;

import de.ugoe.cs.util.console.Console;
import weka.core.Instances;

/**
 * Programmatic WekaTraining
 *
 * first parameter is Trainer Name.
 * second parameter is class name
 * 
 * all subsequent parameters are configuration params (for example for trees)
 * 
 * XML Configurations for Weka Classifiers:
 * <pre>
 * {@code
 * <!-- examples -->
 * <trainer name="WekaTraining2" param="NaiveBayes weka.classifiers.bayes.NaiveBayes" />
 * <trainer name="WekaTraining2" param="Logistic weka.classifiers.functions.Logistic -R 1.0E-8 -M -1" />
 * }
 * </pre>
 * 
 */
public class WekaTraining extends WekaBaseTraining implements ITrainingStrategy {

	@Override
	public void apply(Instances traindata) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			if(classifier == null) {
				Console.traceln(Level.WARNING, String.format("classifier null!"));
			}
			classifier.buildClassifier(traindata);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}
}
