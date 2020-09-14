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

package de.ugoe.cs.cpdp.dataselection;

import java.util.ArrayList;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.experiment.Stats;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 * Abstract class that implements the foundation of setwise data selection strategies using
 * distributional characteristics. This class provides the means to transform the data sets into
 * their characteristic vectors.
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractCharacteristicSelection implements ISetWiseDataselectionStrategy {

    /**
     * vector with the distributional characteristics
     */
    private String[] characteristics = new String[]
        { "mean", "stddev" };

    /**
     * Sets the distributional characteristics. The names of the characteristics are separated by
     * blanks.
     */
    @Override
    public void setParameter(String parameters) {
        if (!"".equals(parameters)) {
            this.characteristics = parameters.split(" ");
        }
    }

    /**
     * Transforms the data into the distributional characteristics. The first instance is the test
     * data, followed by the training data.
     * 
     * @param testversion
     *            version of the test data
     * @param trainversionSet
     *            versions of the training data sets
     * @return distributional characteristics of the data
     */
    protected Instances characteristicInstances(SoftwareVersion testversion,
                                                SetUniqueList<SoftwareVersion> trainversionSet)
    {
        // setup weka Instances for clustering
        final ArrayList<Attribute> atts = new ArrayList<>();
        Instances testdata = testversion.getInstances();

        final Attribute classAtt = testdata.classAttribute();
        for (int i = 0; i < testdata.numAttributes(); i++) {
            Attribute dataAtt = testdata.attribute(i);
            if (!dataAtt.equals(classAtt)) {
                for (String characteristic : this.characteristics) {
                    atts.add(new Attribute(dataAtt.name() + "_" + characteristic));
                }
            }
        }
        final Instances data = new Instances("distributional_characteristics", atts, 0);

        // setup data for clustering
        double[] instanceValues = new double[atts.size()];
        for (int i = 0; i < testdata.numAttributes(); i++) {
            Attribute dataAtt = testdata.attribute(i);
            if (!dataAtt.equals(classAtt)) {
                Stats stats = testdata.attributeStats(i).numericStats;
                for (int j = 0; j < this.characteristics.length; j++) {
                    if ("mean".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] = stats.mean;
                    }
                    else if ("stddev".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] = stats.stdDev;
                    }
                    else if ("var".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] = testdata.variance(j);
                    }
                    else if ("max".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] = stats.max;
                    }
                    else if ("min".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] = stats.min;
                    }
                    else if ("median".equals(this.characteristics[j])) {
                        instanceValues[i * this.characteristics.length + j] =
                            Utils.kthSmallestValue(testdata.attributeToDoubleArray(i),
                                                   testdata.size() / 2);
                    }
                    else {
                        throw new RuntimeException("Unkown distributional characteristic: " +
                            this.characteristics[j]);
                    }
                }
            }
        }
        data.add(new DenseInstance(1.0, instanceValues));

        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
            instanceValues = new double[atts.size()];
            for (int i = 0; i < traindata.numAttributes(); i++) {
                Attribute dataAtt = traindata.attribute(i);
                if (!dataAtt.equals(classAtt)) {
                    Stats stats = traindata.attributeStats(i).numericStats;
                    for (int j = 0; j < this.characteristics.length; j++) {
                        if ("mean".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] = stats.mean;
                        }
                        else if ("stddev".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] = stats.stdDev;
                        }
                        else if ("var".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] = traindata.variance(j);
                        }
                        else if ("max".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] = stats.max;
                        }
                        else if ("min".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] = stats.min;
                        }
                        else if ("median".equals(this.characteristics[j])) {
                            instanceValues[i * this.characteristics.length + j] =
                                Utils.kthSmallestValue(traindata.attributeToDoubleArray(i),
                                                       traindata.size() / 2);
                        }
                        else {
                            throw new RuntimeException("Unkown distributional characteristic: " +
                                this.characteristics[j]);
                        }
                    }
                }
            }
            Instance instance = new DenseInstance(1.0, instanceValues);

            data.add(instance);
        }
        return data;
    }

    /**
     * Returns the normalized distributional characteristics of the training data.
     * 
     * @param testversion
     *            version of the test data
     * @param traindataSet
     *            versions of the training data sets
     * @return normalized distributional characteristics of the data
     */
    protected Instances normalizedCharacteristicInstances(SoftwareVersion testversion,
                                                          SetUniqueList<SoftwareVersion> trainversionSet)
    {
        Instances data = characteristicInstances(testversion, trainversionSet);
        try {
            final Normalize normalizer = new Normalize();
            normalizer.setInputFormat(data);
            data = Filter.useFilter(data, normalizer);
        }
        catch (Exception e) {
            throw new RuntimeException("Unexpected exception during normalization of distributional characteristics.",
                                       e);
        }
        return data;
    }
}
