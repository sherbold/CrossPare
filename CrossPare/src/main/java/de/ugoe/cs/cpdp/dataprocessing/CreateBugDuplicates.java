
package de.ugoe.cs.cpdp.dataprocessing;

import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
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
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        for (SoftwareVersion trainversion : trainversionSet) {
            createDuplicates(trainversion);
        }
        WekaUtils.makeClassBinary(testversion.getInstances());
    }

    /**
     * @see IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        createDuplicates(trainversion);
        WekaUtils.makeClassBinary(testversion.getInstances());
    }

    /**
     * <p>
     * Helper method that creates the duplicates.
     * </p>
     *
     * @param version
     */
    private void createDuplicates(SoftwareVersion version) {
        Instances data = version.getInstances();
        Instances bugmatrix = version.getBugMatrix();
        List<Double> efforts = version.getEfforts();
        List<Double> numBugs = version.getNumBugs();
        // we need to cache the size because it will increase with adding duplicates
        // without caching we would run into an infinite loop
        int sizeBeforeDuplicates = data.size();
        for (int i = 0; i < sizeBeforeDuplicates; i++) {
            int count = (int) data.get(i).classValue();
            for (int j = 1; j < count; j++) {
                Instance copy = new DenseInstance(data.get(i));
                data.add(copy);
                if (bugmatrix != null) {
                    Instance bugcopy = new DenseInstance(bugmatrix.get(i));
                    bugmatrix.add(bugcopy);
                }
                if (efforts != null) {
                    efforts.add(efforts.get(i));
                }
                if (numBugs != null) {
                    numBugs.add(numBugs.get(i));
                }
            }
        }
        WekaUtils.makeClassBinary(data);
    }
}
