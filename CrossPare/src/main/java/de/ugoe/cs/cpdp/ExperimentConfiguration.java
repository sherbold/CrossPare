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

package de.ugoe.cs.cpdp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
import de.ugoe.cs.cpdp.training.ITrainingStrategy;
import de.ugoe.cs.cpdp.versions.IVersionFilter;
import de.ugoe.cs.util.StringTools;
import de.ugoe.cs.util.console.Console;

/**
 * Class that contains all meta information about an experiment, i.e., its configuration. The
 * configuration is loaded from an XML file. <br>
 * <br>
 * In the current implementation, the experiment configuration can only be created using an XML
 * file. Programmatic creation of experiment configurations is currently not possibly.
 * 
 * @author Steffen Herbold
 */
public class ExperimentConfiguration extends DefaultHandler {

    /**
     * handle of the file that contains the configuration
     */
    private final File configFile;

    /**
     * name of the experiment (automatically set to the file name without the .xml ending)
     */
    private String experimentName = "exp";

    /**
     * loads instances
     */
    private List<IVersionLoader> loaders;

    /**
     * path were the results of the experiments are stored
     */
    private String resultsPath = "results";

    /**
     * data set filters applied to all data
     */
    private List<IVersionFilter> versionFilters;

    /**
     * data set filters that decide if a data set is used as test data
     */
    private List<IVersionFilter> testVersionFilters;

    /**
     * data set filters that decide if a data is used as candidate training data
     */
    private List<IVersionFilter> trainingVersionFilters;

    /**
     * setwise data processors that are applied before the setwise data selection
     */
    private List<ISetWiseProcessingStrategy> setwisepreprocessors;

    /**
     * setwise data selection strategies
     */
    private List<ISetWiseDataselectionStrategy> setwiseselectors;

    /**
     * setwise data processors that are applied after the setwise data selection
     */
    private List<ISetWiseProcessingStrategy> setwisepostprocessors;

    /**
     * setwise trainers, i.e., trainers that require the selected training data to be separate from
     * each other
     */
    private List<ISetWiseTrainingStrategy> setwiseTrainers;

    /**
     * setwise testdata aware trainers, i.e., trainers that require the selected training data to be
     * separate from each other and the current testdata
     */
    private List<ISetWiseTestdataAwareTrainingStrategy> setwiseTestdataAwareTrainers;

    /**
     * data processors that are applied before the pointwise data selection
     */
    private List<IProcessesingStrategy> preprocessors;

    /**
     * pointwise data selection strategies
     */
    private List<IPointWiseDataselectionStrategy> pointwiseselectors;

    /**
     * data processors that are applied before the pointwise data selection
     */
    private List<IProcessesingStrategy> postprocessors;

    /**
     * normal trainers, i.e., trainers that require the selected training data in a single data set
     */
    private List<ITrainingStrategy> trainers;

    /**
     * normal trainers, i.e., trainers that require the selected training data in a single data set
     */
    private List<ITestAwareTrainingStrategy> testAwareTrainers;

    /**
     * evaluators used for the the experiment results
     */
    private List<IEvaluationStrategy> evaluators;

    /**
     * result storages used for experiments
     */
    private List<IResultStorage> resultStorages;

    /**
     * indicates, if the classifier should be saved
     */
    private Boolean saveClassifier = null;

    /**
     * number of repetitions of an experiment (to account for randomness)
     */
    private int repetitions = 1;

    /**
     * indicates, which execution strategy to choose (e.g. CrossProjectExperiment,
     * ClassifierCreationExecution). Default is CrossProjectExperiment.
     */
    private String executionStrategy = "CrossProjectExperiment";

    /**
     * parameters to be used by execution strategy (if any)
     */
    private String executionStrategyParameters = null;

    /**
     * Constructor. Creates a new configuration from a given file.
     * 
     * @param filename
     *            name of the file from the configuration is loaded.
     * @throws ExperimentConfigurationException
     *             thrown if there is an error creating the configuration
     */
    public ExperimentConfiguration(String filename) throws ExperimentConfigurationException {
        this(new File(filename));
    }

