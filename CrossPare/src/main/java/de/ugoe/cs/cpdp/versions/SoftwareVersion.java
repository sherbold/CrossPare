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

import java.time.LocalDateTime;
import java.util.List;

import weka.core.Instances;

/**
 * Data class for software versions.
 * 
 * @author Steffen Herbold
 */
public class SoftwareVersion implements Comparable<SoftwareVersion> {

    private final String dataset;

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
     * bug matrix of the version
     */
    private final Instances bugMatrix;

    /**
     * Review effort per instance.
     */
    private final List<Double> efforts;

    /**
     * Number of bugs per instance. Binary 0/1 in case of nominal loading.
     */
    private final List<Double> numBugs;
    
    /**
     * Release date
     */
    private final LocalDateTime releaseDate;

    /**
     * Constructor. Creates a new version.
     * 
     * @param dataset
     *            name of the dataset
     * 
     * @param project
     *            name of the project
     * @param version
     *            name of the version
     * @param instances
     *            data of the version
     * @param bugMatrix
     *            bug matrix of the version
     * @param efforts
     *            review efforts for the version
     * @param numBugs
     *            number of bugs for the instances
     * @param releaseDate
     *            date of the release (null if not available)
     */
    @SuppressWarnings("hiding")
    public SoftwareVersion(String dataset,
                           String project,
                           String version,
                           Instances instances,
                           Instances bugMatrix,
                           List<Double> efforts,
                           List<Double> numBugs,
                           LocalDateTime releaseDate)
    {
        this.dataset = dataset;
        this.project = project;
        this.version = version;
        this.instances = instances;
        this.bugMatrix = bugMatrix;
        this.efforts = efforts;
        this.numBugs = numBugs;
        this.releaseDate = releaseDate;
    }

    /**
     * <p>
     * returns the name of the data set
     * </p>
     *
     * @return data set name
     */
    public String getDataset() {
        return this.dataset;
    }

    /**
     * returns the project name
     * 
     * @return project name
     */
    public String getProject() {
        return this.project;
    }

    /**
     * returns the name of the version
     * 
     * @return name of the version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * returns the data of the version
     * 
     * @return data
     */
    public Instances getInstances() {
        return new Instances(this.instances);
    }
    
    /**
     * returns the bug matrix of this version
     * 
     * @return bug matrix
     */
    public Instances getBugMatrix() {
    	if( this.bugMatrix==null ) {
    		return null;
    	}
    	return new Instances(this.bugMatrix);
    }

    /**
     * <p>
     * returns the review effort of the version
     * </p>
     *
     * @return the review efforts
     */
    public List<Double> getEfforts() {
        return this.efforts;
    }

    /**
     * <p>
     * returns the number of bugs for the instances
     * </p>
     *
     * @return the bug counts
     */
    public List<Double> getNumBugs() {
        return this.numBugs;
    }
    
    public LocalDateTime getReleaseDate() {
    	return releaseDate;
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
        if (this.project != null) {
            projectStrCmp = this.project.compareTo(o.project);
        }
        if (projectStrCmp == 0 && this.version != null) {
            return this.version.compareTo(o.version);
        }
        return projectStrCmp;
    }
}
