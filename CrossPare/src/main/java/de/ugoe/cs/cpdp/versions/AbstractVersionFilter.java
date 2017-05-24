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

import java.util.Iterator;
import java.util.List;

/**
 * Implements a skeletal {@link IVersionFilter}.
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractVersionFilter implements IVersionFilter {

    /*
     * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(java.util.List)
     */
    @Override
    public int apply(List<SoftwareVersion> versions) {
        int removed = 0;
        for (final Iterator<SoftwareVersion> iter = versions.iterator(); iter.hasNext();) {
            SoftwareVersion version = iter.next();

            if (apply(version)) {
                iter.remove();
                removed++;
            }
        }
        return removed;
    }
}
