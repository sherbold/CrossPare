package de.ugoe.cs.cpdp.dataprocessing;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * Removes an attributes from all data sets using their name. 
 * @author Steffen Herbold
 */
public class AttributeRemoval implements ISetWiseProcessingStrategy, IProcessesingStrategy {

	/**
	 * names of the attributes to be removed (determined by {@link #setParameter(String)}) 
	 */
	private String[] attributeNames = new String[]{};
	
	/**
	 * Sets that attributes that will be removed. The string contains the blank-separated names of the attributes to be removed.
	 * <br><br>
	 * Note, that removal of attributes with blanks is currently not supported!
	 * @param parameters string with the blank-separated attribute names
	 */
	@Override
	public void setParameter(String parameters) {
		if( parameters!=null ) {
			attributeNames = parameters.split(" ");
		}
	}

	/**
	 * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		for( String attributeName : attributeNames ) {
			for( int i=0 ; i<testdata.numAttributes() ; i++ ) {
				if( attributeName.equals(testdata.attribute(i).name()) ) {
					testdata.deleteAttributeAt(i);
					for( Instances traindata : traindataSet ) {
						traindata.deleteAttributeAt(i);
					}
				}
			}
		}
	}

	/**
	 * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances, weka.core.Instances)
	 */
	@Override
	public void apply(Instances testdata, Instances traindata) {
		for( String attributeName : attributeNames ) {
			for( int i=0 ; i<testdata.numAttributes() ; i++ ) {
				if( attributeName.equals(testdata.attribute(i).name()) ) {
					testdata.deleteAttributeAt(i);
					traindata.deleteAttributeAt(i);
				}
			}
		}
	}
}
