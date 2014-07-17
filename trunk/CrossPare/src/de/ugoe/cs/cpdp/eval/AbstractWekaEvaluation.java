package de.ugoe.cs.cpdp.eval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.WekaCompatibleTrainer;
import de.ugoe.cs.util.StringTools;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Base class for the evaluation of results of classifiers compatible with the {@link Classifier} interface.
 * For each classifier, the following metrics are calculated:
 * <ul>
 *  <li>Success with recall>0.7, precision>0.5</li>
 *  <li>Success with recall>0.7, precision>0.5</li>
 *  <li>Success with gscore>0.75</li>
 *  <li>Success with gscore>0.6</li>
 *  <li>error rate</li>
 *  <li>recall</li>
 *  <li>precision</li>
 *  <li>fscore</li>
 *  <li>gscore</li>
 *  <li>AUC</li>
 *  <li>AUCEC (weighted by LOC, if applicable; 0.0 if LOC not available)</li>
 *  <li>true positive rate</li>
 *  <li>true negative rate</li>
 *  <li>true positives</li>
 *  <li>false positives</li>
 *  <li>true negatives</li>
 *  <li>false negatives</li>
 * </ul> 
 * @author Steffen Herbold
 */
public abstract class AbstractWekaEvaluation implements IEvaluationStrategy {

	/**
	 * writer for the evaluation results
	 */
	private PrintWriter output = new PrintWriter(System.out);
	
	private boolean outputIsSystemOut = true;
	
	/**
	 * Creates the weka evaluator. Allows the creation of the evaluator in different ways, e.g., for cross-validation
	 * or evaluation on the test data.
	 * @param testdata test data
	 * @param classifier classifier used
	 * @return evaluator
	 */
	protected abstract Evaluation createEvaluator(Instances testdata, Classifier classifier);
	
	/*
	 * (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.eval.EvaluationStrategy#apply(weka.core.Instances, weka.core.Instances, java.util.List, boolean)
	 */
	@Override
	public void apply(Instances testdata, Instances traindata, List<ITrainer> trainers,
			boolean writeHeader) {
		final List<Classifier> classifiers = new LinkedList<Classifier>();
		for( ITrainer trainer : trainers ) {
			if( trainer instanceof WekaCompatibleTrainer ) {
				classifiers.add(((WekaCompatibleTrainer) trainer).getClassifier());
			} else {
				throw new RuntimeException("The selected evaluator only support Weka classifiers");
			}
		}
		
		if( writeHeader ) {
			output.append("version,size_test,size_training");
			for( ITrainer trainer : trainers ) {
				output.append(",succHe_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",succZi_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",succG75_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",succG60_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",error_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",recall_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",precision_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",fscore_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",gscore_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",mcc_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",auc_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",aucec_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",tpr_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",tnr_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",tp_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",fn_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",tn_" + ((WekaCompatibleTrainer) trainer).getName());
				output.append(",fp_" + ((WekaCompatibleTrainer) trainer).getName());
			}
			output.append(StringTools.ENDLINE);
		}
		
		output.append(testdata.relationName());
		output.append("," + testdata.numInstances());	
		output.append("," + traindata.numInstances());
		
		Evaluation eval = null;
		for( Classifier classifier : classifiers ) {
			eval = createEvaluator(testdata, classifier);
			
			double pf = eval.numFalsePositives(1)/(eval.numFalsePositives(1)+eval.numTrueNegatives(1));
			double gmeasure = 2*eval.recall(1)*(1.0-pf)/(eval.recall(1)+(1.0-pf));
			double mcc = (eval.numTruePositives(1)*eval.numTrueNegatives(1)-eval.numFalsePositives(1)*eval.numFalseNegatives(1))/Math.sqrt((eval.numTruePositives(1)+eval.numFalsePositives(1))*(eval.numTruePositives(1)+eval.numFalseNegatives(1))*(eval.numTrueNegatives(1)+eval.numFalsePositives(1))*(eval.numTrueNegatives(1)+eval.numFalseNegatives(1)));
			double aucec = calculateReviewEffort(testdata, classifier);
			
			if( eval.recall(1)>=0.7 && eval.precision(1) >= 0.5 ) {
				output.append(",1");
			} else {
				output.append(",0");
			}
			
			if( eval.recall(1)>=0.7 && eval.precision(1) >= 0.7 ) {
				output.append(",1");
			} else {
				output.append(",0");
			}
			
			if( gmeasure>0.75 ) {
				output.append(",1");
			} else {
				output.append(",0");
			}
			
			if( gmeasure>0.6 ) {
				output.append(",1");
			} else {
				output.append(",0");
			}
			
			output.append("," + eval.errorRate());
			output.append("," + eval.recall(1));
			output.append("," + eval.precision(1));
			output.append("," + eval.fMeasure(1));
			output.append("," + gmeasure);
			output.append("," + mcc);
			output.append("," + eval.areaUnderROC(1));
			output.append("," + aucec);
			output.append("," + eval.truePositiveRate(1));
			output.append("," + eval.trueNegativeRate(1));
			output.append("," + eval.numTruePositives(1));
			output.append("," + eval.numFalseNegatives(1));
			output.append("," + eval.numTrueNegatives(1));
			output.append("," + eval.numFalsePositives(1));
		}
		
		output.append(StringTools.ENDLINE);
		output.flush();
	}
	
