package de.ugoe.cs.cpdp.dataselection;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Applies Out-Of-Sample Bootstrap to the data.
 * <p>
 * With this data selection technique a bootstrap sample from the data is drawn and used as trainings data.
 * The leftover instances, which are not in the bootstrap sample, are used as trainings data.
 * Statistically these are 36.8 % of the data.
 * If there are not at least a minimal number of samples in the drawn trainings data the procedure is repeated.
 *
 * @author Steffen Tunkel
 */
public class OutOfSampleBootstrap implements IPointWiseDataselectionStrategy {

    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * minimum number of defects in a sample to keep it
     */
    private int minDefects = 2;

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            this.minDefects = Integer.parseInt(parameters);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public SoftwareVersion apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        if (testversion.getInstances().classAttribute().isNumeric()) {
            throw new RuntimeException("Error: The class attribute of the test data is not binary.");
        }
        if (trainversion.getInstances().classAttribute().isNumeric()) {
            throw new RuntimeException("Error: The class attribute of the training data is not binary.");
        }
        SoftwareVersion testversioncopy = new SoftwareVersion(testversion);
        Instances testdata = testversion.getInstances();
        Instances testBugMatrix = testversion.getBugMatrix();
        List<Double> testEfforts = testversion.getEfforts();
        List<Double> testNumBugs = testversion.getNumBugs();

        Instances traindata = trainversion.getInstances();
        Instances trainBugMatrix = trainversion.getBugMatrix();
        List<Double> trainEfforts = trainversion.getEfforts();
        List<Double> trainNumBugs = trainversion.getNumBugs();

        boolean takeNewBootstrapSample = true;
        final int abortLimit = 10000;
        int abortCounter = 0;
        final int datasetSize = testdata.size();
        Random rand = new Random();

        while (takeNewBootstrapSample){
            List<Integer> bootstrapSample = new ArrayList<>();
            for (int i = 0; i < datasetSize; i++) {
                bootstrapSample.add(rand.nextInt(datasetSize));
            }
            traindata.clear();
            if (trainBugMatrix != null) {
                trainBugMatrix.clear();
            }
            if (trainEfforts != null) {
                trainEfforts.clear();
            }
            if (trainNumBugs != null) {
                trainNumBugs.clear();
            }
            for (int i = 0; i < datasetSize; i++) {
                int index = bootstrapSample.get(i);
                traindata.add((Instance) testversioncopy.getInstances().get(index).copy());
                if (trainBugMatrix != null) {
                    trainBugMatrix.add((Instance) testversioncopy.getBugMatrix().get(index).copy());
                }
                if (trainEfforts != null) {
                    trainEfforts.add(testversioncopy.getEfforts().get(index));
                }
                if (trainNumBugs != null) {
                    trainNumBugs.add(testversioncopy.getNumBugs().get(index));
                }
            }
            List<Integer> leftovers = new ArrayList<>();
            for (int i = 0; i < datasetSize; i++) {
                leftovers.add(i);
            }
            for (int i = 0; i < datasetSize; i++) {
                leftovers.remove(bootstrapSample.get(i));
            }
            testdata.clear();
            if (testBugMatrix != null) {
                testBugMatrix.clear();
            }
            if (testEfforts != null) {
                testEfforts.clear();
            }
            if (testNumBugs != null) {
                testNumBugs.clear();
            }
            for (int index : leftovers) {
                testdata.add((Instance) testversioncopy.getInstances().get(index).copy());
                if (testBugMatrix != null) {
                    testBugMatrix.add((Instance) testversioncopy.getBugMatrix().get(index).copy());
                }
                if (testEfforts != null) {
                    testEfforts.add(testversioncopy.getEfforts().get(index));
                }
                if (testNumBugs != null) {
                    testNumBugs.add(testversioncopy.getNumBugs().get(index));
                }
            }
            if (traindata.attributeStats(traindata.classIndex()).nominalCounts[1] >= this.minDefects &&
                    testdata.attributeStats(testdata.classIndex()).nominalCounts[1] >= 1){
                takeNewBootstrapSample = false;
            }
            else {
                abortCounter += 1;
                if (abortCounter >= abortLimit){
                    throw new RuntimeException(
                            String.format("Error while taking a bootstrap sample of %s: After %d iterations, " +
                                            "no bootstrap sample containing at least %d defective files in the " +
                                            "training data and at least 1 defective file in the test data was created.",
                                    trainversion.getVersion(), abortLimit, this.minDefects));
                }
            }
        }
        return trainversion;
    }
}
