package de.ugoe.cs.cpdp.training;

import weka.classifiers.Classifier;

public interface IWekaCompatibleTrainer extends ITrainer {
	
	Classifier getClassifier();
	
	String getName();
}
