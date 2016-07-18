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

package de.ugoe.cs.cpdp.versions;

import java.util.List;

import weka.core.Instances;

/**
 * Data class for software versions.
 * 
 * @author Steffen Herbold
 */
public class SoftwareVersion implements Comparable<SoftwareVersion> {

    /**
     * name of the project
     */
    private final String project;

    /**
     * version of the project
     */
    private final String version;

    /**
     * data of the version
     */
    private final Instances instances;

    /**
     * Review effort per instance.
     */
    private final List<Double> efforts;

    /**
     * Constructor. Creates a new version.
     * 
     * @param project
     *            name of the project
     * @param version
     *            name of the version
     * @param instances
     *            data of the version
     */
    public SoftwareVersion(String project,
                           String version,
                           Instances instances,
                           List<Double> efforts)
    {
        this.project = project;
        this.version = version;
        this.instances = instances;
        this.efforts = efforts;
    }

    /**
     * returns the project name
     * 
     * @return project name
     */
    public String getProject() {
        return project;
    }

    /**
     * returns the name of the version
     * 
     * @return name of the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * returns the data of the version
     * 
     * @return data
     */
    public Instances getInstances() {
        return new Instances(instances);
    }

    /**
     * <p>
     * returns the review effort of the version
     * </p>
     *
     * @return
     */
    public List<Double> getEfforts() {
        return efforts;
    }

    /**
     * Compares first based on project name and then based on version. Only string comparisons are
     * performed.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(SoftwareVersion o) {
        int projectStrCmp = 0;
        if (project != null) {
            projectStrCmp = project.compareTo(o.project);
        }
        if (projectStrCmp == 0 && version != null) {
            return version.compareTo(o.version);
        }
        else {
            return projectStrCmp;
        }
    }
}
