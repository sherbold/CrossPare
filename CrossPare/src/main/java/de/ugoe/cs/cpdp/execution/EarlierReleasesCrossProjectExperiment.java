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

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.IParameterizable;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * <p>
 * Implements a cross project experiment where all versions not from the same project
 * that were released at least a certain time span earlier are used.
 * </p>
 * 
 * @author Steffen Tunkel
 */
public class EarlierReleasesCrossProjectExperiment extends AbstractCrossProjectExperiment implements IParameterizable {

    /**
     * minimal time span in months between the releases of training version and test version
     */
    private int minTimeSpan = 0;

    /**
     * Sets the minimal time span in months between the versions
     *
     * @param parameters
     *            minimal time span in months
     */
    @Override
    public void setParameter(String parameters) {
        this.minTimeSpan = Integer.parseInt(parameters);
    }

    /**
     * Constructor. Creates a new experiment based on a configuration.
     *
     * @param config
     *            configuration of the experiment
     */
    @SuppressWarnings("hiding")
    public EarlierReleasesCrossProjectExperiment(ExperimentConfiguration config) {
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
        LocalDate trainingVersionDate =  trainingVersion.getReleaseDate().toLocalDate();
        LocalDate testVersionDate =  testVersion.getReleaseDate().toLocalDate();
        Period timeSpan = Period.between(trainingVersionDate, testVersionDate);
        boolean sufficientTimeSpan = (timeSpan.getYears()*12 + timeSpan.getMonths()) > minTimeSpan;
        boolean differentProject = !trainingVersion.getProject().equals(testVersion.getProject());
        return differentProject && sufficientTimeSpan;
    }
}
