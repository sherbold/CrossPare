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

import weka.core.Instances;

/**
 * Interface for version loaders, i.e., loading of a set of instances from a file
 * 
 * @author Steffen Herbold
 */
public interface SingleVersionLoader {

    /**
     * Loads the instances.
     * 
     * @param file
     *            handle to the file of the instances
     * @return the instances
     */
    Instances load(File file);

    /**
     * Defines a filter for the files to be loaded; only strings that end with the filter are
     * considered.
     * 
     * @param endsWith
     *            string defining the filename filter
     * @return true if a filename shall be considered
     */
    boolean filenameFilter(String endsWith);
}
