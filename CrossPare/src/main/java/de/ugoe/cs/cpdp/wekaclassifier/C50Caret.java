package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.mlr.MLRClassifier;
import weka.core.Instances;
import weka.core.SelectedTag;

/**
 * <p>
 * Implements a Caret-wrapper for C5.0 with MLR.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class C50Caret extends MLRClassifier {
    
    /**  */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        Random rand = new Random();
        
        MLRClassifier withTree = new MLRClassifier();
        withTree.setRLearner(new SelectedTag(MLRClassifier.R_CLASSIF_C50, MLRClassifier.TAGS_LEARNER));
        withTree.setLearnerParams("rules=FALSE");
        
        Evaluation evalTree = new Evaluation(traindata);
        evalTree.crossValidateModel(withTree, traindata, 5, rand);
        double mccTree = evalTree.matthewsCorrelationCoefficient(1);
        
        MLRClassifier withRules = new MLRClassifier();
        withRules.setRLearner(new SelectedTag(MLRClassifier.R_CLASSIF_C50, MLRClassifier.TAGS_LEARNER));
        withRules.setLearnerParams("rules=FALSE");
        
        Evaluation evalRules = new Evaluation(traindata);
        evalRules.crossValidateModel(withRules, traindata, 5, rand);
        double mccRules = evalRules.matthewsCorrelationCoefficient(1);
        
        this.setRLearner(new SelectedTag(MLRClassifier.R_CLASSIF_C50, MLRClassifier.TAGS_LEARNER));
        if (mccRules > mccTree) {
            this.setLearnerParams("rules=TRUE");
        }
        else {
            this.setLearnerParams("rules=FALSE");
        }
        super.buildClassifier(traindata);
    }
}
