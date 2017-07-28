
package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

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
     * @see ISetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for (Instances traindata : traindataSet) {
            createWeights(traindata);
        }
    }

    /**
     * @see IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        createWeights(traindata);
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
