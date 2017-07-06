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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import de.ugoe.cs.cpdp.training.ITrainer;
import de.ugoe.cs.cpdp.training.IWekaCompatibleTrainer;
import de.ugoe.cs.cpdp.util.SortUtils;
import de.ugoe.cs.util.StringTools;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Base class for the evaluation of results of classifiers compatible with the {@link Classifier}
 * interface. For each classifier, the following metrics are calculated:
 * <ul>
 * <li>succHe: Success with recall>0.7, precision>0.5</li>
 * <li>succZi: Success with recall>=0.75, precision>=0.7, and error<=0.25</li>
 * <li>succG75: Success with gscore>0.75</li>
 * <li>succG60: Success with gscore>0.6</li>
 * <li>error</li>
 * <li>recall</li>
 * <li>precision</li>
 * <li>fscore</li>
 * <li>gscore</li>
 * <li>MCC</li>
 * <li>AUC</li>
 * <li>AUCEC (weighted by LOC, if applicable; 0.0 if LOC not available)</li>
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
                this.output.append(",succHe_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",succZi_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",succG75_" + ((IWekaCompatibleTrainer) trainer).getName());
                this.output.append(",succG60_" + ((IWekaCompatibleTrainer) trainer).getName());
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
        Iterator<Classifier> classifierIter = classifiers.iterator();
        Iterator<ExperimentResult> resultIter = experimentResults.iterator();
        while (classifierIter.hasNext()) {
            Classifier classifier = classifierIter.next();
            eval = createEvaluator(testdata, classifier);

            double pf =
                eval.numFalsePositives(1) / (eval.numFalsePositives(1) + eval.numTrueNegatives(1));
            double gmeasure = 2 * eval.recall(1) * (1.0 - pf) / (eval.recall(1) + (1.0 - pf));
            double aucec = calculateReviewEffort(testdata, classifier, efforts);
            double succHe = eval.recall(1) >= 0.7 && eval.precision(1) >= 0.5 ? 1.0 : 0.0;
            double succZi =
                eval.recall(1) >= 0.75 && eval.precision(1) >= 0.75 && eval.errorRate() <= 0.25
                    ? 1.0
                    : 0.0;
            double succG75 = gmeasure > 0.75 ? 1.0 : 0.0;
            double succG60 = gmeasure > 0.6 ? 1.0 : 0.0;

            this.output.append("," + succHe);
            this.output.append("," + succZi);
            this.output.append("," + succG75);
            this.output.append("," + succG60);
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
            result.setAucec(aucec);
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

    /**
     * <p>
     * Calculates the AUCEC.
     * </p>
     *
     * @param testdata
     *            the test data
     * @param classifier
     *            the classifier
     * @param efforts
     *            the effort information for each instance in the test data
     * @return AUCEC if efforts defined, -1 otherwise
     */
    @SuppressWarnings("boxing")
    private static double calculateReviewEffort(Instances testdata,
                                                Classifier classifier,
                                                List<Double> efforts)
    {
        if (efforts == null) {
            return -1.0;
        }

        final ScoreEffortPair[] scores = new ScoreEffortPair[testdata.size()];
        double totalEffort = 0.0d;
        double totalBugs = 0;
        for (int i = 0; i < testdata.numInstances(); i++) {
            try {
                double curEffort = efforts.get(i);
                double curScore = classifier.distributionForInstance(testdata.instance(i))[1];
                double curActualClass = testdata.instance(i).classValue();
                scores[i] = new ScoreEffortPair(curScore, curEffort, curActualClass);
                totalEffort += curEffort;
                totalBugs += curActualClass;
            }
            catch (Exception e) {
                throw new RuntimeException("unexpected error during the evaluation of the review effort",
                                           e);
            }
        }

        // sort by score (descending), in case of equal scores use size (ascending)
        Arrays.sort(scores, Collections.reverseOrder());

        // now calculate the Riemann integral
        // y-axis = relative number of bugs found
        // x-axis = relative effort related overall project size
        double aucec = 0.0;
        double relativeBugsFound = 0.0;
        for (ScoreEffortPair score : scores) {
            double curRelativeEffort = score.getEffort() / totalEffort;
            double curRelativeBugsFound = score.getActualClass() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;
            aucec += curRelativeEffort * relativeBugsFound; // simple Riemann integral
        }
        return aucec;
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
     * Helper class for AUCEC calculation
     * </p>
     * 
     * @author Steffen Herbold
     */
    private static class ScoreEffortPair implements Comparable<ScoreEffortPair> {

        /**
         * Defect prediction score of the pair
         */
        private final double score;

        /**
         * Associated review effort
         */
        private final double effort;

        /**
         * Class of the instance
         */
        private final double actualClass;

        @SuppressWarnings("hiding")
        public ScoreEffortPair(double score, double effort, double actualClass) {
            this.score = score;
            this.effort = effort;
            this.actualClass = actualClass;
        }

        /**
         * @return the effort
         */
        public double getEffort() {
            return effort;
        }

        /**
         * @return the actual class
         */
        public double getActualClass() {
            return actualClass;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(ScoreEffortPair other) {
            if (score > other.score || (score == other.score && effort < other.effort)) {
                return 1;
            }
            if (score < other.score || (score == other.score && effort > other.effort)) {
                return -1;
            }
            return 0;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "score=" + score + ", effort=" + effort + ", actualClass=" + actualClass;
        }

    }
}
