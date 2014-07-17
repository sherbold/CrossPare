package de.ugoe.cs.cpdp.eval;

import java.io.PrintStream;
import java.util.Random;

import org.apache.commons.io.output.NullOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Implements the {@link AbstractWekaEvaluation} for 10-fold cross validation.
 * @author Steffen Herbold
 */
public class CVWekaEvaluation extends AbstractWekaEvaluation {
	
	/**
	 * @see de.ugoe.cs.cpdp.eval.AbstractWekaEvaluation#createEvaluator(weka.core.Instances, weka.classifiers.Classifier)
	 */
	@Override
	protected Evaluation createEvaluator(Instances testdata, Classifier classifier) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			final Evaluation eval = new Evaluation(testdata);
			eval.crossValidateModel(classifier, testdata, 10, new Random(1));
			return eval;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}

}
