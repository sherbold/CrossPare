package de.ugoe.cs.cpdp.dataprocessing;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * A data processing strategy that is applied to the software version of the test data and a multiple software versions as training data.
 * 
 * @author Steffen Herbold
 */
public interface IVersionProcessingStrategy {
	
    /**
     * Applies the processing strategy.
     * 
     * @param testdata
     *            version of the test data
     * @param trainversion
     *            version of the training data
     */
    void apply(SoftwareVersion testdata, SoftwareVersion trainversion);
}

