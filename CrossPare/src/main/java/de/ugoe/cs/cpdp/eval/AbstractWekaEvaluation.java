// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.eval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.util.StringTools;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * Base class for the evaluation of results of classifiers compatible with the {@link Classifier}
 * interface. For each classifier, the following metrics are calculated:
 * <ul>
 * <li>error</li>
 * <li>recall</li>
 * <li>precision</li>
 * <li>fscore</li>
 * <li>gscore</li>
 * <li>MCC</li>
 * <li>AUC</li>
 * <li>balance</li>
 * <li>AUCEC</li>
 * <li>NofB20</li>
 * <li>RelB20</li>
 * <li>NofI80</li>
 * <li>RelI80</li>
 * <li>RelE80</li>
 * <li>NECM15</li>
 * <li>NECM20</li>
 * <li>NECM25</li>
 * <li>tpr: true positive rate</li>
 * <li>tnr: true negative rate</li>
 * <li>fpr: false positive rate</li>
 * <li>fnr: false negative rate</li>
 * <li>tp: true positives</li>
 * <li>fp: false positives</li>
 * <li>tn: true negatives</li>
 * <li>fn: false negatives</li>
 * </ul>
 * 
 * @author Steffen Herbold
 */
public abstract class AbstractWekaEvaluation implements IEvaluationStrategy {

    /**
     * writer for the evaluation results
     */
    private PrintWriter output = new PrintWriter(System.out);

    /**
     * flag that defines if the output is the system out
     */
    private boolean outputIsSystemOut = true;

    /**
     * name of the configuration
     */
    private String configurationName = "default";

