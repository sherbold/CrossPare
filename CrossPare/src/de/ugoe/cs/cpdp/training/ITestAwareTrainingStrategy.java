
package de.ugoe.cs.cpdp.training;

import weka.core.Instances;

/**
 * <p>
 * Training strategy for training with access to the target data and the training data as a single
 * data set.
 * </p>
 * 
 * @author Steffen Herbold
 */
public interface ITestAwareTrainingStrategy extends ITrainer {

    /**
     * <p>
     * Applies the training strategy.
     * </p>
     *
     * @param traindata
     *            the training data for all products
     * @param testdata
     *            the test data from the target product
     */
    void apply(Instances testdata, Instances traindata);

    /**
     * <p>
     * returns the name of the training strategy
     * </p>
     *
     * @return the name
     */
    String getName();
}
