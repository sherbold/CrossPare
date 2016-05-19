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
 */
public class GPTraining implements ISetWiseTrainingStrategy, IWekaCompatibleTrainer  {
    
    private GPVVClassifier classifier = new GPVVClassifier();
    
    private int populationSize = 1000;
    private int initMinDepth = 2;
    private int initMaxDepth = 6;
    private int tournamentSize = 7;
    
    @Override
    public void setParameter(String parameters) {
        // todo, which type of classifier? GPV, GPVV?
        // more config population size, etc.
        // todo: voting for gpvv only 3 votes necessary?
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
    
    // one gprun, we want several for voting
    public class GPRun extends AbstractClassifier {
        private static final long serialVersionUID = -4250422550107888789L;

        private int populationSize = 1000;
        private int initMinDepth = 2;
        private int initMaxDepth = 6;
        private int tournamentSize = 7;
        private int maxGenerations = 50;
        
        private GPGenotype gp;
        private GPProblem problem;
        
        public void configure(int populationSize, int initMinDepth, int initMaxDepth, int tournamentSize, int maxGenerations) {
            this.populationSize = populationSize;
            this.initMinDepth = initMinDepth;
            this.initMaxDepth = initMaxDepth;
            this.tournamentSize = tournamentSize;
            this.maxGenerations = maxGenerations;
        }
        
        public GPGenotype getGp() {
            return this.gp;
        }
        
        public Variable[] getVariables() {
            return ((CrossPareGP)this.problem).getVariables();
        }
        
        public void setEvaldata(Instances testdata) {
            
        }
        
        /**
         * GPProblem implementation
         */
        class CrossPareGP extends GPProblem {
            
            //private static final long serialVersionUID = 7526472295622776147L;

            private double[][] instances;
            private boolean[] output;

            private Variable[] x;

            public CrossPareGP(double[][] instances, boolean[] output, int populationSize, int minInitDept, int maxInitDepth, int tournamentSize) throws InvalidConfigurationException {
                super(new GPConfiguration());
                
                this.instances = instances;
                this.output = output;

                Configuration.reset();
                GPConfiguration config = this.getGPConfiguration();
                //config.reset();
                
                this.x = new Variable[this.instances[0].length];

               
                for(int j=0; j < this.x.length; j++) {
                    this.x[j] = Variable.create(config, "X"+j, CommandGene.DoubleClass);    
                }

                config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator()); // smaller fitness is better
                //config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator()); // bigger fitness is better

                // from paper: 2-6
                config.setMinInitDepth(minInitDept);
                config.setMaxInitDepth(maxInitDepth);

                // missing from paper
                // config.setMaxDepth(20);

                config.setCrossoverProb((float)0.60);
                config.setReproductionProb((float)0.10);
                config.setMutationProb((float)0.30);

                config.setSelectionMethod(new TournamentSelector(tournamentSize));

                // from paper 1000
                config.setPopulationSize(populationSize);

                // BranchTypingCross
                config.setMaxCrossoverDepth(4);
                config.setFitnessFunction(new CrossPareFitness(this.x, this.instances, this.output));
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

                GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true); // 20 = maxNodes, true = verbose output

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

            private double error_type2_weight = 1.0;

            // needed in evaluate
            private Object[] NO_ARGS = new Object[0];

            private double sfitness = 0.0f;
            private int error_type1 = 0;
            private int error_type2 = 0;

            public CrossPareFitness(Variable[] x, double[][] instances, boolean[] output) {
                this.x = x;
                this.instances = instances;
                this.output = output;
            }

            public int getErrorType1() {
                return this.error_type1;
            }

            public int getErrorType2() {
                return this.error_type2;
            }

            public double getSecondFitness() {
                return this.sfitness;
            }

            public int getNumInstances() {
                return this.instances.length;
            }

            @Override
            protected double evaluate(final IGPProgram program) {
                double pfitness = 0.0f;
                this.sfitness = 0.0f;
                double value = 0.0f;

                // count classification errors
                this.error_type1 = 0;
                this.error_type2 = 0;

                for(int i=0; i < this.instances.length; i++) {

                    // requires that we have a variable for each column of our dataset (attribute of instance)
                    for(int j=0; j < this.x.length; j++) {
                        this.x[j].set(this.instances[i][j]);
                    }

                    // value gives us a double, if < 0.5 we set this instance as faulty
                    value = program.execute_double(0, NO_ARGS);  // todo: test with this.x

                    if(value < 0.5) {
                        if(this.output[i] != true) {
                            this.error_type1 += 1;
                        }
                    }else {
                        if(this.output[i] == true) {
                            this.error_type2 += 1;
                        }
                    }
                }

                // now calc pfitness
                pfitness = (this.error_type1 + this.error_type2_weight * this.error_type2) / this.instances.length;

                //System.out.println("pfitness: " + pfitness);

                // number of nodes in the programm, if lower then 10 we assign sFitness of 10
                // we can set metadata with setProgramData to save this
                if(program.getChromosome(0).getSize(0) < 10) {
                    program.setApplicationData(10.0f);
                    this.sfitness = 10.0f;
                    //System.out.println("wenige nodes: "+program.getChromosome(0).getSize(0));
                    //System.out.println(program.toStringNorm(0));
                }

                // sfitness counts the number of nodes in the tree, if it is lower than 10 fitness is increased by 10

                return pfitness;
            }
        }

        @Override
        public void buildClassifier(Instances traindata) throws Exception {
            InstanceData train = new InstanceData(traindata);            
            this.problem = new CrossPareGP(train.getX(), train.getY(), this.populationSize, this.initMinDepth, this.initMaxDepth, this.tournamentSize);
            this.gp = problem.create();
            this.gp.evolve(this.maxGenerations);
        }
    }
    
