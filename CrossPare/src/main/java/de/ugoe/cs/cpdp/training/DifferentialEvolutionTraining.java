package de.ugoe.cs.cpdp.training;

import java.util.ArrayList;
import java.util.List;

import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.ugoe.cs.cpdp.util.WekaUtils.Hyperparameter;
import de.ugoe.cs.cpdp.util.WekaUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.classifiers.Classifier;


/**
 * Trainer for hyperparameter tuning with differential evolution.
 * <p>
 * The algorithm supports Weka classifiers and classifiers from the python package Sklearn.
 * <p>
 * The hyperparameter tuning is only possible for integer or float type hyperparameter.
 * Hyperparameter from other types can be also given as a fixed parameter.
 * <p>
 * The function parameters have to be divided by a ';' as follows:
 * <p>
 * "name;classname;fixed_parameter_string;tuning_parameters;mutationConstant;crossoverConstant;populationSize;numGenerations"
 * <p>
 * It's possible to give only 4 parameters: "name;classname;fixed_parameter_string;tuning_parameters". The algorithm's control parameters are then set to default.
 * <p>
 * The tunable hyperparameters have to be comma separated with the following scheme: "key min_value max_value".
 * <p>
 * For the use of sklearn classifiers the '-parameters' key is automatically set.
 * <p>
 * Following examples for Random Forest classifiers in weka and sklearn with fixed number of trees and tuning of minimum leaf size and used feature ratio:
 * <pre>
 * {@code
 * <!-- example weka -->
 * <trainer name="DifferentialEvolutionTraining" param="RandomForest;weka.classifiers.trees.RandomForest;-I 100;-M 1 20,-K 0.0 1.0;0.9;0.5;10;10"/>
 * <!-- example sklearn -->
 * <trainer name="DifferentialEvolutionTraining" param="SklearnRandomForest;weka.classifiers.sklearn.ScikitLearnClassifier;-learner RandomForestClassifier n_estimators=100;min_samples_leaf 1 20,max_features 0.0 1.0;0.9;0.5;10;10"/>
 * }
 * </pre>
 * </p>
 *
 * @author Steffen Tunkel
 */
public class DifferentialEvolutionTraining implements ITrainingStrategy, IWekaCompatibleTrainer{

    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * Classifier object
     */
    private Classifier clf;

    /**
     * Classifiers class name
     */
    private String clfClass;

    /**
     * Classifiers description name
     */
    private String clfName;

    /**
     * List of hyperparameters for tuning
     */
    private List<Hyperparameter> tuneParams;

    /**
     * String with fixed hyperparameters
     */
    private String fixedParamsString;

    /**
     * Mutation constant in the differential evolution
     */
    private double mutationConstant = 0.9;

    /**
     * Crossover constant in the differential evolution
     */
    private double crossoverConstant = 0.5;

    /**
     * Size of the population in the differential evolution
     */
    private int populationSize = 10;

    /**
     * Number of generations of the differential evolution
     */
    private int numGenerations = 10;

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            String[] functionParameters = parameters.split(";");
            if(functionParameters.length == 4 || functionParameters.length == 8){
                this.clfName = functionParameters[0];
                this.clfClass = functionParameters[1];
                this.fixedParamsString = functionParameters[2];
                this.tuneParams = new ArrayList<>();
                for (String hyperparameterString : functionParameters[3].split(",")) {
                    String[] p = hyperparameterString.split(" ");
                    if (p.length == 3) {
                        if (p[1].contains(".")) {
                            tuneParams.add(new Hyperparameter(p[0], false,
                                    Double.parseDouble(p[1]), Double.parseDouble(p[2])));
                        } else {
                            tuneParams.add(new Hyperparameter(p[0], true,
                                    Double.parseDouble(p[1]), Double.parseDouble(p[2])));
                        }
                    } else {
                        throw new RuntimeException("Error: Invalid format for hyperparameter.");
                    }
                }
                if (functionParameters.length == 7){
                    this.mutationConstant = Double.parseDouble(functionParameters[4]);
                    this.crossoverConstant = Double.parseDouble(functionParameters[5]);
                    this.populationSize = Integer.parseInt(functionParameters[6]);
                    this.numGenerations = Integer.parseInt(functionParameters[7]);
                }
            }
            else{
                throw new RuntimeException("Error: DifferentialEvolutionTraining did not get the right amount of parameters.");
            }
        }
        else{
            throw new RuntimeException("Error: DifferentialEvolutionTraining did not get parameters.");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.training.ITrainingStrategy#apply()
     */
    @Override
    public void apply(SoftwareVersion trainversion) {
        this.clf = WekaUtils.differentialEvolution(trainversion.getInstances(), clfClass, fixedParamsString, tuneParams,
                mutationConstant, crossoverConstant, populationSize, numGenerations);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer#getClassifier()
     */
    @Override
    public Classifier getClassifier() {
        return this.clf;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.ugoe.cs.cpdp.training.ITrainingStrategy#getName()
     */
    @Override
    public String getName() {
        return this.clfName;
    }
}
