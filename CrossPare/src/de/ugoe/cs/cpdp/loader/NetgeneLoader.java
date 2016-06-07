// Copyright 2016 Georg-August-Universität Göttingen, Germany
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import de.ugoe.cs.util.console.Console;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 * <p>
 * Loads the genealogy data published by Herzig et al.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class NetgeneLoader implements SingleVersionLoader {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#load(java.io.File)
     */
    @Override
    public Instances load(File fileMetricsFile) {
        // first determine all files
        String path = fileMetricsFile.getParentFile().getAbsolutePath();
        String project = fileMetricsFile.getName().split("_")[0];
        File bugsFile = new File(path + "/" + project + "_bugs_per_file.csv");
        File networkMetrics = new File(path + "/" + project + "_network_metrics.csv");
        Instances metricsData = null;

        try {
            CSVLoader wekaCsvLoader = new CSVLoader();
            wekaCsvLoader.setSource(fileMetricsFile);
            metricsData = wekaCsvLoader.getDataSet();
            wekaCsvLoader.setSource(bugsFile);
            Instances bugsData = wekaCsvLoader.getDataSet();
            wekaCsvLoader.setSource(networkMetrics);
            Instances networkData = wekaCsvLoader.getDataSet();

            metricsData.setRelationName(project);

            // fix nominal attributes (i.e., NA values)
            for (int j = 2; j < networkData.numAttributes(); j++) {
                if (networkData.attribute(j).isNominal()) {
                    String attributeName = networkData.attribute(j).name();
                    double[] tmpVals = new double[networkData.size()];
                    // get temporary values
                    for (int i = 0; i < networkData.size(); i++) {
                        Instance inst = networkData.instance(i);
                        if (!inst.isMissing(j)) {
                            String val = networkData.instance(i).stringValue(j);
                            try {
                                tmpVals[i] = Double.parseDouble(val);
                            }
                            catch (NumberFormatException e) {
                                // not a number, using 0.0;
                                tmpVals[i] = 0.0;
                            }
                        }
                        else {
                            tmpVals[i] = 0.0;
                        }
                    }
                    // replace attribute
                    networkData.deleteAttributeAt(j);
                    networkData.insertAttributeAt(new Attribute(attributeName), j);
                    for (int i = 0; i < networkData.size(); i++) {
                        networkData.instance(i).setValue(j, tmpVals[i]);
                    }
                }
            }
            // fix string attributes
            for (int j = 2; j < networkData.numAttributes(); j++) {
                if (networkData.attribute(j).isString()) {
                    String attributeName = networkData.attribute(j).name();
                    double[] tmpVals = new double[networkData.size()];
                    // get temporary values
                    for (int i = 0; i < networkData.size(); i++) {
                        Instance inst = networkData.instance(i);
                        if (!inst.isMissing(j)) {
                            String val = networkData.instance(i).stringValue(j);
                            try {
                                tmpVals[i] = Double.parseDouble(val);
                            }
                            catch (NumberFormatException e) {
                                // not a number, using 0.0;
                                tmpVals[i] = 0.0;
                            }
                        }
                        else {
                            tmpVals[i] = 0.0;
                        }
                    }
                    // replace attribute
                    networkData.deleteAttributeAt(j);
                    networkData.insertAttributeAt(new Attribute(attributeName), j);
                    for (int i = 0; i < networkData.size(); i++) {
                        networkData.instance(i).setValue(j, tmpVals[i]);
                    }
                }
            }

            Map<String, Integer> filenames = new HashMap<>();
            for (int j = 0; j < metricsData.size(); j++) {
                filenames.put(metricsData.instance(j).stringValue(0), j);
            }
            // merge with network data
            int attributeIndex;
            for (int j = 2; j < networkData.numAttributes(); j++) {
                attributeIndex = metricsData.numAttributes();
                metricsData.insertAttributeAt(networkData.attribute(j), attributeIndex);
                for (int i = 0; i < networkData.size(); i++) {
                    Integer instanceIndex = filenames.get(networkData.instance(i).stringValue(1));
                    if (instanceIndex != null) {
                        metricsData.instance(instanceIndex)
                            .setValue(attributeIndex, networkData.instance(i).value(j));
                    }
                }
            }

            // add bug information
            attributeIndex = metricsData.numAttributes();
            final ArrayList<String> classAttVals = new ArrayList<String>();
            classAttVals.add("0");
            classAttVals.add("1");
            final Attribute classAtt = new Attribute("bug", classAttVals);
            metricsData.insertAttributeAt(classAtt, attributeIndex);
            for (int i = 0; i < bugsData.size(); i++) {
                if (bugsData.instance(i).value(2) > 0.0d) {
                    Integer instanceIndex = filenames.get(bugsData.instance(i).stringValue(1));
                    if (instanceIndex != null) {
                        metricsData.instance(instanceIndex).setValue(attributeIndex, 1.0);
                    }
                }
            }

            // remove filenames
            metricsData.deleteAttributeAt(0);
            Attribute eigenvector = metricsData.attribute("eigenvector");
            if (eigenvector != null) {
                for (int j = 0; j < metricsData.numAttributes(); j++) {
                    if (metricsData.attribute(j) == eigenvector) {
                        metricsData.deleteAttributeAt(j);
                    }
                }
            }

            metricsData.setClassIndex(metricsData.numAttributes() - 1);

            // set all missing values to 0
            for (int i = 0; i < metricsData.size(); i++) {
                for (int j = 0; j < metricsData.numAttributes(); j++) {
                    if (metricsData.instance(i).isMissing(j)) {
                        metricsData.instance(i).setValue(j, 0.0d);
                    }
                }
            }
        }
        catch (IOException e) {
            Console.traceln(Level.SEVERE, "failure reading file: " + e.getMessage());
            metricsData = null;
        }
        return metricsData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#filenameFilter(java.lang.String)
     */
    @Override
    public boolean filenameFilter(String filename) {
        return filename.endsWith("fileMetrics.csv");
    }

}
