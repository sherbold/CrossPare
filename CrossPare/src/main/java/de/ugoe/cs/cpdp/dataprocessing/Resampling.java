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

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * Resamples the data with WEKA {@link Resample} to have a uniform distribution among all classes.
 * 
 * @author Steffen Herbold
 */
public class Resampling implements IProcessesingStrategy, ISetWiseProcessingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // dummy
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for (Instances traindata : traindataSet) {
            apply(testdata, traindata);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        Resample resample = new Resample();
        resample.setSampleSizePercent(100);
        resample.setBiasToUniformClass(1.0);

        Instances traindataSample;
        try {
            resample.setInputFormat(traindata);
            traindataSample = Filter.useFilter(traindata, resample);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        traindata.clear();
        for (int i = 0; i < traindataSample.size(); i++) {
            traindata.add(traindataSample.get(i));
        }
    }

}
