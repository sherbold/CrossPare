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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

/**
 * Loads ARFF files and chooses the last attribute as class attribute.
 * 
 * @author Steffen Herbold
 */
public class ARFFLoader implements SingleVersionLoader {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#load(java.io.File)
     */
    @Override
    public Instances load(File file) {
        Instances data;
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            data = new Instances(reader);
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException("error reading file: " + file.getName(), e);
        }

        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.SingleVersionLoader#filenameFilter(java.lang.String )
     */
    @Override
    public boolean filenameFilter(String filename) {
        return filename.endsWith(".arff");
    }

}
