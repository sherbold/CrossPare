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

package de.ugoe.cs.cpdp.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import de.ugoe.cs.util.console.Console;
import weka.attributeSelection.SignificanceAttributeEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements Heterogenous Defect Prediction after Nam et al. 2015.
 * 
 * We extend WekaBaseTraining because we have to Wrap the Classifier to use MetricMatching. This
 * also means we can use any Weka Classifier not just LogisticRegression.
 * 
 * Config: <setwisetestdataawaretrainer name="MetricMatchingTraining" param=
 * "Logistic weka.classifiers.functions.Logistic" threshold="0.05" method="spearman"/> Instead of
 * spearman metchod it also takes ks, percentile. Instead of Logistic every other weka classifier
 * can be chosen.
 * 
 * Future work: implement chisquare test in addition to significance for attribute selection
 * http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/stat/inference/
 * ChiSquareTest.html use chiSquareTestDataSetsComparison
 */
public class MetricMatchingTraining extends WekaBaseTraining
    implements ISetWiseTestdataAwareTrainingStrategy
{

    private MetricMatch mm = null;
    private Classifier classifier = null;

    private String method;
    private float threshold;

    /**
     * We wrap the classifier here because of classifyInstance with our MetricMatchingClassfier
     * 
     * @return
     */
    @Override
    public Classifier getClassifier() {
        return this.classifier;
    }

    /**
     * Set similarity measure method.
     */
    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Set threshold for similarity measure.
     */
    @Override
    public void setThreshold(String threshold) {
        this.threshold = Float.parseFloat(threshold);
    }

    /**
     * We need the test data instances to do a metric matching, so in this special case we get this
     * data before evaluation.
     */
    @Override
    public void apply(SetUniqueList<Instances> traindataSet, Instances testdata) {
        // reset these for each run
        this.mm = null;
        this.classifier = null;

        double score = 0; // matching score to select the best matching training data from the set
        int num = 0;
        int biggest_num = 0;
        MetricMatch tmp;
        for (Instances traindata : traindataSet) {
            num++;

            tmp = new MetricMatch(traindata, testdata);

            // metric selection may create error, continue to next training set
            try {
                tmp.attributeSelection();
                tmp.matchAttributes(this.method, this.threshold);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            // we only select the training data from our set with the most matching attributes
            if (tmp.getScore() > score && tmp.attributes.size() > 0) {
                score = tmp.getScore();
                this.mm = tmp;
                biggest_num = num;
            }
        }

        // if we have found a matching instance we use it, log information about the match for
        // additional eval later
        Instances ilist = null;
        if (this.mm != null) {
            ilist = this.mm.getMatchedTrain();
            Console.traceln(Level.INFO, "[MATCH FOUND] match: [" + biggest_num + "], score: [" +
                score + "], instances: [" + ilist.size() + "], attributes: [" +
                this.mm.attributes.size() + "], ilist attrs: [" + ilist.numAttributes() + "]");
            for (Map.Entry<Integer, Integer> attmatch : this.mm.attributes.entrySet()) {
                Console.traceln(Level.INFO, "[MATCHED ATTRIBUTE] source attribute: [" +
                    this.mm.train.attribute(attmatch.getKey()).name() + "], target attribute: [" +
                    this.mm.test.attribute(attmatch.getValue()).name() + "]");
            }
        }
        else {
            Console.traceln(Level.INFO, "[NO MATCH FOUND]");
        }

        // if we have a match we build the MetricMatchingClassifier, if not we fall back to FixClass
        // Classifier
        try {
            if (this.mm != null) {
                this.classifier = new MetricMatchingClassifier();
                this.classifier.buildClassifier(ilist);
                ((MetricMatchingClassifier) this.classifier).setMetricMatching(this.mm);
            }
            else {
                this.classifier = new FixClass();
                this.classifier.buildClassifier(ilist); // this is null, but the FixClass Classifier
                                                        // does not use it anyway
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Encapsulates the classifier configured with WekaBase within but use metric matching. This
     * allows us to use any Weka classifier with Heterogenous Defect Prediction.
     */
    public class MetricMatchingClassifier extends AbstractClassifier {

        private static final long serialVersionUID = -1342172153473770935L;
        private MetricMatch mm;
        private Classifier classifier;

        @Override
        public void buildClassifier(Instances traindata) throws Exception {
            this.classifier = setupClassifier();
            this.classifier.buildClassifier(traindata);
        }

        /**
         * Sets the MetricMatch instance so that we can use matched test data later.
         * 
         * @param mm
         */
        public void setMetricMatching(MetricMatch mm) {
            this.mm = mm;
        }

        /**
         * Here we can not do the metric matching because we only get one instance. Therefore we
         * need a MetricMatch instance beforehand to use here.
         */
        public double classifyInstance(Instance testdata) {
            // get a copy of testdata Instance with only the matched attributes
            Instance ntest = this.mm.getMatchedTestInstance(testdata);

            double ret = 0.0;
            try {
                ret = this.classifier.classifyInstance(ntest);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            return ret;
        }
    }

    /**
     * Encapsulates one MetricMatching process. One source (train) matches against one target
     * (test).
     */
    public class MetricMatch {
        Instances train;
        Instances test;

        // used to sum up the matching values of all attributes
        protected double p_sum = 0;

        // attribute matching, train -> test
        HashMap<Integer, Integer> attributes = new HashMap<Integer, Integer>();

        // used for similarity tests
        protected ArrayList<double[]> train_values;
        protected ArrayList<double[]> test_values;

        public MetricMatch(Instances train, Instances test) {
            // this is expensive but we need to keep the original data intact
            this.train = this.deepCopy(train);
            this.test = test; // we do not need a copy here because we do not drop attributes before
                              // the matching and after the matching we create a new Instances with
                              // only the matched attributes

            // convert metrics of testdata and traindata to later use in similarity tests
            this.train_values = new ArrayList<double[]>();
            for (int i = 0; i < this.train.numAttributes(); i++) {
                if (this.train.classIndex() != i) {
                    this.train_values.add(this.train.attributeToDoubleArray(i));
                }
            }

            this.test_values = new ArrayList<double[]>();
            for (int i = 0; i < this.test.numAttributes(); i++) {
                if (this.test.classIndex() != i) {
                    this.test_values.add(this.test.attributeToDoubleArray(i));
                }
            }
        }

        /**
         * We have a lot of matching possibilities. Here we try to determine the best one.
         * 
         * @return double matching score
         */
        public double getScore() {
            int as = this.attributes.size(); // # of attributes that were matched

            // we use thresholding ranking approach for numInstances to influence the matching score
            int instances = this.train.numInstances();
            int inst_rank = 0;
            if (instances > 100) {
                inst_rank = 1;
            }
            if (instances > 500) {
                inst_rank = 2;
            }

            return this.p_sum + as + inst_rank;
        }

        public HashMap<Integer, Integer> getAttributes() {
            return this.attributes;
        }

        public int getNumInstances() {
            return this.train_values.get(0).length;
        }

        /**
         * The test instance must be of the same dataset as the train data, otherwise WekaEvaluation
         * will die. This means we have to force the dataset of this.train (after matching) and only
         * set the values for the attributes we matched but with the index of the traindata
         * attributes we matched.
         * 
         * @param test
         * @return
         */
        public Instance getMatchedTestInstance(Instance test) {
            Instance ni = new DenseInstance(this.attributes.size() + 1);

            Instances inst = this.getMatchedTrain();

            ni.setDataset(inst);

            // assign only the matched attributes to new indexes
            double val;
            int k = 0;
            for (Map.Entry<Integer, Integer> attmatch : this.attributes.entrySet()) {
                // get value from matched attribute
                val = test.value(attmatch.getValue());

                // set it to new index, the order of the attributes is the same
                ni.setValue(k, val);
                k++;
            }
            ni.setClassValue(test.value(test.classAttribute()));

            return ni;
        }

        /**
         * returns a new instances array with the metric matched training data
         * 
         * @return instances
         */
        public Instances getMatchedTrain() {
            return this.getMatchedInstances("train", this.train);
        }

        /**
         * returns a new instances array with the metric matched test data
         * 
         * @return instances
         */
        public Instances getMatchedTest() {
            return this.getMatchedInstances("test", this.test);
        }

        /**
         * We could drop unmatched attributes from our instances datasets. Alas, that would not be
         * nice for the following postprocessing jobs and would not work at all for evaluation. We
         * keep this as a warning for future generations.
         * 
         * @param name
         * @param data
         */
        @SuppressWarnings("unused")
        private void dropUnmatched(String name, Instances data) {
            for (int i = 0; i < data.numAttributes(); i++) {
                if (data.classIndex() == i) {
                    continue;
                }

                if (name.equals("train") && !this.attributes.containsKey(i)) {
                    data.deleteAttributeAt(i);
                }

                if (name.equals("test") && !this.attributes.containsValue(i)) {
                    data.deleteAttributeAt(i);
                }
            }
        }

        /**
         * Deep Copy (well, reasonably deep, not sure about header information of attributes) Weka
         * Instances.
         * 
         * @param data
         *            Instances
         * @return copy of Instances passed
         */
        private Instances deepCopy(Instances data) {
            Instances newInst = new Instances(data);

            newInst.clear();

            for (int i = 0; i < data.size(); i++) {
                Instance ni = new DenseInstance(data.numAttributes());
                for (int j = 0; j < data.numAttributes(); j++) {
                    ni.setValue(newInst.attribute(j), data.instance(i).value(data.attribute(j)));
                }
                newInst.add(ni);
            }

            return newInst;
        }

        /**
         * Returns a deep copy of passed Instances data for Train or Test data. It only keeps
         * attributes that have been matched.
         * 
         * @param name
         * @param data
         * @return matched Instances
         */
        private Instances getMatchedInstances(String name, Instances data) {
            ArrayList<Attribute> attrs = new ArrayList<Attribute>();

            // bug attr is a string, really!
            ArrayList<String> bug = new ArrayList<String>();
            bug.add("0");
            bug.add("1");

            // add our matched attributes and last the bug
            for (Map.Entry<Integer, Integer> attmatch : this.attributes.entrySet()) {
                attrs.add(new Attribute(String.valueOf(attmatch.getValue())));
            }
            attrs.add(new Attribute("bug", bug));

            // create new instances object of the same size (at least for instances)
            Instances newInst = new Instances(name, attrs, data.size());

            // set last as class
            newInst.setClassIndex(newInst.numAttributes() - 1);

            // copy data for matched attributes, this depends if we return train or test data
            for (int i = 0; i < data.size(); i++) {
                Instance ni = new DenseInstance(this.attributes.size() + 1);

                int j = 0; // new indices!
                for (Map.Entry<Integer, Integer> attmatch : this.attributes.entrySet()) {

                    // test attribute match
                    int value = attmatch.getValue();

                    // train attribute match
                    if (name.equals("train")) {
                        value = attmatch.getKey();
                    }

                    ni.setValue(newInst.attribute(j), data.instance(i).value(value));
                    j++;
                }
                ni.setValue(ni.numAttributes() - 1, data.instance(i).value(data.classAttribute()));
                newInst.add(ni);
            }

            return newInst;
        }

        /**
         * performs the attribute selection we perform attribute significance tests and drop
         * attributes
         * 
         * attribute selection is only performed on the source dataset we retain the top 15%
         * attributes (if 15% is a float we just use the integer part)
         */
        public void attributeSelection() throws Exception {

            // it is a wrapper, we may decide to implement ChiSquare or other means of selecting
            // attributes
            this.attributeSelectionBySignificance(this.train);
        }

        private void attributeSelectionBySignificance(Instances which) throws Exception {
            // Uses:
            // http://weka.sourceforge.net/doc.packages/probabilisticSignificanceAE/weka/attributeSelection/SignificanceAttributeEval.html
            SignificanceAttributeEval et = new SignificanceAttributeEval();
            et.buildEvaluator(which);

            // evaluate all training attributes
            HashMap<String, Double> saeval = new HashMap<String, Double>();
            for (int i = 0; i < which.numAttributes(); i++) {
                if (which.classIndex() != i) {
                    saeval.put(which.attribute(i).name(), et.evaluateAttribute(i));
                }
            }

            // sort by significance
            HashMap<String, Double> sorted = (HashMap<String, Double>) sortByValues(saeval);

            // Keep the best 15%
            double last = ((double) saeval.size() / 100.0) * 15.0;
            int drop_first = saeval.size() - (int) last;

            // drop attributes above last
            Iterator<Entry<String, Double>> it = sorted.entrySet().iterator();
            while (drop_first > 0) {
                Map.Entry<String, Double> pair = (Map.Entry<String, Double>) it.next();
                if (which.attribute((String) pair.getKey()).index() != which.classIndex()) {
                    which.deleteAttributeAt(which.attribute((String) pair.getKey()).index());
                }
                drop_first -= 1;
            }
        }

        /**
         * Helper method to sort a hashmap by its values.
         * 
         * @param map
         * @return sorted map
         */
        private HashMap<String, Double> sortByValues(HashMap<String, Double> map) {
            List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(map.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            HashMap<String, Double> sortedHashMap = new LinkedHashMap<String, Double>();
            for (Map.Entry<String, Double> item : list) {
                sortedHashMap.put(item.getKey(), item.getValue());
            }
            return sortedHashMap;
        }

        /**
         * Executes the similarity matching between train and test data.
         * 
         * After this function is finished we have this.attributes with the correct matching between
         * train and test data attributes.
         * 
         * @param type
         * @param cutoff
         */
        public void matchAttributes(String type, double cutoff) {

            MWBMatchingAlgorithm mwbm =
                new MWBMatchingAlgorithm(this.train.numAttributes(), this.test.numAttributes());

            if (type.equals("spearman")) {
                this.spearmansRankCorrelation(cutoff, mwbm);
            }
            else if (type.equals("ks")) {
                this.kolmogorovSmirnovTest(cutoff, mwbm);
            }
            else if (type.equals("percentile")) {
                this.percentiles(cutoff, mwbm);
            }
            else {
                throw new RuntimeException("unknown matching method");
            }

            // resulting maximal match gets assigned to this.attributes
            int[] result = mwbm.getMatching();
            for (int i = 0; i < result.length; i++) {

                // -1 means that it is not in the set of maximal matching
                if (i != -1 && result[i] != -1) {
                    this.p_sum += mwbm.weights[i][result[i]]; // we add the weight of the returned
                                                              // matching for scoring the complete
                                                              // match later
                    this.attributes.put(i, result[i]);
                }
            }
        }

        /**
         * Calculates the Percentiles of the source and target metrics.
         * 
         * @param cutoff
         */
        public void percentiles(double cutoff, MWBMatchingAlgorithm mwbm) {
            for (int i = 0; i < this.train.numAttributes(); i++) {
                for (int j = 0; j < this.test.numAttributes(); j++) {
                    // negative infinity counts as not present, we do this so we don't have to map
                    // between attribute indexes in weka
                    // and the result of the mwbm computation
                    mwbm.setWeight(i, j, Double.NEGATIVE_INFINITY);

                    // class attributes are not relevant
                    if (this.test.classIndex() == j) {
                        continue;
                    }
                    if (this.train.classIndex() == i) {
                        continue;
                    }

                    // get percentiles
                    double train[] = this.train_values.get(i);
                    double test[] = this.test_values.get(j);

                    Arrays.sort(train);
                    Arrays.sort(test);

                    // percentiles
                    double train_p;
                    double test_p;
                    double score = 0.0;
                    for (int p = 1; p <= 9; p++) {
                        train_p = train[(int) Math.ceil(train.length * (p / 100))];
                        test_p = test[(int) Math.ceil(test.length * (p / 100))];

                        if (train_p > test_p) {
                            score += test_p / train_p;
                        }
                        else {
                            score += train_p / test_p;
                        }
                    }

                    if (score > cutoff) {
                        mwbm.setWeight(i, j, score);
                    }
                }
            }
        }

        /**
         * Calculate Spearmans rank correlation coefficient as matching score. The number of
         * instances for the source and target needs to be the same so we randomly sample from the
         * bigger one.
         * 
         * @param cutoff
         * @param mwbmatching
         */
        public void spearmansRankCorrelation(double cutoff, MWBMatchingAlgorithm mwbm) {
            double p = 0;

            SpearmansCorrelation t = new SpearmansCorrelation();

            // size has to be the same so we randomly sample the number of the smaller sample from
            // the big sample
            if (this.train.size() > this.test.size()) {
                this.sample(this.train, this.test, this.train_values);
            }
            else if (this.test.size() > this.train.size()) {
                this.sample(this.test, this.train, this.test_values);
            }

            // try out possible attribute combinations
            for (int i = 0; i < this.train.numAttributes(); i++) {
                for (int j = 0; j < this.test.numAttributes(); j++) {
                    // negative infinity counts as not present, we do this so we don't have to map
                    // between attribute indexs in weka
                    // and the result of the mwbm computation
                    mwbm.setWeight(i, j, Double.NEGATIVE_INFINITY);

                    // class attributes are not relevant
                    if (this.test.classIndex() == j) {
                        continue;
                    }
                    if (this.train.classIndex() == i) {
                        continue;
                    }

                    p = t.correlation(this.train_values.get(i), this.test_values.get(j));
                    if (p > cutoff) {
                        mwbm.setWeight(i, j, p);
                    }
                }
            }
        }

        /**
         * Helper method to sample instances for the Spearman rank correlation coefficient method.
         * 
         * @param bigger
         * @param smaller
         * @param values
         */
        private void sample(Instances bigger, Instances smaller, ArrayList<double[]> values) {
            // we want to at keep the indices we select the same
            int indices_to_draw = smaller.size();
            ArrayList<Integer> indices = new ArrayList<Integer>();
            Random rand = new Random();
            while (indices_to_draw > 0) {

                int index = rand.nextInt(bigger.size() - 1);

                if (!indices.contains(index)) {
                    indices.add(index);
                    indices_to_draw--;
                }
            }

            // now reduce our values to the indices we choose above for every attribute
            for (int att = 0; att < bigger.numAttributes() - 1; att++) {

                // get double for the att
                double[] vals = values.get(att);
                double[] new_vals = new double[indices.size()];

                int i = 0;
                for (Iterator<Integer> it = indices.iterator(); it.hasNext();) {
                    new_vals[i] = vals[it.next()];
                    i++;
                }

                values.set(att, new_vals);
            }
        }

        /**
         * We run the kolmogorov-smirnov test on the data from our test an traindata if the p value
         * is above the cutoff we include it in the results p value tends to be 0 when the
         * distributions of the data are significantly different but we want them to be the same
         * 
         * @param cutoff
         * @return p-val
         */
        public void kolmogorovSmirnovTest(double cutoff, MWBMatchingAlgorithm mwbm) {
            double p = 0;

            KolmogorovSmirnovTest t = new KolmogorovSmirnovTest();
            for (int i = 0; i < this.train.numAttributes(); i++) {
                for (int j = 0; j < this.test.numAttributes(); j++) {
                    // negative infinity counts as not present, we do this so we don't have to map
                    // between attribute indexs in weka
                    // and the result of the mwbm computation
                    mwbm.setWeight(i, j, Double.NEGATIVE_INFINITY);

                    // class attributes are not relevant
                    if (this.test.classIndex() == j) {
                        continue;
                    }
                    if (this.train.classIndex() == i) {
                        continue;
                    }

                    // this may invoke exactP on small sample sizes which will not terminate in all
                    // cases
                    // p = t.kolmogorovSmirnovTest(this.train_values.get(i),
                    // this.test_values.get(j), false);

                    // this uses approximateP everytime
                    p = t.approximateP(
                                       t.kolmogorovSmirnovStatistic(this.train_values.get(i),
                                                                    this.test_values.get(j)),
                                       this.train_values.get(i).length,
                                       this.test_values.get(j).length);
                    if (p > cutoff) {
                        mwbm.setWeight(i, j, p);
                    }
                }
            }
        }
    }

    /*
     * Copyright (c) 2007, Massachusetts Institute of Technology Copyright (c) 2005-2006, Regents of
     * the University of California All rights reserved.
     * 
     * Redistribution and use in source and binary forms, with or without modification, are
     * permitted provided that the following conditions are met:
     *
     * * Redistributions of source code must retain the above copyright notice, this list of
     * conditions and the following disclaimer.
     *
     * * Redistributions in binary form must reproduce the above copyright notice, this list of
     * conditions and the following disclaimer in the documentation and/or other materials provided
     * with the distribution.
     *
     * * Neither the name of the University of California, Berkeley nor the names of its
     * contributors may be used to endorse or promote products derived from this software without
     * specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
     * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
     * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
     * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
     * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
     * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
     * OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    /**
     * An engine for finding the maximum-weight matching in a complete bipartite graph. Suppose we
     * have two sets <i>S</i> and <i>T</i>, both of size <i>n</i>. For each <i>i</i> in <i>S</i> and
     * <i>j</i> in <i>T</i>, we have a weight <i>w<sub>ij</sub></i>. A perfect matching <i>X</i> is
     * a subset of <i>S</i> x <i>T</i> such that each <i>i</i> in <i>S</i> occurs in exactly one
     * element of <i>X</i>, and each <i>j</i> in <i>T</i> occurs in exactly one element of <i>X</i>.
     * Thus, <i>X</i> can be thought of as a one-to-one function from <i>S</i> to <i>T</i>. The
     * weight of <i>X</i> is the sum, over (<i>i</i>, <i>j</i>) in <i>X</i>, of <i>w
     * <sub>ij</sub></i>. A BipartiteMatcher takes the number <i>n</i> and the weights <i>w
     * <sub>ij</sub></i>, and finds a perfect matching of maximum weight.
     *
     * It uses the Hungarian algorithm of Kuhn (1955), as improved and presented by E. L. Lawler in
     * his book <cite>Combinatorial Optimization: Networks and Matroids</cite> (Holt, Rinehart and
     * Winston, 1976, p. 205-206). The running time is O(<i>n</i><sup>3</sup>). The weights can be
     * any finite real numbers; Lawler's algorithm assumes positive weights, so if necessary we add
     * a constant <i>c</i> to all the weights before running the algorithm. This increases the
     * weight of every perfect matching by <i>nc</i>, which doesn't change which perfect matchings
     * have maximum weight.
     *
     * If a weight is set to Double.NEGATIVE_INFINITY, then the algorithm will behave as if that
     * edge were not in the graph. If all the edges incident on a given node have weight
     * Double.NEGATIVE_INFINITY, then the final result will not be a perfect matching, and an
     * exception will be thrown.
     */
    class MWBMatchingAlgorithm {
        /**
         * Creates a BipartiteMatcher without specifying the graph size. Calling any other method
         * before calling reset will yield an IllegalStateException.
         */

        /**
         * Tolerance for comparisons to zero, to account for floating-point imprecision. We consider
         * a positive number to be essentially zero if it is strictly less than TOL.
         */
        private static final double TOL = 1e-10;
        // Number of left side nodes
        int n;

        // Number of right side nodes
        int m;

        double[][] weights;
        double minWeight;
        double maxWeight;

        // If (i, j) is in the mapping, then sMatches[i] = j and tMatches[j] = i.
        // If i is unmatched, then sMatches[i] = -1 (and likewise for tMatches).
        int[] sMatches;
        int[] tMatches;

        static final int NO_LABEL = -1;
        static final int EMPTY_LABEL = -2;

        int[] sLabels;
        int[] tLabels;

        double[] u;
        double[] v;

        double[] pi;

        List<Integer> eligibleS = new ArrayList<Integer>();
        List<Integer> eligibleT = new ArrayList<Integer>();

        public MWBMatchingAlgorithm() {
            n = -1;
            m = -1;
        }

        /**
         * Creates a BipartiteMatcher and prepares it to run on an n x m graph. All the weights are
         * initially set to 1.
         */
        public MWBMatchingAlgorithm(int n, int m) {
            reset(n, m);
        }

        /**
         * Resets the BipartiteMatcher to run on an n x m graph. The weights are all reset to 1.
         */
        private void reset(int n, int m) {
            if (n < 0 || m < 0) {
                throw new IllegalArgumentException("Negative num nodes: " + n + " or " + m);
            }
            this.n = n;
            this.m = m;

            weights = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    weights[i][j] = 1;
                }
            }
            minWeight = 1;
            maxWeight = Double.NEGATIVE_INFINITY;

            sMatches = new int[n];
            tMatches = new int[m];
            sLabels = new int[n];
            tLabels = new int[m];
            u = new double[n];
            v = new double[m];
            pi = new double[m];

        }

        /**
         * Sets the weight w<sub>ij</sub> to the given value w.
         *
         * @throws IllegalArgumentException
         *             if i or j is outside the range [0, n).
         */
        public void setWeight(int i, int j, double w) {
            if (n == -1 || m == -1) {
                throw new IllegalStateException("Graph size not specified.");
            }
            if ((i < 0) || (i >= n)) {
                throw new IllegalArgumentException("i-value out of range: " + i);
            }
            if ((j < 0) || (j >= m)) {
                throw new IllegalArgumentException("j-value out of range: " + j);
            }
            if (Double.isNaN(w)) {
                throw new IllegalArgumentException("Illegal weight: " + w);
            }

            weights[i][j] = w;
            if ((w > Double.NEGATIVE_INFINITY) && (w < minWeight)) {
                minWeight = w;
            }
            if (w > maxWeight) {
                maxWeight = w;
            }
        }

        /**
         * Returns a maximum-weight perfect matching relative to the weights specified with
         * setWeight. The matching is represented as an array arr of length n, where arr[i] = j if
         * (i,j) is in the matching.
         */
        public int[] getMatching() {
            if (n == -1 || m == -1) {
                throw new IllegalStateException("Graph size not specified.");
            }
            if (n == 0) {
                return new int[0];
            }
            ensurePositiveWeights();

            // Step 0: Initialization
            eligibleS.clear();
            eligibleT.clear();
            for (Integer i = 0; i < n; i++) {
                sMatches[i] = -1;

                u[i] = maxWeight; // ambiguous on p. 205 of Lawler, but see p. 202

                // this is really first run of Step 1.0
                sLabels[i] = EMPTY_LABEL;
                eligibleS.add(i);
            }

            for (int j = 0; j < m; j++) {
                tMatches[j] = -1;

                v[j] = 0;
                pi[j] = Double.POSITIVE_INFINITY;

                // this is really first run of Step 1.0
                tLabels[j] = NO_LABEL;
            }

            while (true) {
                // Augment the matching until we can't augment any more given the
                // current settings of the dual variables.
                while (true) {
                    // Steps 1.1-1.4: Find an augmenting path
                    int lastNode = findAugmentingPath();
                    if (lastNode == -1) {
                        break; // no augmenting path
                    }

                    // Step 2: Augmentation
                    flipPath(lastNode);
                    for (int i = 0; i < n; i++)
                        sLabels[i] = NO_LABEL;

                    for (int j = 0; j < m; j++) {
                        pi[j] = Double.POSITIVE_INFINITY;
                        tLabels[j] = NO_LABEL;
                    }

                    // This is Step 1.0
                    eligibleS.clear();
                    for (int i = 0; i < n; i++) {
                        if (sMatches[i] == -1) {
                            sLabels[i] = EMPTY_LABEL;
                            eligibleS.add(new Integer(i));
                        }
                    }

                    eligibleT.clear();
                }

                // Step 3: Change the dual variables

                // delta1 = min_i u[i]
                double delta1 = Double.POSITIVE_INFINITY;
                for (int i = 0; i < n; i++) {
                    if (u[i] < delta1) {
                        delta1 = u[i];
                    }
                }

                // delta2 = min_{j : pi[j] > 0} pi[j]
                double delta2 = Double.POSITIVE_INFINITY;
                for (int j = 0; j < m; j++) {
                    if ((pi[j] >= TOL) && (pi[j] < delta2)) {
                        delta2 = pi[j];
                    }
                }

                if (delta1 < delta2) {
                    // In order to make another pi[j] equal 0, we'd need to
                    // make some u[i] negative.
                    break; // we have a maximum-weight matching
                }

                changeDualVars(delta2);
            }

            int[] matching = new int[n];
            for (int i = 0; i < n; i++) {
                matching[i] = sMatches[i];
            }
            return matching;
        }

        /**
         * Tries to find an augmenting path containing only edges (i,j) for which u[i] + v[j] =
         * weights[i][j]. If it succeeds, returns the index of the last node in the path. Otherwise,
         * returns -1. In any case, updates the labels and pi values.
         */
        int findAugmentingPath() {
            while ((!eligibleS.isEmpty()) || (!eligibleT.isEmpty())) {
                if (!eligibleS.isEmpty()) {
                    int i = ((Integer) eligibleS.get(eligibleS.size() - 1)).intValue();
                    eligibleS.remove(eligibleS.size() - 1);
                    for (int j = 0; j < m; j++) {
                        // If pi[j] has already been decreased essentially
                        // to zero, then j is already labeled, and we
                        // can't decrease pi[j] any more. Omitting the
                        // pi[j] >= TOL check could lead us to relabel j
                        // unnecessarily, since the diff we compute on the
                        // next line may end up being less than pi[j] due
                        // to floating point imprecision.
                        if ((tMatches[j] != i) && (pi[j] >= TOL)) {
                            double diff = u[i] + v[j] - weights[i][j];
                            if (diff < pi[j]) {
                                tLabels[j] = i;
                                pi[j] = diff;
                                if (pi[j] < TOL) {
                                    eligibleT.add(new Integer(j));
                                }
                            }
                        }
                    }
                }
                else {
                    int j = ((Integer) eligibleT.get(eligibleT.size() - 1)).intValue();
                    eligibleT.remove(eligibleT.size() - 1);
                    if (tMatches[j] == -1) {
                        return j; // we've found an augmenting path
                    }

                    int i = tMatches[j];
                    sLabels[i] = j;
                    eligibleS.add(new Integer(i)); // ok to add twice
                }
            }

            return -1;
        }

        /**
         * Given an augmenting path ending at lastNode, "flips" the path. This means that an edge on
         * the path is in the matching after the flip if and only if it was not in the matching
         * before the flip. An augmenting path connects two unmatched nodes, so the result is still
         * a matching.
         */
        void flipPath(int lastNode) {
            while (lastNode != EMPTY_LABEL) {
                int parent = tLabels[lastNode];

                // Add (parent, lastNode) to matching. We don't need to
                // explicitly remove any edges from the matching because:
                // * We know at this point that there is no i such that
                // sMatches[i] = lastNode.
                // * Although there might be some j such that tMatches[j] =
                // parent, that j must be sLabels[parent], and will change
                // tMatches[j] in the next time through this loop.
                sMatches[parent] = lastNode;
                tMatches[lastNode] = parent;

                lastNode = sLabels[parent];
            }
        }

        void changeDualVars(double delta) {
            for (int i = 0; i < n; i++) {
                if (sLabels[i] != NO_LABEL) {
                    u[i] -= delta;
                }
            }

            for (int j = 0; j < m; j++) {
                if (pi[j] < TOL) {
                    v[j] += delta;
                }
                else if (tLabels[j] != NO_LABEL) {
                    pi[j] -= delta;
                    if (pi[j] < TOL) {
                        eligibleT.add(new Integer(j));
                    }
                }
            }
        }

        /**
         * Ensures that all weights are either Double.NEGATIVE_INFINITY, or strictly greater than
         * zero.
         */
        private void ensurePositiveWeights() {
            // minWeight is the minimum non-infinite weight
            if (minWeight < TOL) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        weights[i][j] = weights[i][j] - minWeight + 1;
                    }
                }

                maxWeight = maxWeight - minWeight + 1;
                minWeight = 1;
            }
        }

        @SuppressWarnings("unused")
        private void printWeights() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(weights[i][j] + " ");
                }
                System.out.println("");
            }
        }
    }
}
