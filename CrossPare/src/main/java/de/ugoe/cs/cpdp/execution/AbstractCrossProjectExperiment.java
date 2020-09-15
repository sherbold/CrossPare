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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ugoe.cs.cpdp.ExperimentConfiguration;
import de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy;
import de.ugoe.cs.cpdp.dataprocessing.ISetWiseProcessingStrategy;
import de.ugoe.cs.cpdp.dataprocessing.IVersionProcessingStrategy;
import de.ugoe.cs.cpdp.dataselection.IPointWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.dataselection.ISetWiseDataselectionStrategy;
import de.ugoe.cs.cpdp.eval.IEvaluationStrategy;
import de.ugoe.cs.cpdp.loader.IVersionLoader;
import de.ugoe.cs.cpdp.training.ISetWiseTestdataAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITestAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.util.CrosspareUtils;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
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
     * @param versions
     *            all software versions in the data set
     * @return true if test candidate can be used for training
     */
    protected abstract boolean isTrainingVersion(SoftwareVersion trainingVersion,
                                                 SoftwareVersion testVersion,
                                                 List<SoftwareVersion> versions);

    /**
     * Helper method that combines a set of {@link SoftwareVersion} sets into a single
     * {@link Instances} set.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} which training data is to be combined
     * @return single {@link Instances} set
     */
    public static Instances makeSingleTrainingSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances traindataFull = null;
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances traindata = trainversion.getInstances();
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
     * Helper method that combines a set of {@link SoftwareVersion} into a single
     * {@link Instances} bugmatrix.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} which bug matrices are to be combined
     * @return single {@link Instances} bugmatrix
     */
    public static Instances makeSingleBugMatrixSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        final ArrayList<Attribute> bugMatrixAtts = new ArrayList<>();
        final Map<String, Integer> nameToIndex = new HashMap<>();
        for (SoftwareVersion trainversion : trainversionSet) {
            for (Iterator<Attribute> iterator = trainversion.getBugMatrix().enumerateAttributes().asIterator(); iterator
                    .hasNext();) {
                String attName = iterator.next().name();
                Attribute att = new Attribute(attName);
                if (!bugMatrixAtts.contains(att)) {
                    bugMatrixAtts.add(att);
                    nameToIndex.put(attName, nameToIndex.size());
                }
            }
        }
        Instances singleBugMatrix = new Instances("singleBugMatrix", bugMatrixAtts, 0);
        for (SoftwareVersion trainversion : trainversionSet) {
            Instances bugMatrix = trainversion.getBugMatrix();
            List<String> attNames = new ArrayList<>();
            for (Iterator<Attribute> iterator = bugMatrix.enumerateAttributes().asIterator(); iterator.hasNext();) {
                attNames.add(iterator.next().name());
            }
            for (Instance instance : bugMatrix) {
                double[] allBugs = new double[bugMatrixAtts.size()];
                double[] bugs = instance.toDoubleArray();
                for (int j = 0; j < bugs.length; j++) {
                    allBugs[nameToIndex.get(attNames.get(j))] = bugs[j];
                }
                singleBugMatrix.add(new DenseInstance(1.0, allBugs));
            }
        }
        return singleBugMatrix;
    }

    /**
     * Helper method that combines a set of SetUniqueList<SoftwareVersion> into a single
     * Softwareversion.
     * 
     * @param trainversionSet
     *            set of {@link SoftwareVersion} to be combined
     * @return single {@link SoftwareVersion} set
     */
    public static SoftwareVersion makeSingleVersionSet(SetUniqueList<SoftwareVersion> trainversionSet) {
        Instances instances = makeSingleTrainingSet(trainversionSet);
        Instances bugmatrix = makeSingleBugMatrixSet(trainversionSet);
        List<Double> efforts = null;
        List<Double> numBugs = null;
        for (SoftwareVersion trainversion : trainversionSet) {
            List<Double> tempEfforts = trainversion.getEfforts();
            List<Double> tempNumBugs = trainversion.getNumBugs();
            if (efforts == null) {
                efforts = new ArrayList<Double>(tempEfforts);
            }
            if (numBugs == null) {
                numBugs = new ArrayList<Double>(tempNumBugs);
            }
            else {
                for (int i = 0; i < tempEfforts.size(); i++) {
                    efforts.add(tempEfforts.get(i));
                }
                for (int i = 0; i < tempNumBugs.size(); i++) {
                    numBugs.add(tempNumBugs.get(i));
                }
            }
        }
        return new SoftwareVersion("singleTrainVersionSet", "trainProjects", "trainVersions", instances, bugmatrix,
                efforts, numBugs, null, null);
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

        for (SoftwareVersion testVersion : versions) {
            if (CrosspareUtils.isVersion(testVersion, versions, this.config.getTestVersionFilters())) {
                testVersionCount++;
            }
        }

        // sort versions
        Collections.sort(versions);

        for (SoftwareVersion testVersion : versions) {
            if (CrosspareUtils.isVersion(testVersion, versions, this.config.getTestVersionFilters())) {
                LOGGER.info(String.format("[%s] [%02d/%02d] %s: starting",
                                              this.config.getExperimentName(), versionCount,
                                              testVersionCount, testVersion.getVersion()));
                int numResultsAvailable = CrosspareUtils.resultsAvailable(testVersion, this.config);
                if (numResultsAvailable >= this.config.getRepetitions()) {
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
                SetUniqueList<SoftwareVersion> trainversionSet =
                    SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
                for (SoftwareVersion trainingVersion : versions) {
                    if (CrosspareUtils.isVersion(trainingVersion, versions, this.config.getTrainingVersionFilters())) {
                        if (trainingVersion != testversion) {
                            if (isTrainingVersion(trainingVersion, testversion, versions)) {
                                SoftwareVersion trainversion = new SoftwareVersion(trainingVersion);
                            	for(IVersionProcessingStrategy processor : this.config.getTrainingVersionProcessors()) {
                            		processor.apply(testversion, trainversion);
                            	}
                                trainversionSet.add(trainversion);
                            }
                        }
                    }
                }
                if (trainversionSet.isEmpty()) {
                	LOGGER.warn(String
                                    .format("[%s] [%02d/%02d] %s: no training data this product; skipped",
                                            this.config.getExperimentName(), versionCount, testVersionCount,
                                            testVersion.getVersion()));
                    versionCount++;
                    continue;
                }

                for (ISetWiseProcessingStrategy processor : this.config.getSetWisePreprocessors()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying setwise preprocessor %s",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion(), processor.getClass().getName()));
                    processor.apply(testversion, trainversionSet);
                }
                for (ISetWiseDataselectionStrategy dataselector : this.config
                    .getSetWiseSelectors())
                {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying setwise selection %s",
                                               this.config.getExperimentName(), versionCount,
                                               testVersionCount, testVersion.getVersion(),
                                               dataselector.getClass().getName()));
                    dataselector.apply(testversion, trainversionSet);
                }
                for (ISetWiseProcessingStrategy processor : this.config
                    .getSetWisePostprocessors())
                {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion(), processor.getClass().getName()));
                    processor.apply(testversion, trainversionSet);
                }
                for (ISetWiseTrainingStrategy setwiseTrainer : this.config.getSetWiseTrainers()) {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying setwise trainer %s",
                                               this.config.getExperimentName(), versionCount,
                                               testVersionCount, testVersion.getVersion(),
                                               setwiseTrainer.getName()));
                    setwiseTrainer.apply(trainversionSet);
                }
                for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : this.config
                    .getSetWiseTestdataAwareTrainers())
                {
                	LOGGER.info(String.format("[%s] [%02d/%02d] %s: applying testdata aware setwise trainer %s",
                                this.config.getExperimentName(), versionCount, testVersionCount,
                                testVersion.getVersion(), setwiseTestdataAwareTrainer.getName()));
                    setwiseTestdataAwareTrainer.apply(trainversionSet, testversion);
                }
                SoftwareVersion trainversion = makeSingleVersionSet(trainversionSet);
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
                    for (ISetWiseTrainingStrategy setwiseTrainer : this.config
                        .getSetWiseTrainers())
                    {
                        allTrainers.add(setwiseTrainer);
                    }
                    for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : this.config
                        .getSetWiseTestdataAwareTrainers())
                    {
                        allTrainers.add(setwiseTestdataAwareTrainer);
                    }
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
                    evaluator.apply(testdata, trainversion.getInstances(), allTrainers, efforts,
                            numBugs, bugMatrix, writeHeader, this.config.getResultStorages());
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
