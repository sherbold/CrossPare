package de.ugoe.cs.cpdp.training;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

// Bagging Strategy: separate models for each training data set
public interface ISetWiseTrainingStrategy extends ITrainer {
	
	void apply(SetUniqueList<Instances> traindataSet);
	
	String getName();
}
