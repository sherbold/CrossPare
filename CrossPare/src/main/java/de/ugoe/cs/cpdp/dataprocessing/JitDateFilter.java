package de.ugoe.cs.cpdp.dataprocessing;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.DoubleStream;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Filters a set of just-in-time training data and the test data in such a way
 * that they are seperated in time by four dates:
 * 
 * Training start date: All instances from the training set before this date
 * will be removed
 * 
 * Training end date: All instances from the training set after this date will
 * be removed
 * 
 * Test start date: All bugs from the training set after this date will be
 * removed, all instances from the test set before this date will be removed
 * 
 * Test end date: All instances from the test set after this date will be
 * removed
 * 
 * @author jvdmosel
 */
public class JitDateFilter implements ISetWiseVersionProcessingStrategy {

	/**
	 * the training start date
	 */
	private OffsetDateTime trainStartDate;

	/**
	 * the training end date
	 */
	private OffsetDateTime trainEndDate;

	/**
	 * the test start date
	 */
	private OffsetDateTime testStartDate;

	/**
	 * the test end date
	 */
	private OffsetDateTime testEndDate;

	/**
	 * Sets start and end dates for training and test data. The string contains four
	 * blank-seperated dates in yyyy-MM-dd format. Order: training start date,
	 * training end date, test start date, test end date
	 * 
	 * @param parameters string of blank-separated dates in yyyy-MM-dd format
	 */
	@Override
	public void setParameter(String parameters) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
		if (parameters != null && !parameters.equals("")) {
			String[] parameterArray = parameters.split(" ");
			trainStartDate = OffsetDateTime.parse(parameterArray[0] + " 00:00:00+00:00", formatter);
			trainEndDate = OffsetDateTime.parse(parameterArray[1] + " 00:00:00+00:00", formatter);
			testStartDate = OffsetDateTime.parse(parameterArray[2] + " 00:00:00+00:00", formatter);
			testEndDate = OffsetDateTime.parse(parameterArray[3] + " 00:00:00+00:00", formatter);
		}
	}

	/**
	 * @see ISetWiseVersionProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
	 *      org.apache.commons.collections4.list.SetUniqueList, weka.core.Instances,
	 *      org.apache.commons.collections4.list.SetUniqueList, weka.core.Instances,
	 *      org.apache.commons.collections4.list.SetUniqueList)
	 */
	@Override
	public void apply(SoftwareVersion testVersion, SetUniqueList<SoftwareVersion> trainVersions, Instances testData,
			SetUniqueList<Instances> trainData, Instances testBugMatrix, SetUniqueList<Instances> trainBugMatrices) {
		removeInstancesByDate(testData, testVersion.getCommitterDates(), testStartDate, testEndDate, testBugMatrix);
		Iterator<SoftwareVersion> versionIterator = trainVersions.iterator();
		Iterator<Instances> bugMatrixIterator = trainBugMatrices.iterator();
		for (Iterator<Instances> dataIterator = trainData.iterator(); dataIterator.hasNext();) {
			SoftwareVersion version = versionIterator.next();
			Instances bugMatrix = bugMatrixIterator.next();
			Instances data = dataIterator.next();
			removeInstancesByDate(data, version.getCommitterDates(), trainStartDate, trainEndDate, bugMatrix);
			removeIssuesByDate(bugMatrix, testStartDate);
			updateLabel(data, bugMatrix);
		}
	}

	/**
	 * Removes all instances from the data and bugmatrix whose date is not between
	 * start and end date
	 * 
	 * @param data      the data
	 * @param dates     the corresponding dates of the data
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @param bugMatrix the bugmatrix
	 */
	private void removeInstancesByDate(Instances data, List<OffsetDateTime> dates, OffsetDateTime startDate,
			OffsetDateTime endDate, Instances bugMatrix) {
		int index = 0;
		Iterator<Instance> bugMatrixIterator = bugMatrix.iterator();
		for (Iterator<Instance> dataIterator = data.iterator(); dataIterator.hasNext(); index++) {
			dataIterator.next();
			bugMatrixIterator.next();
			if (dates.get(index).compareTo(startDate) < 0 || dates.get(index).compareTo(endDate) > 0) {
				dataIterator.remove();
				bugMatrixIterator.remove();
			}
		}
	}

	/**
	 * Removes all bugs from the bugmatrix after a certain date
	 * 
	 * @param bugMatrix the bugmatrix
	 * @param date      the date
	 */
	private void removeIssuesByDate(Instances bugMatrix, OffsetDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
		int index = 0;
		for (Iterator<Attribute> iterator = bugMatrix.enumerateAttributes().asIterator(); iterator.hasNext(); index++) {
			Attribute attribute = iterator.next();
			String attributeName = attribute.name();
			String dateString = attributeName.substring(attributeName.lastIndexOf("_") + 1);
			dateString += (dateString.contains("+")) ? "" : "+00:00";
			OffsetDateTime issueDate = OffsetDateTime.parse(dateString, formatter);
			if (issueDate.compareTo(date) >= 0) {
				bugMatrix.deleteAttributeAt(index);
				index--;
			}
		}
	}

	/**
	 * Updates the labels to reflect changes in the bugmatrix (i.e. removed bugs)
	 * 
	 * @param data      the instances containing the labels
	 * @param bugMatrix the bugMatrix
	 */
	private void updateLabel(Instances data, Instances bugMatrix) {
		Iterator<Instance> bugMatrixIterator = bugMatrix.iterator();
		for (Iterator<Instance> dataIterator = data.iterator(); dataIterator.hasNext();) {
			Instance dataInstance = dataIterator.next();
			Instance bugMatrixInstance = bugMatrixIterator.next();
			double[] bugMatrixValues = bugMatrixInstance.toDoubleArray();
			int label = (int) DoubleStream.of(bugMatrixValues).sum();
			if (dataInstance.classAttribute().isNominal()) {
				if (label == 0) {
					dataInstance.setClassValue(0);
				} else {
					dataInstance.setClassValue(1);
				}
			} else {
				dataInstance.setClassValue(label);
			}
		}
	}

}