    /**
     * Constructor. Creates a new configuration from a given file.
     * 
     * @param file
     *            handle of the file from the configuration is loaded.
     * @throws ExperimentConfigurationException
     *             thrown if there is an error creating the configuration
     */
    public ExperimentConfiguration(File file) throws ExperimentConfigurationException {
        this.loaders = new LinkedList<>();
        this.versionFilters = new LinkedList<>();
        this.testVersionFilters = new LinkedList<>();
        this.trainingVersionFilters = new LinkedList<>();
        this.setwisepreprocessors = new LinkedList<>();
        this.setwiseselectors = new LinkedList<>();
        this.setwisepostprocessors = new LinkedList<>();
        this.setwiseTrainers = new LinkedList<>();
        this.setwiseTestdataAwareTrainers = new LinkedList<>();
        this.preprocessors = new LinkedList<>();
        this.pointwiseselectors = new LinkedList<>();
        this.postprocessors = new LinkedList<>();
        this.trainers = new LinkedList<>();
        this.testAwareTrainers = new LinkedList<>();
        this.evaluators = new LinkedList<>();
        this.resultStorages = new LinkedList<>();

        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("file must not be a directory");
        }
        this.configFile = file;

        this.experimentName = file.getName().split("\\.")[0];

        final SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(true);

        SAXParser saxParser = null;
        InputSource inputSource = null;
        try {
            saxParser = spf.newSAXParser();
        }
        catch (ParserConfigurationException | SAXException e) {
            throw new ExperimentConfigurationException(e);
        }

