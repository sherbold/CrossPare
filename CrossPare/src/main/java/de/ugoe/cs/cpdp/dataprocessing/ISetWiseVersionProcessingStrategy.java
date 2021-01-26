package de.ugoe.cs.cpdp.dataprocessing;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.cpdp.IParameterizable;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * A data processing strategy that is applied to the software version of the
 * test data and a multiple software versions as training data.
 * 
 * @author jvdmosel
 */
public interface ISetWiseVersionProcessingStrategy extends IParameterizable {

    /**
     * Applies the processing strategy.
     * 
     * @param testVersion      version of the test data
     * @param trainVersions    versions of the training data
     * @param testData         instances of the test data
     * @param trainData        instances of the training data
     * @param testBugMatrix    bugMatrix of the test data
     * @param trainBugMatrices bugMatrices of the training data
     */
    void apply(SoftwareVersion testVersion, SetUniqueList<SoftwareVersion> trainVersions, Instances testData,
            SetUniqueList<Instances> trainData, Instances testBugMatrix, SetUniqueList<Instances> trainBugMatrices);
}
