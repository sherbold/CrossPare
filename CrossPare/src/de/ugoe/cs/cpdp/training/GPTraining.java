package de.ugoe.cs.cpdp.training;

import org.apache.commons.collections4.list.SetUniqueList;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import org.apache.commons.lang3.ArrayUtils;

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

import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.util.CloneException;

/**
 * Genetic Programming Trainer
 *
 */
public class GPTraining implements ISetWiseTrainingStrategy, IWekaCompatibleTrainer  {
    
    private final GPClassifier classifier = new GPClassifier();
    
    private int populationSize = 1000;
    private int initMinDepth = 2;
    private int initMaxDepth = 6;
    private int tournamentSize = 7;
    
    @Override
    public void setParameter(String parameters) {
        System.out.println("setParameters");
    }

    @Override
    public void apply(SetUniqueList<Instances> traindataSet) {
        System.out.println("apply");
        for (Instances traindata : traindataSet) {
            try {
                classifier.buildClassifier(traindata);
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getName() {
        System.out.println("getName");
        return "GPTraining";
    }

    @Override
    public Classifier getClassifier() {
        System.out.println("getClassifier");
        return this.classifier;
    }
    
    public class InstanceData {
        private double[][] instances_x;
        private boolean[] instances_y;
        
        public InstanceData(Instances instances) {
            this.instances_x = new double[instances.numInstances()][instances.numAttributes()-1];

            Instance current;
            for(int i=0; i < this.instances_x.length; i++) {
                current = instances.get(i);
                for(int j=0; j < this.instances_x[0].length; j++) {
                    this.instances_x[i][j] = current.value(j);
                }
                
                this.instances_y[i] = current.stringValue(instances.classIndex()).equals("Y");
            }
        }
        
        public double[][] getX() {
            return instances_x;
        }
        public boolean[] getY() {
            return instances_y;
        }
    }
    
    public class GPClassifier extends AbstractClassifier {

        private static final long serialVersionUID = 3708714057579101522L;

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
        
        @Override
        public void buildClassifier(Instances instances) throws Exception {
            // load instances into double[][] and boolean[]
            InstanceData train = new InstanceData(instances);
            this.problem = new CrossPareGP(train.getX(), train.getY(), this.populationSize, this.initMinDepth, this.initMaxDepth, this.tournamentSize);
            
            this.gp = problem.create();
            this.gp.evolve(this.maxGenerations);
        }
        
        @Override
        public double classifyInstance(Instance instance) {
            Variable[] vars = ((CrossPareGP)this.problem).getVariables();
            
            double[][] x = new double[1][instance.numAttributes()-1];
            boolean[] y = new boolean[1];
            
            for(int i = 0; i < instance.numAttributes()-1; i++) {
                x[0][i] = instance.value(i);
            }
            y[0] = instance.stringValue(instance.classIndex()).equals("Y");
            
            CrossPareFitness test = new CrossPareFitness(vars, x, y);
            IGPProgram fitest = gp.getAllTimeBest();
            
            double sfitness = test.evaluate(fitest);
            
            // korrekt sind wir wenn wir geringe fitness haben?
            if(sfitness < 0.5) {
                return 1.0;
            }
            return 0;
            
        }

        /**
         * GPProblem implementation
         */
        class CrossPareGP extends GPProblem {
            
            private static final long serialVersionUID = 7526472295622776147L;

            private double[][] instances;
            private boolean[] output;

            private Variable[] x;

            public CrossPareGP(double[][] instances, boolean[] output, int populationSize, int minInitDept, int maxInitDepth, int tournamentSize) throws InvalidConfigurationException {
                super(new GPConfiguration());

                this.instances = instances;
                this.output = output;

                GPConfiguration config = this.getGPConfiguration();

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

                // variables + functions
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

                    // value gives us a double, if > 0.5 we set this instance as faulty
                    value = program.execute_double(0, NO_ARGS);

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
                if(program.getChromosome(0).getSize(0) < 10) {
                    this.sfitness = 10.0f;
                    //System.out.println("wenige nodes: "+program.getChromosome(0).getSize(0));
                    //System.out.println(program.toStringNorm(0));
                }

                // sfitness counts the number of nodes in the tree, if it is lower than 10 fitness is increased by 10

                return pfitness;
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
