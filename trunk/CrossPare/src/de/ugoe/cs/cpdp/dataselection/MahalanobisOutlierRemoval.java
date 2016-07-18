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

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.stat.correlation.Covariance;

import de.lmu.ifi.dbs.elki.logging.Logging.Level;
import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.util.console.Console;
import weka.core.Instances;

/**
 * <p>
 * Uses the Mahalanobis distance for outlier removal. All instances that are epsilon times the
 * distance are removed. The default for epsilon is 3.0.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class MahalanobisOutlierRemoval
    implements IPointWiseDataselectionStrategy, ISetWiseDataselectionStrategy
{

    /**
     * Distance outside which entities are removed as outliers.
     */
    private double epsilon = 3.0d;

    /**
     * Sets epsilon. Default is 3.0.
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            epsilon = Double.parseDouble(parameters);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for (Instances traindata : traindataSet) {
            applyMahalanobisDistancesRemoval(traindata);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public Instances apply(Instances testdata, Instances traindata) {
        applyMahalanobisDistancesRemoval(traindata);
        return traindata;
    }

    /**
     * <p>
     * removes all instances, whose Mahalanobi distance to the mean of the data is greater than
     * epsilon.
     * </p>
     *
     * @param data
     *            data where the outliers are removed
     */
    private void applyMahalanobisDistancesRemoval(Instances data) {
        RealMatrix values = new BlockRealMatrix(data.size(), data.numAttributes() - 1);
        for (int i = 0; i < data.size(); i++) {
            values.setRow(i, WekaUtils.instanceValues(data.get(i)));
        }
        RealMatrix inverseCovariance;
        try {
            inverseCovariance = new LUDecomposition(new Covariance(values).getCovarianceMatrix())
                .getSolver().getInverse();
        }
        catch (SingularMatrixException e) {
            Console
                .traceln(Level.WARNING,
                         "could not perform Mahalanobis outlier removal due to singular covariance matrix");
            return;
        }
        // create mean vector
        double[] meanValues = new double[data.numAttributes() - 1];
        int k = 0;
        for (int j = 0; j < data.numAttributes(); j++) {
            if (j != data.classIndex()) {
                meanValues[k] = data.attributeStats(j).numericStats.mean;
                k++;
            }
        }

        for (int i = data.size() - 1; i >= 0; i--) {
            double distance =
                mahalanobisDistance(inverseCovariance, WekaUtils.instanceValues(data.get(i)),
                                    meanValues);
            if (distance > epsilon) {
                data.remove(i);
            }
        }
    }

    /**
     * <p>
     * Calculates the Mahalanobis distance between two vectors for a given inverse covariance
     * matric.
     * </p>
     *
     * @param inverseCovariance
     * @param vector1
     * @param vector2
     * @return
     */
    private double mahalanobisDistance(RealMatrix inverseCovariance,
                                       double[] vector1,
                                       double[] vector2)
    {
        RealMatrix x = new BlockRealMatrix(1, vector1.length);
        x.setRow(0, vector1);
        RealMatrix y = new BlockRealMatrix(1, vector2.length);
        y.setRow(0, vector2);

        RealMatrix deltaxy = x.subtract(y);

        return Math
            .sqrt(deltaxy.multiply(inverseCovariance).multiply(deltaxy.transpose()).getEntry(0, 0));
    }
}
