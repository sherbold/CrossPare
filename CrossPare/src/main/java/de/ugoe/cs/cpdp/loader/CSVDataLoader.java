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
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import de.ugoe.cs.util.FileTools;

/**
 * Loads the instances for a software version from a CSV file of the PROMISE data set mined by
 * Jurezko and Madeyski.
 * 
 * @author Steffen Herbold
 */
class CSVDataLoader implements SingleVersionLoader {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#load( java.io.File)
     */
    @Override
    public Instances load(File file, boolean binaryClass) {
        final String[] lines;
        try {
            lines = FileTools.getLinesFromFile(file.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // configure Instances
        final ArrayList<Attribute> atts = new ArrayList<>();

        String[] lineSplit = lines[0].split(",");
        for (int j = 0; j < lineSplit.length - 4; j++) {
            atts.add(new Attribute(lineSplit[j + 3]));
        }
        Attribute classAtt;
        if(binaryClass) {
            // add nominal class attribute
            final ArrayList<String> classAttVals = new ArrayList<>();
            classAttVals.add("0");
            classAttVals.add("1");
            classAtt = new Attribute("bug", classAttVals);
        } else {
            // add numeric class attribute
            classAtt = new Attribute("bugs");
        }
        atts.add(classAtt);
        
        final Instances data = new Instances(file.getName(), atts, 0);
        data.setClass(classAtt);

        // fetch data
        for (int i = 1; i < lines.length; i++) {
            lineSplit = lines[i].split(",");
            double[] values = new double[lineSplit.length - 3];
            for (int j = 0; j < values.length - 1; j++) {
                values[j] = Double.parseDouble(lineSplit[j + 3].trim());
            }
            if(binaryClass) {
                // nominal class value
                values[values.length - 1] = lineSplit[lineSplit.length - 1].trim().equals("0") ? 0 : 1;
            } else {
                // numeric class value
                values[values.length - 1] = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
            }
            data.add(new DenseInstance(1.0, values));
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
        return filename.endsWith(".csv");
    }

}
