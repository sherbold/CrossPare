package de.ugoe.cs.cpdp.training;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import org.apache.commons.lang3.ArrayUtils;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;

import org.jgap.gp.function.Add;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Log;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Sine;
import org.jgap.gp.function.Cosine;
import org.jgap.gp.function.Max;
import org.jgap.gp.function.Exp;

import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.TournamentSelector;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;
import org.jgap.gp.MathCommand;
import org.jgap.util.ICloneable;

import de.ugoe.cs.cpdp.util.WekaUtils;

import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.util.CloneException;

/**
 * Genetic Programming Trainer
 * 
 * Implementation (mostly) according to Liu et al. Evolutionary Optimization of Software Quality Modeling with Multiple Repositories.
 * 
 * - GPRun is a Run of a complete Genetic Programm Evolution, we want several complete runs.
 * - GPVClassifier is the Validation Classifier
 * - GPVVClassifier is the Validation-Voting Classifier
 * 
 *  config: <setwisetrainer name="GPTraining" param="populationSize:1000,numberRuns:10" />
 */
public class GPTraining implements ISetWiseTrainingStrategy, IWekaCompatibleTrainer  {
    
    private GPVVClassifier classifier = null;
    
    // default values from the paper
    private int populationSize = 1000;
    private int initMinDepth = 2;
    private int initMaxDepth = 6;
    private int tournamentSize = 7;
    private int maxGenerations = 50;
    private double errorType2Weight = 15;
    private int numberRuns = 20;  // im paper 20 per errorType2Weight then additional 20
    private int maxDepth = 20;  // max depth within one program
    private int maxNodes = 100;  // max nodes within one program

    @Override
    public void setParameter(String parameters) {
        
        String[] params = parameters.split(",");
        String[] keyvalue = new String[2];

        for(int i=0; i < params.length; i++) {
            keyvalue = params[i].split(":");
            
            switch(keyvalue[0]) {
                case "populationSize":
                    this.populationSize = Integer.parseInt(keyvalue[1]);
                break;
                
                case "initMinDepth":
                    this.initMinDepth = Integer.parseInt(keyvalue[1]);
                break;
                
                case "tournamentSize":
                    this.tournamentSize = Integer.parseInt(keyvalue[1]);
                break;
                
                case "maxGenerations":
                    this.maxGenerations = Integer.parseInt(keyvalue[1]);
                break;
                
                case "errorType2Weight":
                    this.errorType2Weight = Double.parseDouble(keyvalue[1]);
                break;
                
                case "numberRuns":
                    this.numberRuns = Integer.parseInt(keyvalue[1]);
                break;
                
                case "maxDepth":
                    this.maxDepth = Integer.parseInt(keyvalue[1]);
                break;
                
                case "maxNodes":
                    this.maxNodes = Integer.parseInt(keyvalue[1]);
                break;
            }
        }
        
        this.classifier = new GPVVClassifier();
        ((GPVClassifier)this.classifier).configure(populationSize, initMinDepth, initMaxDepth, tournamentSize, maxGenerations, errorType2Weight, numberRuns, maxDepth, maxNodes);
    }

