
package de.ugoe.cs.cpdp.training;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * <p>
 * Training strategy for training with access to the target data and one data set per input product.
 * </p>
 * 
 * @author Steffen Herbold
 */
public interface ISetWiseTestdataAwareTrainingStrategy extends ITrainer {

    /**
     * <p>
     * Applies the training strategy.
     * </p>
     *
     * @param trainversionSet
     *            the training data per product
     * @param testversion
     *            the test data from the target product
     */
    void apply(SetUniqueList<SoftwareVersion> traindataSet, SoftwareVersion testversion);

    /**
     * <p>
     * returns the name of the training strategy
     * </p>
     *
     * @return the name
     */
    String getName();

    // TODO: these two methods look like they should be removed and instead be handled using the
    // parameters
    /**
     * <p>
     * Sets the method for the strategy
     * </p>
     *
     * @param method the method
     */
    void setMethod(String method);

    /**
     * <p>
     * Sets the threshold for the strategy
     * </p>
     *
     * @param threshold the threshold
     */
    void setThreshold(String threshold);
}
