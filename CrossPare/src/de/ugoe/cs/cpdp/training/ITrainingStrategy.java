package de.ugoe.cs.cpdp.training;

import weka.core.Instances;

public interface ITrainingStrategy extends ITrainer {
	
	void apply(Instances traindata);
}