    @Override
    public void apply(SetUniqueList<Instances> traindataSet) {
        try {
            classifier.buildClassifier(traindataSet);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "GPTraining";
    }

    @Override
    public Classifier getClassifier() {
        return this.classifier;
    }
    
    public class InstanceData {
        private double[][] instances_x;
        private boolean[] instances_y;
        
        public InstanceData(Instances instances) {
            this.instances_x = new double[instances.numInstances()][instances.numAttributes()-1];
            this.instances_y = new boolean[instances.numInstances()];
            
            Instance current;
            for(int i=0; i < this.instances_x.length; i++) {
                current = instances.get(i);
                this.instances_x[i] = WekaUtils.instanceValues(current);
                this.instances_y[i] = 1.0 == current.classValue();
            }
        }
        
        public double[][] getX() {
            return instances_x;
        }
        public boolean[] getY() {
            return instances_y;
        }
    }
    
    /**
     * One Run executed by a GP Classifier
     */
    public class GPRun extends AbstractClassifier {
        private static final long serialVersionUID = -4250422550107888789L;

        private int populationSize;
        private int initMinDepth;
        private int initMaxDepth;
        private int tournamentSize;
        private int maxGenerations;
        private double errorType2Weight;
        private int maxDepth;
        private int maxNodes;
        
        private GPGenotype gp;
        private GPProblem problem;
        
        public void configure(int populationSize, int initMinDepth, int initMaxDepth, int tournamentSize, int maxGenerations, double errorType2Weight, int maxDepth, int maxNodes) {
            this.populationSize = populationSize;
            this.initMinDepth = initMinDepth;
            this.initMaxDepth = initMaxDepth;
            this.tournamentSize = tournamentSize;
            this.maxGenerations = maxGenerations;
            this.errorType2Weight = errorType2Weight;
            this.maxDepth = maxDepth;
            this.maxNodes = maxNodes;
        }
        
        public GPGenotype getGp() {
            return this.gp;
        }
        
        public Variable[] getVariables() {
            return ((CrossPareGP)this.problem).getVariables();
        }

        @Override
        public void buildClassifier(Instances traindata) throws Exception {
            InstanceData train = new InstanceData(traindata);            
            this.problem = new CrossPareGP(train.getX(), train.getY(), this.populationSize, this.initMinDepth, this.initMaxDepth, this.tournamentSize, this.errorType2Weight, this.maxDepth, this.maxNodes);
            this.gp = problem.create();
            this.gp.evolve(this.maxGenerations);
        }
        
        /**
         * GPProblem implementation
         */
        class CrossPareGP extends GPProblem {
            private double[][] instances;
            private boolean[] output;

            private int maxDepth;
            private int maxNodes;
            
            private Variable[] x;

            public CrossPareGP(double[][] instances, boolean[] output, int populationSize, int minInitDept, int maxInitDepth, int tournamentSize, double errorType2Weight, int maxDepth, int maxNodes) throws InvalidConfigurationException {
                super(new GPConfiguration());
                
                this.instances = instances;
                this.output = output;
                this.maxDepth = maxDepth;
                this.maxNodes = maxNodes;

                Configuration.reset();
                GPConfiguration config = this.getGPConfiguration();
                
                this.x = new Variable[this.instances[0].length];
               
                for(int j=0; j < this.x.length; j++) {
                    this.x[j] = Variable.create(config, "X"+j, CommandGene.DoubleClass);    
                }

                config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator()); // smaller fitness is better
                //config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator()); // bigger fitness is better

                config.setMinInitDepth(minInitDept);
                config.setMaxInitDepth(maxInitDepth);
                
                config.setCrossoverProb((float)0.60);
                config.setReproductionProb((float)0.10);
                config.setMutationProb((float)0.30);

                config.setSelectionMethod(new TournamentSelector(tournamentSize));

                config.setPopulationSize(populationSize);

                config.setMaxCrossoverDepth(4);
                config.setFitnessFunction(new CrossPareFitness(this.x, this.instances, this.output, errorType2Weight));
                config.setStrictProgramCreation(true);
            }

            // used for running the fitness function again for testing
            public Variable[] getVariables() {
                return this.x;
            }


            public GPGenotype create() throws InvalidConfigurationException {
                GPConfiguration config = this.getGPConfiguration();

                // return type
                Class[] types = {CommandGene.DoubleClass};

                // Arguments of result-producing chromosome: none
                Class[][] argTypes = { {} };

                // variables + functions, we set the variables with the values of the instances here
                CommandGene[] vars = new CommandGene[this.instances[0].length];
                for(int j=0; j < this.instances[0].length; j++) {
                    vars[j] = this.x[j];
                }
                CommandGene[] funcs = {
                    new Add(config, CommandGene.DoubleClass),
                    new Subtract(config, CommandGene.DoubleClass),
                    new Multiply(config, CommandGene.DoubleClass),
                    new Divide(config, CommandGene.DoubleClass),
                    new Sine(config, CommandGene.DoubleClass),
                    new Cosine(config, CommandGene.DoubleClass),
                    new Exp(config, CommandGene.DoubleClass),
                    new Log(config, CommandGene.DoubleClass),
                    new GT(config, CommandGene.DoubleClass),
                    new Max(config, CommandGene.DoubleClass),
                    new Terminal(config, CommandGene.DoubleClass, -100.0, 100.0, true), // min, max, whole numbers
                };

                CommandGene[] comb = (CommandGene[])ArrayUtils.addAll(vars, funcs);
                CommandGene[][] nodeSets = {
                    comb,
                };
                
                // we only have one chromosome so this suffices
                int minDepths[] = {config.getMinInitDepth()};
                int maxDepths[] = {this.maxDepth};
                GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, minDepths, maxDepths, this.maxNodes, false); // 40 = maxNodes, true = verbose output

                return result;
            }
        }

        
        /**
         * Fitness function
         */
        class CrossPareFitness extends GPFitnessFunction {
            
            private static final long serialVersionUID = 75234832484387L;

            private Variable[] x;

            private double[][] instances;
            private boolean[] output;

            private double errorType2Weight = 1.0;

