
package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * This processor creates weight for entities based on their number of bugs.
 * 
 * This strategy requires the bug labels to be numeric. Afterwards they will be binary.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class WeightByNumBugs implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        for (SoftwareVersion trainversion : trainversionSet) {
            createWeights(trainversion.getInstances());
        }
    }

    /**
     * @see IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        createWeights(trainversion.getInstances());
    }

    /**
     * <p>
     * Helper method that creates the weights.
     * </p>
     *
     * @param data
     */
    private void createWeights(Instances data) {
        for (Instance instance : data) {
            if (instance.classValue() >= 1.0) {
                instance.setWeight(instance.classValue());
            }
        }
    }
}
