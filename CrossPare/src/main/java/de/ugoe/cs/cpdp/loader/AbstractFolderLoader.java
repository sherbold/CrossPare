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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import de.ugoe.cs.cpdp.IParameterizable;
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
     * Default for class type is binary, not numeric. This ensure downwards compability.
     */
    private boolean isBinaryClass = true;
    
    /**
     * parameters that may be used by single version loaders
     */
    private String parameters = null;
    
    /**
     * parameters are passed on to SingleVersionLoader, if the loader supports parameters
     * @see IParameterizable#setParameter(String)
     */
    @Override
    public void setParameter(String parameters) {
    	this.parameters = parameters;
    }

    /**
     * @see de.ugoe.cs.cpdp.loader.IVersionLoader#setLocation(java.lang.String)
     */
    @Override
    public void setLocation(String location) {
        this.path = location;
    }

    @Override
    public void setClassType(String classType) {
        if ("binary".equals(classType)) {
            isBinaryClass = true;
        }
        else if ("numeric".equals(classType)) {
            isBinaryClass = false;
        }
        else {
            throw new RuntimeException("Unsupported type for class attribute (allowed: binary, numeric): " +
                classType);
        }
    }

    /*
     * @see de.ugoe.cs.cpdp.loader.IVersionLoader#load()
     */
    @Override
    public List<SoftwareVersion> load() {
        final List<SoftwareVersion> versions = new LinkedList<>();

        final File dataDir = new File(this.path);
        if (dataDir.listFiles() == null) {
            return versions;
        }
        String datasetName = dataDir.getName();
        
        final SingleVersionLoader instancesLoader = getSingleLoader();
        if( parameters!=null && parameters.length()>0) {
        	if( instancesLoader instanceof IParameterizable ) {
        		((IParameterizable) instancesLoader).setParameter(parameters);
        	} else {
        		throw new RuntimeException("there are parameters specified for a data loader that does not support parameters");
        	}
        }

        for (File projectDir : dataDir.listFiles()) {
            if (projectDir.isDirectory()) {
                String projectName = projectDir.getName();
                if (projectDir.listFiles() != null) {
                    for (File versionFile : projectDir.listFiles()) {
                        if (versionFile.isFile() &&
                            instancesLoader.filenameFilter(versionFile.getName()))
                        {
                            // currently only supports binary classification
                            // TODO allow regression loading
                            Instances data = instancesLoader.load(versionFile, isBinaryClass);
                            Instances bugMatrix = null;
                            if(instancesLoader instanceof IBugMatrixLoader) {
                            	bugMatrix = ((IBugMatrixLoader) instancesLoader).getBugMatrix();
                            }
                            String versionName = data.relationName();
                            List<Double> efforts = getEfforts(data);
                            List<Double> numBugs = getNumBugs(data);
                            LocalDateTime releaseDate = null;
                            if (instancesLoader instanceof MynbouDataLoader) {
                            	releaseDate = ((MynbouDataLoader) instancesLoader).getReleaseDate();
                            }
                            versions.add(new SoftwareVersion(datasetName, projectName, versionName,
                                                             data, bugMatrix, efforts, numBugs, releaseDate));
                        }
                    }
                }
            }
        }
        return versions;
    }

    /**
     * <p>
     * Sets the efforts for the instances
     * </p>
     *
     * @param data
     *            the data
     * @return
     */
    @SuppressWarnings("boxing")
    public static List<Double> getEfforts(Instances data) {
        // attribute in the JURECZKO data and default
        Attribute effortAtt = data.attribute("loc");
        if (effortAtt == null) {
            // attribute in the NASA/SOFTMINE/MDP data
            effortAtt = data.attribute("LOC_EXECUTABLE");
        }
        if (effortAtt == null) {
            // attribute in the AEEEM data
            effortAtt = data.attribute("numberOfLinesOfCode");
        }
        if (effortAtt == null) {
            // attribute in the RELINK data
            effortAtt = data.attribute("CountLineCodeExe");
        }
        if (effortAtt == null) {
            // attribute in the SMARTSHARK data
            effortAtt = data.attribute("LOC");
        }
        if (effortAtt == null) {
        	effortAtt = data.attribute("SM_file_lloc");
        }
        List<Double> efforts = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            if(effortAtt!=null) {
                efforts.add(data.get(i).value(effortAtt));
            } else {
                // add constant effort per instance (default)
                efforts.add(1.0);
            }
        }
        return efforts;
    }

    /**
     * <p>
     * Retrieves the number of bugs from the class attribute of the data and stores it separately in
     * a list.
     * </p>
     *
     * @param data
     *            the data
     * @return list with bug counts
     */
    private static List<Double> getNumBugs(Instances data) {
        List<Double> numBugs = new ArrayList<>(data.size());
        for (Instance instance : data) {
            numBugs.add(instance.classValue());
        }
        return numBugs;
    }

    /**
     * Returns the concrete {@link SingleVersionLoader} to be used with this folder loader.
     * 
     * @return the version loader
     */
    abstract protected SingleVersionLoader getSingleLoader();
}