    /**
     * GP Multiple Data Sets Validation-Voting Classifier
     * 
     * As the GP Multiple Data Sets Validation Classifier
     * But here we do keep a model candidate for each training set which may later vote
     *
     */
    public class GPVVClassifier extends GPVClassifier {
        
        private List<Classifier> classifiers = null;
        
        @Override
        public void buildClassifier(Instances arg0) throws Exception {
            // TODO Auto-generated method stub
            
        }
        
        public void buildClassifier(SetUniqueList<Instances> traindataSet) throws Exception {

            // each classifier is trained with one project from the set
            // then is evaluated on the rest
            for(int i=0; i < traindataSet.size(); i++) {
                
                // candidates we get out of evaluation
                LinkedList<Classifier> candidates = new LinkedList<>();
                
                // 200 runs
                
                for(int k=0; k < 200; k++) {
                    Classifier classifier = new GPRun();
                    
                    // one project is training data
                    classifier.buildClassifier(traindataSet.get(i));
                    
                    double[] errors;
                    // rest of the set is evaluation data, we evaluate now
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            // if type1 and type2 errors are < 0.5 we allow the model in the final voting
                            errors = this.evaluate((GPRun)classifier, traindataSet.get(j));
                            if((errors[0] / traindataSet.get(j).numInstances()) < 0.5 && (errors[0] / traindataSet.get(j).numInstances()) < 0.5) {
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
         * Use the remaining classifiers for our voting
         */
        @Override
        public double classifyInstance(Instance instance) {
            
            int vote_positive = 0;
            int vote_negative = 0;
            
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
                }else {
                    vote_negative += 1;
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
     *
     * for one test data set:
     *   for one in 6 possible training data sets:
     *     For 200 GP Runs:
     *       train one Classifier with this training data
     *       then evaluate the classifier with the remaining project
     *       if the candidate model performs bad (error type1 or type2 > 50%) discard it
     * for the remaining model candidates the best one is used
     *
     */
    public class GPVClassifier extends AbstractClassifier {
        
        private List<Classifier> classifiers = null;
        private Classifier best = null;

        private static final long serialVersionUID = 3708714057579101522L;


        /** Build the GP Multiple Data Sets Validation Classifier
         * 
         * - Traindata one of the Instances of the Set (which one? The firsT? as it is a list?)
         * - Testdata one other Instances of the Set (the next one? chose randomly?)
         * - Evaluation the rest of the instances
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
                
                // 200 runs
                for(int k=0; k < 200; k++) {
                    Classifier classifier = new GPRun();
                    
                    // one project is training data
                    classifier.buildClassifier(traindataSet.get(i));
                    
                    double[] errors;
                    
                    // rest of the set is evaluation data, we evaluate now
                    for(int j=0; j < traindataSet.size(); j++) {
                        if(j != i) {
                            // if type1 and type2 errors are < 0.5 we allow the model in the final voting
                            errors = this.evaluate((GPRun)classifier, traindataSet.get(j));
                            if((errors[0] / traindataSet.get(j).numInstances()) < 0.5 && (errors[0] / traindataSet.get(j).numInstances()) < 0.5) {
                                candidates.add(classifier);                            
                            }
                        }
                    }
                }
                
                // now after the evaluation we do a model selection where only one model remains per training data set
                // from that we chose the best model
                
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
            
            // now determine the best classifier for all training data
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
            classifier.buildClassifier(traindata);
            classifiers.add(classifier);
        }
        
        public double[] evaluate(GPRun classifier, Instances evalData) {
            GPGenotype gp = classifier.getGp();
            Variable[] vars = classifier.getVariables();
            
            IGPProgram fitest = gp.getAllTimeBest();  // selects the fitest of all not just the last generation
            
            double classification;
            int error_type1 = 0;
            int error_type2 = 0;
            int number_instances = evalData.numInstances();
            
            for(Instance instance: evalData) {
                
                for(int i = 0; i < instance.numAttributes()-1; i++) {
                    vars[i].set(instance.value(i));
                }
                
                classification = fitest.execute_double(0, vars);
                
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
            
            double et1_per = error_type1 / number_instances;
            double et2_per = error_type2 / number_instances; 
            
            // return some kind of fehlerquote?
            //return (error_type1 + error_type2) / number_instances;
            return new double[]{error_type1, error_type2};
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
    
    
    /**
    * Custom GT implementation from the paper
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
