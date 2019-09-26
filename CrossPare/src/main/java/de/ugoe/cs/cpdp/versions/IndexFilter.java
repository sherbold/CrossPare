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

/**
 * Selects a single version based on the index in the list of available test versions
 * 
 * @author Steffen Herbold
 */
public class IndexFilter implements IVersionFilter {

    /**
     * index of the version that is kept
     */
    private int index = 0;

    /*
     * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public boolean apply(SoftwareVersion version, List<SoftwareVersion> allVersions) {
    	return allVersions.indexOf(version)!=index;
    }

    /**
     * Sets the index that shall be kept
     * 
     * @param parameters
     *            number of instances
     */
    @Override
    public void setParameter(String parameters) {
        this.index = Integer.parseInt(parameters);
    }

}
