package de.ugoe.cs.cpdp.training;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unihannover.gimo_m.mining.common.Blackboard;
import de.unihannover.gimo_m.mining.common.ResultData;
import de.unihannover.gimo_m.mining.common.RecordSet;
import de.unihannover.gimo_m.mining.common.TargetFunction;
import de.unihannover.gimo_m.mining.common.ValuedResult;
import de.unihannover.gimo_m.mining.common.Blackboard.RecordsAndRemarks;
import de.unihannover.gimo_m.mining.common.RawEvaluationResult;
import de.unihannover.gimo_m.mining.common.Record;
import de.unihannover.gimo_m.mining.common.RecordScheme;
import de.unihannover.gimo_m.mining.common.RuleSet;
import de.ugoe.cs.cpdp.versions.SoftwareVersion;
import de.unihannover.gimo_m.mining.agents.MiningAgent;

/**
 * Implementation of a classifier that uses the genetic rule mining algorithm
 * GIMO after Baum et al. 2018 for training, adapted to work with defect
 * prediction data.
 * 
 * @author jvdmosel
 */
public class GimoTraining implements IWekaCompatibleTrainer, ITrainingStrategy {

    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * Reference to the weka classifier
     */
    private GimoClassifier gimoClassifier = new GimoClassifier();

    /**
     * @see de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer#getClassifier()
     */
    @Override
    public Classifier getClassifier() {
        return gimoClassifier;
    }

    /**
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        String[] params = parameters.split(" ");
        try {
            gimoClassifier.setOptions(params);
        } catch (Exception e) {
            LOGGER.error("One or more parameters not found. Using default parameters.");
            try {
                gimoClassifier.setOptions(new String[]{"-A", "1", "-C", "30", "-P", "0.1", "-T", "60", "-V", "false"});
            } catch (Exception f) {
                f.printStackTrace();
                throw new RuntimeException(f);
            }
        }
    }

    /**
     * @see ITrainingStrategy#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public void apply(SoftwareVersion trainversion) {
        // set bugmatrix and efforts
        gimoClassifier.setBugMatrix(trainversion.getBugMatrix());
        gimoClassifier.setEfforts(trainversion.getEfforts());
        try {
            gimoClassifier.buildClassifier(trainversion.getInstances());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @see ITrainingStrategy#getName()
     */
    @Override
    public String getName() {
        return "GimoTraining";
    }

    /**
     * Internal weka GIMO classifier
     */
    public class GimoClassifier extends AbstractClassifier {

        /**
         * Default serial ID
         */
        private static final long serialVersionUID = 1L;

        /**
         * Blackboard of the GIMO classifier
         */
        private Blackboard blackboard = null;

        /**
         * Bugmatrix of the GIMO classifier
         */
        private double[][] bugMatrix = null;

        /**
         * Efforts of the GIMO classifier
         */
        private List<Double> efforts = null;

        /**
         * Best rule found by training GIMO, used for classification
         */
        private RuleSet bestRule = null;

        /**
         * Maximum complexity of the best rule
         */
        private double maxComplexity = 30;

        /**
         * Number of agents (threads)
         */
        private int numberOfAgents = 1;

        /**
         * Training time in minutes
         */
        private int trainingTimeInMinutes = 60;

        /**
         * Percentage in which the GIMO classifier is willing to deteriorate the cost
         * range in favor of further complexity reduction
         */
        private double withinPercent = 0.1;

        /**
         * Controls whether the GIMO output should be displayed
         */
        private boolean verbose = false;

        /**
         * @see weka.classifiers.AbstractClassifier#setOptions(java.lang.String[])
         */
        @Override
        public void setOptions(String[] options) throws Exception {
            numberOfAgents = Integer.parseInt(Utils.getOption('A', options));
            maxComplexity = Double.parseDouble(Utils.getOption('C', options));
            withinPercent = Double.parseDouble(Utils.getOption('P', options));
            trainingTimeInMinutes = Integer.parseInt(Utils.getOption('T', options));
            verbose = Boolean.parseBoolean(Utils.getOption('V', options));
        }

        /**
         * Sets the bugmatrix of the GIMO classifier
         * @param bugMatrix
         *              the bugmatrix
         */
        public void setBugMatrix(Instances bugMatrix) {
            this.bugMatrix = toDoubleMatrix(bugMatrix);
        }
        
        /**
         * Sets the efforts of the GIMO classifier
         * @param efforts
         *              the efforts
         */
        public void setEfforts(List<Double> efforts) {
            this.efforts = new ArrayList<Double>(efforts);
        }

        /**
         * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
         */
        @Override
        public double classifyInstance(Instance instance) {
            if (bestRule == null) {
                LOGGER.info("No best rule found. Using default rule.");
                // create rule: normally use 0
                bestRule = RuleSet.create("0");
            }
            Record r = instanceToRecord(instance, 0);
            String classification = bestRule.apply(r);
            return Double.parseDouble(classification);
        }

