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

import javax.management.RuntimeErrorException;

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
	 * We need the testdata instances to do a metric matching, so in this special case we get this data
	 * before evaluation
	 */
	@Override
	public void apply(SetUniqueList<Instances> traindataSet, Instances testdata) {
		this.traindataSet = traindataSet;

		int rank = 0; // we want at least 5 matching attributes
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
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			if (this.method.equals("spearman")) {
			    tmp.spearmansRankCorrelation(this.threshold);
			}
			else if (this.method.equals("kolmogorov")) {
			    tmp.kolmogorovSmirnovTest(this.threshold);
			}
			else if( this.method.equals("percentile") ) {
			    tmp.percentiles(this.threshold);
			}
			else {
			    throw new RuntimeException("unknown method");
			}

			// we only select the training data from our set with the most matching attributes
			if (tmp.getRank() > rank) {
				rank = tmp.getRank();
				biggest = tmp;
				biggest_num = num;
			}
		}
		
		if (biggest == null) {
		    throw new RuntimeException("not enough matching attributes found");
		}

		// we use the best match
		
		this.mm = biggest;
		Instances ilist = this.mm.getMatchedTrain();
		Console.traceln(Level.INFO, "Chosing the trainingdata set num "+biggest_num +" with " + rank + " matching attributes, " + ilist.size() + " instances out of a possible set of " + traindataSet.size() + " sets");
		
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
	 * encapsulates the classifier configured with WekaBase
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
		 
		 HashMap<Integer, Integer> attributes = new HashMap<Integer,Integer>();
		 
		 ArrayList<double[]> train_values;
		 ArrayList<double[]> test_values;
		 
		 // todo: this constructor does not work
		 public MetricMatch() {
		 }
		 
		 public MetricMatch(Instances train, Instances test) {
			 this.train = new Instances(train);  // expensive! but we are dropping the attributes
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
		  * returns the number of matched attributes
		  * as a way of scoring traindata sets individually
		  * 
		  * @return
		  */
		 public int getRank() {
			 return this.attributes.size();
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
			 
			 //System.out.println(ni);
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
		 
		 // https://weka.wikispaces.com/Programmatic+Use
		 private Instances getMatchedInstances(String name, Instances data) {
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
		 */
		public void attributeSelection() throws Exception {
			//Console.traceln(Level.INFO, "Attribute Selection on Training Attributes ("+this.train.numAttributes()+")");
			this.attributeSelection(this.train);
			//Console.traceln(Level.INFO, "-----");
			//Console.traceln(Level.INFO, "Attribute Selection on Test Attributes ("+this.test.numAttributes()+")");
			this.attributeSelection(this.test);
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
			
			// die letzen 15% wollen wir haben
			float last = ((float)saeval.size() / 100) * 15;
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
//		    //Console.traceln(Level.INFO, "Now we have " + which.numAttributes() + " attributes left (incl. class attribute!)");
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
		 
		/**
		 * Calculates the Percentiles of the source and target metrics.
		 * 
		 * @param cutoff
		 */
		public void percentiles(double cutoff) {
		    for( int i = 0; i < this.train.numAttributes()-1; i++ ) {
                for( int j = 0; j < this.test.numAttributes()-1; j++ ) {
                    // class attributes are not relevant 
                    if( this.train.classIndex() == i ) {
                        continue;
                    }
                    if( this.test.classIndex() == j ) {
                        continue;
                    }
                    
                    
                    if( !this.attributes.containsKey(i) ) {
                        // get percentiles
                        double train[] = this.train_values.get(i);
                        double test[] = this.test_values.get(j);
                        
                        Arrays.sort(train);
                        Arrays.sort(test);
                        
                        // percentiles
                        double train_p;
                        double test_p;
                        double score = 0.0;
                        for( double p=0.1; p < 1; p+=0.1 ) {
                            train_p = train[(int)Math.ceil(train.length * p)];
                            test_p = test[(int)Math.ceil(test.length * p)];
                        
                            if( train_p > test_p ) {
                                score += test_p / train_p;
                            }else {
                                score += train_p / test_p;
                            }
                        }
                        
                        if( score > cutoff ) {
                            this.attributes.put(i, j);
                        }
                    }
                }
            }
		}
		 
		 /**
		  * calculate Spearmans rank correlation coefficient as matching score
		  * 
		  * @param cutoff
		  */
		 public void spearmansRankCorrelation(double cutoff) {
			 double p = 0;			 
			 SpearmansCorrelation t = new SpearmansCorrelation();

			 // size has to be the same so we randomly sample the number of the smaller sample from the big sample
			 if (this.train.size() > this.test.size()) {
			     this.sample(this.train, this.test, this.train_values);
			 }else if (this.test.size() > this.train.size()) {
			     this.sample(this.test, this.train, this.test_values);
			 }
			 
			 // try out possible attribute combinations
            for (int i=0; i < this.train.numAttributes()-1; i++) {
                for (int j=0; j < this.test.numAttributes()-1; j++) {
                    // class attributes are not relevant 
                    if (this.train.classIndex() == i) {
                        continue;
                    }
                    if (this.test.classIndex() == j) {
                        continue;
                    }
                    
                    
					if (!this.attributes.containsKey(i)) {
						p = t.correlation(this.train_values.get(i), this.test_values.get(j));
						if (p > cutoff) {
							this.attributes.put(i, j);
						}
					}
				}
		    }
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
		public void kolmogorovSmirnovTest(double cutoff) {
			double p = 0;
			
			KolmogorovSmirnovTest t = new KolmogorovSmirnovTest();

			// todo: this should be symmetrical we don't have to compare i to j and then j to i 
			// todo: this relies on the last attribute being the class, 
			//Console.traceln(Level.INFO, "Starting Kolmogorov-Smirnov test for traindata size: " + this.train.size() + " attributes("+this.train.numAttributes()+") and testdata size: " + this.test.size() + " attributes("+this.test.numAttributes()+")");
			for (int i=0; i < this.train.numAttributes()-1; i++) {
				for ( int j=0; j < this.test.numAttributes()-1; j++) {
					//p = t.kolmogorovSmirnovTest(this.train_values.get(i), this.test_values.get(j));
					//p = t.kolmogorovSmirnovTest(this.train_values.get(i), this.test_values.get(j));
                    // class attributes are not relevant 
                    if ( this.train.classIndex() == i ) {
                        continue;
                    }
                    if ( this.test.classIndex() == j ) {
                        continue;
                    }
					// PRoblem: exactP is forced for small sample sizes and it never finishes
					if (!this.attributes.containsKey(i)) {
						
						// todo: output the values and complain on the math.commons mailinglist
						p = t.kolmogorovSmirnovTest(this.train_values.get(i), this.test_values.get(j));
						if (p > cutoff) {
							this.attributes.put(i, j);
						}
					}
				}
			}

			//Console.traceln(Level.INFO, "Found " + this.attmatch.size() + " matching attributes");
		}
	 }
}