        try( InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8"); ) {
            inputSource = new InputSource(reader);
            inputSource.setSystemId("file://" + file.getAbsolutePath());
            saxParser.parse(inputSource, this);
        }
        catch (UnsupportedEncodingException | FileNotFoundException e) {
            throw new ExperimentConfigurationException("Could not open configuration file.", e);
        }
        catch (SAXException | IOException e) {
            throw new ExperimentConfigurationException("Error parsing configuration.", e);
        }
    }

    /**
     * returns the name of the experiment
     * 
     * @return name of the experiment
     */
    public String getExperimentName() {
        return this.experimentName;
    }

    /**
     * returns the loaders for instances
     * 
     * @return data loaders
     */
    public List<IVersionLoader> getLoaders() {
        return this.loaders;
    }

    /**
     * returns the results path
     * 
     * @return results path
     */
    public String getResultsPath() {
        return this.resultsPath;
    }

    /**
     * returns the data set filters of the experiment
     * 
     * @return data set filters of the experiment
     */
    public List<IVersionFilter> getVersionFilters() {
        return this.versionFilters;
    }

    /**
     * returns the test set filters of the experiment
     * 
     * @return test set filters of the experiment
     */
    public List<IVersionFilter> getTestVersionFilters() {
        return this.testVersionFilters;
    }

    /**
     * returns the candidate training version filters of the experiment
     * 
     * @return candidate training version filters of the experiment
     */
    public List<IVersionFilter> getTrainingVersionFilters() {
        return this.trainingVersionFilters;
    }

    /**
     * returns the setwise processors applied before the setwise data selection
     * 
     * @return setwise processors applied before the setwise data selection
     */
    public List<ISetWiseProcessingStrategy> getSetWisePreprocessors() {
        return this.setwisepreprocessors;
    }

    /**
     * returns the setwise data selection strategies
     * 
     * @return setwise data selection strategies
     */
    public List<ISetWiseDataselectionStrategy> getSetWiseSelectors() {
        return this.setwiseselectors;
    }

    /**
     * returns the setwise processors applied after the setwise data selection
     * 
     * @return setwise processors applied after the setwise data selection
     */
    public List<ISetWiseProcessingStrategy> getSetWisePostprocessors() {
        return this.setwisepostprocessors;
    }

    /**
     * returns the setwise training algorithms
     * 
     * @return setwise training algorithms
     */
    public List<ISetWiseTrainingStrategy> getSetWiseTrainers() {
        return this.setwiseTrainers;
    }

    /**
     * returns the setwise training algorithms
     * 
     * @return setwise training algorithms
     */
    public List<ISetWiseTestdataAwareTrainingStrategy> getSetWiseTestdataAwareTrainers() {
        return this.setwiseTestdataAwareTrainers;
    }

    /**
     * returns the processors applied before the pointwise data selection
     * 
     * @return processors applied before the pointwise data selection
     */
    public List<IProcessesingStrategy> getPreProcessors() {
        return this.preprocessors;
    }

    /**
     * returns the pointwise data selection strategies
     * 
     * @return pointwise data selection strategies
     */
    public List<IPointWiseDataselectionStrategy> getPointWiseSelectors() {
        return this.pointwiseselectors;
    }

    /**
     * returns the processors applied after the pointwise data selection
     * 
     * @return processors applied after the pointwise data selection
     */
    public List<IProcessesingStrategy> getPostProcessors() {
        return this.postprocessors;
    }

    /**
     * returns the normal training algorithm
     * 
     * @return normal training algorithms
     */
    public List<ITrainingStrategy> getTrainers() {
        return this.trainers;
    }

    /**
     * returns the test aware training algorithms
     * 
     * @return normal training algorithms
     */
    public List<ITestAwareTrainingStrategy> getTestAwareTrainers() {
        return this.testAwareTrainers;
    }

    /**
     * returns the evaluation strategies
     * 
     * @return evaluation strategies
     */
    public List<IEvaluationStrategy> getEvaluators() {
        return this.evaluators;
    }

    /**
     * <p>
     * returns the result storages
     * </p>
     *
     * @return result storages
     */
    public List<IResultStorage> getResultStorages() {
        return this.resultStorages;
    }

    /**
     * returns boolean, if classifier should be saved
     * 
     * @return boolean
     */
    public boolean getSaveClassifier() {
        return this.saveClassifier.booleanValue();
    }

    /**
     * number of repetitions of an experiment
     *
     * @return number of repetitions
     */
    public int getRepetitions() {
        return this.repetitions;
    }

    /**
     * returns the execution strategy
     * 
     * @return String execution strategy
     */
    public String getExecutionStrategy() {
        return this.executionStrategy;
    }

    /**
     * returns the parameters of an execution strategy
     * 
     * @return execution strategy paramters
     */
    public String getExecutionStrategyParameters() {
        return this.executionStrategyParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
     * java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException
    {
        try {
            if (qName.equals("config")) {
                // ingore
            }
            else if (qName.equals("loader")) {
                final IVersionLoader loader = (IVersionLoader) Class
                    .forName("de.ugoe.cs.cpdp.loader." + attributes.getValue("name")).newInstance();
                loader.setLocation(attributes.getValue("datalocation"));
                this.loaders.add(loader);

                // TODO location as relative
            }
            else if (qName.equals("resultspath")) {
                this.resultsPath = attributes.getValue("path");
            }
            else if (qName.equals("versionfilter")) {
                final IVersionFilter filter = (IVersionFilter) Class
                    .forName("de.ugoe.cs.cpdp.versions." + attributes.getValue("name"))
                    .newInstance();
                filter.setParameter(attributes.getValue("param"));
                this.versionFilters.add(filter);
            }
            else if (qName.equals("testVersionfilter")) {
                final IVersionFilter filter = (IVersionFilter) Class
                    .forName("de.ugoe.cs.cpdp.versions." + attributes.getValue("name"))
                    .newInstance();
                filter.setParameter(attributes.getValue("param"));
                this.testVersionFilters.add(filter);
            }
            else if (qName.equals("trainVersionfilter")) {
                final IVersionFilter filter = (IVersionFilter) Class
                    .forName("de.ugoe.cs.cpdp.versions." + attributes.getValue("name"))
                    .newInstance();
                filter.setParameter(attributes.getValue("param"));
                this.trainingVersionFilters.add(filter);
            }
            else if (qName.equals("setwisepreprocessor")) {
                final ISetWiseProcessingStrategy processor = (ISetWiseProcessingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.dataprocessing." + attributes.getValue("name"))
                    .newInstance();
                processor.setParameter(attributes.getValue("param"));
                this.setwisepreprocessors.add(processor);
            }
            else if (qName.equals("setwiseselector")) {
                final ISetWiseDataselectionStrategy selection =
                    (ISetWiseDataselectionStrategy) Class
                        .forName("de.ugoe.cs.cpdp.dataselection." + attributes.getValue("name"))
                        .newInstance();
                selection.setParameter(attributes.getValue("param"));
                this.setwiseselectors.add(selection);
            }
            else if (qName.equals("setwisepostprocessor")) {
                final ISetWiseProcessingStrategy processor = (ISetWiseProcessingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.dataprocessing." + attributes.getValue("name"))
                    .newInstance();
                processor.setParameter(attributes.getValue("param"));
                this.setwisepostprocessors.add(processor);
            }
            else if (qName.equals("setwisetrainer")) {
                final ISetWiseTrainingStrategy trainer = (ISetWiseTrainingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.training." + attributes.getValue("name"))
                    .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                this.setwiseTrainers.add(trainer);
            }
            else if (qName.equals("setwisetestdataawaretrainer")) {
                final ISetWiseTestdataAwareTrainingStrategy trainer =
                    (ISetWiseTestdataAwareTrainingStrategy) Class
                        .forName("de.ugoe.cs.cpdp.training." + attributes.getValue("name"))
                        .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                trainer.setMethod(attributes.getValue("method"));
                trainer.setThreshold(attributes.getValue("threshold"));
                this.setwiseTestdataAwareTrainers.add(trainer);
            }
            else if (qName.equals("preprocessor")) {
                final IProcessesingStrategy processor = (IProcessesingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.dataprocessing." + attributes.getValue("name"))
                    .newInstance();
                processor.setParameter(attributes.getValue("param"));
                this.preprocessors.add(processor);
            }
            else if (qName.equals("pointwiseselector")) {
                final IPointWiseDataselectionStrategy selection =
                    (IPointWiseDataselectionStrategy) Class
                        .forName("de.ugoe.cs.cpdp.dataselection." + attributes.getValue("name"))
                        .newInstance();
                selection.setParameter(attributes.getValue("param"));
                this.pointwiseselectors.add(selection);
            }
            else if (qName.equals("postprocessor")) {
                final IProcessesingStrategy processor = (IProcessesingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.dataprocessing." + attributes.getValue("name"))
                    .newInstance();
                processor.setParameter(attributes.getValue("param"));
                this.postprocessors.add(processor);
            }
            else if (qName.equals("trainer")) {
                final ITrainingStrategy trainer = (ITrainingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.training." + attributes.getValue("name"))
                    .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                this.trainers.add(trainer);
            }
            else if (qName.equals("testawaretrainer")) {
                final ITestAwareTrainingStrategy trainer = (ITestAwareTrainingStrategy) Class
                    .forName("de.ugoe.cs.cpdp.training." + attributes.getValue("name"))
                    .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                this.testAwareTrainers.add(trainer);
            }
            else if (qName.equals("eval")) {
                final IEvaluationStrategy evaluator = (IEvaluationStrategy) Class
                    .forName("de.ugoe.cs.cpdp.eval." + attributes.getValue("name")).newInstance();
                this.evaluators.add(evaluator);
            }
            else if (qName.equals("storage")) {
                IResultStorage resultStorage;
                String param = attributes.getValue("param");
                if (param != null && !param.isEmpty()) {
                    // use constructor that takes string parameters
                    resultStorage = (IResultStorage) Class
                        .forName("de.ugoe.cs.cpdp.eval." + attributes.getValue("name"))
                        .getConstructor(String.class).newInstance(param);
                }
                else {
                    // use default contructor
                    resultStorage = (IResultStorage) Class
                        .forName("de.ugoe.cs.cpdp.eval." + attributes.getValue("name"))
                        .newInstance();
                }
                this.resultStorages.add(resultStorage);

                // <storage name="MySQLResultStorage" param="" />
            }
            else if (qName.equals("saveClassifier")) {
                this.saveClassifier = Boolean.TRUE;
            }
            else if (qName.equals("repetitions")) {
                this.repetitions = Integer.parseInt(attributes.getValue("number"));
            }
            else if (qName.equals("executionStrategy")) {
                this.executionStrategy = attributes.getValue("name");
                this.executionStrategyParameters = attributes.getValue("param");
            }
            else if (qName.equals("partialconfig")) {
                String path = attributes.getValue("path");
                try {
                    boolean relative = true;
                    if (attributes.getValue("relative") != null) {
                        relative = Boolean.parseBoolean(attributes.getValue("relative"));
                    }

                    if (relative) {
                        path = this.configFile.getParentFile().getPath() + "/" + path;
                    }
                    addConfigurations(new ExperimentConfiguration(path));
                }
                catch (ExperimentConfigurationException e) {
                    throw new SAXException("Could not load partial configuration: " + path, e);
                }
            }
            else {
                Console.traceln(Level.WARNING, "element in config-file " + this.configFile.getName() +
                    " ignored: " + qName);
            }
        }
        catch (NoClassDefFoundError | ClassNotFoundException | IllegalAccessException
                | InstantiationException | ClassCastException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e)
        {
            throw new SAXException("Could not initialize class correctly", (Exception) e);
        }
    }

    /**
     * Adds the information of another experiment configuration to this configuration. This
     * mechanism allows the usage of partial configuration files. The name of the other
     * configuration is lost. <br>
     * <br>
     * If the current data path is the empty string (&quot;&quot;), it is override by the datapath
     * of the other configuration. Otherwise, the current data path is kept.
     * 
     * @param other
     *            experiment whose information is added
     * @throws ExperimentConfigurationException
     */
    private void addConfigurations(ExperimentConfiguration other)
        throws ExperimentConfigurationException
    {
        if ("results".equals(this.resultsPath)) {
            this.resultsPath = other.resultsPath;
        }
        this.loaders.addAll(other.loaders);
        this.versionFilters.addAll(other.versionFilters);
        this.testVersionFilters.addAll(other.testVersionFilters);
        this.trainingVersionFilters.addAll(other.trainingVersionFilters);
        this.setwisepreprocessors.addAll(other.setwisepreprocessors);
        this.setwiseselectors.addAll(other.setwiseselectors);
        this.setwisepostprocessors.addAll(other.setwisepostprocessors);
        this.setwiseTrainers.addAll(other.setwiseTrainers);
        this.setwiseTestdataAwareTrainers.addAll(other.setwiseTestdataAwareTrainers);
        this.preprocessors.addAll(other.preprocessors);
        this.pointwiseselectors.addAll(other.pointwiseselectors);
        this.postprocessors.addAll(other.postprocessors);
        this.trainers.addAll(other.trainers);
        this.evaluators.addAll(other.evaluators);

        if (!this.executionStrategy.equals(other.executionStrategy)) {
            throw new ExperimentConfigurationException("Executionstrategies must be the same, if config files should be added.");
        }

        /*
         * Only if saveClassifier is not set in the main config and the other configs saveClassifier
         * is true, it must be set.
         */
        if (this.saveClassifier == null && other.saveClassifier == Boolean.TRUE) {
            this.saveClassifier = other.saveClassifier;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Experiment name: " + this.experimentName + StringTools.ENDLINE);
        builder.append("Loaders: " + this.loaders + StringTools.ENDLINE);
        builder.append("Results path: " + this.resultsPath + StringTools.ENDLINE);
        builder.append("Version filters: " + this.versionFilters.toString() + StringTools.ENDLINE);
        builder
            .append("Test version filters: " + this.testVersionFilters.toString() + StringTools.ENDLINE);
        builder.append("Training version filters: " + this.trainingVersionFilters.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise preprocessors: " + this.setwisepreprocessors.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise selectors: " + this.setwiseselectors.toString() + StringTools.ENDLINE);
        builder.append("Setwise postprocessors: " + this.setwisepostprocessors.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise trainers: " + this.setwiseTrainers.toString() + StringTools.ENDLINE);
        builder.append("Setwise Testdata Aware trainers: " +
            this.setwiseTestdataAwareTrainers.toString() + StringTools.ENDLINE);
        builder
            .append("Pointwise preprocessors: " + this.preprocessors.toString() + StringTools.ENDLINE);
        builder
            .append("Pointwise selectors: " + this.pointwiseselectors.toString() + StringTools.ENDLINE);
        builder
            .append("Pointwise postprocessors: " + this.postprocessors.toString() + StringTools.ENDLINE);
        builder.append("Pointwise trainers: " + this.trainers.toString() + StringTools.ENDLINE);
        builder.append("Evaluators: " + this.evaluators.toString() + StringTools.ENDLINE);
        builder.append("Save Classifier?: " + this.saveClassifier + StringTools.ENDLINE);
        builder.append("Execution Strategy: " + this.executionStrategy + StringTools.ENDLINE);

        return builder.toString();
    }
}