    /**
     * Creates the Weka evaluator. Allows the creation of the evaluator in different ways, e.g., for
     * cross-validation or evaluation on the test data.
     * 
     * @param testdata
     *            test data
     * @param classifier
     *            classifier used
     * @return evaluator
     */
    protected abstract Evaluation createEvaluator(Instances testdata, Classifier classifier);

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.eval.EvaluationStrategy#apply(weka.core.Instances, weka.core.Instances,
     * java.util.List, boolean)
     */
    @Override
    public void apply(Instances testdata,
                      Instances traindata,
                      List<ITrainer> trainers,
                      List<Double> efforts,
                      List<Double> numBugs,
                      boolean writeHeader,
                      List<IResultStorage> storages)
    {
        final List<Classifier> classifiers = new LinkedList<>();
        final List<ExperimentResult> experimentResults = new LinkedList<>();
        String productName = testdata.relationName();

        for (ITrainer trainer : trainers) {
            if (trainer instanceof IWekaCompatibleTrainer) {
                classifiers.add(((IWekaCompatibleTrainer) trainer).getClassifier());
                experimentResults
                    .add(new ExperimentResult(this.configurationName, productName,
                                              ((IWekaCompatibleTrainer) trainer).getName()));
            }
            else {
                throw new RuntimeException("The selected evaluator only support Weka classifiers");
            }
        }

        if (writeHeader) {
            this.output.append("version,size_test,size_training");
            for (ITrainer trainer : trainers) {
                this.output.append(",error_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",recall_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",precision_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",gscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",mcc_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",auc_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",aucec_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tp_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fn_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tn_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fp_" + ((IWekaCompatibleTrainer) trainer).getName());
            }
            this.output.append(StringTools.ENDLINE);
        }

        this.output.append(productName);
        this.output.append("," + testdata.numInstances());
        this.output.append("," + traindata.numInstances());

        Evaluation eval = null;
        EffortMetricCalculator effortEval = null;
        Iterator<Classifier> classifierIter = classifiers.iterator();
        Iterator<ExperimentResult> resultIter = experimentResults.iterator();
        while (classifierIter.hasNext()) {
            Classifier classifier = classifierIter.next();
            eval = createEvaluator(testdata, classifier);
            effortEval = new EffortMetricCalculator(testdata, classifier, efforts, numBugs);
            
            double pf =
                eval.numFalsePositives(1) / (eval.numFalsePositives(1) + eval.numTrueNegatives(1));
            double gmeasure;
            if( eval.recall(1)==0.0 && pf==1.0 ) {
                gmeasure = 0.0;
            } else {
                gmeasure = 2 * eval.recall(1) * (1.0 - pf) / (eval.recall(1) + (1.0 - pf));
            }
            double balance = 1.0-Math.sqrt(Math.pow(1-eval.recall(1),2)+Math.pow(pf,2))/Math.sqrt(2);
            double aucec = effortEval.getAUCEC();
            double nofb20 = effortEval.getNofb20();
            double relb20 = effortEval.getRelb20();
            double nofi80 = effortEval.getNofi80();
            double reli80 = effortEval.getReli80();
            double rele80 = effortEval.getRele80();
            
            double necm15 = getNECM(eval, 15.0);
            double necm20 = getNECM(eval, 20.0);
            double necm25 = getNECM(eval, 25.0);
            
            double nofbPredicted = effortEval.getNofBPredicted();
            double nofbMissed = effortEval.getNofBMissed();
            
            this.output.append("," + eval.errorRate());
            this.output.append("," + eval.recall(1));
            this.output.append("," + eval.precision(1));
            this.output.append("," + eval.fMeasure(1));
            this.output.append("," + gmeasure);
            this.output.append("," + eval.matthewsCorrelationCoefficient(1));
            this.output.append("," + eval.areaUnderROC(1));
            this.output.append("," + aucec);
            this.output.append("," + eval.truePositiveRate(1));
            this.output.append("," + eval.trueNegativeRate(1));
            this.output.append("," + eval.falsePositiveRate(1));
            this.output.append("," + eval.falseNegativeRate(1));
            this.output.append("," + eval.numTruePositives(1));
            this.output.append("," + eval.numFalseNegatives(1));
            this.output.append("," + eval.numTrueNegatives(1));
            this.output.append("," + eval.numFalsePositives(1));

            ExperimentResult result = resultIter.next();
            result.setSizeTestData(testdata.numInstances());
            result.setSizeTrainingData(traindata.numInstances());
            result.setError(eval.errorRate());
            result.setRecall(eval.recall(1));
            result.setPrecision(eval.precision(1));
            result.setFscore(eval.fMeasure(1));
            result.setGscore(gmeasure);
            result.setMcc(eval.matthewsCorrelationCoefficient(1));
            result.setAuc(eval.areaUnderROC(1));
            result.setBalance(balance);
            result.setAucec(aucec);
            result.setNofb20(nofb20);
            result.setRelb20(relb20);
            result.setNofi80(nofi80);
            result.setReli80(reli80);
            result.setRele80(rele80);
            result.setNecm15(necm15);
            result.setNecm20(necm20);
            result.setNecm25(necm25);
            result.setNofbPredicted(nofbPredicted);
            result.setNofbMissed(nofbMissed);
            result.setTpr(eval.truePositiveRate(1));
            result.setTnr(eval.trueNegativeRate(1));
            result.setFpr(eval.falsePositiveRate(1));
            result.setFnr(eval.falseNegativeRate(1));
            result.setTp(eval.numTruePositives(1));
            result.setFn(eval.numFalseNegatives(1));
            result.setTn(eval.numTrueNegatives(1));
            result.setFp(eval.numFalsePositives(1));
            for (IResultStorage storage : storages) {
                storage.addResult(result);
            }
        }

        this.output.append(StringTools.ENDLINE);
        this.output.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.Parameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        if (this.output != null && !this.outputIsSystemOut) {
            this.output.close();
        }
        if ("system.out".equals(parameters) || "".equals(parameters)) {
            this.output = new PrintWriter(System.out);
            this.outputIsSystemOut = true;
        }
        else {
            try {
                this.output = new PrintWriter(new FileOutputStream(parameters));
                this.outputIsSystemOut = false;
                int filenameStart = parameters.lastIndexOf('/') + 1;
                int filenameEnd = parameters.lastIndexOf('.');
                this.configurationName = parameters.substring(filenameStart, filenameEnd);
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * <p>
     * Calculates the normalized expected cost of misclassification through a ratio
     * of the cost between the cost of false positives (unnecessary review effort) vs
     * the cost of false negatives (due to the bug introduced in the final product).
     * </p>
     *
     * @param eval evaluator used
     * @param factor ratio between the costs as cost_fn/cost_fp
     * @return NECM value
     */
    public static double getNECM(Evaluation eval, double factor) {
        return (eval.numFalsePositives(1)+factor*eval.numFalseNegatives(1))/
                (eval.numInstances());
    }
}
