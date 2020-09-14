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

package de.ugoe.cs.cpdp.dataprocessing;

import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Normalizes all attributes by the effort, i.e., divides all attributes by the effort
 * 
 * @author Steffen Herbold
 */
public class NormalizeByEffort implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        normalizeByEffort(testversion);
        for (SoftwareVersion trainversion : trainversionSet) {
            normalizeByEffort(trainversion);
        }
    }

    /**
     * @see IProcessesingStrategy#apply(weka.core.de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        normalizeByEffort(testversion);
        normalizeByEffort(trainversion);
    }

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // no parameters
    }

    /**
     * Implements the effort normalization
     * 
     * @param version
     *            version of the data that is normalized
     */
    private void normalizeByEffort(SoftwareVersion version) {
        Instances data = version.getInstances();
        List<Double> efforts = version.getEfforts();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.numAttributes(); j++) {
                if (j != data.classIndex()) {
                    data.get(i).setValue(j, data.get(i).value(j) / (efforts.get(i) + 1));
                }
            }
        }
    }
}
