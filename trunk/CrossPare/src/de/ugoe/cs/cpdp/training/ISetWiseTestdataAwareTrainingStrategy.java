package de.ugoe.cs.cpdp.training;

import org.apache.commons.collections4.list.SetUniqueList;
import weka.core.Instances;

public interface ISetWiseTestdataAwareTrainingStrategy extends ITrainer {

    void apply(SetUniqueList<Instances> traindataSet, Instances testdata);

    String getName();
}
