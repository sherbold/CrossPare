package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;

public class SMORBFKernelBagging extends BaggingTraining {

	@Override
	public String getName() {
		return "SMORBFBagging";
	}

	@Override
	protected Classifier setupClassifier() {
		//CVParameterSelection ps = new CVParameterSelection();
		final SMO smoRBF = new SMO();
		smoRBF.setKernel(new RBFKernel());
		return smoRBF;
		/*ps.setClassifier(smoRBF);
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
