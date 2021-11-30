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

package de.ugoe.cs.cpdp.execution;

import java.util.List;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.IParameterizable;
import de.ugoe.cs.cpdp.dataprocessing.IVersionProcessingStrategy;
import de.ugoe.cs.cpdp.util.CrosspareUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * Experiment workflow where the n previous valid versions of a project are used for training.
 * 
 * @author Steffen Herbold
 */
public class WithinProjectPreviousReleasesExperiment extends AbstractCrossProjectExperiment implements IParameterizable {

    /**
     * number of previous releases that are considered
     */
    private int numPreviousReleases = 1;
    
    /**
     * Constructor. Creates a new experiment based on a configuration.
     * 
     * @param config
     *            configuration of the experiment
     */
    @SuppressWarnings("hiding")
    public WithinProjectPreviousReleasesExperiment(ExperimentConfiguration config) {
        super(config);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ugoe.cs.cpdp.execution.AbstractCrossProjectExperiment#isTrainingVersion(de.ugoe.cs.cpdp.
     * versions.SoftwareVersion, de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    protected boolean isTrainingVersion(SoftwareVersion trainingVersion,
                                        SoftwareVersion testVersion,
                                        List<SoftwareVersion> versions)
    {
        // find all previous versions of test version
        int trainIndex = versions.indexOf(trainingVersion);
        int testIndex = versions.indexOf(testVersion);
        
        boolean isSameProject = trainingVersion.getProject().equals(testVersion.getProject());
        boolean isWithinPreviousReleases = testIndex>trainIndex && (testIndex-trainIndex)<=numPreviousReleases;

        if( !config.getTrainingVersionFilters().isEmpty() && testIndex>trainIndex && (testIndex-trainIndex)>numPreviousReleases ) {
            int validPreviousReleasesCount = 0;
            for ( int i=trainIndex; i<testIndex; i++ ) {
                SoftwareVersion otherTrainingVersion = new SoftwareVersion(versions.get(i));
                for(IVersionProcessingStrategy processor : this.config.getTrainingVersionProcessors()) {
                    processor.apply(testVersion, otherTrainingVersion);
                }
                if ( CrosspareUtils.isVersion(otherTrainingVersion, versions, this.config.getTrainingVersionFilters()) ) {
                    validPreviousReleasesCount += 1;
                }
            }
            if( validPreviousReleasesCount <= numPreviousReleases ){
                isWithinPreviousReleases = true;
            }
        }
        return isSameProject && isWithinPreviousReleases;
    }

    @Override
    public void setParameter(String parameters) {
        if( parameters.length()>0 ) {
            numPreviousReleases =  Integer.parseInt(parameters);
        }
    }
}
