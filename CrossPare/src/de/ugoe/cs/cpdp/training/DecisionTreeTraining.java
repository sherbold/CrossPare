package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.trees.J48;

public class DecisionTreeTraining extends WekaTraining {

	@Override
	public String getName() {
		return "C4.5-DTree";
	}

	@Override
	protected Classifier setupClassifier() {
		final CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new J48());
		try {
			// Parameter optimization with 5x5 CV
			ps.setNumFolds(5);
			ps.addCVParameter("C 0.1 0.5 5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ps;
	}
}
