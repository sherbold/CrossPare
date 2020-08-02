package de.ugoe.cs.cpdp.dataprocessing;

import static org.junit.Assert.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import org.junit.Test;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.cpdp.loader.JitFolderLoader;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;
import weka.core.Attribute;

public class JitDateFilterTest {

    @Test
    public void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        OffsetDateTime trainStartDate = OffsetDateTime.parse("2003-05-23" + " 00:00:00+00:00", formatter);
        OffsetDateTime trainEndDate = OffsetDateTime.parse("2015-06-23" + " 00:00:00+00:00", formatter);
        OffsetDateTime testStartDate = OffsetDateTime.parse("2015-09-23" + " 00:00:00+00:00", formatter);
        OffsetDateTime testEndDate = OffsetDateTime.parse("2017-11-01" + " 00:00:00+00:00", formatter);

        JitDateFilter filter = new JitDateFilter();
        filter.setParameter("2003-05-23 2015-06-23 2015-09-23 2017-11-01");

        JitFolderLoader loader = new JitFolderLoader();
        loader.setLocation("testdata/jit");
        loader.setParameter("jira");
        List<SoftwareVersion> versions = loader.load();

        SetUniqueList<SoftwareVersion> trainVersions = SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
        SetUniqueList<Instances> trainData = SetUniqueList.setUniqueList(new LinkedList<Instances>());
        SetUniqueList<Instances> trainBugMatrices = SetUniqueList.setUniqueList(new LinkedList<Instances>());
        trainVersions.addAll(versions);
        trainData.add(versions.get(0).getInstances());
        trainData.add(versions.get(1).getInstances());
        trainBugMatrices.add(versions.get(0).getBugMatrix());
        trainBugMatrices.add(versions.get(1).getBugMatrix());

        SoftwareVersion testVersion = versions.get(0);
        Instances testData = versions.get(0).getInstances();
        Instances testBugMatrix = versions.get(0).getBugMatrix();

        filter.apply(testVersion, trainVersions, testData, trainData, testBugMatrix, trainBugMatrices);

        int[] trainDateCount = new int[trainVersions.size()];
        int testDateCount = 0;
        for (int i = 0; i < versions.size(); i++) {
            for (OffsetDateTime date : versions.get(i).getCommitterDates()) {
                if (i == 0 && date.compareTo(testStartDate) >= 0 && date.compareTo(testEndDate) < 0) {
                    testDateCount++;
                }
                if (date.compareTo(trainStartDate) >= 0 && date.compareTo(trainEndDate) < 0) {
                    trainDateCount[i]++;
                }
            }
        }
        assertEquals(trainDateCount[0], trainData.get(0).size());
        assertEquals(trainDateCount[1], trainData.get(1).size());
        assertEquals(testDateCount, testData.size());

        for (Instances bugMatrix : trainBugMatrices) {
            for (Iterator<Attribute> iterator = bugMatrix.enumerateAttributes().asIterator(); iterator.hasNext();) {
                Attribute attribute = iterator.next();
                String attributeName = attribute.name();
                String dateString = attributeName.substring(attributeName.lastIndexOf("_") + 1);
                dateString += (dateString.contains("+")) ? "" : "+00:00";
                OffsetDateTime issueDate = OffsetDateTime.parse(dateString, formatter);
                assertTrue(issueDate.compareTo(trainStartDate) >= 0 && issueDate.compareTo(testStartDate) < 0);
            }
        }

        assertEquals(trainData.get(0).size(), trainBugMatrices.get(0).size());
        assertEquals(trainData.get(1).size(), trainBugMatrices.get(1).size());
        assertEquals(testData.size(), testBugMatrix.size());
    }
}