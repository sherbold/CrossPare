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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * <p>
 * Implements a template method for effort metric calculations
 * </p>
 * 
 * @author Steffen Herbold
 */
public class EffortMetricCalculator {

    /**
     * sorted scores for the test data
     */
    final ScoreEffortPair[] scores;

    /**
     * total sum of effort of the test data
     */
    final double totalEffort;

    /**
     * total sum of bugs of the test data
     */
    final double totalBugs;

    /**
     * <p>
     * Creates a new instance.
     * </p>
     *
     * @param testdata
     *            the test data
     * @param classifier
     *            the classifier
     * @param efforts
     *            the effort information for each instance in the test data
     * @param numBugs
     *            the bug counts for each instance in the test data
     * @return value of the effort metric if efforts defined, -1 otherwise
     */
    public EffortMetricCalculator(Instances testdata,
                                  Classifier classifier,
                                  List<Double> efforts,
                                  List<Double> numBugs)
    {
        if (efforts == null) {
            // do not initialize
            scores = null;
            totalEffort = -1;
            totalBugs = -1;
        }
        else {
            this.scores = new ScoreEffortPair[testdata.size()];
            double tmpTotalEffort = 0.0d;
            double tmpTotalBugs = 0;
            double[][] distributions;
            try {
                distributions = ((AbstractClassifier) classifier).distributionsForInstances(testdata);
            }
            catch (Exception e) {
                throw new RuntimeException("unexpected error during the evaluation of the review effort",
                                           e);
            }
            for (int i = 0; i < testdata.numInstances(); i++) {
                double curEffort = efforts.get(i);
                double curScore = distributions[i][1];
                double curClass = distributions[i][1]>distributions[i][0] ? 1.0 : 0.0;
                double curBugCount = numBugs.get(i);
                this.scores[i] = new ScoreEffortPair(curScore, curClass, curEffort, curBugCount);
                tmpTotalEffort += curEffort;
                tmpTotalBugs += curBugCount;
            }
            this.totalEffort = tmpTotalEffort;
            this.totalBugs = tmpTotalBugs;
            // sort by score (descending), in case of equal scores use size (ascending)
            Arrays.sort(scores, Collections.reverseOrder());
        }
    }

