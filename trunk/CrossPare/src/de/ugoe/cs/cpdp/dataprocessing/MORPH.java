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

import java.security.InvalidParameterException;
import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.util.MathArrays;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements the MORPH data privatization. 
 * 
 * 
 * @author Steffen Herbold
 */
public class MORPH implements ISetWiseProcessingStrategy, IProcessesingStrategy {

    /**
     * random number generator for MORPH
     */
    Random rand = new Random();
    
    /**
     * parameter alpha for MORPH, default is 0.15
     */
    double alpha = 0.15;
    
    /**
     * parameter beta for MORPH, default is 0.35
     */
    double beta = 0.35;
    
    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.equals("")) {
            String[] values = parameters.split(" ");
            if( values.length!=2 ) {
                throw new InvalidParameterException("MORPH requires two doubles as parameter or no parameters to use default values");
            }
            try {
                alpha = Double.parseDouble(values[0]);
                beta = Double.parseDouble(values[1]);
            } catch(NumberFormatException e) {
                throw new InvalidParameterException("MORPH requires two doubles as parameter or no parameters to use default values");
            }
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.SetWiseProcessingStrategy#apply(weka.core.Instances,
     *      org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        for( Instances traindata : traindataSet ) {
            applyMORPH(traindata);
        }
    }

    /**
     * @see de.ugoe.cs.cpdp.dataprocessing.ProcessesingStrategy#apply(weka.core.Instances,
     *      weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        applyMORPH(traindata);
    }
    
    /**
     * 
     * <p>
     * Applies MORPH to the data
     * </p>
     *
     * @param data data to which the processor is applied
     */
    public void applyMORPH(Instances data) {
        for (int i=0; i<data.numInstances(); i++ ) {
            morphInstance(data.get(i), data);
        }
    }
    
    /**
     * <p>
     * Applies MORPH to a single instance
     * </p>
     *
     * @param instance instance that is morphed
     * @param data data based on which the instance is morphed
     */
    public void morphInstance(Instance instance, Instances data) {
        Instance nearestUnlikeNeighbor = getNearestUnlikeNeighbor(instance, data);
        if( nearestUnlikeNeighbor==null ) {
            throw new RuntimeException("could not find nearest unlike neighbor within the data: " + data.relationName());
        }
        for( int j=0; j<data.numAttributes() ; j++ ) {
            if( data.attribute(j)!=data.classAttribute() && data.attribute(j).isNumeric()) {
                double randVal = rand.nextDouble()*(beta-alpha)+alpha;
                instance.setValue(j, instance.value(j) + randVal*(instance.value(j)-nearestUnlikeNeighbor.value(j)) );
            }
        }
    }
    
    /**
     * <p>
     * Determines the nearest unlike neighbor of an instance. 
     * </p>
     *
     * @param instance instance to which the nearest unlike neighbor is determined
     * @param data data where the nearest unlike neighbor is determined from
     * @return nearest unlike instance
     */
    public Instance getNearestUnlikeNeighbor(Instance instance, Instances data) {
        Instance nearestUnlikeNeighbor = null;
        
        double[] instanceVector = new double[data.numAttributes()-1];
        int tmp = 0;
        for( int j=0; j<data.numAttributes(); j++ ) {
            if( data.attribute(j)!=data.classAttribute() && data.attribute(j).isNumeric()) {
                instanceVector[tmp] = instance.value(j);
            }
        }
        
        double minDistance = Double.MAX_VALUE;
        for( int i=0 ; i<data.numInstances() ; i++ ) {
            if( instance.classValue() != data.instance(i).classValue() ) {
                double[] otherVector = new double[data.numAttributes() - 1];
                tmp = 0;
                for (int j = 0; j < data.numAttributes(); j++) {
                    if (data.attribute(j) != data.classAttribute() && data.attribute(j).isNumeric()) {
                        otherVector[tmp++] = data.instance(i).value(j);
                    }
                }
                if( MathArrays.distance(instanceVector, otherVector)<minDistance) {
                    minDistance = MathArrays.distance(instanceVector, otherVector);
                    nearestUnlikeNeighbor = data.instance(i);
                }
            }
        }
        return nearestUnlikeNeighbor;
    }
}
