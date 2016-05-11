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
 * TODO
 * 
 * @author sherbold
 * 
 */
class AUDIDataLoader implements SingleVersionLoader {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#load( java.io.File)
     */
    @Override
    public Instances load(File file) {
        final String[] lines;
        try {
            lines = FileTools.getLinesFromFile(file.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // information about bugs are in another file
        String path = file.getAbsolutePath();
        path = path.substring(0, path.length() - 14) + "repro.csv";
        final String[] linesBug;
        try {
            linesBug = FileTools.getLinesFromFile(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // configure Instances
        final ArrayList<Attribute> atts = new ArrayList<Attribute>();

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
        final ArrayList<String> classAttVals = new ArrayList<String>();
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
