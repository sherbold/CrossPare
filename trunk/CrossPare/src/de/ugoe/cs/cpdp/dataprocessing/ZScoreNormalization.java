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

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * N4 after "Transfer Defect Learning" by Jaechang Nam, Sinno Jialin Pan, and Sunghun Kim.
 * 
 * @author Steffen Herbold
 */
public class ZScoreNormalization implements ISetWiseProcessingStrategy, IProcessesingStrategy {

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

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        normalize(testdata);
        for (Instances instances : traindataSet) {
            normalize(instances);
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        normalize(testdata);
        normalize(traindata);
    }

    private void normalize(Instances instances) {
        instances.toString();
        final Attribute classAttribute = instances.classAttribute();

        final double[] means = new double[instances.numAttributes()];
        final double[] stddevs = new double[instances.numAttributes()];

        // get means and stddevs of data
        for (int j = 0; j < instances.numAttributes(); j++) {
            if (instances.attribute(j) != classAttribute) {
                means[j] = instances.meanOrMode(j);
                stddevs[j] = Math.sqrt(instances.variance(j));
            }
        }
        for (int i = 0; i < instances.numAttributes(); i++) {
            if (!instances.attribute(i).equals(classAttribute)) {
                for (int j = 0; j < instances.numInstances(); j++) {
                    Instance inst = instances.get(i);
                    double newValue = (inst.value(i) - means[i]) / stddevs[i];
                    if (newValue == Double.NaN) {
                        System.out.println("foooooo");
                    }
                    inst.setValue(i, newValue);
                }
            }
        }
    }

}
