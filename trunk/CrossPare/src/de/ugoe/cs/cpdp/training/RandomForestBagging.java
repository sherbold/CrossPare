package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.trees.RandomForest;

public class RandomForestBagging extends BaggingTraining {

	@Override
	protected Classifier setupClassifier() {
		final CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new RandomForest());
		try {
			// Parameter optimization with 5x5 CV
			ps.setNumFolds(5);
			ps.addCVParameter("I 5 25 5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ps;
	}
	
	@Override
	public String getName() {
		return "RandomForestBagging";
	}

}
