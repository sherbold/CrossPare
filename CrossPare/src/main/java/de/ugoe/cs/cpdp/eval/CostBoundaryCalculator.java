package de.ugoe.cs.cpdp.eval;

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class CostBoundaryCalculator {
	
	private final Evaluation eval;
	
	private final double probQAFailure;
	
	private final double effortTrue;
	
	private final double effortFalse;
	
	private final double bugCountTrue;
	
	private final double bugCountFalse;
	
	private final double bugFound;
	
	private final double bugMiss;
	
	public CostBoundaryCalculator(Instances testdata, Classifier classifier, List<Double> efforts, Instances bugMatrix, Evaluation eval, double probQAFailure) {
		// TODO make robust against nulls (efforts, bugMatrix)
		this.eval = eval;
		this.probQAFailure = probQAFailure;
		
        double[][] distributions;
        double effortTrueTmp = 0.0;
        double effortFalseTmp = 0.0;
        double bugCountTrueTmp = 0.0;
        double bugCountFalseTmp = 0.0;
        double bugFoundTmp = 0.0;
        double bugMissTmp = 0.0;
        
        if(efforts!=null && bugMatrix!=null) {
	        try {
	            distributions = ((AbstractClassifier) classifier).distributionsForInstances(testdata);
	        }
	        catch (Exception e) {
	            throw new RuntimeException("unexpected error during the evaluation of the review effort",
	                                       e);
	        }
	        for (int i = 0; i < testdata.numInstances(); i++) {
	        	double curBugCount = StatUtils.sum(bugMatrix.get(i).toDoubleArray());
	        	if( distributions[i][1]>distributions[i][0] ) {
	        		effortTrueTmp += efforts.get(i);
	        		bugCountTrueTmp += curBugCount;
	        		
	        	} else {
	        		effortFalseTmp += efforts.get(i);
	        		bugCountFalseTmp += curBugCount;
	        	}
	        }
	        
	        
	        for (int j=0; j<bugMatrix.numAttributes(); j++) {
	        	int numPredicted = 0;
	        	int numAffected = 0;
	            for (int i = 0; i < testdata.numInstances(); i++) {
	            	if (bugMatrix.get(i).value(j)>0.0) {
	            		numAffected++;
	        			if (distributions[i][1]>distributions[i][0]) {
	        				numPredicted++;
	        			}
	            	}
	            }
	            if (numPredicted<numAffected) {
	            	bugMissTmp += Math.pow(1-probQAFailure, numAffected);
	            } else {
	            	bugFoundTmp += Math.pow(1-probQAFailure, numAffected);
	            }
	        }
        }
        
        this.effortTrue = effortTrueTmp;
        this.effortFalse = effortFalseTmp;
        this.bugCountTrue = bugCountTrueTmp;
        this.bugCountFalse = bugCountFalseTmp;
        this.bugFound = bugFoundTmp;
        this.bugMiss = bugMissTmp;
	}

	public double getLowerConst1to1() {
		double val = (eval.numTruePositives(1)+eval.numFalsePositives(1))/(eval.numTruePositives(1)*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperConst1to1() {
		double val = (eval.numTrueNegatives(1)+eval.numFalseNegatives(1))/(eval.numFalseNegatives(1)*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getLowerSize1to1() {
		double val = effortTrue/(eval.numTruePositives(1)*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperSize1to1() {
		double val = effortFalse/(eval.numFalseNegatives(1)*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getLowerConst1toM() {
		double val = (eval.numTruePositives(1)+eval.numFalsePositives(1))/(bugCountTrue*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperConst1toM() {
		double val = (eval.numTrueNegatives(1)+eval.numFalseNegatives(1))/(bugCountFalse*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getLowerSize1toM() {
		double val = effortTrue/(bugCountTrue*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperSize1toM() {
		double val = effortFalse/(bugCountFalse*(1.0-probQAFailure));
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getLowerConstNtoM() {
		double val = (eval.numTruePositives(1)+eval.numFalsePositives(1))/bugFound;
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperConstNtoM() {
		double val = (eval.numTrueNegatives(1)+eval.numFalseNegatives(1))/bugMiss;
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getLowerSizeNtoM() {
		double val = effortTrue/bugFound;
		return Double.isFinite(val)  ? val : -1;
	}
	
	public double getUpperSizeNtoM() {
		double val = effortFalse/bugMiss;
		return Double.isFinite(val)  ? val : -1;
	}
}
