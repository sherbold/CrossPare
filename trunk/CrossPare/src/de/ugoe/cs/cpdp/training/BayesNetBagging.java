package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;

public class BayesNetBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "BayesNetBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		return new BayesNet();
	}

}
