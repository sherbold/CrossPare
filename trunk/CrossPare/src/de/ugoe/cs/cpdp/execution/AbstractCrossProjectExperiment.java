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
import java.util.logging.Level;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy;
import de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy;
import de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.eval.IEvaluationStrategy;
import de.ugoe.cs.cpdp.eval.IResultStorage;
import de.ugoe.cs.cpdp.loader.IVersionLoader;
import de.ugoe.cs.cpdp.training.ISetWiseTestdataAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.cpdp.versions.IVersionFilter;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.util.console.Console;
import weka.core.Instances;

/**
 * Class responsible for executing an experiment according to an {@link ExperimentConfiguration}.
 * The steps of an experiment are as follows:
 * <ul>
 * <li>load the data from the provided data path</li>
 * <li>filter the data sets according to the provided version filters</li>
 * <li>execute the following steps for each data sets as test data that is not ignored through the
 * test version filter:
 * <ul>
 * <li>filter the data sets to setup the candidate training data:
 * <ul>
 * <li>remove all data sets from the same project</li>
 * <li>filter all data sets according to the training data filter
 * </ul>
 * </li>
 * <li>apply the setwise preprocessors</li>
 * <li>apply the setwise data selection algorithms</li>
 * <li>apply the setwise postprocessors</li>
 * <li>train the setwise training classifiers</li>
 * <li>unify all remaining training data into one data set</li>
 * <li>apply the preprocessors</li>
 * <li>apply the pointwise data selection algorithms</li>
 * <li>apply the postprocessors</li>
 * <li>train the normal classifiers</li>
 * <li>evaluate the results for all trained classifiers on the training data</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * Note that this class implements {@link Runnable}, i.e., each experiment can be started in its own
 * thread.
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractCrossProjectExperiment implements IExecutionStrategy {

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
    public AbstractCrossProjectExperiment(ExperimentConfiguration config) {
        this.config = config;
    }

    /**
     * <p>
     * Defines which products are allowed for training.
     * </p>
     *
     * @param trainingVersion
     *            training version
     * @param testVersion
     *            test candidate
     * @return true if test candidate can be used for training
     */
    protected abstract boolean isTrainingVersion(SoftwareVersion trainingVersion,
                                                 SoftwareVersion testVersion);

    /**
     * Helper method that combines a set of Weka {@link Instances} sets into a single
     * {@link Instances} set.
     * 
     * @param traindataSet
     *            set of {@link Instances} to be combines
     * @return single {@link Instances} set
     */
    public static Instances makeSingleTrainingSet(SetUniqueList<Instances> traindataSet) {
        Instances traindataFull = null;
        for (Instances traindata : traindataSet) {
            if (traindataFull == null) {
                traindataFull = new Instances(traindata);
            }
            else {
                for (int i = 0; i < traindata.numInstances(); i++) {
                    traindataFull.add(traindata.instance(i));
                }
            }
        }
        return traindataFull;
    }

    /**
     * Executes the experiment with the steps as described in the class comment.
     * 
     * @see Runnable#run()
     */
    @Override
    public void run() {
        final List<SoftwareVersion> versions = new LinkedList<>();

        for (IVersionLoader loader : config.getLoaders()) {
            versions.addAll(loader.load());
        }

        for (IVersionFilter filter : config.getVersionFilters()) {
            filter.apply(versions);
        }
        boolean writeHeader = true;
        int versionCount = 1;
        int testVersionCount = 0;
        int numTrainers = 0;

        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {
                testVersionCount++;
            }
        }
        
        numTrainers += config.getSetWiseTrainers().size();
        numTrainers += config.getSetWiseTestdataAwareTrainers().size();
        numTrainers += config.getTrainers().size();
        numTrainers += config.getTestAwareTrainers().size();

        // sort versions
        Collections.sort(versions);

        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {
                Console.traceln(Level.INFO,
                                String.format("[%s] [%02d/%02d] %s: starting",
                                              config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                int numResultsAvailable = resultsAvailable(testVersion);
                if (numResultsAvailable >= numTrainers*config.getRepetitions()) {
                    Console.traceln(Level.INFO,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: results already available; skipped",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion()));
                    versionCount++;
                    continue;
                }

                // Setup testdata and training data
                Instances testdata = testVersion.getInstances();
                SetUniqueList<Instances> traindataSet =
                    SetUniqueList.setUniqueList(new LinkedList<Instances>());
                for (SoftwareVersion trainingVersion : versions) {
                    if (isVersion(trainingVersion, config.getTrainingVersionFilters())) {
                        if (trainingVersion != testVersion) {
                            if (isTrainingVersion(trainingVersion, testVersion)) {
                                traindataSet.add(trainingVersion.getInstances());
                            }
                        }
                    }
                }

                for (ISetWiseProcessingStrategy processor : config.getSetWisePreprocessors()) {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying setwise preprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindataSet);
                }
                for (ISetWiseDataselectionStrategy dataselector : config.getSetWiseSelectors()) {
                    Console
                        .traceln(Level.FINE,
                                 String.format("[%s] [%02d/%02d] %s: applying setwise selection %s",
                                               config.getExperimentName(), versionCount,
                                               testVersionCount, testVersion.getVersion(),
                                               dataselector.getClass().getName()));
                    dataselector.apply(testdata, traindataSet);
                }
                for (ISetWiseProcessingStrategy processor : config.getSetWisePostprocessors()) {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindataSet);
                }
                for (ISetWiseTrainingStrategy setwiseTrainer : config.getSetWiseTrainers()) {
                    Console
                        .traceln(Level.FINE,
                                 String.format("[%s] [%02d/%02d] %s: applying setwise trainer %s",
                                               config.getExperimentName(), versionCount,
                                               testVersionCount, testVersion.getVersion(),
                                               setwiseTrainer.getName()));
                    setwiseTrainer.apply(traindataSet);
                }
                for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
                    .getSetWiseTestdataAwareTrainers())
                {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying testdata aware setwise trainer %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  setwiseTestdataAwareTrainer.getName()));
                    setwiseTestdataAwareTrainer.apply(traindataSet, testdata);
                }
                Instances traindata = makeSingleTrainingSet(traindataSet);
                for (IProcessesingStrategy processor : config.getPreProcessors()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying preprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindata);
                }
                for (IPointWiseDataselectionStrategy dataselector : config
                    .getPointWiseSelectors())
                {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying pointwise selection %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  dataselector.getClass().getName()));
                    traindata = dataselector.apply(testdata, traindata);
                }
                for (IProcessesingStrategy processor : config.getPostProcessors()) {
                    Console.traceln(Level.FINE,
                                    String.format(
                                                  "[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  processor.getClass().getName()));
                    processor.apply(testdata, traindata);
                }
                for (ITrainingStrategy trainer : config.getTrainers()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(traindata);
                }
                for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying trainer %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  trainer.getName()));
                    trainer.apply(testdata, traindata);
                }
                File resultsDir = new File(config.getResultsPath());
                if (!resultsDir.exists()) {
                    resultsDir.mkdir();
                }
                for (IEvaluationStrategy evaluator : config.getEvaluators()) {
                    Console.traceln(Level.FINE,
                                    String.format("[%s] [%02d/%02d] %s: applying evaluator %s",
                                                  config.getExperimentName(), versionCount,
                                                  testVersionCount, testVersion.getVersion(),
                                                  evaluator.getClass().getName()));
                    List<ITrainer> allTrainers = new LinkedList<>();
                    for (ISetWiseTrainingStrategy setwiseTrainer : config.getSetWiseTrainers()) {
                        allTrainers.add(setwiseTrainer);
                    }
                    for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
                        .getSetWiseTestdataAwareTrainers())
                    {
                        allTrainers.add(setwiseTestdataAwareTrainer);
                    }
                    for (ITrainingStrategy trainer : config.getTrainers()) {
                        allTrainers.add(trainer);
                    }
                    for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
                        allTrainers.add(trainer);
                    }
                    if (writeHeader) {
                        evaluator.setParameter(config.getResultsPath() + "/" +
                            config.getExperimentName() + ".csv");
                    }
                    evaluator.apply(testdata, traindata, allTrainers, writeHeader,
                                    config.getResultStorages());
                    writeHeader = false;
                }
                Console.traceln(Level.INFO,
                                String.format("[%s] [%02d/%02d] %s: finished",
                                              config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                versionCount++;
            }
        }
    }

    /**
     * Helper method that checks if a version passes all filters.
     * 
     * @param version
     *            version that is checked
     * @param filters
     *            list of the filters
     * @return true, if the version passes all filters, false otherwise
     */
    private boolean isVersion(SoftwareVersion version, List<IVersionFilter> filters) {
        boolean result = true;
        for (IVersionFilter filter : filters) {
            result &= !filter.apply(version);
        }
        return result;
    }

    private int resultsAvailable(SoftwareVersion version) {
        if (config.getResultStorages().isEmpty()) {
            return 0;
        }
        
        List<ITrainer> allTrainers = new LinkedList<>();
        for (ISetWiseTrainingStrategy setwiseTrainer : config.getSetWiseTrainers()) {
            allTrainers.add(setwiseTrainer);
        }
        for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
            .getSetWiseTestdataAwareTrainers())
        {
            allTrainers.add(setwiseTestdataAwareTrainer);
        }
        for (ITrainingStrategy trainer : config.getTrainers()) {
            allTrainers.add(trainer);
        }
        for (ITestAwareTrainingStrategy trainer : config.getTestAwareTrainers()) {
            allTrainers.add(trainer);
        }
        
        int available = Integer.MAX_VALUE;
        for (IResultStorage storage : config.getResultStorages()) {
            String classifierName = ((IWekaCompatibleTrainer) allTrainers.get(0)).getName();
            int curAvailable = storage.containsResult(config.getExperimentName(), version.getVersion(), classifierName);
            if( curAvailable<available ) {
                available = curAvailable;
            }
        }
        return available;
    }
}
