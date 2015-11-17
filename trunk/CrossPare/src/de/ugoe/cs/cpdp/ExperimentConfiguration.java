// Copyright 2015 Georg-August-Universit�t G�ttingen, Germany
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
import de.ugoe.cs.cpdp.loader.IVersionLoader;
import de.ugoe.cs.cpdp.training.ISetWiseTestdataAwareTrainingStrategy;
import de.ugoe.cs.cpdp.training.ISetWiseTrainingStrategy;
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
     * setwise testdata aware trainers, i.e., trainers that require the selected training data to be separate from
     * each other and the current testdata 
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
     * evaluators used for the the experiment results
     */
    private List<IEvaluationStrategy> evaluators;

    /**
     * indicates, if the classifier should be saved
     */
    private Boolean saveClassifier = null;

    /**
     * indicates, which execution strategy to choose (e.g. CrossProjectExperiment,
     * ClassifierCreationExecution). Default is CrossProjectExperiment.
     */
    private String executionStrategy = "CrossProjectExperiment";

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
     * @param filename
     *            handle of the file from the configuration is loaded.
     * @throws ExperimentConfigurationException
     *             thrown if there is an error creating the configuration
     */
    public ExperimentConfiguration(File file) throws ExperimentConfigurationException {
        loaders = new LinkedList<>();
        versionFilters = new LinkedList<>();
        testVersionFilters = new LinkedList<>();
        trainingVersionFilters = new LinkedList<>();
        setwisepreprocessors = new LinkedList<>();
        setwiseselectors = new LinkedList<>();
        setwisepostprocessors = new LinkedList<>();
        setwiseTrainers = new LinkedList<>();
        setwiseTestdataAwareTrainers = new LinkedList<>();
        preprocessors = new LinkedList<>();
        pointwiseselectors = new LinkedList<>();
        postprocessors = new LinkedList<>();
        trainers = new LinkedList<>();
        evaluators = new LinkedList<>();

        if (file == null) {
            throw new IllegalArgumentException("file must not be null");
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("file must not be a directory");
        }
        configFile = file;

        experimentName = file.getName().split("\\.")[0];

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

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            inputSource = new InputSource(reader);
        }
        catch (UnsupportedEncodingException | FileNotFoundException e) {
            throw new ExperimentConfigurationException("Could not open configuration file.", e);
        }

        if (inputSource != null) {
            inputSource.setSystemId("file://" + file.getAbsolutePath());
            try {
                saxParser.parse(inputSource, this);
            }
            catch (SAXException | IOException e) {
                throw new ExperimentConfigurationException("Error parsing configuration.", e);
            }
        }
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException e) {
                throw new ExperimentConfigurationException("Error closing reader.", e);
            }
        }
    }

    /**
     * returns the name of the experiment
     * 
     * @return name of the experiment
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * returns the loaders for instances
     * 
     * @return data loaders
     */
    public List<IVersionLoader> getLoaders() {
        return loaders;
    }

    /**
     * returns the results path
     * 
     * @return results path
     */
    public String getResultsPath() {
        return resultsPath;
    }

    /**
     * returns the data set filters of the experiment
     * 
     * @return data set filters of the experiment
     */
    public List<IVersionFilter> getVersionFilters() {
        return versionFilters;
    }

    /**
     * returns the test set filters of the experiment
     * 
     * @return test set filters of the experiment
     */
    public List<IVersionFilter> getTestVersionFilters() {
        return testVersionFilters;
    }

    /**
     * returns the candidate training version filters of the experiment
     * 
     * @return candidate training version filters of the experiment
     */
    public List<IVersionFilter> getTrainingVersionFilters() {
        return trainingVersionFilters;
    }

    /**
     * returns the setwise processors applied before the setwise data selection
     * 
     * @return setwise processors applied before the setwise data selection
     */
    public List<ISetWiseProcessingStrategy> getSetWisePreprocessors() {
        return setwisepreprocessors;
    }

    /**
     * returns the setwise data selection strategies
     * 
     * @return setwise data selection strategies
     */
    public List<ISetWiseDataselectionStrategy> getSetWiseSelectors() {
        return setwiseselectors;
    }

    /**
     * returns the setwise processors applied after the setwise data selection
     * 
     * @return setwise processors applied after the setwise data selection
     */
    public List<ISetWiseProcessingStrategy> getSetWisePostprocessors() {
        return setwisepostprocessors;
    }

    /**
     * returns the setwise training algorithms
     * 
     * @return setwise training algorithms
     */
    public List<ISetWiseTrainingStrategy> getSetWiseTrainers() {
        return setwiseTrainers;
    }

    /**
     * returns the setwise training algorithms
     * 
     * @return setwise training algorithms
     */
    public List<ISetWiseTestdataAwareTrainingStrategy> getSetWiseTestdataAwareTrainers() {
        return setwiseTestdataAwareTrainers;
    }
    
    /**
     * returns the processors applied before the pointwise data selection
     * 
     * @return processors applied before the pointwise data selection
     */
    public List<IProcessesingStrategy> getPreProcessors() {
        return preprocessors;
    }

    /**
     * returns the pointwise data selection strategies
     * 
     * @return pointwise data selection strategies
     */
    public List<IPointWiseDataselectionStrategy> getPointWiseSelectors() {
        return pointwiseselectors;
    }

    /**
     * returns the processors applied after the pointwise data selection
     * 
     * @return processors applied after the pointwise data selection
     */
    public List<IProcessesingStrategy> getPostProcessors() {
        return postprocessors;
    }

    /**
     * returns the normal training algorithm
     * 
     * @return normal training algorithms
     */
    public List<ITrainingStrategy> getTrainers() {
        return trainers;
    }

    /**
     * returns the evaluation strategies
     * 
     * @return evaluation strategies
     */
    public List<IEvaluationStrategy> getEvaluators() {
        return evaluators;
    }

    /**
     * returns boolean, if classifier should be saved
     * 
     * @return boolean
     */
    public boolean getSaveClassifier() {
        return saveClassifier;
    }

    /**
     * returns the execution strategy
     * 
     * @return String execution strategy
     */
    public String getExecutionStrategy() {
        return executionStrategy;
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
                final IVersionLoader loader =
                    (IVersionLoader) Class.forName("de.ugoe.cs.cpdp.loader." +
                                                       attributes.getValue("name")).newInstance();
                loader.setLocation(attributes.getValue("datalocation"));
                loaders.add(loader);

                // TODO location as relative
            }
            else if (qName.equals("resultspath")) {
                resultsPath = attributes.getValue("path");
            }
            else if (qName.equals("versionfilter")) {
                final IVersionFilter filter =
                    (IVersionFilter) Class.forName("de.ugoe.cs.cpdp.versions." +
                                                       attributes.getValue("name")).newInstance();
                filter.setParameter(attributes.getValue("param"));
                versionFilters.add(filter);
            }
            else if (qName.equals("testVersionfilter")) {
                final IVersionFilter filter =
                    (IVersionFilter) Class.forName("de.ugoe.cs.cpdp.versions." +
                                                       attributes.getValue("name")).newInstance();
                filter.setParameter(attributes.getValue("param"));
                testVersionFilters.add(filter);
            }
            else if (qName.equals("trainVersionfilter")) {
                final IVersionFilter filter =
                    (IVersionFilter) Class.forName("de.ugoe.cs.cpdp.versions." +
                                                       attributes.getValue("name")).newInstance();
                filter.setParameter(attributes.getValue("param"));
                trainingVersionFilters.add(filter);
            }
            else if (qName.equals("setwisepreprocessor")) {
                final ISetWiseProcessingStrategy processor =
                    (ISetWiseProcessingStrategy) Class.forName("de.ugoe.cs.cpdp.dataprocessing." +
                                                                   attributes.getValue("name"))
                        .newInstance();
                processor.setParameter(attributes.getValue("param"));
                setwisepreprocessors.add(processor);
            }
            else if (qName.equals("setwiseselector")) {
                final ISetWiseDataselectionStrategy selection =
                    (ISetWiseDataselectionStrategy) Class.forName("de.ugoe.cs.cpdp.dataselection." +
                                                                      attributes.getValue("name"))
                        .newInstance();
                selection.setParameter(attributes.getValue("param"));
                setwiseselectors.add(selection);
            }
            else if (qName.equals("setwisepostprocessor")) {
                final ISetWiseProcessingStrategy processor =
                    (ISetWiseProcessingStrategy) Class.forName("de.ugoe.cs.cpdp.dataprocessing." +
                                                                   attributes.getValue("name"))
                        .newInstance();
                processor.setParameter(attributes.getValue("param"));
                setwisepostprocessors.add(processor);
            }
            else if (qName.equals("setwisetrainer")) {
                final ISetWiseTrainingStrategy trainer =
                    (ISetWiseTrainingStrategy) Class.forName("de.ugoe.cs.cpdp.training." +
                                                                 attributes.getValue("name"))
                        .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                setwiseTrainers.add(trainer);
            }
            else if (qName.equals("setwisetestdataawaretrainer")) {
                final ISetWiseTestdataAwareTrainingStrategy trainer =
                    (ISetWiseTestdataAwareTrainingStrategy) Class.forName("de.ugoe.cs.cpdp.training." +
                                                                 attributes.getValue("name"))
                        .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                setwiseTestdataAwareTrainers.add(trainer);
            }
            else if (qName.equals("preprocessor")) {
                final IProcessesingStrategy processor =
                    (IProcessesingStrategy) Class.forName("de.ugoe.cs.cpdp.dataprocessing." +
                                                              attributes.getValue("name"))
                        .newInstance();
                processor.setParameter(attributes.getValue("param"));
                preprocessors.add(processor);
            }
            else if (qName.equals("pointwiseselector")) {
                final IPointWiseDataselectionStrategy selection =
                    (IPointWiseDataselectionStrategy) Class
                        .forName("de.ugoe.cs.cpdp.dataselection." + attributes.getValue("name"))
                        .newInstance();
                selection.setParameter(attributes.getValue("param"));
                pointwiseselectors.add(selection);
            }
            else if (qName.equals("postprocessor")) {
                final IProcessesingStrategy processor =
                    (IProcessesingStrategy) Class.forName("de.ugoe.cs.cpdp.dataprocessing." +
                                                              attributes.getValue("name"))
                        .newInstance();
                processor.setParameter(attributes.getValue("param"));
                postprocessors.add(processor);
            }
            else if (qName.equals("trainer")) {
                final ITrainingStrategy trainer =
                    (ITrainingStrategy) Class.forName("de.ugoe.cs.cpdp.training." +
                                                          attributes.getValue("name"))
                        .newInstance();
                trainer.setParameter(attributes.getValue("param"));
                trainers.add(trainer);
            }
            else if (qName.equals("eval")) {
                final IEvaluationStrategy evaluator =
                    (IEvaluationStrategy) Class.forName("de.ugoe.cs.cpdp.eval." +
                                                            attributes.getValue("name"))
                        .newInstance();
                evaluators.add(evaluator);
            }
            else if (qName.equals("saveClassifier")) {
                saveClassifier = true;
            }
            else if (qName.equals("executionStrategy")) {
                executionStrategy = attributes.getValue("name");
            }
            else if (qName.equals("partialconfig")) {
                String path = attributes.getValue("path");
                try {
                    boolean relative = true;
                    if (attributes.getValue("relative") != null) {
                        relative = Boolean.parseBoolean(attributes.getValue("relative"));
                    }

                    if (relative) {
                        path = configFile.getParentFile().getPath() + "/" + path;
                    }
                    addConfigurations(new ExperimentConfiguration(path));
                }
                catch (ExperimentConfigurationException e) {
                    throw new SAXException("Could not load partial configuration: " + path, e);
                }
            }
            else {
                Console.traceln(Level.WARNING, "element in config-file " + configFile.getName() +
                    " ignored: " + qName);
            }
        }
        catch (NoClassDefFoundError | ClassNotFoundException | IllegalAccessException
                | InstantiationException | ClassCastException e)
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
        if ("results".equals(resultsPath)) {
            resultsPath = other.resultsPath;
        }
        loaders.addAll(other.loaders);
        versionFilters.addAll(other.versionFilters);
        testVersionFilters.addAll(other.testVersionFilters);
        trainingVersionFilters.addAll(other.trainingVersionFilters);
        setwisepreprocessors.addAll(other.setwisepreprocessors);
        setwiseselectors.addAll(other.setwiseselectors);
        setwisepostprocessors.addAll(other.setwisepostprocessors);
        setwiseTrainers.addAll(other.setwiseTrainers);
        setwiseTestdataAwareTrainers.addAll(other.setwiseTestdataAwareTrainers);
        preprocessors.addAll(other.preprocessors);
        pointwiseselectors.addAll(other.pointwiseselectors);
        postprocessors.addAll(other.postprocessors);
        trainers.addAll(other.trainers);
        evaluators.addAll(other.evaluators);

        if (!executionStrategy.equals(other.executionStrategy)) {
            throw new ExperimentConfigurationException(
                                                       "Executionstrategies must be the same, if config files should be added.");
        }

        /*
         * Only if saveClassifier is not set in the main config and the other configs saveClassifier
         * is true, it must be set.
         */
        if (saveClassifier == null && other.saveClassifier == true) {
            saveClassifier = other.saveClassifier;
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
        builder.append("Experiment name: " + experimentName + StringTools.ENDLINE);
        builder.append("Loaders: " + loaders + StringTools.ENDLINE);
        builder.append("Results path: " + resultsPath + StringTools.ENDLINE);
        builder.append("Version filters: " + versionFilters.toString() + StringTools.ENDLINE);
        builder.append("Test version filters: " + testVersionFilters.toString() +
            StringTools.ENDLINE);
        builder.append("Training version filters: " + trainingVersionFilters.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise preprocessors: " + setwisepreprocessors.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise selectors: " + setwiseselectors.toString() + StringTools.ENDLINE);
        builder.append("Setwise postprocessors: " + setwisepostprocessors.toString() +
            StringTools.ENDLINE);
        builder.append("Setwise trainers: " + setwiseTrainers.toString() + StringTools.ENDLINE);
        builder.append("Setwise Testdata Aware trainers: " + setwiseTestdataAwareTrainers.toString() + StringTools.ENDLINE);
        builder
            .append("Pointwise preprocessors: " + preprocessors.toString() + StringTools.ENDLINE);
        builder.append("Pointwise selectors: " + pointwiseselectors.toString() +
            StringTools.ENDLINE);
        builder.append("Pointwise postprocessors: " + postprocessors.toString() +
            StringTools.ENDLINE);
        builder.append("Pointwise trainers: " + trainers.toString() + StringTools.ENDLINE);
        builder.append("Evaluators: " + evaluators.toString() + StringTools.ENDLINE);
        builder.append("Save Classifier?: " + saveClassifier + StringTools.ENDLINE);
        builder.append("Execution Strategy: " + executionStrategy + StringTools.ENDLINE);

        return builder.toString();
    }
}
