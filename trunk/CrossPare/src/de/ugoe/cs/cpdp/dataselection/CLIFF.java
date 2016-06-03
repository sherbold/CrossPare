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

import java.util.Arrays;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.core.Instances;

/**
 * Implements CLIFF data pruning.
 * 
 * @author Steffen Herbold
 */
public class CLIFF implements IPointWiseDataselectionStrategy, ISetWiseDataselectionStrategy {

    private double percentage = 0.10;
    
    private final int numRanges = 10;

    /**
     * Sets the number of neighbors.
     * 
     * @param parameters
     *            number of neighbors
     */
    @Override
    public void setParameter(String parameters) {
        if( parameters!=null ) {
            percentage = Double.parseDouble(parameters);
        }
    }
    
    /**
     * @see de.ugoe.cs.cpdp.dataselection.SetWiseDataselectionStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for( Instances traindata : traindataSet ) {
            applyCLIFF(traindata);
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataselection.PointWiseDataselectionStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public Instances apply(Instances testdata, Instances traindata) {
        return applyCLIFF(traindata);
    }

    protected Instances applyCLIFF(Instances data) {
        final double[][] powerAttributes = new double[data.size()][data.numAttributes()];
        final double[] powerEntity = new double[data.size()];
        
        final int[] counts = data.attributeStats(data.classIndex()).nominalCounts;
        final double probDefect = data.numInstances() / (double) counts[1];
        
        for( int j=0; j<data.numAttributes(); j++ ) {
            if( data.attribute(j)!=data.classAttribute()) {
                final double[] ranges = getRanges(data, j);
                final double[] probDefectRange = getRangeProbabilities(data, j, ranges);
                
                for( int i=0 ; i<data.numInstances() ; i++ ) {
                    final double value = data.instance(i).value(j);
                    final int range = determineRange(ranges, value);
                    double probClass, probNotClass, probRangeClass, probRangeNotClass;
                    if( data.instance(i).classValue()==1 ) {
                        probClass = probDefect;
                        probNotClass = 1.0-probDefect;
                        probRangeClass = probDefectRange[range];
                        probRangeNotClass = 1.0-probDefectRange[range];
                    } else {
                        probClass = 1.0-probDefect;
                        probNotClass = probDefect;
                        probRangeClass = 1.0-probDefectRange[range];
                        probRangeNotClass = probDefectRange[range];
                    }
                    powerAttributes[i][j] = Math.pow(probRangeClass, 2.0)/(probRangeClass*probClass+probRangeNotClass*probNotClass);
                }
            }
        }
        
        for( int i=0; i<data.numInstances(); i++ ) {
            powerEntity[i] = 1.0;
            for (int j=0; j<data.numAttributes() ; j++ ) {
                powerEntity[i] *= powerAttributes[i][j];
            }
        }
        double[] sortedPower = powerEntity.clone();
        Arrays.sort(sortedPower);
        double cutOff = sortedPower[(int) (data.numInstances()*(1-percentage))];

        final Instances selected = new Instances(data);
        selected.delete();
        for (int i=0; i<data.numInstances(); i++) {
            if( powerEntity[i]>=cutOff ) {
                selected.add(data.instance(i));
            }
        }
        return selected;
    }
    
    private double[] getRanges(Instances data, int j) {
        double[] values = new double[numRanges+1];
        for( int k=0; k<numRanges; k++ ) {
            values[k] = data.kthSmallestValue(j, (int) (data.size()*(k+1.0)/numRanges));
        }
        values[numRanges] = data.attributeStats(j).numericStats.max;
        return values;
    }
    
    private double[] getRangeProbabilities(Instances data, int j, double[] ranges) {
        double[] probDefectRange = new double[numRanges];
        int[] countRange = new int[numRanges];
        int[] countDefect = new int[numRanges];
        for( int i=0; i<data.numInstances() ; i++ ) {
            int range = determineRange(ranges, data.instance(i).value(j)); 
            countRange[range]++;
            if( data.instance(i).classValue()== 1 ) {
                countDefect[range]++;
            }

        }
        for( int k=0; k<numRanges; k++ ) {
            probDefectRange[k] = ((double) countDefect[k]) / countRange[k];
        }
        return probDefectRange;
    }
    
    private int determineRange(double[] ranges, double value) {
        for( int k=0; k<numRanges; k++ ) {
            if( value<=ranges[k+1] ) {
                return k;
            }
        }
        throw new RuntimeException("invalid range or value");
    }
}