	private double calculateReviewEffort(Instances testdata, Classifier classifier) {
		
		final Attribute loc = testdata.attribute("loc");
		if( loc==null ) {
			return 0.0;
		}
				
		final List<Integer> bugPredicted = new ArrayList<>();
		final List<Integer> nobugPredicted = new ArrayList<>(); 
		double totalLoc = 0.0d;
		int totalBugs = 0;
		for( int i=0 ; i<testdata.numInstances() ; i++ ) {
			try {
				if( Double.compare(classifier.classifyInstance(testdata.instance(i)),0.0d)==0 ) {
					nobugPredicted.add(i);
				} else {
					bugPredicted.add(i);
				}
			} catch (Exception e) {
				throw new RuntimeException("unexpected error during the evaluation of the review effort", e);
			}
			if(Double.compare(testdata.instance(i).classValue(),1.0d)==0) {
				totalBugs++;
			}
			totalLoc += testdata.instance(i).value(loc);
		}
		
		final List<Double> reviewLoc = new ArrayList<>(testdata.numInstances());
		final List<Double> bugsFound = new ArrayList<>(testdata.numInstances());
		
		double currentBugsFound = 0;
		
		while( !bugPredicted.isEmpty() ) {
			double minLoc = Double.MAX_VALUE;
			int minIndex = -1;
			for( int i=0 ; i<bugPredicted.size() ; i++ ) {
				double currentLoc = testdata.instance(bugPredicted.get(i)).value(loc);
				if( currentLoc<minLoc ) {
					minIndex = i;
					minLoc = currentLoc;
				}
			}
			if( minIndex!=-1 ) {
				reviewLoc.add(minLoc/totalLoc);
				
				currentBugsFound += testdata.instance(bugPredicted.get(minIndex)).classValue();
				bugsFound.add(currentBugsFound);
				
				bugPredicted.remove(minIndex);
			} else {
				throw new RuntimeException("Shouldn't happen!");
			}
		}
		
		while( !nobugPredicted.isEmpty() ) {
			double minLoc = Double.MAX_VALUE;
			int minIndex = -1;
			for( int i=0 ; i<nobugPredicted.size() ; i++ ) {
				double currentLoc = testdata.instance(nobugPredicted.get(i)).value(loc);
				if( currentLoc<minLoc ) {
					minIndex = i;
					minLoc = currentLoc;
				}
			}
			if( minIndex!=-1 ) {				
				reviewLoc.add(minLoc/totalLoc);
				
				currentBugsFound += testdata.instance(nobugPredicted.get(minIndex)).classValue();
				bugsFound.add(currentBugsFound);
				nobugPredicted.remove(minIndex);
			} else {
				throw new RuntimeException("Shouldn't happen!");
			}
		}
		
		double auc = 0.0;
		for( int i=0 ; i<bugsFound.size() ; i++ ) {
			auc += reviewLoc.get(i)*bugsFound.get(i)/totalBugs;
		}
		
		return auc;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ugoe.cs.cpdp.Parameterizable#setParameter(java.lang.String)
	 */
	@Override
	public void setParameter(String parameters) {
		if( output!=null && !outputIsSystemOut ) {
			output.close();
		}
		if( "system.out".equals(parameters) || "".equals(parameters) ) {
			output = new PrintWriter(System.out);
			outputIsSystemOut = true;
		} else {
			try {
				output = new PrintWriter(new FileOutputStream(parameters));
				outputIsSystemOut = false;
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
