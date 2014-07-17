package de.ugoe.cs.cpdp.training;

import java.io.PrintStream;

import org.apache.commons.io.output.NullOutputStream;

import weka.classifiers.Classifier;
import weka.core.Instances;

public abstract class WekaTraining implements ITrainingStrategy, WekaCompatibleTrainer {
	
	private final Classifier classifier = setupClassifier();

	protected abstract Classifier setupClassifier();
	
	@Override
	public Classifier getClassifier() {
		return classifier;
	}
	
	@Override
	public void apply(Instances traindata) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			classifier.buildClassifier(traindata);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}
	
	@Override
	public void setParameter(String parameters) {
		// TODO should allow passing of weka parameters to the classifier
	}
}
