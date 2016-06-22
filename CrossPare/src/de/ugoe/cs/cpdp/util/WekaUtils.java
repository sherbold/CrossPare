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

package de.ugoe.cs.cpdp.util;

// TODO comment
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import weka.core.Instance;
import weka.core.Instances;

public class WekaUtils {

    public static class DistChar {
        public final double mean;
        public final double std;
        public final double min;
        public final double max;
        public final int num;
        private DistChar(double mean, double std, double min, double max, int num) {
            this.mean = mean;
            this.std = std;
            this.min = min;
            this.max = max;
            this.num = num;
        }
    }
    
    /**
     * Scaling value that moves the decimal point by 5 digets.
     */
    public final static double SCALER = 10000.0d;
    
    /**
     * <p>
     * Adoption of the Hamming difference to numerical values, i.e., basically a count of different
     * metric values.
     * </p>
     *
     * @param inst1
     *            first instance to be compared
     * @param inst2
     *            second instance to be compared
     * @return the distance
     */
    public static double hammingDistance(Instance inst1, Instance inst2) {
        double distance = 0.0;
        for (int j = 0; j < inst1.numAttributes(); j++) {
            if (j != inst1.classIndex()) {
                if (inst1.value(j) != inst2.value(j)) {
                    distance += 1.0;
                }
            }
        }
        return distance;
    }
    
    public static double[] instanceValues(Instance instance) {
        double[] values = new double[instance.numAttributes()-1];
        int k=0; 
        for( int j=0; j<instance.numAttributes() ; j++ ) {
            if( j!= instance.classIndex() ) {
                values[k] = instance.value(j);
                k++;
            }
        }
        return values;
    }
    
    public static DistChar datasetDistance(Instances data) {
        double distance;
        double sumAll = 0.0;
        double sumAllQ = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int numCmp = 0;
        int l = 0;
        double[] inst1 = new double[data.numAttributes()-1];
        double[] inst2 = new double[data.numAttributes()-1];
        EuclideanDistance euclideanDistance = new EuclideanDistance();
        for( int i=0; i<data.numInstances(); i++ ) {
            l=0;
            for( int k=0; k<data.numAttributes(); k++ ) {
                if( k!=data.classIndex() ) {
                    inst1[l] = data.instance(i).value(k);
                }
            }
            for( int j=0; j<data.numInstances(); j++ ) {
                if( j!=i ) {
                    l=0;
                    for( int k=0; k<data.numAttributes(); k++ ) {
                        if( k!=data.classIndex() ) {
                            inst2[l] = data.instance(j).value(k);
                        }
                    }
                    distance = euclideanDistance.compute(inst1, inst2);
                    sumAll += distance;
                    sumAllQ += distance*distance;
                    numCmp++;
                    if( distance < min ) {
                        min = distance;
                    }
                    if( distance > max ) {
                        max = distance;
                    }
                }
            }
        }
        double mean = sumAll / numCmp;
        double std = Math.sqrt((sumAllQ-(sumAll*sumAll)/numCmp) *
                                  (1.0d / (numCmp - 1)));
        return new DistChar(mean, std, min, max, data.numInstances());
    }
    
    // like above, but for single attribute
    public static DistChar attributeDistance(Instances data, int index) {
        double distance;
        double sumAll = 0.0;
        double sumAllQ = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int numCmp = 0;
        double value1, value2;
        for( int i=0; i<data.numInstances(); i++ ) {
            value1 = data.instance(i).value(index);
            for( int j=0; j<data.numInstances(); j++ ) {
                if( j!=i ) {
                    value2 = data.instance(j).value(index);
                    distance = Math.abs(value1-value2);
                    sumAll += distance;
                    sumAllQ += distance*distance;
                    numCmp++;
                    if( distance < min ) {
                        min = distance;
                    }
                    if( distance > max ) {
                        max = distance;
                    }
                }
            }
        }
        double mean = sumAll / numCmp;
        double std = Math.sqrt((sumAllQ-(sumAll*sumAll)/numCmp) *
                                  (1.0d / (numCmp - 1)));
        return new DistChar(mean, std, min, max, data.numInstances());
    }
    
    /**
     * <p>
     * Upscales the value of a single attribute. This is a workaround to get BayesNet running for
     * all data. Works on a copy of the training data, i.e., leaves the original data untouched.
     * </p>
     *
     * @param traindata
     *            data from which the attribute is upscaled.
     * @param attributeIndex
     *            index of the attribute
     * @return data with upscaled attribute
     */
    public static Instances upscaleAttribute(Instances traindata, int attributeIndex) {
        Instances traindataCopy = new Instances(traindata);
        for (int i = 0; i < traindata.size(); i++) {
            traindataCopy.get(i).setValue(attributeIndex,
                                          traindata.get(i).value(attributeIndex) * SCALER);
        }
        return traindataCopy;
    }
}
