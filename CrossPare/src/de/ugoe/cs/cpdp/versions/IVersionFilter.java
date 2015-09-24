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

import de.ugoe.cs.cpdp.IParameterizable;

/**
 * Implements the interface for a {@link SoftwareVersion} filter.
 * 
 * @author Steffen Herbold
 */
public interface IVersionFilter extends IParameterizable {

    /**
     * Applies the filter to a single version.
     * 
     * @param version
     *            the version
     * @return true if filter applies to version, false otherwise
     */
    boolean apply(SoftwareVersion version);

    /**
     * Applies the filter a a list of versions. Versions were the filter applies are automatically
     * removed from the list.
     * 
     * @param versions
     *            list of versions
     * @return number of removed versions
     */
    int apply(List<SoftwareVersion> versions);
}
