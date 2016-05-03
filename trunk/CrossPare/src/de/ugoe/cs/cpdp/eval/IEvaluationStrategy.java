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

package de.ugoe.cs.cpdp.eval;

import java.util.List;

import de.ugoe.cs.cpdp.IParameterizable;
import de.ugoe.cs.cpdp.training.ITrainer;

import weka.core.Instances;

/**
 * Interface for evaluation strategies to evaluate the performance of classifiers.
 * 
 * @author Steffen Herbold
 */
public interface IEvaluationStrategy extends IParameterizable {

    /**
     * Applies the evaluation strategy.
     * 
     * @param testdata
     *            test data for the evaluation
     * @param traindata
     *            training data used
     * @param trainers
     *            list of training algorithms used to train the classifiers
     * @param writeHeader
     *            if true, a header line for the results file is written (may not be applicable)
     * @param storages
     *            result storages that shall additionally be used
     */
    void apply(Instances testdata,
               Instances traindata,
               List<ITrainer> trainers,
               boolean writeHeader,
               List<IResultStorage> storages);
}
