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
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements an approach for data weighting suggested after Y. Ma, G. Luo, X. Zeng, and A. Chen:
 * Transfer learning for cross-company software defect prediction. The instances are weighted
 * higher, the more attributes are within the range they are in the training data.
 * 
 * @author Steffen Herbold
 */
public class DataGravitation implements IProcessesingStrategy, ISetWiseProcessingStrategy {

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
     * @see de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(SoftwareVersion testversion, SetUniqueList<SoftwareVersion> trainversionSet) {
        for (SoftwareVersion trainversion : trainversionSet) {
            apply(testversion, trainversion);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion,
     * de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion testversion, SoftwareVersion trainversion) {
        Instances testdata = testversion.getInstances();
        Instances traindata = trainversion.getInstances();
        Attribute classAtt = testdata.classAttribute();

        double[] minAttValues = new double[testdata.numAttributes()];
        double[] maxAttValues = new double[testdata.numAttributes()];
        double[] weights = new double[traindata.numInstances()];
        double weightsum = 0.0;

        for (int j = 0; j < testdata.numAttributes(); j++) {
            if (testdata.attribute(j) != classAtt) {
                minAttValues[j] = testdata.attributeStats(j).numericStats.min;
                maxAttValues[j] = testdata.attributeStats(j).numericStats.max;
            }
        }

        for (int i = 0; i < traindata.numInstances(); i++) {
            Instance inst = traindata.instance(i);
            int similar = 0;
            for (int j = 0; j < testdata.numAttributes(); j++) {
                if (testdata.attribute(j) != classAtt) {
                    if (inst.value(j) >= minAttValues[j] && inst.value(j) <= maxAttValues[j]) {
                        similar++;
                    }
                }
            }
            weights[i] = similar / Math.sqrt(testdata.numAttributes() - similar);
            weightsum += weights[i];
        }
        for (int i = 0; i < traindata.numInstances(); i++) {
            traindata.instance(i).setWeight(weights[i] * traindata.numInstances() / weightsum);
        }
    }

}
