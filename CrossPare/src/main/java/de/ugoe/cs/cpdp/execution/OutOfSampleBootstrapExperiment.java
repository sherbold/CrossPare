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
import java.util.*;

import de.ugoe.cs.cpdp.IParameterizable;
import de.ugoe.cs.cpdp.util.WekaUtils;
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
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

/**
 * Execution strategy that applies Out-Of-Sample Bootstrap to the data.
 * <p>
 * With this data selection technique a bootstrap sample from the data is drawn and used as trainings data.
 * The leftover instances, which are not in the bootstrap sample, are used as trainings data.
 * Statistically these are 36.8 % of the data.
 * If there are not at least a minimal number of samples in the drawn trainings data the procedure is repeated.
 *
 * @author Steffen Tunkel
 */
public class OutOfSampleBootstrapExperiment implements IExecutionStrategy, IParameterizable {

    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * configuration of the experiment
     */
    protected final ExperimentConfiguration config;

    /**
     * number of bootstrap samples to be taken
     */
    private int numSamples = 1;

    /**
     * minimum number of defects to keep the sample
     */
    private int minDefects = 2;

    /**
     * Blank separated parameters with the format: "numSamples minDefects"
     * If only one parameter is given, it is used as number of bootstrap samples and minimum required defects is kept
     * as default. defaults: numSamples: 1, minDefects: 2
     *
     * @param parameters
     *            string with the number of bootstrap samples and minimum required defects (blank separated)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            if(parameters.split(" ").length>2){
                throw new RuntimeException("Too many parameters given. Format:'numSamples minDefects'");
            }
            if(parameters.split(" ").length==2){
                this.numSamples = Integer.parseInt(parameters.split(" ")[0]);
                this.minDefects = Integer.parseInt(parameters.split(" ")[1]);
            }
            if(parameters.split(" ").length==1){
                this.numSamples = Integer.parseInt(parameters.split(" ")[0]);
            }
        }
    }

    /**
     * Constructor. Creates a new experiment based on a configuration.
     *
     * @param config
     *            configuration of the experiment
     */
    @SuppressWarnings("hiding")
    public OutOfSampleBootstrapExperiment(ExperimentConfiguration config) {
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

                // Bootstrap Sampling
                for (int bootstrapIndex = 0; bootstrapIndex < numSamples; bootstrapIndex++){
                    LOGGER.info(String.format("[%s] [%02d/%02d] %s: creating bootstrap sample %d",
                            this.config.getExperimentName(), versionCount, testVersionCount,
                            testVersion.getVersion(), bootstrapIndex));
                    SoftwareVersion testversion = new SoftwareVersion(testVersion);

                    SetUniqueList<SoftwareVersion> trainversionSet =
                            SetUniqueList.setUniqueList(new LinkedList<SoftwareVersion>());
                    SoftwareVersion trainversion = new SoftwareVersion(testVersion);
                    for(IVersionProcessingStrategy processor : this.config.getTrainingVersionProcessors()) {
                        processor.apply(testversion, trainversion);
                    }
                    trainversionSet.add(trainversion);

                    WekaUtils.makeClassBinary(testversion.getInstances());
                    WekaUtils.makeClassBinary(trainversion.getInstances());
                    createBootstrapSample(testversion, trainversion);



                    if (trainversionSet.isEmpty()) {
                        LOGGER.warn(String
                                .format("[%s] [%02d/%02d] %s: no training data this product; skipped",
                                        this.config.getExperimentName(), versionCount, testVersionCount,
                                        testVersion.getVersion()));
                        versionCount++;
                        continue;
                    }
                    SoftwareVersion trainversionOriginal = CrosspareUtils.makeSingleVersionSet(trainversionSet);
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
                    trainversion = CrosspareUtils.makeSingleVersionSet(trainversionSet);
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
                        evaluator.apply(testversion.getInstances(), trainversion.getInstances(),
                                trainversionOriginal.getInstances(), allTrainers, testversion.getEfforts(),
                                testversion.getNumBugs(), testversion.getBugMatrix(), writeHeader,
                                this.config.getResultStorages());
                        writeHeader = false;
                    }
                }