    /**
     * Calculates AUCEC ("area under the cost-effectiveness curve"), i.e.,
     * a ROC curve of relative bugs found vs relative review effort.
     *
     * @return AUCEC value
     */
    public double getAUCEC() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        // Calculate the Riemann integral
        // y-axis = relative number of bugs found
        // x-axis = relative effort related overall project size
        double aucec = 0.0;
        double relativeBugsFound = 0.0;
        for (ScoreEffortPair score : scores) {
            double curRelativeEffort = score.getEffort() / totalEffort;
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;
            aucec += curRelativeEffort * relativeBugsFound; // simple Riemann integral
        }
        return aucec;
    }

    /**
     * Calculates the AUC of an Alberg diagram, i.e.,
     * a ROC curve with percentage of found bugs vs percentage of modules.
     *
     * @return AUC Alberg value
     */
    public double getAUCAlberg() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        double relativeBugsFound = 0.0;
        double aucAlberg = 0.0;
        double percentagePerModule = 1.0 / (double) scores.length;
        for (ScoreEffortPair score : scores) {
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;
            aucAlberg += relativeBugsFound * percentagePerModule;
        }
        return aucAlberg;
    }

    /**
     * Calculates the area under the curve of the ROC defined by recall and false positive rate in a specific
     * region of interest. The region is defined by thresholds for recall and fpr, which are given by
     * a baseline model based on the class level imbalance. I.e., only the area counts where the recall is bigger
     * and the fpr is smaller than the number of defective instances by the number of all instances.
     *
     * @return AUC in region of interest
     */
    public double getAUCwRoI() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        int numInstances = scores.length;
        double numPositives = 0.0;
        int[] cumLabel = new int[numInstances];
        if (scores[0].getBugCount() > 0.0){
            numPositives += 1;
            cumLabel[0] = 1;
        }
        else {
            cumLabel[0] = 0;
        }
        for (int i=1; i< numInstances; i++){
            if (scores[i].getBugCount() > 0.0){
                numPositives += 1;
                cumLabel[i] = cumLabel[i-1] + 1;
            }
            else {
                cumLabel[i] = cumLabel[i-1];
            }
        }
        double ratio = numPositives / numInstances;
        double numNegatives = numInstances - numPositives;
        double curRecall;
        double curFPR;
        double nextFPR;
        double auc = 0.0;
        for (int i=0; i < numInstances; i++){
            curRecall = cumLabel[i] / numPositives;
            curFPR = ((i+1) - cumLabel[i]) / numNegatives;
            if (i < numInstances - 1){
                nextFPR = ((i+2) - cumLabel[i+1]) / numNegatives;
            }
            else {
                nextFPR = ratio;
            }
            if (curRecall <= ratio){
                curRecall = 0.0;
            }
            else {
                curRecall -= ratio;
            }
            if (nextFPR > ratio){
                auc += curRecall * (ratio - curFPR);
                break;
            }
            else {
                auc += curRecall * (nextFPR - curFPR);
            }
        }
        return auc;
    }

    /**
     * Calculate the number of bugs found if 20 percent of the source code are reviewed.
     *
     * @return NofB20 value
     */
    public double getNofb20() {
        if( scores==null ) {
            return -1;
        }
        double bugsFound = 0.0;
        double relativeEffort = 0.0;
        for (ScoreEffortPair score : scores) {
            double curRelativeEffort = score.getEffort() / totalEffort;
            relativeEffort += curRelativeEffort;
            if (relativeEffort + curRelativeEffort > 0.2) {
                // if this next instance is taken into account, the effort will be greater than 20%
                break;
            }
            bugsFound += score.getBugCount();
        }
        return bugsFound;
    }

    /**
     * Calculate the percentage of bugs found if 20 percent of the source code are reviewed.
     *
     * @return RelB20 value
     */
    public double getRelb20() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        double relativeBugsFound = 0.0;
        double relativeEffort = 0.0;
        for (ScoreEffortPair score : scores) {
            double curRelativeEffort = score.getEffort() / totalEffort;
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeEffort += curRelativeEffort;
            if (relativeEffort + curRelativeEffort > 0.2) {
                // if this next instance is taken into account, the effort will be greater than 20%
                break;
            }
            relativeBugsFound += curRelativeBugsFound;

        }
        return relativeBugsFound;
    }

    /**
     * Calculate the number of instances visited until 80 percent of the bugs are found.
     *
     * @return NofI80 value
     */
    public double getNofi80() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        double relativeBugsFound = 0.0;
        int numInstances = 0;
        for (ScoreEffortPair score : scores) {
            if (relativeBugsFound > 0.8) {
                // already found 80% of bugs
                break;
            }
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;

            numInstances++;
        }
        return numInstances;
    }

    /**
     * Calculate the percentage of instances visited until 80 percent of the bugs are found.
     * 
     * @return RelI80 value
     */
    public double getReli80() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        double relativeBugsFound = 0.0;
        int numInstances = 0;
        for (ScoreEffortPair score : scores) {
            if (relativeBugsFound > 0.8) {
                // already found 80% of bugs
                break;
            }
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;

            numInstances++;
        }
        return numInstances / (double) scores.length;
    }

    /**
     * Calculate the percentage of effort invested until 80 percent of the bugs are found.
     * 
     * @return
     */
    public double getRele80() {
        if( scores==null ) {
            return -1;
        }
        if( totalBugs==0 ) {
            return -1;
        }
        double relativeBugsFound = 0.0;
        double relativeEffort = 0.0;
        for (ScoreEffortPair score : scores) {
            if (relativeBugsFound > 0.8) {
                // already found 80% of bugs
                break;
            }
            double curRelativeEffort = score.getEffort() / totalEffort;
            double curRelativeBugsFound = score.getBugCount() / totalBugs;
            relativeBugsFound += curRelativeBugsFound;
            relativeEffort += curRelativeEffort;
        }
        return relativeEffort;
    }
    
    /**
     * <p>
     * Number of bugs that are found if the classification is used, i.e., all instances are reviewed that are predicted as defect-prone.
     * </p>
     *
     * @return
     */
    public double getNofBPredicted() {
        double nofbPredicted = 0.0;
        for( ScoreEffortPair score : scores ) {
            if(score.getClassification()==1.0) {
                nofbPredicted += score.getBugCount();
            }
        }
        return nofbPredicted;
    }
    
    /**
     * <p>
     * Number of bugs that are missed if the classification is used, i.e., all instances are reviewed that are predicted as defect-prone.
     * </p>
     *
     * @return
     */
    public double getNofBMissed() {
        double nofbMissed = 0.0;
        for( ScoreEffortPair score : scores ) {
            if(score.getClassification()!=1.0) {
                nofbMissed += score.getBugCount();
            }
        }
        return nofbMissed;
    }

    /**
     * <p>
     * Cost of the quality assurance, i.e., the effort of artifacts containing a predicted bug.
     * </p>
     *
     * @return
     */
    public double getCost() {
        double cost = 0.0;
        for( ScoreEffortPair score : scores ) {
            if(score.getClassification()==1.0) {
                cost += score.getEffort();
            }
        }
        return cost;
    }

    /**
     * <p>
     * Helper class for effort metric calculation
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
         * Classification of the pair
         */
        private final double classification;

        /**
         * Associated review effort
         */
        private final double effort;

        /**
         * Class of the instance
         */
        private final double bugCount;

        @SuppressWarnings("hiding")
        public ScoreEffortPair(double score, double classification, double effort, double bugCount) {
            this.score = score;
            this.classification = classification;
            this.effort = effort;
            this.bugCount = bugCount;
        }

        public double getClassification() {
            return classification;
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
        public double getBugCount() {
            return bugCount;
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
            return "score=" + score + ", effort=" + effort + ", bugCount=" + bugCount;
        }

    }
}
