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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
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
 * <li>aucRoI</li>
 * <li>NofB20</li>
 * <li>RelB20</li>
 * <li>NofI80</li>
 * <li>RelI80</li>
 * <li>RelE80</li>
 * <li>NECM10</li>
 * <li>NECM15</li>
 * <li>NECM20</li>
 * <li>NECM25</li>
 * <li>cost</li>
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
                      Instances traindataOriginal,
                      List<ITrainer> trainers,
                      List<Double> efforts,
                      List<Double> numBugs,
                      Instances bugMatrix,
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
            this.output.append("version,size_test,size_training,size_training_original");
            this.output.append(",bias_test,bias_training,bias_training_original,prop1_defect,prop1_clean");
            for (ITrainer trainer : trainers) {
                this.output.append(",error_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",recall_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",precision_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",gscore_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",mcc_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",auc_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",aucec_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",aucAlberg_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",aucRoI_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",balance_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",nofb20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",nofi80_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",necm10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",necm25_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",cost_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fpr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fnr_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tp_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fn_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",tn_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",fp_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoM_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1Imp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1Imp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1Imp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1Imp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoMImp10_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1Imp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1Imp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1Imp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1Imp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoMImp20_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1Imp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1Imp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1Imp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1Imp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoMImp30_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1Imp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1Imp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1Imp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1Imp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoMImp40_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1to1Imp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1to1Imp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConst1toMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConst1toMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerConstNtoMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperConstNtoMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1to1Imp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1to1Imp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSize1toMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSize1toMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",lowerSizeNtoMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",upperSizeNtoMImp50_" + ((IWekaCompatibleTrainer) trainer).getName());
            }
            this.output.append(System.lineSeparator());
        }

        this.output.append(productName);
        this.output.append("," + testdata.numInstances());
        this.output.append("," + traindata.numInstances());
        this.output.append("," + traindataOriginal.numInstances());
        this.output.append("," + getBias(testdata));
        this.output.append("," + getBias(traindata));
        this.output.append("," + getBias(traindataOriginal));
        this.output.append("," + getProp1Defects(testdata, efforts));
        this.output.append("," + getProp1Clean(testdata, efforts));

        Evaluation eval = null;
        EffortMetricCalculator effortEval = null;
        CostBoundaryCalculator costBoundaryEval = null;
        CostBoundaryCalculator costBoundaryEvalImp10 = null;
        CostBoundaryCalculator costBoundaryEvalImp20 = null;
        CostBoundaryCalculator costBoundaryEvalImp30 = null;
        CostBoundaryCalculator costBoundaryEvalImp40 = null;
        CostBoundaryCalculator costBoundaryEvalImp50 = null;
        Iterator<Classifier> classifierIter = classifiers.iterator();
        Iterator<ExperimentResult> resultIter = experimentResults.iterator();
        while (classifierIter.hasNext()) {
            Classifier classifier = classifierIter.next();
            eval = createEvaluator(testdata, classifier);
            effortEval = new EffortMetricCalculator(testdata, classifier, efforts, numBugs);
            costBoundaryEval = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.0);
            costBoundaryEvalImp10 = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.1);
            costBoundaryEvalImp20 = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.2);
            costBoundaryEvalImp30 = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.3);
            costBoundaryEvalImp40 = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.4);
            costBoundaryEvalImp50 = new CostBoundaryCalculator(testdata, classifier, efforts, bugMatrix, eval, 0.5);
                        
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
            double aucAlberg = effortEval.getAUCAlberg();
            double aucWithRoI = effortEval.getAUCwRoI();
            double nofb20 = effortEval.getNofb20();
            double relb20 = effortEval.getRelb20();
            double nofi80 = effortEval.getNofi80();
            double reli80 = effortEval.getReli80();
            double rele80 = effortEval.getRele80();

            double necm10 = getNECM(eval, 10.0);
            double necm15 = getNECM(eval, 15.0);
            double necm20 = getNECM(eval, 20.0);
            double necm25 = getNECM(eval, 25.0);

            double nofbPredicted = effortEval.getNofBPredicted();
            double nofbMissed = effortEval.getNofBMissed();
            double cost = effortEval.getCost();
            
            this.output.append("," + eval.errorRate());
            this.output.append("," + eval.recall(1));
            this.output.append("," + eval.precision(1));
            this.output.append("," + eval.fMeasure(1));
            this.output.append("," + gmeasure);
            this.output.append("," + eval.matthewsCorrelationCoefficient(1));
            this.output.append("," + eval.areaUnderROC(1));
            this.output.append("," + aucec);
            this.output.append("," + aucAlberg);
            this.output.append("," + aucWithRoI);
            this.output.append("," + balance);
            this.output.append("," + nofb20);
            this.output.append("," + nofi80);
            this.output.append("," + necm10);
            this.output.append("," + necm25);
            this.output.append("," + cost);
            this.output.append("," + eval.truePositiveRate(1));
            this.output.append("," + eval.trueNegativeRate(1));
            this.output.append("," + eval.falsePositiveRate(1));
            this.output.append("," + eval.falseNegativeRate(1));
            this.output.append("," + eval.numTruePositives(1));
            this.output.append("," + eval.numFalseNegatives(1));
            this.output.append("," + eval.numTrueNegatives(1));
            this.output.append("," + eval.numFalsePositives(1));
            this.output.append("," + costBoundaryEval.getLowerConst1to1());
            this.output.append("," + costBoundaryEval.getUpperConst1to1());
            this.output.append("," + costBoundaryEval.getLowerConst1toM());
            this.output.append("," + costBoundaryEval.getUpperConst1toM());
            this.output.append("," + costBoundaryEval.getLowerConstNtoM());
            this.output.append("," + costBoundaryEval.getUpperConstNtoM());
            this.output.append("," + costBoundaryEval.getLowerSize1to1());
            this.output.append("," + costBoundaryEval.getUpperSize1to1());
            this.output.append("," + costBoundaryEval.getLowerSize1toM());
            this.output.append("," + costBoundaryEval.getUpperSize1toM());
            this.output.append("," + costBoundaryEval.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEval.getUpperSizeNtoM());
            this.output.append("," + costBoundaryEvalImp10.getLowerConst1to1());
            this.output.append("," + costBoundaryEvalImp10.getUpperConst1to1());
            this.output.append("," + costBoundaryEvalImp10.getLowerConst1toM());
            this.output.append("," + costBoundaryEvalImp10.getUpperConst1toM());
            this.output.append("," + costBoundaryEvalImp10.getLowerConstNtoM());
            this.output.append("," + costBoundaryEvalImp10.getUpperConstNtoM());
            this.output.append("," + costBoundaryEvalImp10.getLowerSize1to1());
            this.output.append("," + costBoundaryEvalImp10.getUpperSize1to1());
            this.output.append("," + costBoundaryEvalImp10.getLowerSize1toM());
            this.output.append("," + costBoundaryEvalImp10.getUpperSize1toM());
            this.output.append("," + costBoundaryEvalImp10.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEvalImp10.getUpperSizeNtoM());
            this.output.append("," + costBoundaryEvalImp20.getLowerConst1to1());
            this.output.append("," + costBoundaryEvalImp20.getUpperConst1to1());
            this.output.append("," + costBoundaryEvalImp20.getLowerConst1toM());
            this.output.append("," + costBoundaryEvalImp20.getUpperConst1toM());
            this.output.append("," + costBoundaryEvalImp20.getLowerConstNtoM());
            this.output.append("," + costBoundaryEvalImp20.getUpperConstNtoM());
            this.output.append("," + costBoundaryEvalImp20.getLowerSize1to1());
            this.output.append("," + costBoundaryEvalImp20.getUpperSize1to1());
            this.output.append("," + costBoundaryEvalImp20.getLowerSize1toM());
            this.output.append("," + costBoundaryEvalImp20.getUpperSize1toM());
            this.output.append("," + costBoundaryEvalImp20.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEvalImp20.getUpperSizeNtoM());
            this.output.append("," + costBoundaryEvalImp30.getLowerConst1to1());
            this.output.append("," + costBoundaryEvalImp30.getUpperConst1to1());
            this.output.append("," + costBoundaryEvalImp30.getLowerConst1toM());
            this.output.append("," + costBoundaryEvalImp30.getUpperConst1toM());
            this.output.append("," + costBoundaryEvalImp30.getLowerConstNtoM());
            this.output.append("," + costBoundaryEvalImp30.getUpperConstNtoM());
            this.output.append("," + costBoundaryEvalImp30.getLowerSize1to1());
            this.output.append("," + costBoundaryEvalImp30.getUpperSize1to1());
            this.output.append("," + costBoundaryEvalImp30.getLowerSize1toM());
            this.output.append("," + costBoundaryEvalImp30.getUpperSize1toM());
            this.output.append("," + costBoundaryEvalImp30.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEvalImp30.getUpperSizeNtoM());
            this.output.append("," + costBoundaryEvalImp40.getLowerConst1to1());
            this.output.append("," + costBoundaryEvalImp40.getUpperConst1to1());
            this.output.append("," + costBoundaryEvalImp40.getLowerConst1toM());
            this.output.append("," + costBoundaryEvalImp40.getUpperConst1toM());
            this.output.append("," + costBoundaryEvalImp40.getLowerConstNtoM());
            this.output.append("," + costBoundaryEvalImp40.getUpperConstNtoM());
            this.output.append("," + costBoundaryEvalImp40.getLowerSize1to1());
            this.output.append("," + costBoundaryEvalImp40.getUpperSize1to1());
            this.output.append("," + costBoundaryEvalImp40.getLowerSize1toM());
            this.output.append("," + costBoundaryEvalImp40.getUpperSize1toM());
            this.output.append("," + costBoundaryEvalImp40.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEvalImp40.getUpperSizeNtoM());
            this.output.append("," + costBoundaryEvalImp50.getLowerConst1to1());
            this.output.append("," + costBoundaryEvalImp50.getUpperConst1to1());
            this.output.append("," + costBoundaryEvalImp50.getLowerConst1toM());
            this.output.append("," + costBoundaryEvalImp50.getUpperConst1toM());
            this.output.append("," + costBoundaryEvalImp50.getLowerConstNtoM());
            this.output.append("," + costBoundaryEvalImp50.getUpperConstNtoM());
            this.output.append("," + costBoundaryEvalImp50.getLowerSize1to1());
            this.output.append("," + costBoundaryEvalImp50.getUpperSize1to1());
            this.output.append("," + costBoundaryEvalImp50.getLowerSize1toM());
            this.output.append("," + costBoundaryEvalImp50.getUpperSize1toM());
            this.output.append("," + costBoundaryEvalImp50.getLowerSizeNtoM());
            this.output.append("," + costBoundaryEvalImp50.getUpperSizeNtoM());

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
            result.setLowerConst1to1(costBoundaryEval.getLowerConst1to1());
            result.setUpperConst1to1(costBoundaryEval.getUpperConst1to1());
            result.setLowerConst1toM(costBoundaryEval.getLowerConst1toM());
            result.setUpperConst1toM(costBoundaryEval.getUpperConst1toM());
            result.setLowerConstNtoM(costBoundaryEval.getLowerConstNtoM());
            result.setUpperConstNtoM(costBoundaryEval.getUpperConstNtoM());
            result.setLowerSize1to1(costBoundaryEval.getLowerSize1to1());
            result.setUpperSize1to1(costBoundaryEval.getUpperSize1to1());
            result.setLowerSize1toM(costBoundaryEval.getLowerSize1toM());
            result.setUpperSize1toM(costBoundaryEval.getUpperSize1toM());
            result.setLowerSizeNtoM(costBoundaryEval.getLowerSizeNtoM());
            result.setUpperSizeNtoM(costBoundaryEval.getUpperSizeNtoM());
            result.setLowerConst1to1Imp10(costBoundaryEvalImp10.getLowerConst1to1());
            result.setUpperConst1to1Imp10(costBoundaryEvalImp10.getUpperConst1to1());
            result.setLowerConst1toMImp10(costBoundaryEvalImp10.getLowerConst1toM());
            result.setUpperConst1toMImp10(costBoundaryEvalImp10.getUpperConst1toM());
            result.setLowerConstNtoMImp10(costBoundaryEvalImp10.getLowerConstNtoM());
            result.setUpperConstNtoMImp10(costBoundaryEvalImp10.getUpperConstNtoM());
            result.setLowerSize1to1Imp10(costBoundaryEvalImp10.getLowerSize1to1());
            result.setUpperSize1to1Imp10(costBoundaryEvalImp10.getUpperSize1to1());
            result.setLowerSize1toMImp10(costBoundaryEvalImp10.getLowerSize1toM());
            result.setUpperSize1toMImp10(costBoundaryEvalImp10.getUpperSize1toM());
            result.setLowerSizeNtoMImp10(costBoundaryEvalImp10.getLowerSizeNtoM());
            result.setUpperSizeNtoMImp10(costBoundaryEvalImp10.getUpperSizeNtoM());
            result.setLowerConst1to1Imp20(costBoundaryEvalImp20.getLowerConst1to1());
            result.setUpperConst1to1Imp20(costBoundaryEvalImp20.getUpperConst1to1());
            result.setLowerConst1toMImp20(costBoundaryEvalImp20.getLowerConst1toM());
            result.setUpperConst1toMImp20(costBoundaryEvalImp20.getUpperConst1toM());
            result.setLowerConstNtoMImp20(costBoundaryEvalImp20.getLowerConstNtoM());
            result.setUpperConstNtoMImp20(costBoundaryEvalImp20.getUpperConstNtoM());
            result.setLowerSize1to1Imp20(costBoundaryEvalImp20.getLowerSize1to1());
            result.setUpperSize1to1Imp20(costBoundaryEvalImp20.getUpperSize1to1());
            result.setLowerSize1toMImp20(costBoundaryEvalImp20.getLowerSize1toM());
            result.setUpperSize1toMImp20(costBoundaryEvalImp20.getUpperSize1toM());
            result.setLowerSizeNtoMImp20(costBoundaryEvalImp20.getLowerSizeNtoM());
            result.setUpperSizeNtoMImp20(costBoundaryEvalImp20.getUpperSizeNtoM());
            result.setLowerConst1to1Imp30(costBoundaryEvalImp30.getLowerConst1to1());
            result.setUpperConst1to1Imp30(costBoundaryEvalImp30.getUpperConst1to1());
            result.setLowerConst1toMImp30(costBoundaryEvalImp30.getLowerConst1toM());
            result.setUpperConst1toMImp30(costBoundaryEvalImp30.getUpperConst1toM());
            result.setLowerConstNtoMImp30(costBoundaryEvalImp30.getLowerConstNtoM());
            result.setUpperConstNtoMImp30(costBoundaryEvalImp30.getUpperConstNtoM());
            result.setLowerSize1to1Imp30(costBoundaryEvalImp30.getLowerSize1to1());
            result.setUpperSize1to1Imp30(costBoundaryEvalImp30.getUpperSize1to1());
            result.setLowerSize1toMImp30(costBoundaryEvalImp30.getLowerSize1toM());
            result.setUpperSize1toMImp30(costBoundaryEvalImp30.getUpperSize1toM());
            result.setLowerSizeNtoMImp30(costBoundaryEvalImp30.getLowerSizeNtoM());
            result.setUpperSizeNtoMImp30(costBoundaryEvalImp30.getUpperSizeNtoM());
            result.setLowerConst1to1Imp40(costBoundaryEvalImp40.getLowerConst1to1());
            result.setUpperConst1to1Imp40(costBoundaryEvalImp40.getUpperConst1to1());
            result.setLowerConst1toMImp40(costBoundaryEvalImp40.getLowerConst1toM());
            result.setUpperConst1toMImp40(costBoundaryEvalImp40.getUpperConst1toM());
            result.setLowerConstNtoMImp40(costBoundaryEvalImp40.getLowerConstNtoM());
            result.setUpperConstNtoMImp40(costBoundaryEvalImp40.getUpperConstNtoM());
            result.setLowerSize1to1Imp40(costBoundaryEvalImp40.getLowerSize1to1());
            result.setUpperSize1to1Imp40(costBoundaryEvalImp40.getUpperSize1to1());
            result.setLowerSize1toMImp40(costBoundaryEvalImp40.getLowerSize1toM());
            result.setUpperSize1toMImp40(costBoundaryEvalImp40.getUpperSize1toM());
            result.setLowerSizeNtoMImp40(costBoundaryEvalImp40.getLowerSizeNtoM());
            result.setUpperSizeNtoMImp40(costBoundaryEvalImp40.getUpperSizeNtoM());
            result.setLowerConst1to1Imp50(costBoundaryEvalImp50.getLowerConst1to1());
            result.setUpperConst1to1Imp50(costBoundaryEvalImp50.getUpperConst1to1());
            result.setLowerConst1toMImp50(costBoundaryEvalImp50.getLowerConst1toM());
            result.setUpperConst1toMImp50(costBoundaryEvalImp50.getUpperConst1toM());
            result.setLowerConstNtoMImp50(costBoundaryEvalImp50.getLowerConstNtoM());
            result.setUpperConstNtoMImp50(costBoundaryEvalImp50.getUpperConstNtoM());
            result.setLowerSize1to1Imp50(costBoundaryEvalImp50.getLowerSize1to1());
            result.setUpperSize1to1Imp50(costBoundaryEvalImp50.getUpperSize1to1());
            result.setLowerSize1toMImp50(costBoundaryEvalImp50.getLowerSize1toM());
            result.setUpperSize1toMImp50(costBoundaryEvalImp50.getUpperSize1toM());
            result.setLowerSizeNtoMImp50(costBoundaryEvalImp50.getLowerSizeNtoM());
            result.setUpperSizeNtoMImp50(costBoundaryEvalImp50.getUpperSizeNtoM());
            for (IResultStorage storage : storages) {
                storage.addResult(result);
            }
        }

        this.output.append(System.lineSeparator());
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

    /**
     * <p>
     * Calculates the bias of a data set. That means the percentage of instances in
     * the training data that are defective.
     * </p>
     *
     * @param data data set to calculate the bias for
     * @return bias
     */
    public static double getBias(Instances data) {
        double count = 0.0;
        for (Instance instance : data) {
            if (instance.classValue() > 0.0) {
                count += 1.0;
            }
        }
        return (count / data.size());
    }

    /**
     * <p>
     * Calculates ratio of the effort of the largest percentile of defect artifacts by
     * the overall amount of defect artifacts.
     * </p>
     *
     * @param data test data set
     * @param efforts effort list from the test data set
     * @return Effort of the largest percentile of defect artifacts by overall amount of defect artifacts.
     */
    public static double getProp1Defects(Instances data, List<Double> efforts) {
        List<Double> effortDefect = new ArrayList<>();
        int amountDefectArtifacts = 0;
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).classValue() > 0.0) {
                amountDefectArtifacts += 1;
                effortDefect.add(efforts.get(i));
            }
        }
        effortDefect.sort(Collections.reverseOrder());
        int percentileIndex = (int) Math.ceil(effortDefect.size() / 100.0);
        double largestPercentileEffort = 0.0;
        for (int i = 0; i < percentileIndex; i++){
            largestPercentileEffort += effortDefect.get(i);
        }
        return largestPercentileEffort / (double)amountDefectArtifacts;
    }

    /**
     * <p>
     * Calculates ratio of the effort of the largest percentile of clean artifacts by
     * the overall amount of clean artifacts.
     * </p>
     *
     * @param data test data set
     * @param efforts effort list from the test data set
     * @return Effort of the largest percentile of clean artifacts by overall amount of clean artifacts.
     */
    public static double getProp1Clean(Instances data, List<Double> efforts) {
        List<Double> effortClean = new ArrayList<>();
        int amountCleanArtifacts = 0;
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).classValue() < 1.0) {
                amountCleanArtifacts += 1;
                effortClean.add(efforts.get(i));
            }
        }
        effortClean.sort(Collections.reverseOrder());
        int percentileIndex = (int)Math.ceil(effortClean.size() / 100.0);
        double largestPercentileEffort = 0.0;
        for (int i = 0; i < percentileIndex; i++){
            largestPercentileEffort += effortClean.get(i);
        }
        return largestPercentileEffort / (double)amountCleanArtifacts;
    }
}
