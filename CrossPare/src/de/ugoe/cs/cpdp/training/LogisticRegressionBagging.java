package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

public class LogisticRegressionBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "LogisticRegressionBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		return new Logistic();
	}

}
