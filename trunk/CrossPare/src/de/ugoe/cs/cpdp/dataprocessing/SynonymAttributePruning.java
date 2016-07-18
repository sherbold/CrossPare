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

import weka.core.Instances;

/**
 * <p>
 * Synonym pruning after Amasaki et al. (2015). The selection of the attributes for pruning happens
 * only on the training data. The attributes are deleted from both the training and test data.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SynonymAttributePruning implements IProcessesingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {

    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        applySynonymPruning(testdata, traindata);
    }

    /**
     * <p>
     * Applies the synonym pruning based on the training data.
     * </p>
     *
     * @param testdata
     *            the test data
     * @param traindata
     *            the training data
     */
    private void applySynonymPruning(Instances testdata, Instances traindata) {
        double distance;
        for (int j = traindata.numAttributes() - 1; j >= 0; j--) {
            if (j != traindata.classIndex()) {
                boolean hasClosest = false;
                for (int i1 = 0; !hasClosest && i1 < traindata.size(); i1++) {
                    for (int i2 = 0; !hasClosest && i2 < traindata.size(); i2++) {
                        if (i1 != i2) {
                            double minVal = Double.MAX_VALUE;
                            double distanceJ = Double.MAX_VALUE;
                            for (int k = 0; k < traindata.numAttributes(); k++) {
                                distance = Math
                                    .abs(traindata.get(i1).value(k) - traindata.get(i2).value(k));
                                if (distance < minVal) {
                                    minVal = distance;
                                }
                                if (k == j) {
                                    distanceJ = distance;
                                }
                            }
                            hasClosest = distanceJ <= minVal;
                        }
                    }
                }
                if (!hasClosest) {
                    testdata.deleteAttributeAt(j);
                    traindata.deleteAttributeAt(j);
                }
            }
        }
    }
}
