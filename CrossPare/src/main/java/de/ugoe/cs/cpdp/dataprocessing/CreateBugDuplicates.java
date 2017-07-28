
package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * This processor creates duplicates for each defect-prone entities, such that the number of
 * duplicates equals the number of bugs known for the entity. This serves as weighting strategy to
 * give higher weight for defect-prone entities. Moreover, we hope that this offsets the class
 * imbalance to some degree.
 * 
 * This strategy requires the bug labels to be numeric. Afterwards they will be binary.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class CreateBugDuplicates implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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
            createDuplicates(traindata);
        }
        WekaUtils.makeClassBinary(testdata);
    }

    /**
     * @see IProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        createDuplicates(traindata);
        WekaUtils.makeClassBinary(testdata);
    }

    /**
     * <p>
     * Helper method that creates the duplicates.
     * </p>
     *
     * @param data
     */
    private void createDuplicates(Instances data) {
        // we need to cache the size because it will increase with adding duplicates
        // without caching we would run into an infinite loop
        int sizeBeforeDuplicates = data.size();
        for (int i = 0; i < sizeBeforeDuplicates; i++) {
            int count = (int) data.get(i).classValue();
            for (int j = 1; j < count; j++) {
                Instance copy = new DenseInstance(data.get(i));
                data.add(copy);
            }
        }
        WekaUtils.makeClassBinary(data);
    }
}
