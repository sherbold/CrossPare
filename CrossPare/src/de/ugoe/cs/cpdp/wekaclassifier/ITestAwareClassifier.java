package de.ugoe.cs.cpdp.wekaclassifier;

import weka.core.Instances;

public interface ITestAwareClassifier {
    
    public void setTestdata(Instances testdata);

}
