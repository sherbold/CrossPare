package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class DecisionTreeBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "C4.5-DTreeBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		return new J48();
	}

}
