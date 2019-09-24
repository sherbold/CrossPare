package de.ugoe.cs.cpdp.loader;

import weka.core.Instances;

/**
 * Interface that describes if a data loader also retrieves a bug matrix, i.e.,
 * a matrix in which the columns are bugs and the rows are artifacts. The matrix
 * contains a 1 if the bug affects an artifact and 0 otherwise.
 * 
 * @author sherbold
 *
 */
public interface IBugMatrixLoader {

	/**
	 * returns the bug matrix
	 * 
	 * @return bug matrix
	 */
	public Instances getBugMatrix();
}
