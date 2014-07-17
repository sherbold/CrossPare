package de.ugoe.cs.cpdp.eval;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Implements the {@link AbstractWekaEvaluation} for evaluation on the test data.
 * @author Steffen Herbold
 *
 */
public class NormalWekaEvaluation extends AbstractWekaEvaluation {

	/**
	 * @see de.ugoe.cs.cpdp.eval.AbstractWekaEvaluation#createEvaluator(weka.core.Instances, weka.classifiers.Classifier)
	 */
	@Override
	protected Evaluation createEvaluator(Instances testdata, Classifier classifier) {
		try {
			final Evaluation eval = new Evaluation(testdata);
			eval.evaluateModel(classifier, testdata);
			return eval;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
