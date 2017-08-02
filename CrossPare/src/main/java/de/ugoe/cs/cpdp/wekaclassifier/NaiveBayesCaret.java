
package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Random;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

/**
 * <p>
 * Extends WEKA's NaiveBayes implementation with automated selection of using kernel estimator
 * (option -K). We pick the one with the better MCC in 5x5 CV.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class NaiveBayesCaret extends NaiveBayes {

    /**  */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.bayes.NaiveBayes#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        Random rand = new Random();
        
        NaiveBayes classifierWithKernel = new NaiveBayes();
        // copy options
        classifierWithKernel.setBatchSize(this.getBatchSize());
        classifierWithKernel.setNumDecimalPlaces(this.getNumDecimalPlaces());
        classifierWithKernel.setUseSupervisedDiscretization(this.getUseSupervisedDiscretization());
        // set kernel
        classifierWithKernel.setUseKernelEstimator(true);
        Evaluation evalWithKernel = new Evaluation(traindata);
        evalWithKernel.crossValidateModel(classifierWithKernel, traindata, 5, rand);
        double mccWithKernel = evalWithKernel.matthewsCorrelationCoefficient(1);

        NaiveBayes classifierWithoutKernel = new NaiveBayes();
        // copy options
        classifierWithoutKernel.setBatchSize(this.getBatchSize());
        classifierWithoutKernel.setNumDecimalPlaces(this.getNumDecimalPlaces());
        classifierWithKernel.setUseSupervisedDiscretization(this.getUseSupervisedDiscretization());
        // set kernel
        classifierWithoutKernel.setUseKernelEstimator(false);
        Evaluation evalWithoutKernel = new Evaluation(traindata);
        evalWithoutKernel.crossValidateModel(classifierWithoutKernel, traindata, 5, rand);
        double mccWithoutKernel = evalWithoutKernel.matthewsCorrelationCoefficient(1);

        if (mccWithKernel > mccWithoutKernel) {
            this.setUseKernelEstimator(true);
        }
        else {
            this.setUseKernelEstimator(false);
        }
        super.buildClassifier(traindata);
    }

}
