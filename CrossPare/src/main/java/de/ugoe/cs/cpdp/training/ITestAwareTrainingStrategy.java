
package de.ugoe.cs.cpdp.training;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

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
     * @param trainversion
     *            the training data for all products
     * @param testversion
     *            the test data from the target product
     */
    void apply(SoftwareVersion testversion, SoftwareVersion trainversion);

    /**
     * <p>
     * returns the name of the training strategy
     * </p>
     *
     * @return the name
     */
    String getName();
}
