package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;

public class BayesNetTraining extends WekaTraining {

	@Override
	public String getName() {
		return "BayesNet";
	}

	@Override
	protected Classifier setupClassifier() {
		return new BayesNet();
	}

}
