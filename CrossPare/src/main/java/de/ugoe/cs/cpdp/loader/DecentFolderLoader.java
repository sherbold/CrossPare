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
import java.util.LinkedList;
import java.util.List;

import weka.core.Instances;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * Implements the {@link AbstractFolderLoader}
 * 
 * @author Fabian Trautsch
 */
public class DecentFolderLoader extends AbstractFolderLoader {
    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.loader.AbstractFolderLoader#getSingleLoader()
     */
    @Override
    protected SingleVersionLoader getSingleLoader() {
        return new DecentDataLoader();
    }

    /**
     * @see de.ugoe.cs.cpdp.loader.IVersionLoader#load()
     */
    @Override
    public List<SoftwareVersion> load() {
        final List<SoftwareVersion> versions = new LinkedList<>();

        final File dataDir = new File(this.path);
        final SingleVersionLoader instancesLoader = getSingleLoader();

        String projectName = dataDir.getName();

        /*
         * The following lines make it possible, that we can have two different possibilities to
         * load data: 1) From one project (e.g. /decent/input/project1) 2) From more than one
         * project (e.g. /decent/input/)
         * 
         * Requirement is, that we have a folder structure like this:
         * "/decent/input/project1/model.decent, /decent/input/project2/model.decent, ..."
         * 
         * In the first one the "else" is executed, therefore it will just search the folder
         * "project1" for a "model.decent" file. In the second one, it will look into each folder
         * and searches for "model.decent" files.
         */
        if (dataDir.listFiles() == null) {
            return versions;
        }
        for (File projectDir : dataDir.listFiles()) {
            if (projectDir.isDirectory()) {
                projectName = projectDir.getName();
                if (projectDir.listFiles() != null) {
                    for (File versionFile : projectDir.listFiles()) {
                        loadDataFromFile(versionFile, instancesLoader, projectName, versions);
                    }
                }
            }
            else {
                loadDataFromFile(projectDir, instancesLoader, projectName, versions);
            }
        }
        return versions;
    }

    /**
     * Loads data from a file and adds the instances from the load method to the versions List.
     * 
     * @param versionFile
     *            file to load from
     * @param instancesLoader
     *            loader that should be used
     * @param projectName
     *            name of the project which was loaded
     * @param versions
     *            list, where the weka instances are added to
     */
    private static void loadDataFromFile(File versionFile,
                                         SingleVersionLoader instancesLoader,
                                         String projectName,
                                         List<SoftwareVersion> versions)
    {
        if (versionFile.isFile() && instancesLoader.filenameFilter(versionFile.getName())) {
            String versionName = versionFile.getName();
            // TODO currently only supports binary class
            Instances data = instancesLoader.load(versionFile, true);
            versions.add(new SoftwareVersion("decent", projectName, versionName, data, null, null, null));
        }
    }

}
