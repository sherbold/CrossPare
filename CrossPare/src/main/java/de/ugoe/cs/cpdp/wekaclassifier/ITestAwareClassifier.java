
package de.ugoe.cs.cpdp.wekaclassifier;

import weka.core.Instances;

/**
 * <p>
 * Interface for test data aware classifier implementations
 * </p>
 * 
 * @author Steffen Herbold
 */
public interface ITestAwareClassifier {

    /**
     * <p>
     * passes the test data to the classifier
     * </p>
     *
     * @param testdata
     *            the test data
     */
    public void setTestdata(Instances testdata);

}
