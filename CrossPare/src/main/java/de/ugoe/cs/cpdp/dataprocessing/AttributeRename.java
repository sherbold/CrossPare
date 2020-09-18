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

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * Renames an attribute
 * 
 * @author Steffen Herbold
 */
public class AttributeRename implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * old name of the attribute
     */
    private String oldName = "";
    
    /**
     * new name of the attribute
     */
    private String newName = "";

    /**
     * Attribute that will be renamed. Format: oldname,newname <br>
     * <br>
     * Note, that renaming of attributes with commas is currently not supported!
     * 
     * @param parameters
     *            string with the old and the new name separated by a comma
     */
    @Override
    public void setParameter(String parameters) {
    	if (parameters == null || parameters.split(",").length!=2) {
    		throw new RuntimeException("AttributeRename requires a parameter of the form oldname,newname");
    	}
        this.oldName = parameters.split(",")[0];
        this.newName = parameters.split(",")[1];
    }

    /**
     * @see ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances testdata = testversion.getInstances();
    	testdata.renameAttribute(testdata.attribute(oldName), newName);
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
            traindata.renameAttribute(traindata.attribute(oldName), newName);
        }
    }

    /**
     * @see IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     *      de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances testdata = trainversion.getInstances();
        Instances traindata = trainversion.getInstances();
    	testdata.renameAttribute(testdata.attribute(oldName), newName);
        traindata.renameAttribute(traindata.attribute(oldName), newName);
    }
}
