
package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.core.Instances;

/**
 * <p>
 * Makes the class attribute binary, in case it was a numeric bug count before.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class MakeClassBinary implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        // dummy, no parameters
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        WekaUtils.makeClassBinary(testdata);
        WekaUtils.makeClassBinary(traindata);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        WekaUtils.makeClassBinary(testdata);
        for (Instances traindata : traindataSet) {
            WekaUtils.makeClassBinary(traindata);
        }
    }

}
