package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

public class NeuralNetworkTraining extends WekaTraining {

	@Override
	public String getName() {
		return "NeuralNetwork";
	}

	@Override
	protected Classifier setupClassifier() {
		return new MultilayerPerceptron();
	}
}
