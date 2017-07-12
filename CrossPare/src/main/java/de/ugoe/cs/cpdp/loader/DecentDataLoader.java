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

package de.ugoe.cs.cpdp.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

import de.ugoe.cs.cpdp.decentApp.models.arffx.Instance;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Model;
import de.ugoe.cs.cpdp.decentApp.models.arffx.Value;
import de.ugoe.cs.cpdp.decentApp.ARFFxResourceTool;
import de.ugoe.cs.cpdp.decentApp.DECENTEpsilonModelHandler;
import de.ugoe.cs.util.console.Console;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/**
 * Class for loading a decent model file. Loads a decent model file and (if no arff file is present)
 * and does the following conversions: DECENT -> ARFFX -> ARFF
 * 
 * @author Fabian Trautsch
 * 
 */
public class DecentDataLoader implements SingleVersionLoader {

    // Model Handler for Decent Models
    private DECENTEpsilonModelHandler modelHandler = new DECENTEpsilonModelHandler();

    // Set log level
    String logLevel = "1";
    String logToFile = "false";

    // This list contains attributes, that should be removed before building the arff file
    private static List<String> attributeFilter = new LinkedList<>();

    // This list contains all names of the different artifacts
    private static Set<String> artifactNames = new LinkedHashSet<>();

    // Name of the class attribute.
    private static final String classAttributeName = "LABEL.Artifact.Target.BugFix.AverageWeight";

    private static int getIndexOfArtifactName(String artifactName) {
        int index = -1;
        if (artifactNames.contains(artifactName)) {
            int i = 0;
            for (String nameInSet : artifactNames) {
                if (nameInSet.equals(artifactName)) {
                    index = i;
                }
                else {
                    i++;
                }
            }
        }

        return index;
    }

    /**
     * Defines attributes, that should be removed before building the ARFF File from.
     */
    private static void setAttributeFilter() {
        attributeFilter.add("Agent.Name");

    }

