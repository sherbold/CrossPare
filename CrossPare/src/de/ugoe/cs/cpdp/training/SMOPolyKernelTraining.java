package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;

public class SMOPolyKernelTraining extends WekaTraining {

	@Override
	public String getName() {
		return "SMOPoly";
	}

	@Override
	protected Classifier setupClassifier() {
		return new SMO();
		/*CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new SMO());
		try {
			// Parameter optimization with 5x5 CV
			ps.setNumFolds(5);
			ps.addCVParameter("C 1 100 4");
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return ps;*/
	}

}
