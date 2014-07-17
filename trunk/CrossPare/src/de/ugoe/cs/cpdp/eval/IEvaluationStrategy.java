package de.ugoe.cs.cpdp.eval;

import java.util.List;

import de.ugoe.cs.cpdp.IParameterizable;
import de.ugoe.cs.cpdp.training.ITrainer;

import weka.core.Instances;

/**
 * Interface for evaluation strategies to evaluate the performance of classifiers. 
 * @author Steffen Herbold
 */
public interface IEvaluationStrategy extends IParameterizable {

	/**
	 * Applies the evaluation strategy. 
	 * @param testdata test data for the evaluation
	 * @param traindata training data used
	 * @param trainers list of training algorithms used to train the classifiers
	 * @param writeHeader if true, a header line for the results file is written (may not be applicable)
	 */
	void apply(Instances testdata, Instances traindata, List<ITrainer> trainers, boolean writeHeader);
}