        /**
         * @see weka.classifiers.AbstractClassifier#buildClassifier(weka.core.Instances)
         */
        @Override
        public void buildClassifier(Instances trainData) throws Exception {
            if (this.bugMatrix == null || this.efforts == null) {
                LOGGER.error("GimoTraining requires a bugmatrix and efforts for training");
                throw new RuntimeException();
            }

            // transform training data into GIMO compatible format
            RecordSet records = new RecordSet(determineScheme(trainData), transformToRecords(trainData));
            ResultData resultData = new ResultData(records);
            List<TargetFunction> targetFunctions = RawEvaluationResult.createTargetFunctions(resultData, bugMatrix,
                    efforts);

            // setup blackboard ()
            blackboard = new Blackboard(records, resultData, targetFunctions, System.currentTimeMillis());
            blackboard.setLog(verbose);

            // start agents
            ExecutorService executor = Executors.newFixedThreadPool(numberOfAgents);
            for (int i = 1; i <= numberOfAgents; i++) {
                if (verbose) {
                    LOGGER.info(String.format("Agent started. %d agents now running.", i));
                }
                executor.execute(new MiningAgent(blackboard));
            }

            // train GIMO for X = trainingTimeInMinutes minutes
            executor.awaitTermination(trainingTimeInMinutes, TimeUnit.MINUTES);

            // stop all agents, this might take some time
            if (verbose) {
                LOGGER.info("Stopping all agents.");
            }
            executor.shutdownNow();
            while (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                // waiting for all agents to stop
            }
            if (verbose) {
                LOGGER.info("All agents stopped.");
            }

            // try to find a rule having <= maxComplexity complexity in the pareto front
            try {
                bestRule = getBestRule();
            } catch (Exception e) {
                LOGGER.info("No best rule found. Using default rule.");
                // default rule: normally use 0
                bestRule = RuleSet.create("0");
            }
            System.out.println("Best Rule: \n" + bestRule);
        }

        /**
         * Finds best rule in the pareto front given the parameters. Filters the pareto
         * front to find rules with complexity <= maximum Complexity 
         * Let R be the rule in the filtered pareto set with the best cost range 
         * Best rule := rule with minimal complexity within the given percentage 
         * e.g. R's range = 3.0, withinPercent = 0.1, find rule with minimal complexity 
         * and range 2.7 - 3.0 
         * 
         * @return best Rule
         * @throws Exception
         */
        public RuleSet getBestRule() throws Exception {
            RuleSet bestRule = blackboard.getBestRule(maxComplexity, withinPercent);
            if (bestRule == null) {
                LOGGER.error("No rule in the ParetoFront with maxComplexity <= " + maxComplexity);
                throw new RuntimeException();
            }
            return bestRule;
        }

        /**
         * Applys any given GIMO rule and calculates its cost range for this blackboard
         * Rule does not have to be part of the pareto front 
         * Needed for GIMO cross validation classifier, not used as of now
         * 
         * @param rule the Rule
         * @return Cost range of the rule
         */
        public double applyRule(RuleSet rule) {
            RecordsAndRemarks rr = blackboard.getRecords();
            // set the bugmatrix and efforts every time since RawEvaluationResult is static
            RawEvaluationResult.setBugMatrix(this.bugMatrix);
            RawEvaluationResult.setEfforts(this.efforts);
            // apply the rule
            ValuedResult<RuleSet> vr = ValuedResult.create(rule, rr.getRecords(), rr.getResultData());
            // cost range might be NaN or lower bound might be greater than upper bound (terrible)
            if (vr.isNaN() || vr.isTerrible()) {
                return Double.MIN_VALUE;
                // cost range might be positive infinite
            } else if (vr.isInfinite()) {
                return Double.MAX_VALUE;
            } else {
                // calculate range
                return Math.abs(vr.getLowerBoundary() - vr.getUpperBoundary());
            }
        }

        /**
         * Helper method that creates a de.unihannover.gimo_m.mining.common.RecordScheme
         * of the training data
         * 
         * @param data the training data
         * @return RecordScheme
         */
        private RecordScheme determineScheme(Instances data) {
            final List<String> numericColumns = new ArrayList<>();
            final List<String> stringColumns = new ArrayList<>();
            for (Iterator<Attribute> it = data.enumerateAttributes().asIterator(); it.hasNext();) {
                Attribute att = it.next();
                // replace whitespaces in attribute names with underscores
                String columnName = att.name().replaceAll("\\s+", "_");
                numericColumns.add(columnName);
            }
            return new RecordScheme(numericColumns, stringColumns);
        }

        /**
         * Converts a weka.core.Instance to de.unihannover.gimo_m.mining.common.Record
         * 
         * @param instance weka instance to be converted
         * @param id       id of the record
         * @return Record
         */
        public Record instanceToRecord(Instance instance, int id) {
            List<Double> numericValues = DoubleStream.of(instance.toDoubleArray()).boxed().collect(Collectors.toList());
            List<String> stringValues = new ArrayList<>();
            String classification;
            if (instance.classAttribute().isNominal()) {
                classification = instance.stringValue(instance.classAttribute());
            } else {
                classification = (instance.classValue() == 0) ? "0" : "1";
            }
            return new Record(id, numericValues, stringValues, classification);
        }

        /**
         * Helper method that converts the training data to GIMO records
         * 
         * @param data the training data
         * @return Array of records
         */
        private Record[] transformToRecords(Instances data) {
            Record[] records = new Record[data.size()];
            int id = 0;
            for (Instance instance : data) {
                records[id] = instanceToRecord(instance, id);
                id++;
            }
            return records;
        }

        /**
         * Helper method that converts a set of weka Instances to a primitive double
         * matrix
         * 
         * @param instances the weka Instances
         * @return Double matrix
         */
        public double[][] toDoubleMatrix(Instances instances) {
            double[][] matrix = new double[instances.size()][instances.numAttributes()];
            int i = 0;
            for (Instance instance : instances) {
                matrix[i++] = instance.toDoubleArray();
            }
            return matrix;
        }
    }    
}
