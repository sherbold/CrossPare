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
import de.ugoe.cs.cpdp.versions.SoftwareVersion;

/**
 * <p>
 * Implements a cross project experiment where all versions not from the same project are used.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class CrossProjectExperiment extends AbstractCrossProjectExperiment {

    /**
     * Constructor. Creates a new experiment based on a configuration.
     * 
     * @param config
     *            configuration of the experiment
     */
    @SuppressWarnings("hiding")
    public CrossProjectExperiment(ExperimentConfiguration config) {
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
        return !trainingVersion.getProject().equals(testVersion.getProject());
    }
}
