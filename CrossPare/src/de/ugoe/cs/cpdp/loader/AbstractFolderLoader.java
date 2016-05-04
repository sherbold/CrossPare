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
 * Abstract class for loading data from a folder. The subfolders of a defined folder define the
 * projects, the file contained in the subfolder are the versions of a project.
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractFolderLoader implements IVersionLoader {

    /**
     * Path of the data.
     */
    protected String path = "";

    /**
     * @see de.ugoe.cs.cpdp.loader.IVersionLoader#setLocation(java.lang.String)
     */
    @Override
    public void setLocation(String location) {
        path = location;
    }

    /**
     * @see de.ugoe.cs.cpdp.loader.IVersionLoader#load()
     */
    @Override
    public List<SoftwareVersion> load() {
        final List<SoftwareVersion> versions = new LinkedList<SoftwareVersion>();

        final File dataDir = new File(path);
        final SingleVersionLoader instancesLoader = getSingleLoader();
        if (dataDir.listFiles() == null) {
            return versions;
        }
        for (File projectDir : dataDir.listFiles()) {
            if (projectDir.isDirectory()) {
                String projectName = projectDir.getName();
                if (projectDir.listFiles() != null) {
                    for (File versionFile : projectDir.listFiles()) {
                        if (versionFile.isFile() &&
                            instancesLoader.filenameFilter(versionFile.getName()))
                        {
                            String versionName = versionFile.getName();
                            Instances data = instancesLoader.load(versionFile);
                            versions.add(new SoftwareVersion(projectName, versionName, data));
                        }
                    }
                }
            }
        }
        return versions;
    }

    /**
     * Returns the concrete {@link SingleVersionLoader} to be used with this folder loader.
     * 
     * @return
     */
    abstract protected SingleVersionLoader getSingleLoader();
}
