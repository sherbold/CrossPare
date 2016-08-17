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
import java.util.logging.Level;

import java.util.Random;

import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import de.ugoe.cs.util.console.Console;
import weka.attributeSelection.SignificanceAttributeEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements Heterogenous Defect Prediction after Nam et al.
 * 
 * TODO:
 * - spacing, coding conventions
 * - we depend on having exactly one class attribute on multiple locations
 * - 
 */
public class MetricMatchingTraining extends WekaBaseTraining implements ISetWiseTestdataAwareTrainingStrategy {

    private SetUniqueList<Instances> traindataSet;
    private MetricMatch mm;
    private final Classifier classifier = new MetricMatchingClassifier();
    
    private String method;
    private float threshold;
    
    /**
     * We wrap the classifier here because of classifyInstance
     * @return
     */
    @Override
    public Classifier getClassifier() {
        return this.classifier;
    }


    @Override
    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public void setThreshold(String threshold) {
        this.threshold = Float.parseFloat(threshold);
    }

	/**
	 * We need the test data instances to do a metric matching, so in this special case we get this data
	 * before evaluation.
	 */
	@Override
	public void apply(SetUniqueList<Instances> traindataSet, Instances testdata) {
		this.traindataSet = traindataSet;

		double score = 0; // custom ranking score to select the best training data from the set
		int num = 0;
		int biggest_num = 0;
		MetricMatch tmp;
		MetricMatch biggest = null;
		for (Instances traindata : this.traindataSet) {
			num++;

			tmp = new MetricMatch(traindata, testdata);

			// metric selection may create error, continue to next training set
			try {
				tmp.attributeSelection();
				tmp.matchAttributes(this.method, this.threshold);
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			// we only select the training data from our set with the most matching attributes
			if (tmp.getScore() > score) {
				score = tmp.getScore();
				biggest = tmp;
				biggest_num = num;
			}
		}
		
		if (biggest == null) {
		    throw new RuntimeException("not enough matching attributes found");
		}

		// we use the best match according to our matching score
		this.mm = biggest;
		Instances ilist = this.mm.getMatchedTrain();
		Console.traceln(Level.INFO, "Chosing the trainingdata set num "+biggest_num +" with " + score + " matching score, " + ilist.size() + " instances, and " + biggest.attributes.size() + " matched attributes out of a possible set of " + traindataSet.size() + " sets");
		
		for(int i = 0; i < this.mm.attributes.size(); i++) {
		    Console.traceln(Level.INFO, "Matched Attribute: " + this.mm.train.attribute(i).name() + " with " + this.mm.test.attribute((int)this.mm.attributes.get(i)).name());
		}
		// replace traindataSEt
		//traindataSet = new SetUniqueList<Instances>();
		traindataSet.clear();
		traindataSet.add(ilist);
		
		// we have to build the classifier here:
		try {
		    
			//
		    if (this.classifier == null) {
		        Console.traceln(Level.SEVERE, "Classifier is null");
		    }
			//Console.traceln(Level.INFO, "Building classifier with the matched training data with " + ilist.size() + " instances and "+ ilist.numAttributes() + " attributes");
			this.classifier.buildClassifier(ilist);
			((MetricMatchingClassifier) this.classifier).setMetricMatching(this.mm);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * Encapsulates the classifier configured with WekaBase within but use metric matching.
	 * This allows us to use any Weka classifier with Heterogenous Defect Prediction.
	 */
	public class MetricMatchingClassifier extends AbstractClassifier {

		private static final long serialVersionUID = -1342172153473770935L;
		private MetricMatch mm;
		private Classifier classifier;
		
		@Override
		public void buildClassifier(Instances traindata) throws Exception {
			this.classifier = setupClassifier();  // parent method from WekaBase
			this.classifier.buildClassifier(traindata);
		}

		public void setMetricMatching(MetricMatch mm) {
			this.mm = mm;
		}
		
		/**
		 * Here we can not do the metric matching because we only get one instance
		 */
		public double classifyInstance(Instance testdata) {
			// todo: maybe we can pull the instance out of our matched testdata?
			Instance ntest = this.mm.getMatchedTestInstance(testdata);

			double ret = 0.0;
			try {
				ret = this.classifier.classifyInstance(ntest);
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			return ret;
		}
	}
	
	/**
	 * Encapsulates MetricMatching on Instances Arrays
	 */
    public class MetricMatch {
	    Instances train;
		Instances test;
		
		// used to sum up the matching values of all attributes
		double p_sum = 0;
		
		// attribute matching, train -> test
		HashMap<Integer, Integer> attributes = new HashMap<Integer,Integer>();
		//double[][] weights;  /* weight matrix, needed to find maximum weighted bipartite matching */
		 
		ArrayList<double[]> train_values;
		ArrayList<double[]> test_values;

		// todo: this constructor does not work
		public MetricMatch() {
		}
		 
		public MetricMatch(Instances train, Instances test) {
		    // expensive! but we are dropping the attributes so we have to copy all of the data
		    this.train = new Instances(train);
			this.test = new Instances(test);
			 
			// 1. convert metrics of testdata and traindata to later use in test
			this.train_values = new ArrayList<double[]>();
			for (int i = 0; i < this.train.numAttributes()-1; i++) {
			    this.train_values.add(train.attributeToDoubleArray(i));
			}
			
			this.test_values = new ArrayList<double[]>();
			for (int i=0; i < this.test.numAttributes()-1; i++) {
			    this.test_values.add(this.test.attributeToDoubleArray(i));
			}
		}
		 
		/**
		 * We have a lot of matching possibilities.
		 * Here we try to determine the best one.
		 * 
		 * @return double matching score
		 */
	    public double getScore() {
	        int as = this.attributes.size();
	        
	        // we use thresholding ranking approach for numInstances to influence the matching score
	        int instances = this.train.numInstances();
	        int inst_rank = 0;
	        if(instances > 100) {
	            inst_rank = 1;
	        }
	        if(instances > 500) {
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
		 
		 public Instance getMatchedTestInstance(Instance test) {
			 // create new instance with our matched number of attributes + 1 (the class attribute)
			 //Console.traceln(Level.INFO, "getting matched instance");
			 Instances testdata = this.getMatchedTest();
			 
			 //Instance ni = new DenseInstance(this.attmatch.size()+1);
			 Instance ni = new DenseInstance(this.attributes.size()+1);
			 ni.setDataset(testdata);
			 
			 //Console.traceln(Level.INFO, "Attributes to match: " + this.attmatch.size() + "");
			 
			 Iterator it = this.attributes.entrySet().iterator();
			 int j = 0;
			 while (it.hasNext()) {
				 Map.Entry values = (Map.Entry)it.next();
				 ni.setValue(testdata.attribute(j), test.value((int)values.getValue()));
				 j++;
				 
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
		 
		 // todo: there must be a better way
		 // https://weka.wikispaces.com/Programmatic+Use
		 private Instances getMatchedInstances(String name, Instances data) {
			 //Console.traceln(Level.INFO, "Constructing instances from: " + name);
		     // construct our new attributes
			 Attribute[] attrs = new Attribute[this.attributes.size()+1];
			 FastVector fwTrain = new FastVector(this.attributes.size());
			 for (int i=0; i < this.attributes.size(); i++) {
				 attrs[i] = new Attribute(String.valueOf(i));
				 fwTrain.addElement(attrs[i]);
			 }
			 // add our ClassAttribute (which is not numeric!)
			 ArrayList<String> acl= new ArrayList<String>();
			 acl.add("0");
			 acl.add("1");
			 
			 fwTrain.addElement(new Attribute("bug", acl));
			 Instances newTrain = new Instances(name, fwTrain, data.size());
			 newTrain.setClassIndex(newTrain.numAttributes()-1);
			 
			 //Console.traceln(Level.INFO, "data attributes: " + data.numAttributes() + ", this.attributes: "+this.attributes.size());
			 
			 for (int i=0; i < data.size(); i++) {
				 Instance ni = new DenseInstance(this.attributes.size()+1);
				
				 Iterator it = this.attributes.entrySet().iterator();
				 int j = 0;
				 while (it.hasNext()) {
					 Map.Entry values = (Map.Entry)it.next();
					 int value = (int)values.getValue();
					 
					 // key ist traindata
					 if (name.equals("train")) {
						 value = (int)values.getKey();
					 }
					 //Console.traceln(Level.INFO, "setting attribute " + j + " with data from instance: " + i);
					 ni.setValue(newTrain.attribute(j), data.instance(i).value(value));
					 j++;
				 }
				 ni.setValue(ni.numAttributes()-1, data.instance(i).value(data.classAttribute()));
				 
				 newTrain.add(ni);
			 }
			 
		    return newTrain;
        }
		 
		 
		/**
		 * performs the attribute selection
		 * we perform attribute significance tests and drop attributes 
		 * 
		 * attribute selection is only performed on the source dataset
		 * we retain the top 15% attributes (if 15% is a float we just use the integer part)
		 */
		public void attributeSelection() throws Exception {
			//Console.traceln(Level.INFO, "Attribute Selection on Training Attributes ("+this.train.numAttributes()+")");
			this.attributeSelection(this.train);
			//Console.traceln(Level.INFO, "-----");
			//Console.traceln(Level.INFO, "Attribute Selection on Test Attributes ("+this.test.numAttributes()+")");
			//this.attributeSelection(this.test);
			//Console.traceln(Level.INFO, "-----");
		}
		
		private void attributeSelection(Instances which) throws Exception {
			// 1. step we have to categorize the attributes
			//http://weka.sourceforge.net/doc.packages/probabilisticSignificanceAE/weka/attributeSelection/SignificanceAttributeEval.html
			
			SignificanceAttributeEval et = new SignificanceAttributeEval();
			et.buildEvaluator(which);
			//double tmp[] = new double[this.train.numAttributes()];
			HashMap<String,Double> saeval = new HashMap<String,Double>();
			// evaluate all training attributes
			// select top 15% of metrics
			for(int i=0; i < which.numAttributes(); i++) { 
				if(which.classIndex() != i) {
					saeval.put(which.attribute(i).name(), et.evaluateAttribute(i));
				}
				//Console.traceln(Level.SEVERE, "Significance Attribute Eval: " + tmp);
			}
			
			HashMap<String, Double> sorted = sortByValues(saeval);
			
			// die besten 15% wollen wir haben
			double last = ((double)saeval.size() / 100.0) * 15.0;
			int drop_first = saeval.size() - (int)last;
			
			//Console.traceln(Level.INFO, "Dropping "+ drop_first + " of " + sorted.size() + " attributes (we only keep the best 15% "+last+")");
			/*
			Iterator it = sorted.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry pair = (Map.Entry)it.next();
		    	Console.traceln(Level.INFO, "key: "+(int)pair.getKey()+", value: " + (double)pair.getValue() + "");
		    }*/
			
			// drop attributes above last
			Iterator it = sorted.entrySet().iterator();
		    while (drop_first > 0) {
	    		Map.Entry pair = (Map.Entry)it.next();
	    		if(which.attribute((String)pair.getKey()).index() != which.classIndex()) {
	    			
	    			which.deleteAttributeAt(which.attribute((String)pair.getKey()).index());
	    			//Console.traceln(Level.INFO, "dropping attribute: "+ (String)pair.getKey());
	    		}
		    	drop_first-=1;
		    }
		    //Console.traceln(Level.INFO, "Now we have " + which.numAttributes() + " attributes left (incl. class attribute!)");
		}
		
		
		private HashMap sortByValues(HashMap map) {
	       List list = new LinkedList(map.entrySet());

	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });


	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
		}
		 
		
        
		public void matchAttributes(String type, double cutoff) {
		    

		    MWBMatchingAlgorithm mwbm = new MWBMatchingAlgorithm(this.train.numAttributes(), this.test.numAttributes());
		    
		    if (type.equals("spearman")) {
		        this.spearmansRankCorrelation(cutoff, mwbm);
		    }else if(type.equals("ks")) {
		        this.kolmogorovSmirnovTest(cutoff, mwbm);
		    }else if(type.equals("percentile")) {
		        this.percentiles(cutoff, mwbm);
		    }else {
		        throw new RuntimeException("unknown matching method");
		    }
		    
		    // resulting maximal match
            int[] result = mwbm.getMatching();
            for( int i = 0; i < result.length; i++) {
                
                // -1 means that it is not in the set of maximal matching
                if( i != -1 && result[i] != -1) {
                    //Console.traceln(Level.INFO, "Found maximal bipartite match between: "+ i + " and " + result[i]);
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
		    for( int i = 0; i < this.train.numAttributes(); i++ ) {
                for( int j = 0; j < this.test.numAttributes(); j++ ) {
                    // negative infinity counts as not present, we do this so we don't have to map between attribute indexs in weka
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
                    for( int p=1; p <= 9; p++ ) {
                        train_p = train[(int)Math.ceil(train.length * (p/100))];
                        test_p = test[(int)Math.ceil(test.length * (p/100))];
                    
                        if( train_p > test_p ) {
                            score += test_p / train_p;
                        }else {
                            score += train_p / test_p;
                        }
                    }
                    
                    if( score > cutoff ) {
                        this.p_sum += score;
                        mwbm.setWeight(i, j, score);
                    }
                }
            }
		}
		 
		 /**
		  * Calculate Spearmans rank correlation coefficient as matching score
		  * The number of instances for the source and target needs to be the same so we randomly sample from the bigger one.
		  * 
		  * @param cutoff
		  * @param mwbmatching
		  */
		 public void spearmansRankCorrelation(double cutoff, MWBMatchingAlgorithm mwbm) {
			 double p = 0;

			 SpearmansCorrelation t = new SpearmansCorrelation();

			 // size has to be the same so we randomly sample the number of the smaller sample from the big sample
			 if (this.train.size() > this.test.size()) {
			     this.sample(this.train, this.test, this.train_values);
			 }else if (this.test.size() > this.train.size()) {
			     this.sample(this.test, this.train, this.test_values);
			 }
			
            // try out possible attribute combinations
            for (int i=0; i < this.train.numAttributes(); i++) {

                for (int j=0; j < this.test.numAttributes(); j++) {
                    // negative infinity counts as not present, we do this so we don't have to map between attribute indexs in weka
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
					    this.p_sum += p;
                        mwbm.setWeight(i, j, p);
					    //Console.traceln(Level.INFO, "Found match: p-val: " + p);
					}
				}
		    }

            //Console.traceln(Level.INFO, "Found " + this.attributes.size() + " matching attributes");
        }

		 
        public void sample(Instances bigger, Instances smaller, ArrayList<double[]> values) {
            // we want to at keep the indices we select the same
            int indices_to_draw = smaller.size();
            ArrayList<Integer> indices = new ArrayList<Integer>();
            Random rand = new Random();
            while (indices_to_draw > 0) {
                
                int index = rand.nextInt(bigger.size()-1);
                
                if (!indices.contains(index)) {
                    indices.add(index);
                    indices_to_draw--;
                }
            }
            
            // now reduce our values to the indices we choose above for every attribute
            for (int att=0; att < bigger.numAttributes()-1; att++) {
                
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
		 * We run the kolmogorov-smirnov test on the data from our test an traindata
		 * if the p value is above the cutoff we include it in the results 
		 * p value tends to be 0 when the distributions of the data are significantly different
		 * but we want them to be the same
		 * 
		 * @param cutoff
		 * @return p-val
		 */
		public void kolmogorovSmirnovTest(double cutoff, MWBMatchingAlgorithm mwbm) {
			double p = 0;
            
			KolmogorovSmirnovTest t = new KolmogorovSmirnovTest();

			//Console.traceln(Level.INFO, "Starting Kolmogorov-Smirnov test for traindata size: " + this.train.size() + " attributes("+this.train.numAttributes()+") and testdata size: " + this.test.size() + " attributes("+this.test.numAttributes()+")");
			for (int i=0; i < this.train.numAttributes(); i++) {
				for ( int j=0; j < this.test.numAttributes(); j++) {
                    // negative infinity counts as not present, we do this so we don't have to map between attribute indexs in weka
                    // and the result of the mwbm computation
                    mwbm.setWeight(i, j, Double.NEGATIVE_INFINITY);
                    
                    // class attributes are not relevant 
                    if (this.test.classIndex() == j) {
                        continue;
                    }
                    if (this.train.classIndex() == i) {
                        continue;
                    }
                    
                    // this may invoke exactP on small sample sizes which will not terminate in all cases
					//p = t.kolmogorovSmirnovTest(this.train_values.get(i), this.test_values.get(j), false);
					p = t.approximateP(t.kolmogorovSmirnovStatistic(this.train_values.get(i), this.test_values.get(j)), this.train_values.get(i).length, this.test_values.get(j).length);
					if (p > cutoff) {
                        this.p_sum += p;
                        mwbm.setWeight(i, j, p);
					}
				}
			}
			//Console.traceln(Level.INFO, "Found " + this.attributes.size() + " matching attributes");
	    }
    }

    /*
     * Copyright (c) 2007, Massachusetts Institute of Technology
     * Copyright (c) 2005-2006, Regents of the University of California
     * All rights reserved.
     * 
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     * * Redistributions of source code must retain the above copyright
     *   notice, this list of conditions and the following disclaimer.
     *
     * * Redistributions in binary form must reproduce the above copyright
     *   notice, this list of conditions and the following disclaimer in
     *   the documentation and/or other materials provided with the
     *   distribution.  
     *
     * * Neither the name of the University of California, Berkeley nor
     *   the names of its contributors may be used to endorse or promote
     *   products derived from this software without specific prior 
     *   written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
     * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
     * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
     * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
     * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
     * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
     * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
     * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
     * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
     * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
     * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
     * OF THE POSSIBILITY OF SUCH DAMAGE.
     */



    /**
     * An engine for finding the maximum-weight matching in a complete
     * bipartite graph.  Suppose we have two sets <i>S</i> and <i>T</i>,
     * both of size <i>n</i>.  For each <i>i</i> in <i>S</i> and <i>j</i>
     * in <i>T</i>, we have a weight <i>w<sub>ij</sub></i>.  A perfect
     * matching <i>X</i> is a subset of <i>S</i> x <i>T</i> such that each
     * <i>i</i> in <i>S</i> occurs in exactly one element of <i>X</i>, and
     * each <i>j</i> in <i>T</i> occurs in exactly one element of
     * <i>X</i>.  Thus, <i>X</i> can be thought of as a one-to-one
     * function from <i>S</i> to <i>T</i>.  The weight of <i>X</i> is the
     * sum, over (<i>i</i>, <i>j</i>) in <i>X</i>, of
     * <i>w<sub>ij</sub></i>.  A BipartiteMatcher takes the number
     * <i>n</i> and the weights <i>w<sub>ij</sub></i>, and finds a perfect
     * matching of maximum weight.
     *
     * It uses the Hungarian algorithm of Kuhn (1955), as improved and
     * presented by E. L. Lawler in his book <cite>Combinatorial
     * Optimization: Networks and Matroids</cite> (Holt, Rinehart and
     * Winston, 1976, p. 205-206).  The running time is
     * O(<i>n</i><sup>3</sup>).  The weights can be any finite real
     * numbers; Lawler's algorithm assumes positive weights, so if
     * necessary we add a constant <i>c</i> to all the weights before
     * running the algorithm.  This increases the weight of every perfect
     * matching by <i>nc</i>, which doesn't change which perfect matchings
     * have maximum weight.
     *
     * If a weight is set to Double.NEGATIVE_INFINITY, then the algorithm will 
     * behave as if that edge were not in the graph.  If all the edges incident on 
     * a given node have weight Double.NEGATIVE_INFINITY, then the final result 
     * will not be a perfect matching, and an exception will be thrown.  
     */
     class MWBMatchingAlgorithm {
        /**
         * Creates a BipartiteMatcher without specifying the graph size.  Calling 
         * any other method before calling reset will yield an 
         * IllegalStateException.
         */
        
         /**
         * Tolerance for comparisons to zero, to account for
         * floating-point imprecision.  We consider a positive number to
         * be essentially zero if it is strictly less than TOL.
         */
        private static final double TOL = 1e-10;
        //Number of left side nodes
        int n;

        //Number of right side nodes
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
         * Creates a BipartiteMatcher and prepares it to run on an n x m graph.  
         * All the weights are initially set to 1.  
         */
        public MWBMatchingAlgorithm(int n, int m) {
        reset(n, m);
        }

        /**
         * Resets the BipartiteMatcher to run on an n x m graph.  The weights are 
         * all reset to 1.
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
         * @throws IllegalArgumentException if i or j is outside the range [0, n).
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
         * Returns a maximum-weight perfect matching relative to the weights 
         * specified with setWeight.  The matching is represented as an array arr 
         * of length n, where arr[i] = j if (i,j) is in the matching.
         */
        public int[] getMatching() {
        if (n == -1 || m == -1 ) {
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
         * Tries to find an augmenting path containing only edges (i,j) for which 
         * u[i] + v[j] = weights[i][j].  If it succeeds, returns the index of the 
         * last node in the path.  Otherwise, returns -1.  In any case, updates 
         * the labels and pi values.
         */
        int findAugmentingPath() {
        while ((!eligibleS.isEmpty()) || (!eligibleT.isEmpty())) {
            if (!eligibleS.isEmpty()) {
            int i = ((Integer) eligibleS.get(eligibleS.size() - 1)).
                intValue();
            eligibleS.remove(eligibleS.size() - 1);
            for (int j = 0; j < m; j++) {
                // If pi[j] has already been decreased essentially
                // to zero, then j is already labeled, and we
                // can't decrease pi[j] any more.  Omitting the 
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
            } else {
            int j = ((Integer) eligibleT.get(eligibleT.size() - 1)).
                intValue();
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
         * Given an augmenting path ending at lastNode, "flips" the path.  This 
         * means that an edge on the path is in the matching after the flip if 
         * and only if it was not in the matching before the flip.  An augmenting 
         * path connects two unmatched nodes, so the result is still a matching. 
         */ 
        void flipPath(int lastNode) {
            while (lastNode != EMPTY_LABEL) {
                int parent = tLabels[lastNode];
    
                // Add (parent, lastNode) to matching.  We don't need to 
                // explicitly remove any edges from the matching because: 
                //  * We know at this point that there is no i such that 
                //    sMatches[i] = lastNode.  
                //  * Although there might be some j such that tMatches[j] =
                //    parent, that j must be sLabels[parent], and will change 
                //    tMatches[j] in the next time through this loop.  
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
                } else if (tLabels[j] != NO_LABEL) {
                pi[j] -= delta;
                if (pi[j] < TOL) {
                    eligibleT.add(new Integer(j));
                }
                }
            }
        }

        /**
         * Ensures that all weights are either Double.NEGATIVE_INFINITY, 
         * or strictly greater than zero.
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
