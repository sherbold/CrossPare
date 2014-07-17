package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

public class NeuralNetworkBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "NeuralNetworkBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		return new MultilayerPerceptron();
	}

}
