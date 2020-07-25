package de.ugoe.cs.cpdp.dataprocessing;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.cpdp.IParameterizable;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * A data processing strategy that is applied to the software version of the test data and a multiple software versions as training data.
 * 
 * @author jvdmosel
 */
public interface ISetWiseVersionProcessingStrategy extends IParameterizable {
	
    /**
     * Applies the processing strategy.
     * 
     * @param testversion
     *            version of the test data
     * @param trainversion
     *            version of the training data
     * @param testdata
     *            instances of the test data
     * @param traindata
     *            instances of the training data
     * @param testBugMatrix
     *            bugMatrix of the test data
     * @param trainBugMatrix
     *            bugMatrix of the training data
     */
    void apply(SoftwareVersion testVersion, SetUniqueList<SoftwareVersion> trainVersions, Instances testData, SetUniqueList<Instances> trainData, Instances testBugMatrix, SetUniqueList<Instances> trainBugMatrices);
}

