
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
 * <p>
 * Implements a execuction strategy for heterogeneous defect prediction.
 * </p>
 * 
 * @author Alexander Trautsch
 */
public class HeterogeneousExperiment implements IExecutionStrategy {

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
    public HeterogeneousExperiment(ExperimentConfiguration config) {
        this.config = config;
    }

    /**
     * DUBLICATE FROM AbstractCrossProjectExperiment
     */
    private boolean isVersion(SoftwareVersion version, List<IVersionFilter> filters) {
        boolean result = true;
        for (IVersionFilter filter : filters) {
            result &= !filter.apply(version);
        }
        return result;
    }

    /**
     * DUBLICATE FROM AbstractCrossProjectExperiment
     * 
     * @param traindataSet
     *            {@link AbstractCrossProjectExperiment#makeSingleTrainingSet(SetUniqueList)}
     * @return {@link AbstractCrossProjectExperiment#makeSingleTrainingSet(SetUniqueList)}
     * @see AbstractCrossProjectExperiment#makeSingleTrainingSet(SetUniqueList)
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
    protected boolean isTrainingVersion(SoftwareVersion trainingVersion,
                                        SoftwareVersion testVersion)
    {
        if (testVersion.getDataset().equals(trainingVersion.getDataset())) {
            return false;
        }

        return true;
    }

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

        // changed
        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {
                for (SoftwareVersion trainingVersion : versions) {
                    if (isVersion(trainingVersion, config.getTrainingVersionFilters())) {
                        testVersionCount++;
                    }
                }
            }
        }

        // sort versions
        Collections.sort(versions);

        // todo: test version check problematic
        //
        for (SoftwareVersion testVersion : versions) {
            if (isVersion(testVersion, config.getTestVersionFilters())) {

                // now iterate trainVersions
                for (SoftwareVersion trainingVersion : versions) {
                    if (isVersion(trainingVersion, config.getTrainingVersionFilters())) {
                        if (trainingVersion != testVersion) {
                            if (isTrainingVersion(trainingVersion, testVersion)) { // checks if they
                                                                                   // are the same
                                                                                   // dataset

                                Console.traceln(Level.INFO,
                                                String.format("[%s] [%02d/%02d] %s:%s starting",
                                                              config.getExperimentName(),
                                                              versionCount, testVersionCount,
                                                              testVersion.getVersion(),
                                                              trainingVersion.getVersion()));
                                int numResultsAvailable =
                                    resultsAvailable(testVersion, trainingVersion);
                                if (numResultsAvailable >= config.getRepetitions()) {
                                    Console.traceln(Level.INFO,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s:%s results already available; skipped",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  trainingVersion.getVersion()));
                                    versionCount++;
                                    continue;
                                }

                                // Setup testdata and training data
                                Instances testdata = testVersion.getInstances();
                                List<Double> efforts = testVersion.getEfforts();
                                Instances traindata = trainingVersion.getInstances();

                                // only one set
                                SetUniqueList<Instances> traindataSet =
                                    SetUniqueList.setUniqueList(new LinkedList<Instances>());
                                traindataSet.add(traindata);

                                for (ISetWiseProcessingStrategy processor : config
                                    .getSetWisePreprocessors())
                                {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s:%s applying setwise preprocessor %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  trainingVersion.getVersion(),
                                                                  processor.getClass().getName()));
                                    processor.apply(testdata, traindataSet);
                                }
                                for (ISetWiseDataselectionStrategy dataselector : config
                                    .getSetWiseSelectors())
                                {
                                    Console.traceln(Level.FINE, String
                                        .format("[%s] [%02d/%02d] %s: applying setwise selection %s",
                                                config.getExperimentName(), versionCount,
                                                testVersionCount, testVersion.getVersion(),
                                                dataselector.getClass().getName()));
                                    dataselector.apply(testdata, traindataSet);
                                }
                                for (ISetWiseProcessingStrategy processor : config
                                    .getSetWisePostprocessors())
                                {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  processor.getClass().getName()));
                                    processor.apply(testdata, traindataSet);
                                }
                                for (ISetWiseTrainingStrategy setwiseTrainer : config
                                    .getSetWiseTrainers())
                                {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying setwise trainer %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  setwiseTrainer.getName()));
                                    setwiseTrainer.apply(traindataSet);
                                }
                                for (ISetWiseTestdataAwareTrainingStrategy setwiseTestdataAwareTrainer : config
                                    .getSetWiseTestdataAwareTrainers())
                                {
                                    Console.traceln(Level.FINE, String
                                        .format("[%s] [%02d/%02d] %s:%s applying testdata aware setwise trainer %s",
                                                config.getExperimentName(), versionCount,
                                                testVersionCount, testVersion.getVersion(),
                                                trainingVersion.getVersion(),
                                                setwiseTestdataAwareTrainer.getName()));
                                    setwiseTestdataAwareTrainer.apply(traindataSet, testdata);
                                }

                                // this part will not work in heterogeneous
                                // Instances traindata = makeSingleTrainingSet(traindataSet);
                                for (IProcessesingStrategy processor : config.getPreProcessors()) {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying preprocessor %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  processor.getClass().getName()));
                                    processor.apply(testdata, traindata);
                                }
                                for (IPointWiseDataselectionStrategy dataselector : config
                                    .getPointWiseSelectors())
                                {
                                    Console.traceln(Level.FINE, String
                                        .format("[%s] [%02d/%02d] %s: applying pointwise selection %s",
                                                config.getExperimentName(), versionCount,
                                                testVersionCount, testVersion.getVersion(),
                                                dataselector.getClass().getName()));
                                    traindata = dataselector.apply(testdata, traindata);
                                }
                                for (IProcessesingStrategy processor : config.getPostProcessors()) {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying setwise postprocessor %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  processor.getClass().getName()));
                                    processor.apply(testdata, traindata);
                                }
                                for (ITrainingStrategy trainer : config.getTrainers()) {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying trainer %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  trainer.getName()));
                                    trainer.apply(traindata);
                                }
                                for (ITestAwareTrainingStrategy trainer : config
                                    .getTestAwareTrainers())
                                {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s: applying trainer %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  trainer.getName()));
                                    trainer.apply(testdata, traindata);
                                }
                                File resultsDir = new File(config.getResultsPath());
                                if (!resultsDir.exists()) {
                                    resultsDir.mkdir();
                                }
                                for (IEvaluationStrategy evaluator : config.getEvaluators()) {
                                    Console.traceln(Level.FINE,
                                                    String.format(
                                                                  "[%s] [%02d/%02d] %s:%s applying evaluator %s",
                                                                  config.getExperimentName(),
                                                                  versionCount, testVersionCount,
                                                                  testVersion.getVersion(),
                                                                  trainingVersion.getVersion(),
                                                                  evaluator.getClass().getName()));
                                    List<ITrainer> allTrainers = new LinkedList<>();
                                    for (ISetWiseTrainingStrategy setwiseTrainer : config
                                        .getSetWiseTrainers())
                                    {
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
                                    for (ITestAwareTrainingStrategy trainer : config
                                        .getTestAwareTrainers())
                                    {
                                        allTrainers.add(trainer);
                                    }
                                    if (writeHeader) {
                                        evaluator.setParameter(config.getResultsPath() + "/" +
                                            config.getExperimentName() + ".csv");
                                    }
                                    evaluator.apply(testdata, traindata, allTrainers, efforts,
                                                    writeHeader, config.getResultStorages());
                                    writeHeader = false;
                                }
                                Console.traceln(Level.INFO,
                                                String.format("[%s] [%02d/%02d] %s: finished",
                                                              config.getExperimentName(),
                                                              versionCount, testVersionCount,
                                                              testVersion.getVersion()));
                                versionCount++;

                            }
                        }
                    }
                }

            } /* end if check training */
        } /* end for iteration test version */
    }

    /**
     * DUBLICATE FROM AbstractCrossProjectExperiment
     */
    private int resultsAvailable(SoftwareVersion version, SoftwareVersion trainVersion) {
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
            int curAvailable = storage
                .containsHeterogeneousResult(config.getExperimentName(), version.getVersion(),
                                             classifierName, trainVersion.getVersion());
            if (curAvailable < available) {
                available = curAvailable;
            }
        }
        return available;
    }
}
