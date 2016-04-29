package de.ugoe.cs.cpdp.training;

import weka.core.Instances;

public interface ITestAwareTrainingStrategy extends ITrainer {
    
    void apply(Instances testdata, Instances traindata);

    String getName();
}
