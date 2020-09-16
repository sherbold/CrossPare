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

import java.util.Arrays;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * <p>
 * Remove instances from training and testdata by attributes and their values.
 * 
 * Examle: <setwisepreprocessor name="InstanceRemoval" param="test1:0,test2:1" />
 * 
 * Removes every instance where attribute test1 is 0 and test2 is 1 (value are parsed to Double).
 * </p>
 * 
 * @author Alexander Trautsch
 */
public class InstanceRemoval implements ISetWiseProcessingStrategy, IProcessesingStrategy {
    private String[] condList; // holds an array of conditions, every condition needs to match to
                               // remove an instance.

    @Override
    public void setParameter(String parameters) {
        this.condList = parameters.split(",");
    }

    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        removeInstances(testversion);
        removeInstances(trainversion);
    }

    @SuppressWarnings("boxing")
    private void removeInstances(SoftwareVersion version) {
        Instances ilist = version.getInstances();
        for (int i = ilist.size() - 1; i >= 0; i--) {
            Boolean matchFound[] = new Boolean[this.condList.length];
            int j = 0;
            System.out.println("");
            for (String cond : this.condList) {
                String[] match = cond.split(":");
                matchFound[j] =
                    ilist.get(i).value(ilist.attribute(match[0])) == Double.parseDouble(match[1]);
            }
            // remove Instance only if every condition is true (the Boolean array does not contain
            // any false)
            if (!Arrays.asList(matchFound).contains(false)) {
                ilist.delete(i);
                if (version.getBugMatrix() != null) {
                    version.getBugMatrix().delete(i);
                }
                if (version.getEfforts() != null) {
                    version.getEfforts().remove(i);
                }
                if (version.getNumBugs() != null) {
                    version.getNumBugs().remove(i);
                }
            }
        }
    }

    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        removeInstances(testversion);
        for (SoftwareVersion trainversion : trainversionSet) {
            removeInstances(trainversion);
        }
    }
}
