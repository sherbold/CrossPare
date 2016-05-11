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

import de.ugoe.cs.cpdp.util.WekaUtils;
import de.ugoe.cs.cpdp.util.WekaUtils.DistChar;
import weka.core.Instances;

// normalization selected according to TCA+ rules (TCA has to be applied separately
public class TCAPlusNormalization implements IProcessesingStrategy {

    /**
     * Does not have parameters. String is ignored.
     * 
     * @param parameters
     *            ignored
     */
    @Override
    public void setParameter(String parameters) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void apply(Instances testdata, Instances traindata) {
        applyTCAPlus(testdata, traindata);
    }
    
    private void applyTCAPlus(Instances testdata, Instances traindata) {
        DistChar dcTest = WekaUtils.datasetDistance(testdata);
        DistChar dcTrain = WekaUtils.datasetDistance(traindata);
        
        // RULE 1:
        if( 0.9*dcTrain.mean<=dcTest.mean && 1.1*dcTrain.mean>=dcTest.mean &&
            0.9*dcTrain.std<=dcTest.std && 1.1*dcTrain.std>=dcTest.std) {
            // do nothing
        }
        // RULE 2:
        else if((0.4*dcTrain.min>dcTest.min || 1.6*dcTrain.min<dcTest.min) &&
                (0.4*dcTrain.max>dcTest.max || 1.6*dcTrain.min<dcTest.max) &&
                (0.4*dcTrain.min>dcTest.num || 1.6*dcTrain.min<dcTest.num)) {
            NormalizationUtil.minMax(testdata);
            NormalizationUtil.minMax(traindata);
        }
        // RULE 3:
        else if((0.4*dcTrain.std>dcTest.std && dcTrain.num<dcTest.num) || 
                (1.6*dcTrain.std<dcTest.std)&& dcTrain.num>dcTest.num) {
            NormalizationUtil.zScoreTraining(testdata, traindata);
        }
        // RULE 4:
        else if((0.4*dcTrain.std>dcTest.std && dcTrain.num>dcTest.num) || 
                (1.6*dcTrain.std<dcTest.std)&& dcTrain.num<dcTest.num) {
            NormalizationUtil.zScoreTarget(testdata, traindata);
        }
        //RULE 5:
        else {
            NormalizationUtil.zScore(testdata);
            NormalizationUtil.zScore(traindata);
        }
    }
}