            // needed in evaluate
            //private Object[] NO_ARGS = new Object[0];

            private double sfitness = 0.0f;
            private int errorType1 = 0;
            private int errorType2 = 0;

            public CrossPareFitness(Variable[] x, double[][] instances, boolean[] output, double errorType2Weight) {
                this.x = x;
                this.instances = instances;
                this.output = output;
                this.errorType2Weight = errorType2Weight;
            }

            public int getErrorType1() {
                return this.errorType1;
            }

            public int getErrorType2() {
                return this.errorType2;
            }

            public double getSecondFitness() {
                return this.sfitness;
            }

            public int getNumInstances() {
                return this.instances.length;
            }

            /**
             * This is the fitness function
             * 
             * Our fitness is best if we have the less wrong classifications, this includes a weight for type2 errors
             */
            @Override
            protected double evaluate(final IGPProgram program) {
                double pfitness = 0.0f;
                this.sfitness = 0.0f;
                double value = 0.0f;

                // count classification errors
                this.errorType1 = 0;
                this.errorType2 = 0;

                for(int i=0; i < this.instances.length; i++) {

                    // requires that we have a variable for each column of our dataset (attribute of instance)
                    for(int j=0; j < this.x.length; j++) {
                        this.x[j].set(this.instances[i][j]);
                    }

                    // value gives us a double, if < 0.5 we set this instance as faulty
                    value = program.execute_double(0, this.x);

                    if(value < 0.5) {
                        if(this.output[i] != true) {
                            this.errorType1 += 1;
                        }
                    }else {
                        if(this.output[i] == true) {
                            this.errorType2 += 1;
                        }
                    }
                }

                // now calc pfitness
                pfitness = (this.errorType1 + this.errorType2Weight * this.errorType2) / this.instances.length;

                // number of nodes in the programm, if lower then 10 we assign sFitness of 10
                // we can set metadata with setProgramData to save this
                if(program.getChromosome(0).getSize(0) < 10) {
                    program.setApplicationData(10.0f);
                }

                return pfitness;
            }
        }
        
        /**
         * Custom GT implementation used in the GP Algorithm.
         */
         public class GT extends MathCommand implements ICloneable {
             
             private static final long serialVersionUID = 113454184817L;

             public GT(final GPConfiguration a_conf, java.lang.Class a_returnType) throws InvalidConfigurationException {
                 super(a_conf, 2, a_returnType);
             }

             public String toString() {
                 return "GT(&1, &2)";
             }

             public String getName() {
                 return "GT";
             }   

             public float execute_float(ProgramChromosome c, int n, Object[] args) {
                 float f1 = c.execute_float(n, 0, args);
                 float f2 = c.execute_float(n, 1, args);

                 float ret = 1.0f;
                 if(f1 > f2) {
                     ret = 0.0f;
                 }

                 return ret;
             }

             public double execute_double(ProgramChromosome c, int n, Object[] args) {
                 double f1 = c.execute_double(n, 0, args);
                 double f2 = c.execute_double(n, 1, args);

                 double ret = 1;
                 if(f1 > f2)  {
                     ret = 0;
                 }
                 return ret;
             }

