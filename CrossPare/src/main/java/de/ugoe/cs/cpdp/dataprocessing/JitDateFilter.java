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

public class JitDateFilter implements ISetWiseVersionProcessingStrategy {
    
	private OffsetDateTime trainStartDate;
	private OffsetDateTime trainEndDate;
	private OffsetDateTime testStartDate;
	private OffsetDateTime testEndDate;

	@Override
	public void setParameter(String parameters) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
		if (parameters != null && !parameters.equals("")) {
			String[] parameterArray = parameters.split(" ");
			trainStartDate = OffsetDateTime.parse(parameterArray[0]+" 00:00:00+00:00", formatter);
			trainEndDate = OffsetDateTime.parse(parameterArray[1]+" 00:00:00+00:00", formatter);
			testStartDate = OffsetDateTime.parse(parameterArray[2]+" 00:00:00+00:00", formatter);
			testEndDate = OffsetDateTime.parse(parameterArray[3]+" 00:00:00+00:00", formatter);
		}
	}

	@Override
	public void apply(SoftwareVersion testVersion, SetUniqueList<SoftwareVersion> trainVersions, Instances testData, SetUniqueList<Instances> trainData, Instances testBugMatrix, SetUniqueList<Instances> trainBugMatrices) {
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

	private void removeInstancesByDate(Instances data, List<OffsetDateTime> dates, OffsetDateTime startDate, OffsetDateTime endDate, Instances bugMatrix) {
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

	private void updateLabel(Instances data, Instances bugMatrix) {
		Iterator<Instance> bugMatrixIterator = bugMatrix.iterator();
		for (Iterator<Instance> dataIterator = data.iterator(); dataIterator.hasNext();) {
			Instance dataInstance = dataIterator.next();
			Instance bugMatrixInstance = bugMatrixIterator.next();
			double[] bugMatrixValues = bugMatrixInstance.toDoubleArray();
			int label = (int)DoubleStream.of(bugMatrixValues).sum();
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
