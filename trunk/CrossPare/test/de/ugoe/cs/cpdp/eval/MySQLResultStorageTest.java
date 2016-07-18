package de.ugoe.cs.cpdp.eval;

import org.junit.Test;

public class MySQLResultStorageTest {

    @Test
    public void testAddResult() throws Exception {
        MySQLResultStorage storage = new MySQLResultStorage();
        
        ExperimentResult result = new ExperimentResult("dummy", "product-1.0", "classifier");
        result.setSizeTestData(100);
        result.setSizeTrainingData(200);
        result.setError(0.2);
        result.setRecall(0.8);
        result.setPrecision(0.7);
        result.setFscore(0.75);
        result.setGscore(0.85);
        result.setMcc(0.5);
        result.setAuc(0.7);
        result.setAucec(0.6);
        result.setTpr(0.8);
        result.setFpr(0.2);
        result.setTnr(0.7);
        result.setFnr(0.3);
        result.setTp(40);
        result.setFn(10);
        result.setTn(35);
        result.setFp(15);
        storage.addResult(result);
    }
}
