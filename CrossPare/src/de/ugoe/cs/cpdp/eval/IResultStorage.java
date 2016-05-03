package de.ugoe.cs.cpdp.eval;

public interface IResultStorage {

    public void addResult(ExperimentResult result);
    
    public boolean containsResult(String experimentName, String productName);
}
