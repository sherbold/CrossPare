package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;

public interface WekaCompatibleTrainer extends ITrainer {
	
	Classifier getClassifier();
	
	String getName();
}
