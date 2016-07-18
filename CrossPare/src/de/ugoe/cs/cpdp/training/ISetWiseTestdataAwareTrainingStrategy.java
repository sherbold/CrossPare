
package de.ugoe.cs.cpdp.training;

import org.apache.commons.collections4.list.SetUniqueList;
import weka.core.Instances;

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
     * @param traindataSet
     *            the training data per product
     * @param testdata
     *            the test data from the target product
     */
    void apply(SetUniqueList<Instances> traindataSet, Instances testdata);

    /**
     * <p>
     * returns the name of the training strategy
     * </p>
     *
     * @return the name
     */
    String getName();

    // TODO: these two methods look like they should be removed and instead be handled using the parameters
    void setMethod(String method);

    void setThreshold(String threshold);
}