             public Object clone() {
                 try {
                     GT result = new GT(getGPConfiguration(), getReturnType());
                     return result;
                 }catch(Exception ex) {
                     throw new CloneException(ex);
                 }
             }
         }
    }
    
    /**
     * GP Multiple Data Sets Validation-Voting Classifier
     * 
     * Basically the same as the GP Multiple Data Sets Validation Classifier.
     * But here we do keep a model candidate for each training set which may later vote
     *
     */
    public class GPVVClassifier extends GPVClassifier {

        private static final long serialVersionUID = -654710583852839901L;
        private List<Classifier> classifiers = null;
        
        @Override
        public void buildClassifier(Instances arg0) throws Exception {
            // TODO Auto-generated method stub
            
        }
        
        /** Build the GP Multiple Data Sets Validation-Voting Classifier
         * 
         * This is according to Section 6 of the Paper by Liu et al.
         * It is basically the Multiple Data Sets Validation Classifier but here we keep the best models an let them vote.
         * 
         * @param traindataSet
         * @throws Exception
         */
        public void buildClassifier(SetUniqueList<Instances> traindataSet) throws Exception {

            // each classifier is trained with one project from the set
            // then is evaluated on the rest
            classifiers = new LinkedList<>();
            for(int i=0; i < traindataSet.size(); i++) {
                
                // candidates we get out of evaluation
                LinkedList<Classifier> candidates = new LinkedList<>();
                
                // number of runs
                for(int k=0; k < this.numberRuns; k++) {
                    Classifier classifier = new GPRun();
                    ((GPRun)classifier).configure(this.populationSize, this.initMinDepth, this.initMaxDepth, this.tournamentSize, this.maxGenerations, this.errorType2Weight, this.maxDepth, this.maxNodes);
                    
                    // one project is training data
                    classifier.buildClassifier(traindataSet.get(i));
                    
                    double[] errors;
                    // rest of the set is evaluation data, we evaluate now
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            // if type1 and type2 errors are < 0.5 we allow the model in the candidates
                            errors = this.evaluate((GPRun)classifier, traindataSet.get(j));
                            if((errors[0] < 0.5) && (errors[0] < 0.5)) {
                                candidates.add(classifier);
                            }
                        }
                    }
                }
                
                // now after the evaluation we do a model selection where only one model remains for the given training data
                double smallest_error_count = Double.MAX_VALUE;
                double[] errors;
                Classifier best = null;
                for(int ii=0; ii < candidates.size(); ii++) {
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            errors = this.evaluate((GPRun)candidates.get(ii), traindataSet.get(j));
                            
                            if(errors[0]+errors[1] < smallest_error_count) {
                                best = candidates.get(ii);
                            }
                        }
                    }
                }
                
                // now we have the best classifier for this training data
                classifiers.add(best);
            }
        }
        
        /**
         * Use the best classifiers for each training data in a majority voting
         */
        @Override
        public double classifyInstance(Instance instance) {
            
            int vote_positive = 0;
            
            for (int i = 0; i < classifiers.size(); i++) {
                Classifier classifier = classifiers.get(i);
                
                GPGenotype gp = ((GPRun)classifier).getGp();
                Variable[] vars = ((GPRun)classifier).getVariables();
                
                IGPProgram fitest = gp.getAllTimeBest();  // all time fitest
                for(int j = 0; j < instance.numAttributes()-1; j++) {
                   vars[j].set(instance.value(j));
                }
                
                if(fitest.execute_double(0, vars) < 0.5) {
                    vote_positive += 1;
                }
            }
            
            if(vote_positive >= (classifiers.size()/2)) {
                return 1.0;
            }else {
                return 0.0;
            }
        }
    }
    
    /**
     * GP Multiple Data Sets Validation Classifier
     * 
     * We train a Classifier with one training project $numberRun times.
     * Then we evaluate the classifier on the rest of the training projects and keep the best classifier.
     * After that we have for each training project the best classifier as per the evaluation on the rest of the data set.
     * Then we determine the best classifier from these candidates and keep it to be used later.
     */
    public class GPVClassifier extends AbstractClassifier {
        
        private List<Classifier> classifiers = null;
        private Classifier best = null;

        private static final long serialVersionUID = 3708714057579101522L;

        protected int populationSize;
        protected int initMinDepth;
        protected int initMaxDepth;
        protected int tournamentSize;
        protected int maxGenerations;
        protected double errorType2Weight;
        protected int numberRuns;
        protected int maxDepth;
        protected int maxNodes;

        /**
         * Configure the GP Params and number of Runs
         * 
         * @param populationSize
         * @param initMinDepth
         * @param initMaxDepth
         * @param tournamentSize
         * @param maxGenerations
         * @param errorType2Weight
         */
        public void configure(int populationSize, int initMinDepth, int initMaxDepth, int tournamentSize, int maxGenerations, double errorType2Weight, int numberRuns, int maxDepth, int maxNodes) {
            this.populationSize = populationSize;
            this.initMinDepth = initMinDepth;
            this.initMaxDepth = initMaxDepth;
            this.tournamentSize = tournamentSize;
            this.maxGenerations = maxGenerations;
            this.errorType2Weight = errorType2Weight;
            this.numberRuns = numberRuns;
            this.maxDepth = maxDepth;
            this.maxNodes = maxNodes;
        }
        
        /** Build the GP Multiple Data Sets Validation Classifier
         * 
         * This is according to Section 6 of the Paper by Liu et al. except for the selection of the best model.
         * Section 4 describes a slightly different approach.
         * 
         * @param traindataSet
         * @throws Exception
         */
        public void buildClassifier(SetUniqueList<Instances> traindataSet) throws Exception {

            // each classifier is trained with one project from the set
            // then is evaluated on the rest
            for(int i=0; i < traindataSet.size(); i++) {
                
                // candidates we get out of evaluation
                LinkedList<Classifier> candidates = new LinkedList<>();
                
                // numberRuns full GPRuns, we generate numberRuns models for each traindata
                for(int k=0; k < this.numberRuns; k++) {
                    Classifier classifier = new GPRun();
                    ((GPRun)classifier).configure(this.populationSize, this.initMinDepth, this.initMaxDepth, this.tournamentSize, this.maxGenerations, this.errorType2Weight, this.maxDepth, this.maxNodes);
                    
                    classifier.buildClassifier(traindataSet.get(i));
                    
                    double[] errors;

                    // rest of the set is evaluation data, we evaluate now
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            // if type1 and type2 errors are < 0.5 we allow the model in the candidate list
                            errors = this.evaluate((GPRun)classifier, traindataSet.get(j));
                            if((errors[0] < 0.5) && (errors[0] < 0.5)) {
                                candidates.add(classifier);
                            }
                        }
                    }
                }
                
                // after the numberRuns we have < numberRuns candidate models for this trainData
                // we now evaluate the candidates
                // finding the best model is not really described in the paper we go with least errors
                double smallest_error_count = Double.MAX_VALUE;
                double[] errors;
                Classifier best = null;
                for(int ii=0; ii < candidates.size(); ii++) {
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            errors = this.evaluate((GPRun)candidates.get(ii), traindataSet.get(j));
                            
                            if(errors[0]+errors[1] < smallest_error_count) {
                                best = candidates.get(ii);
                            }
                        }
                    }
                }
                
                // now we have the best classifier for this training data
                classifiers.add(best);
            } /* endfor trainData */
            
            // now we have one best classifier for each trainData 
            // we evaluate again to find the best classifier of all time
            // this selection is now according to section 4 of the paper and not 6 where an average of the 6 models is build 
            double smallest_error_count = Double.MAX_VALUE;
            double error_count;
            double errors[];
            for(int j=0; j < classifiers.size(); j++) {
                error_count = 0;
                Classifier current = classifiers.get(j);
                for(int i=0; i < traindataSet.size(); i++) {
                    errors = this.evaluate((GPRun)current, traindataSet.get(i));
                    error_count = errors[0] + errors[1];
                }
                
                if(error_count < smallest_error_count) {
                    best = current;
                }
            }
        }
        
        @Override
        public void buildClassifier(Instances traindata) throws Exception {
            final Classifier classifier = new GPRun();
            ((GPRun)classifier).configure(populationSize, initMinDepth, initMaxDepth, tournamentSize, maxGenerations, errorType2Weight, this.maxDepth, this.maxNodes);
            classifier.buildClassifier(traindata);
            classifiers.add(classifier);
        }
        
        /**
         * Evaluation of the Classifier
         * 
         * We evaluate the classifier with the Instances of the evalData.
         * It basically assigns the instance attribute values to the variables of the s-expression-tree and 
         * then counts the missclassifications. 
         * 
         * @param classifier
         * @param evalData
         * @return
         */
        public double[] evaluate(GPRun classifier, Instances evalData) {
            GPGenotype gp = classifier.getGp();
            Variable[] vars = classifier.getVariables();
            
            IGPProgram fitest = gp.getAllTimeBest();  // selects the fitest of all not just the last generation
            
            double classification;
            int error_type1 = 0;
            int error_type2 = 0;
            int positive = 0;
            int negative = 0;
            
            for(Instance instance: evalData) {
                
                // assign instance attribute values to the variables of the s-expression-tree
                double[] tmp = WekaUtils.instanceValues(instance);
                for(int i = 0; i < tmp.length; i++) {
                    vars[i].set(tmp[i]);
                }
                
                classification = fitest.execute_double(0, vars);
                
                // we need to count the absolutes of positives for percentage
                if(instance.classValue() == 1.0) {
                    positive +=1;
                }else {
                    negative +=1;
                }
                
                // classification < 0.5 we say defective
                if(classification < 0.5) {
                    if(instance.classValue() != 1.0) {
                        error_type1 += 1;
                    }
                }else {
                    if(instance.classValue() == 1.0) {
                        error_type2 += 1;
                    }
                }
            }
            
            // return error types percentages for the types 
            double et1_per = error_type1 / negative;
            double et2_per = error_type2 / positive; 
            return new double[]{et1_per, et2_per};
        }
        
        /**
         * Use only the best classifier from our evaluation phase
         */
        @Override
        public double classifyInstance(Instance instance) {
            GPGenotype gp = ((GPRun)best).getGp();
            Variable[] vars = ((GPRun)best).getVariables();
            
            IGPProgram fitest = gp.getAllTimeBest();  // all time fitest
            for(int i = 0; i < instance.numAttributes()-1; i++) {
               vars[i].set(instance.value(i));
            }
            
            double classification = fitest.execute_double(0, vars);
            
            if(classification < 0.5) {
                return 1.0;
            }else {
                return 0.0;
            }
        }
    }
}
