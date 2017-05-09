// Copyright 2017 Georg-August-Universität Göttingen, Germany
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * <p>
 * Loader for JSON data for defect prediciton exported from SmartSHARK.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class JsonDataLoader implements SingleVersionLoader {

    /**
     * Keys in JSON that do not contain metric data
     */
    private static Set<String> ignoredKeys = new HashSet<>(Arrays.asList(new String[]
        { "bugs", "file", "label", "imports", "type" }));

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader.SingleVersionLoader#load( java.io.File)
     */
    @Override
    public Instances load(File file) {
        String jsonData = null;
        try {
            jsonData = FileUtils.readFileToString(file);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray entityArray = new JSONArray(jsonData);
        // first get all keys (in case of missing values)
        Set<String> keySet = new TreeSet<>();
        for (int i = 0; i < entityArray.length(); i++) {
            keySet.addAll(entityArray.getJSONObject(i).keySet());
        }
        // configure Instances
        final ArrayList<Attribute> atts = new ArrayList<Attribute>();

        for (String key : keySet) {
            // filter keys that are not metrics
            if (!ignoredKeys.contains(key)) {
                atts.add(new Attribute(key));
            }
        }
        final ArrayList<String> classAttVals = new ArrayList<String>();
        classAttVals.add("0");
        classAttVals.add("1");
        final Attribute classAtt = new Attribute("bug", classAttVals);
        atts.add(classAtt);

        final Instances data = new Instances(file.getName(), atts, 0);
        data.setClass(classAtt);

        // fetch data
        for (int i = 0; i < entityArray.length(); i++) {
            JSONObject entity = entityArray.getJSONObject(i);
            double[] values = new double[data.numAttributes()];
            int j = 0;
            for (String key : keySet) {
                // filter keys that are not metrics
                if (!ignoredKeys.contains(key)) {
                    if (key.equals("gui") || key.equals("db") || key.equals("multithreading") ||
                        key.equals("test") || key.equals("network") || key.equals("webservice") ||
                        key.equals("fileio"))
                    {
                        // keys with boolean values
                        values[j] = entity.getBoolean(key) ? 1.0 : 0.0;
                    }
                    else {
                        // keys with numeric values
                        values[j] = entity.getDouble(key);
                    }
                    j++;
                }
            }
            values[values.length - 1] = entity.getBoolean("label") ? 1.0 : 0.0;
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
        return filename.endsWith(".json");
    }

}