                LOGGER.info(String.format("[%s] [%02d/%02d] %s: finished",
                        this.config.getExperimentName(), versionCount,
                        testVersionCount, testVersion.getVersion()));
                versionCount++;
            }
        }
    }

    /**
     * Helper function to create a Out-Of-Sample Bootstrap sample
     * <p>
     * A bootstrap sample from the data is drawn and used as trainings data.
     * The leftover instances, which are not in the bootstrap sample, are used as trainings data.
     * Statistically these are 36.8 % of the data.
     * If there are not at least a minimal number of samples in the drawn trainings data the procedure is repeated.
     *
     * @param testversion test set software version
     * @param trainversion train set software version
     */
    private void createBootstrapSample(SoftwareVersion testversion, SoftwareVersion trainversion) {
        SoftwareVersion testversioncopy = new SoftwareVersion(testversion);

        Instances testdata = testversion.getInstances();
        Instances testBugMatrix = testversion.getBugMatrix();
        List<Double> testEfforts = testversion.getEfforts();
        List<Double> testNumBugs = testversion.getNumBugs();

        Instances traindata = trainversion.getInstances();
        Instances trainBugMatrix = trainversion.getBugMatrix();
        List<Double> trainEfforts = trainversion.getEfforts();
        List<Double> trainNumBugs = trainversion.getNumBugs();

        boolean takeNewBootstrapSample = true;
        final int abortLimit = 10000;
        int abortCounter = 0;
        final int datasetSize = testdata.size();
        Random rand = new Random();

        while (takeNewBootstrapSample){

            List<Integer> bootstrapSample = new ArrayList<>();
            for (int i = 0; i < datasetSize; i++) {
                bootstrapSample.add(rand.nextInt(datasetSize));
            }

            traindata.clear();
            if (trainBugMatrix != null) {
                trainBugMatrix.clear();
            }
            if (trainEfforts != null) {
                trainEfforts.clear();
            }
            if (trainNumBugs != null) {
                trainNumBugs.clear();
            }
            for (int i = 0; i < datasetSize; i++) {
                int index = bootstrapSample.get(i);
                traindata.add((Instance) testversioncopy.getInstances().get(index).copy());
                if (trainBugMatrix != null) {
                    trainBugMatrix.add((Instance) testversioncopy.getBugMatrix().get(index).copy());
                }
                if (trainEfforts != null) {
                    trainEfforts.add(testversioncopy.getEfforts().get(index));
                }
                if (trainNumBugs != null) {
                    trainNumBugs.add(testversioncopy.getNumBugs().get(index));
                }
            }
            if (traindata.attributeStats(traindata.classIndex()).nominalCounts[1] >= this.minDefects){
                takeNewBootstrapSample = false;
            }
            else{
                abortCounter += 1;
                if (abortCounter >= abortLimit){
                    throw new RuntimeException(
                            String.format("error while taking a bootstrap sample of %s: After %d iterations, " +
                                            "no bootstrap sample containing a defect was created.",
                                    trainversion.getVersion(), abortLimit));
                }
                continue;
            }

            List<Double> leftovers = new ArrayList<>();
            for (int i = 0; i < datasetSize; i++) {
                leftovers.add((double) i);
            }
            for (int i = 0; i < datasetSize; i++) {
                leftovers.remove((double) bootstrapSample.get(i));
            }
            testdata.clear();
            if (testBugMatrix != null) {
                testBugMatrix.clear();
            }
            if (testEfforts != null) {
                testEfforts.clear();
            }
            if (testNumBugs != null) {
                testNumBugs.clear();
            }
            for (int i = 0; i < leftovers.size(); i++) {
                int index = leftovers.get(i).intValue();
                testdata.add((Instance) testversioncopy.getInstances().get(index).copy());
                if (testBugMatrix != null) {
                    testBugMatrix.add(((Instance) testversioncopy.getBugMatrix().get(index).copy()));
                }
                if (testEfforts != null) {
                    testEfforts.add(testversioncopy.getEfforts().get(index));
                }
                if (testNumBugs != null) {
                    testNumBugs.add(testversioncopy.getNumBugs().get(index));
                }
            }
        }
    }
}
