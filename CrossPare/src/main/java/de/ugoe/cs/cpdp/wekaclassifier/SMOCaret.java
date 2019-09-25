package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Implements a simple grid search over C and Gamma for RBF Kernel SMOs similar to R's caret.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SMOCaret extends AbstractClassifier {
    
    /**  */
    private static final long serialVersionUID = 1L;
    
    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
    
    Classifier internalClassifier = null;

    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        // build classifiers with all SMO parameter combinations
        // we do this manually, because GridSearch is not working
        
        Random rand = new Random();
        
        double[] valuesC = new double[] {0.25, 0.5, 1, 2, 4};
        double[] valuesG = new double[] {0.1, 0.3, 0.5, 0.7, 0.9};
        
        double bestScore = 0.0;
        Classifier bestClassifier = null;
        for( int i=0; i<valuesC.length; i++) {
            for( int j=0; j<valuesG.length; j++) {
                SMO currentClassifier = new SMO();
                currentClassifier.setC(valuesC[i]);
                RBFKernel kernel = new RBFKernel();
                kernel.setGamma(valuesG[j]);
                kernel.setGamma(valuesG[j]);
                
                Evaluation eval = new Evaluation(traindata);
                eval.crossValidateModel(currentClassifier, traindata, 2, rand);
                double currentScore = eval.matthewsCorrelationCoefficient(1);
                if( currentClassifier==null || currentScore>bestScore ) {
                	LOGGER.debug("new best score: " + currentScore + ", C: " + valuesC[i] + ", G: " + valuesG[j]);
                    bestScore = currentScore;
                    bestClassifier = currentClassifier;
                }
            }
        }
        internalClassifier = bestClassifier;
        internalClassifier.buildClassifier(traindata);
    }
    
    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        return internalClassifier.distributionForInstance(instance);
    }
    
}
