// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * <p>
 * Loads data from the automative defect data set from Audi Electronic Ventures donated by Altinger
 * et al. at the MSR 2015. This loader contains the changes per commit, i.e., it is for JIT defect
 * prediction.
 * </p>
 * 
 * @author Steffen Herbold
 */
class AUDIChangeLoader implements SingleVersionLoader {

    /**
     * <p>
     * Internal helper class.
     * </p>
     * 
     * @author Steffen Herbold
     */
    private class EntityRevisionPair implements Comparable<EntityRevisionPair> {

        /**
         * string that defines an entity
         */
        final String entity;

        /**
         * revision number of the entity
         */
        private final int revision;

        /**
         * <p>
         * Constructor. Creates a new EntityRevisionPair.
         * </p>
         *
         * @param entity
         *            the entity
         * @param revision
         *            the revision
         */
        @SuppressWarnings("hiding")
        public EntityRevisionPair(String entity, int revision) {
            this.entity = entity;
            this.revision = revision;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof EntityRevisionPair)) {
                return false;
            }
            return compareTo((EntityRevisionPair) other) == 0;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return this.entity.hashCode() + this.revision;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(EntityRevisionPair other) {
            int strCmp = this.entity.compareTo(other.entity);
            if (strCmp != 0) {
                return strCmp;
            }
            return Integer.compare(this.revision, other.revision);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return this.entity + "@" + this.revision;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#load(java.io.File)
     */
    @SuppressWarnings("boxing")
    @Override
    public Instances load(File file, boolean binaryClass) {
        if(!binaryClass) {
            // TODO implement regression loading
            throw new RuntimeException("regrssion loading not yet supported for AUDIChangeLoader");
        }
        final String[] lines;
        String[] lineSplit;
        String[] lineSplitBug;

        try {
        	List<String> stringList = Files.readAllLines(file.toPath());
			lines = stringList.toArray(new String[] {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // information about bugs are in another file
        String path = file.getAbsolutePath();
        path = path.substring(0, path.length() - 14) + "repro.csv";
        final String[] linesBug;
        try {
            List<String> stringList = Files.readAllLines(file.toPath());
			linesBug = stringList.toArray(new String[] {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        int revisionIndex = -1;
        int bugIndex = -1;
        lineSplitBug = linesBug[0].split(";");
        for (int j = 0; j < lineSplitBug.length; j++) {
            if (lineSplitBug[j].equals("svnrev")) {
                revisionIndex = j;
            }
            if (lineSplitBug[j].equals("num_bugs_trace")) {
                bugIndex = j;
            }
        }
        if (revisionIndex < 0) {
            throw new RuntimeException("could not find SVN revisions");
        }
        if (bugIndex < 0) {
            throw new RuntimeException("could not find bug information");
        }

        int metricsStartIndex = -1;
        int metricsEndIndex = -1;
        lineSplit = lines[0].split(";");
        for (int j = 0; j < lineSplit.length; j++) {
            if (lineSplit[j].equals("lm_LOC")) {
                metricsStartIndex = j;
            }
            if (lineSplit[j].equals("h_E")) {
                metricsEndIndex = j;
            }
        }
        if (metricsStartIndex < 0) {
            throw new RuntimeException("could not find first metric, i.e., lm_LOC");
        }
        if (metricsEndIndex < 0) {
            throw new RuntimeException("could not find last metric, i.e., h_E");
        }
        int numMetrics = metricsEndIndex - metricsStartIndex + 1;

        // create sets of all filenames and revisions
        SortedMap<EntityRevisionPair, Integer> entityRevisionPairs = new TreeMap<>();
        for (int i = 1; i < linesBug.length; i++) {
            lineSplitBug = linesBug[i].split(";");
            entityRevisionPairs.put(
                                    new EntityRevisionPair(lineSplitBug[0],
                                                           Integer
                                                               .parseInt(lineSplitBug[revisionIndex])),
                                    i);
        }

        // prepare weka instances
        final ArrayList<Attribute> atts = new ArrayList<>();
        lineSplit = lines[0].split(";");
        for (int j = metricsStartIndex; j <= metricsEndIndex; j++) {
            atts.add(new Attribute(lineSplit[j] + "_delta"));
        }
        for (int j = metricsStartIndex; j <= metricsEndIndex; j++) {
            atts.add(new Attribute(lineSplit[j] + "_abs"));
        }
        final ArrayList<String> classAttVals = new ArrayList<>();
        classAttVals.add("0");
        classAttVals.add("1");
        final Attribute classAtt = new Attribute("bug", classAttVals);
        atts.add(classAtt);

        final Instances data = new Instances(file.getName(), atts, 0);
        data.setClass(classAtt);

        // create data
        String lastFile = null;
        double[] lastValues = new double[numMetrics];
        int lastNumBugs = 0;
        for (Entry<EntityRevisionPair, Integer> entry : entityRevisionPairs.entrySet()) {
            try {
                // first get values
                lineSplit = lines[entry.getValue()].split(";");
                lineSplitBug = linesBug[entry.getValue()].split(";");
                int i = 0;
                double[] values = new double[numMetrics];
                for (int j = metricsStartIndex; j <= metricsEndIndex; j++) {
                    values[i] = Double.parseDouble(lineSplit[j]);
                    i++;
                }
                int numBugs = Integer.parseInt(lineSplitBug[bugIndex]);

                // then check if an entity must be created
                if (entry.getKey().entity.equals(lastFile)) {
                    // create new instance
                    double[] instanceValues = new double[2 * numMetrics + 1];
                    for (int j = 0; j < numMetrics; j++) {
                        instanceValues[j] = values[j] - lastValues[j];
                        instanceValues[j + numMetrics] = values[j];
                    }
                    // check if any value>0
                    boolean changeOccured = false;
                    for (int j = 0; j < numMetrics; j++) {
                        if (instanceValues[j] > 0) {
                            changeOccured = true;
                        }
                    }
                    if (changeOccured) {
                        instanceValues[instanceValues.length - 1] = numBugs <= lastNumBugs ? 0 : 1;
                        data.add(new DenseInstance(1.0, instanceValues));
                    }
                }
                lastFile = entry.getKey().entity;
                lastValues = values;
                lastNumBugs = numBugs;
            }
            catch (IllegalArgumentException e) {
                System.err.println("error in line " + entry.getValue() + ": " + e.getMessage());
                System.err.println("metrics line: " + lines[entry.getValue()]);
                System.err.println("bugs line: " + linesBug[entry.getValue()]);
                System.err.println("line is ignored");
            }
        }

        return data;
    }

    /*
     * This is a dummy method for testing purposes
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#load( java.io.File)
     */
    @SuppressWarnings("static-method")
    public Instances load(File file, @SuppressWarnings("unused") String dummy) {
        final String[] lines;
        try {
        	List<String> stringList = Files.readAllLines(file.toPath());
			lines = stringList.toArray(new String[] {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // information about bugs are in another file
        String path = file.getAbsolutePath();
        path = path.substring(0, path.length() - 14) + "repro.csv";
        final String[] linesBug;
        try {
            List<String> stringList = Files.readAllLines(file.toPath());
			linesBug = stringList.toArray(new String[] {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // configure Instances
        final ArrayList<Attribute> atts = new ArrayList<>();

        String[] lineSplit = lines[0].split(";");
        // ignore first three/four and last two columns
        int offset;
        if (lineSplit[3].equals("project_rev")) {
            offset = 4;
        }
        else {
            offset = 3;
        }
        for (int j = 0; j < lineSplit.length - (offset + 2); j++) {
            atts.add(new Attribute(lineSplit[j + offset]));
        }
        final ArrayList<String> classAttVals = new ArrayList<>();
        classAttVals.add("0");
        classAttVals.add("1");
        final Attribute classAtt = new Attribute("bug", classAttVals);
        atts.add(classAtt);

        final Instances data = new Instances(file.getName(), atts, 0);
        data.setClass(classAtt);

        // fetch data
        for (int i = 1; i < lines.length; i++) {
            boolean validInstance = true;
            lineSplit = lines[i].split(";");
            String[] lineSplitBug = linesBug[i].split(";");
            double[] values = new double[data.numAttributes()];
            for (int j = 0; validInstance && j < values.length - 1; j++) {
                if (lineSplit[j + offset].trim().isEmpty()) {
                    validInstance = false;
                }
                else {
                    values[j] = Double.parseDouble(lineSplit[j + offset].trim());
                }
            }
            if (offset == 3) {
                values[values.length - 1] = lineSplitBug[7].equals("0") ? 0 : 1;
            }
            else {
                values[values.length - 1] = lineSplitBug[8].equals("0") ? 0 : 1;
            }

            if (validInstance) {
                data.add(new DenseInstance(1.0, values));
            }
            else {
                System.out.println("instance " + i + " is invalid");
            }
        }
        return data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#
     * filenameFilter(java.lang.String)
     */
    @Override
    public boolean filenameFilter(String filename) {
        return filename.endsWith("src.csv");
    }

}
