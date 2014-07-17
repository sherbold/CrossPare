package de.ugoe.cs.cpdp.dataselection;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.util.MathArrays;

import weka.core.Instances;

/**
 * Filter based on the k-nearest neighbor (KNN) algorithm S. Herbold: Training data selection for cross-project defect prediction
 * @author Steffen Herbold
 */
public class SetWiseKNNSelection extends AbstractCharacteristicSelection {
	
	/**
	 * number of neighbors selected
	 */
	private int k = 1;
	
	/**
	 * @see de.ugoe.cs.cpdp.dataselection.SetWiseDataselectionStrategy#apply(weka.core.Instances, org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
		final Instances data = normalizedCharacteristicInstances(testdata, traindataSet);
		
		final Set<Integer> selected = new HashSet<Integer>();		
		for( int i=0 ; i<k ; i++ ) {
			int closestIndex = getClosest(data);
			
			selected.add(closestIndex);
			data.delete(closestIndex);
		}
		
		for( int i=traindataSet.size()-1; i>=0 ; i-- ) {
			if( selected.contains(i) ) {
				traindataSet.remove(i);
			}
		}
	}
	
	/**
	 * Helper method that determines the index of the instance with the smallest distance to the first instance (index 0).
	 * @param data data set
	 * @return index of the closest instance
	 */
	private int getClosest(Instances data) {
		double closestDistance = Double.MAX_VALUE;
		int closestIndex = 1;
		for( int i=1 ; i<data.numInstances() ; i++ ) {
			double distance = MathArrays.distance(data.instance(0).toDoubleArray(), data.instance(i).toDoubleArray());
			if( distance < closestDistance) {
				closestDistance = distance;
				closestIndex = i;
			}
		}
		return closestIndex;
	}

	/**
	 * Sets the number of neighbors followed by the distributional characteristics, the values are separated by blanks.
	 * @see AbstractCharacteristicSelection#setParameter(String) 
	 */
	@Override
	public void setParameter(String parameters) {
		if( !"".equals(parameters) ) {
			final String[] split = parameters.split(" ");
			k = Integer.parseInt(split[0]);
			String str = "";
			for( int i=1 ; i<split.length; i++ ) {
				str += split[i];
				if( i<split.length-1 )  {
					str += " ";
				}
			}
			super.setParameter(str);
		}
	}
}
