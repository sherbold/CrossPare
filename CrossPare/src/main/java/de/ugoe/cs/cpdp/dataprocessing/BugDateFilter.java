package de.ugoe.cs.cpdp.dataprocessing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Uses the bug matrix to modify the training data such that only bugs that were
 * fixed before the release of the test data are counted.
 * 
 * @author sherbold
 */
public class BugDateFilter implements IVersionProcessingStrategy {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
	/**
	 * Resets the class label of the training data to ignore all bugs that were not
	 * yet fixed when the test data was released.
	 * 
	 * @param testversion  the testdata
	 * @param trainversion the trainversion
	 * @param traindata    the training data
	 */
	@Override
	public void apply(SoftwareVersion testversion, SoftwareVersion trainversion, Instances traindata) {
		if (testversion.getReleaseDate() == null || trainversion.getBugMatrix()==null) {
			LOGGER.warn("BugDateFilter does nothing because release date or bug matrix is not available");
			return;
		}
		LocalDateTime releaseDate = testversion.getReleaseDate();
		
		Instances bugMatrix = trainversion.getBugMatrix();

		int[] bugCounts = new int[traindata.size()];
		for (int j = 0; j < bugMatrix.numAttributes(); j++) {
			String attributeName = bugMatrix.attribute(j).name();
			String bugfixDate = attributeName.substring(attributeName.lastIndexOf("_") + 1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime bugDate = LocalDateTime.parse(bugfixDate, formatter);
			if (releaseDate.compareTo(bugDate) > 0) {
				for (int i = 0; i < bugMatrix.size(); i++) {
					if (bugMatrix.get(i).value(j) > 0) {
						bugCounts[i]++;
					}
				}
			}
		}

		for (int i = 0; i < traindata.size(); i++) {
			if (traindata.classAttribute().isNominal()) {
				if (bugCounts[i] > 0) {
					traindata.get(i).setClassValue(1.0d);
				} else {
					traindata.get(i).setClassValue(0.0d);
				}
			} else if (traindata.classAttribute().isNumeric()) {
				traindata.get(i).setClassValue(bugCounts[i]);
			} else {
				throw new RuntimeException("class attribute invalid: neither numeric nor nominal");
			}
		}
	}
}
