package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesTraining extends WekaTraining {

	@Override
	public String getName() {
		return "NaiveBayes";
	}

	@Override
	protected Classifier setupClassifier() {
		return new NaiveBayes();
	}

}
