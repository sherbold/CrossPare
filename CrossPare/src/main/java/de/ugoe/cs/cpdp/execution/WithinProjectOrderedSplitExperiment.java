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

package de.ugoe.cs.cpdp.execution;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy;
import de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.eval.IEvaluationStrategy;
import de.ugoe.cs.cpdp.loader.IVersionLoader;
import de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.util.CrosspareUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Instances;

/**
 * This execution strategy allows for within-project experiments, where the first part of the data
 * is used for training and the second part of the data is used for testing. The percentage for the
 * split is defined using the "param" attribute.
 * 
 * This experiment should only be used with time-ordered data in order to guarantee that only the
 * past is used to predict the future.
 * 
 * @author Steffen Herbold
 */
public class WithinProjectOrderedSplitExperiment implements IExecutionStrategy {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
    /**
     * configuration of the experiment
     */
    protected final ExperimentConfiguration config;

    /**
     * Constructor. Creates a new experiment based on a configuration.
     * 
     * @param config
     *            configuration of the experiment
     */
    @SuppressWarnings("hiding")
    public WithinProjectOrderedSplitExperiment(ExperimentConfiguration config) {
        this.config = config;
    }

    /**
     * Executes the experiment with the steps as described in the class comment.
     * 
     * @see Runnable#run()
     */
    @SuppressWarnings("boxing")
    @Override
    public void run() {
        final List<SoftwareVersion> versions = new LinkedList<>();

        for (IVersionLoader loader : this.config.getLoaders()) {
            versions.addAll(loader.load());
        }

        CrosspareUtils.filterVersions(versions, this.config.getVersionFilters());

        boolean writeHeader = true;
        int versionCount = 1;
        int testVersionCount = 0;
        int numTrainers = 0;

        for (SoftwareVersion testVersion : versions) {
            if (CrosspareUtils.isVersion(testVersion, versions, this.config.getTestVersionFilters())) {
                testVersionCount++;
            }
        }

        numTrainers += this.config.getSetWiseTrainers().size();
        numTrainers += this.config.getSetWiseTestdataAwareTrainers().size();
        numTrainers += this.config.getTrainers().size();
        numTrainers += this.config.getTestAwareTrainers().size();

        // sort versions
        Collections.sort(versions);

        for (SoftwareVersion testVersion : versions) {
            if (CrosspareUtils.isVersion(testVersion, versions, this.config.getTestVersionFilters())) {
                LOGGER.info(String.format("[%s] [%02d/%02d] %s: starting",
                                              this.config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                int numResultsAvailable = CrosspareUtils.resultsAvailable(testVersion, this.config);
                if (numResultsAvailable >= numTrainers * this.config.getRepetitions()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: results already available; skipped",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion()));
                    versionCount++;
                    continue;
                }

                // Setup testdata and training data
                SoftwareVersion testversion = new SoftwareVersion(testVersion);
                Instances testdata = testversion.getInstances();
                List<Double> efforts = testversion.getEfforts();
                List<Double> numBugs = testversion.getNumBugs();
                Instances bugMatrix = testversion.getBugMatrix();


                // now split data into parts
                double percentage = 0.5; // 0.5 as default value
                String param = this.config.getExecutionStrategyParameters();
                if (this.config.getExecutionStrategyParameters() != null) {
                    try {
                        percentage = Double.parseDouble(param);
                    }
                    catch (@SuppressWarnings("unused") NumberFormatException e) {
                        throw new RuntimeException("invalid execution strategy parameter, must be numeric: " +
                            param);
                    }
                }
                int initialTestSize = testdata.size();
                SoftwareVersion trainversion = new SoftwareVersion(testversion);
                for (int i = initialTestSize - 1; i >= 0; i--) {
                    if ((((double) i) / initialTestSize) < percentage) {
                        testdata.delete(i);
                        if (efforts != null) {
                            efforts.remove(i);
                        }
                        if (numBugs != null) {
                            numBugs.remove(i);
                        }
                        if (bugMatrix != null) {
                            bugMatrix.delete(i);
                        }
                    }
                    else {
                        trainversion.getInstances().delete(i);
                        if (trainversion.getBugMatrix() != null) {
                            trainversion.getBugMatrix().delete(i);
                        }
                        if (trainversion.getEfforts() != null) {
                            trainversion.getEfforts().remove(i);
                        }
                        if (trainversion.getNumBugs() != null) {
                            trainversion.getNumBugs().remove(i);
                        }
                    }
                }

                for (IProcessesingStrategy processor : this.config.getPreProcessors()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying preprocessor %s",
                                                  this.config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testversion, trainversion);
                }
                for (IPointWiseDataselectionStrategy dataselector : this.config
                    .getPointWiseSelectors())
                {
                    LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying pointwise selection %s",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion(), dataselector.getClass().getName()));
                    trainversion = dataselector.apply(testversion, trainversion);
                }
                for (IProcessesingStrategy processor : this.config.getPostProcessors()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion(), processor.getClass().getName()));
                    processor.apply(testversion, trainversion);
                }
                for (ITrainingStrategy trainer : this.config.getTrainers()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  this.config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(trainversion);
                }
                for (ITestAwareTrainingStrategy trainer : this.config.getTestAwareTrainers()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  this.config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(testversion, trainversion);
                }
                File resultsDir = new File(this.config.getResultsPath());
                if (!resultsDir.exists()) {
                    resultsDir.mkdir();
                }
                for (IEvaluationStrategy evaluator : this.config.getEvaluators()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying evaluator %s",
                                                  this.config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  evaluator.getClass().getName()));
                    List<ITrainer> allTrainers = new LinkedList<>();
                    for (ITrainingStrategy trainer : this.config.getTrainers()) {
                        allTrainers.add(trainer);
                    }
                    for (ITestAwareTrainingStrategy trainer : this.config.getTestAwareTrainers()) {
                        allTrainers.add(trainer);
                    }
                    if (writeHeader) {
                        evaluator.setParameter(this.config.getResultsPath() + "/" +
                            this.config.getExperimentName() + ".csv");
                    }
                    evaluator.apply(testdata, trainversion.getInstances(), allTrainers, efforts, numBugs, bugMatrix,
                            writeHeader, this.config.getResultStorages());
                    writeHeader = false;
                }
                LOGGER.info(String.format("[%s] [%02d/%02d] %s: finished",
                                              this.config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                versionCount++;
            }
        }
    }
}