    /**
     * Saves the dataset as arff after transformation (decent->arffx) and filtering
     * 
     * @param dataSet
     *            the WEKA dataset to save
     * @param arffLocation
     *            location where it should be saved to
     */
    public static void save(Instances dataSet, String arffLocation) {

        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataSet);
        try {
            saver.setFile(new File(arffLocation));
            saver.writeBatch();
        }
        catch (IOException e) {
            Console.printerrln("Cannot save the file to path: " + arffLocation);
            e.printStackTrace();
        }
    }

    /**
     * Loads the given decent file and tranform it from decent->arffx->arff
     * 
     * @return Instances in WEKA format
     */
    @Override
    public Instances load(File file, boolean binaryClass) {
        if(!binaryClass) {
            // TODO implement regression loading
            throw new RuntimeException("regrssion loading not yet supported for DecentDataLoader");
        }
        
        // Set attributeFilter
        setAttributeFilter();

        // Register MetaModels
        try {
            registerMetaModels();
        }
        catch (Exception e1) {
            Console.printerrln("Metamodels cannot be registered!");
            e1.printStackTrace();
        }

        // Set location of decent and arffx Model
        String decentModelLocation = file.getAbsolutePath();
        String pathToDecentModelFolder =
            decentModelLocation.substring(0, decentModelLocation.lastIndexOf(File.separator));
        String arffxModelLocation = pathToDecentModelFolder + "/model.arffx";
        String logModelLocation = pathToDecentModelFolder + "/model.log";
        String arffLocation = pathToDecentModelFolder + "/model.arff";

        // If arff File exists, load from it!
        if (new File(arffLocation).exists()) {
            System.out.println("Loading arff File...");
            Instances data = null;
            try(BufferedReader reader = new BufferedReader(new FileReader(arffLocation));) {
                data = new Instances(reader);
            }
            catch (FileNotFoundException e) {
                Console.printerrln("File with path: " + arffLocation + " was not found.");
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                Console.printerrln("File with path: " + arffLocation + " cannot be read.");
                throw new RuntimeException(e);
            }

            // Set class attribute if not set
            if (data.classIndex() == -1) {
                Attribute classAttribute = data.attribute(classAttributeName);
                data.setClass(classAttribute);
            }

            return data;
        }

        // Location of EOL Scripts
        String preprocess = "./decent/epsilon/query/preprocess.eol";
        String arffxToArffSource = "./decent/epsilon/query/addLabels.eol";

        // Set Log Properties
        System.setProperty("epsilon.logLevel", this.logLevel);
        System.setProperty("epsilon.logToFile", this.logToFile);
        System.setProperty("epsilon.logFileAvailable", "false");

        // Set decent2arffx Properties
        System.setProperty("epsilon.transformation.decent2arffx.skipSource", "false");
        System.setProperty("epsilon.transformation.decent2arffx.type", "code");

        // Preprocess Data, transform from decent2arffx
        try {
            IEolExecutableModule preProcessModule = loadModule(preprocess);
            IModel preProcessDecentModel =
                this.modelHandler.getDECENTModel(decentModelLocation, true, true);
            IModel preProcessArffxarffxModel =
                this.modelHandler.getARFFxModel(arffxModelLocation, false, true);
            preProcessModule.getContext().getModelRepository().addModel(preProcessDecentModel);
            preProcessModule.getContext().getModelRepository().addModel(preProcessArffxarffxModel);
            execute(preProcessModule, logModelLocation);
            preProcessDecentModel.dispose();
            preProcessArffxarffxModel.dispose();
            preProcessModule.reset();
        }
        catch (URISyntaxException e) {
            Console.printerrln("URI Syntax for decent or arffx model is wrong.");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Transform to arff, for label and confidence attributes
        try {
            IEolExecutableModule arffxToArffModule = loadModule(arffxToArffSource);
            IModel arffxToArffArffxModel =
                this.modelHandler.getARFFxModel(arffxModelLocation, true, true);
            arffxToArffModule.getContext().getModelRepository().addModel(arffxToArffArffxModel);
            execute(arffxToArffModule, logModelLocation);
            arffxToArffArffxModel.dispose();
            // can be stored and retained alternatively
            arffxToArffModule.reset();
        }
        catch (URISyntaxException e) {
            Console.printerrln("URI Syntax for arffx model is wrong.");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Unregister MetaModels, otherwise cast will fail
        HashMap<String, Object> metaModelCache = new HashMap<>();
        for (String key : EPackage.Registry.INSTANCE.keySet()) {
            metaModelCache.put(key, EPackage.Registry.INSTANCE.get(key));
        }

        for (String key : metaModelCache.keySet()) {
            EPackage.Registry.INSTANCE.remove(key);
        }

        // Workaround to gernerate a usable URI. Absolute path is not
        // possible, therefore we need to construct a relative path

        URL location = DecentDataLoader.class.getProtectionDomain().getCodeSource().getLocation();
        String basePath = location.getFile();

        // Location is the bin folder, so we need to delete the last 4 characters
        basePath = basePath.substring(0, basePath.length() - 4);
        String relativePath =
            new File(basePath).toURI().relativize(new File(arffxModelLocation).toURI()).getPath();

        // Loard arffx file and create WEKA Instances
        ARFFxResourceTool tool = new ARFFxResourceTool();
        Resource resource = tool.loadResourceFromXMI(relativePath, "arffx");

        Instances dataSet = null;
        for (EObject o : resource.getContents()) {
            Model m = (Model) o;
            dataSet = createWekaDataFormat(m);

            for (Instance i : m.getData()) {
                createWekaInstance(dataSet, i);
            }
        }
        if( dataSet == null ) {
            throw new RuntimeException("Could not load EMF model");
        }

        // Set class attribute
        Attribute classAttribute = dataSet.attribute(classAttributeName);
        dataSet.setClass(classAttribute);

        // Save as ARFF
        save(dataSet, arffLocation);

        return dataSet;

    }

    /**
     * Creates a WekaInstance from an ARFFX Model Instance
     * 
     * @param dataSet
     *            WekaInstance dataset, where the arffx model instances should be added to
     * @param i
     *            arffx model instance
     */
    private static void createWekaInstance(Instances dataSet, Instance i) {
        double[] values = new double[dataSet.numAttributes()];
        int j = 0;

        for (Value value : i.getValues()) {
            String dataValue = value.getContent();
            String attributeName = value.getOfAttribute().getName();

            if (attributeFilter.contains(attributeName)) {
                continue;
            }

            // Is value a LABEL.* attribute?
            if (isLabel(attributeName)) {
                values[j] = dataSet.attribute(j).indexOfValue(dataValue);
            }
            else if (isConfidenceLabel(attributeName)) {
                // Is value a CONFIDENCE.* attribute?
                values[j] = dataSet.attribute(j).indexOfValue(dataValue);
            }
            else if (attributeName.equals("Artifact.Name")) {
                // Is it the name of the artifact?
                artifactNames.add(dataValue);
                values[j] = getIndexOfArtifactName(dataValue);
            }
            else {
                // Is it a numeric value?
                values[j] = Double.parseDouble(dataValue);
            }

            j++;
        }

        DenseInstance inst = new DenseInstance(1.0, values);
        dataSet.add(inst);
    }

    /**
     * Creates a Weka Instances set out of a arffx model
     * 
     * @param m
     *            arffx model
     * @return
     */
    private static Instances createWekaDataFormat(Model m) {

        // Bad solution, can be enhanced (continue in for loop)
        ArrayList<Attribute> datasetAttributes = new ArrayList<>();
        for (de.ugoe.cs.cpdp.decentApp.models.arffx.Attribute attribute : m.getAttributes()) {
            String attributeName = attribute.getName();

            if (attributeFilter.contains(attributeName)) {
                continue;
            }

            Attribute wekaAttr;

            // Is attribute a LABEL.* attribute?
            if (isLabel(attributeName)) {
                // Classattribute
                final ArrayList<String> classAttVals = new ArrayList<>();
                classAttVals.add("false");
                classAttVals.add("true");
                wekaAttr = new Attribute(attributeName, classAttVals);
            }
            else if (isConfidenceLabel(attributeName)) {
                // Is attribute a CONFIDENCE.* attribute?
                ArrayList<String> labels = new ArrayList<>();
                labels.add("high");
                labels.add("low");
                wekaAttr = new Attribute(attributeName, labels);
            }
            else {
                // Is it a numeric attribute?
                wekaAttr = new Attribute(attributeName);
            }

            datasetAttributes.add(wekaAttr);
        }

        return new Instances("test-dataset", datasetAttributes, 0);
    }

    /**
     * Helper methods which indicates if the given value starts with "LABEL"
     * 
     * @param value
     *            to test
     * @return
     */
    private static boolean isLabel(String value) {
        if (value.length() >= 5 && value.substring(0, 5).equals("LABEL")) {
            return true;
        }

        return false;
    }

    /**
     * Helper method which indicates if the given value starts with "CONFIDENCE"
     * 
     * @param value
     *            to test
     * @return
     */
    private static boolean isConfidenceLabel(String value) {
        if (value.length() >= 10 && value.substring(0, 10).equals("CONFIDENCE")) {
            return true;
        }

        return false;
    }

    /**
     * Returns if a filename ends with ".decent"
     * 
     * @return true if a .decent file
     */
    @Override
    public boolean filenameFilter(String filename) {
        return filename.endsWith(".decent");
    }

    /**
     * Helper method for executing a eol scripts and adding the log model beforehand
     * 
     * @param module
     *            module to execute
     * @param logModelLocation
     *            location of the log model
     * @throws Exception
     */
    private static void execute(IEolExecutableModule module, String logModelLocation) throws Exception {
        IModel logModel = DECENTEpsilonModelHandler.getLOGModel(logModelLocation, true, true);
        module.getContext().getModelRepository().addModel(logModel);
        module.execute();
        logModel.dispose();
    }

    /**
     * Loads the module from a given source
     * 
     * @param source
     *            where the module is (e.g. eol script)
     * @return
     * @throws Exception
     * @throws URISyntaxException
     */
    private static IEolExecutableModule loadModule(String source) throws Exception, URISyntaxException {

        IEolExecutableModule module = null;
        if (source.endsWith("etl")) {
            module = new EtlModule();
        }
        else if (source.endsWith("eol")) {
            module = new EolModule();
        }
        else {
            Console
                .printerrln("Could not determine model type, file should end with either .etl or .eol");
            return null;
        }

        module.parse(new File(source));

        if (module.getParseProblems().size() > 0) {
            Console.printerrln("Parse error occured...");
            for (ParseProblem problem : module.getParseProblems()) {
                Console.printerrln(problem.toString());
            }
            // System.exit(-1);
        }

        return module;
    }

    /**
     * Helper method for registering the metamodels
     * 
     * @throws Exception
     */
    private static void registerMetaModels() throws Exception {
        String metaModelsPath = DECENTEpsilonModelHandler.metaPath;
        File metaModelsLocation = new File(metaModelsPath);
        for (File file : metaModelsLocation.listFiles()) {
            if (file.getName().endsWith(".ecore")) {
                EmfUtil.register(URI.createFileURI(file.getAbsolutePath()),
                                 EPackage.Registry.INSTANCE);
            }
        }
    }

}
