package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "NaiveBayesBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		return new NaiveBayes();
	}

}
