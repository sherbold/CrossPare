package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

public class LogisticRegressionTraining extends WekaTraining {

	@Override
	public String getName() {
		return "LogisticRegression";
	}

	@Override
	protected Classifier setupClassifier() {
		return new Logistic();
	}
}
