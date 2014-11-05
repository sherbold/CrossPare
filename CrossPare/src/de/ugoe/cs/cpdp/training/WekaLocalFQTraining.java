package de.ugoe.cs.cpdp.training;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.io.output.NullOutputStream;

import de.ugoe.cs.cpdp.training.QuadTree;
import de.ugoe.cs.util.console.Console;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * Trainer with reimplementation of WHERE clustering algorithm from:
 * Tim Menzies, Andrew Butcher, David Cok, Andrian Marcus, Lucas Layman, 
 * Forrest Shull, Burak Turhan, Thomas Zimmermann, 
 * "Local versus Global Lessons for Defect Prediction and Effort Estimation," 
 * IEEE Transactions on Software Engineering, vol. 39, no. 6, pp. 822-834, June, 2013  
 * 
 * With WekaLocalFQTraining we do the following:
 * 1) Run the Fastmap algorithm on all training data, let it calculate the 2 most significant 
 *    dimensions and projections of each instance to these dimensions
 * 2) With these 2 dimensions we span a QuadTree which gets recursively split on median(x) and median(y) values.
 * 3) We cluster the QuadTree nodes together if they have similar density (50%)
 * 4) We save the clusters and their training data
 * 5) We only use clusters with > ALPHA instances (currently Math.sqrt(SIZE)), rest is discarded with the training data of this cluster
 * 6) We train a Weka classifier for each cluster with the clusters training data
 * 7) We recalculate Fastmap distances for a single instance with the old pivots and then try to find a cluster containing the coords of the instance.
 * 7.1.) If we can not find a cluster (due to coords outside of all clusters) we find the nearest cluster.
 * 8) We classify the Instance with the classifier and traindata from the Cluster we found in 7.
 */
public class WekaLocalFQTraining extends WekaBaseTraining implements ITrainingStrategy {
	
	private final TraindatasetCluster classifier = new TraindatasetCluster();
	
	@Override
	public Classifier getClassifier() {
		return classifier;
	}
	
	@Override
	public void apply(Instances traindata) {
		PrintStream errStr	= System.err;
		System.setErr(new PrintStream(new NullOutputStream()));
		try {
			classifier.buildClassifier(traindata);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.setErr(errStr);
		}
	}
	
	
	public class TraindatasetCluster extends AbstractClassifier {
		
		private static final long serialVersionUID = 1L;
		
		/* classifier per cluster */
		private HashMap<Integer, Classifier> cclassifier;
		
		/* instances per cluster */
		private HashMap<Integer, Instances> ctraindata; 
		
		/* holds the instances and indices of the pivot objects of the Fastmap calculation in buildClassifier*/
		private HashMap<Integer, Instance> cpivots;
		
		/* holds the indices of the pivot objects for x,y and the dimension [x,y][dimension]*/
		private int[][] cpivotindices;

		/* holds the sizes of the cluster multiple "boxes" per cluster */
		private HashMap<Integer, ArrayList<Double[][]>> csize;
		
		/* debug vars */
		@SuppressWarnings("unused")
		private boolean show_biggest = true;
		
		@SuppressWarnings("unused")
		private int CFOUND = 0;
		@SuppressWarnings("unused")
		private int CNOTFOUND = 0;
		
		
		private Instance createInstance(Instances instances, Instance instance) {
			// attributes for feeding instance to classifier
			Set<String> attributeNames = new HashSet<>();
			for( int j=0; j<instances.numAttributes(); j++ ) {
				attributeNames.add(instances.attribute(j).name());
			}
			
			double[] values = new double[instances.numAttributes()];
			int index = 0;
			for( int j=0; j<instance.numAttributes(); j++ ) {
				if( attributeNames.contains(instance.attribute(j).name())) {
					values[index] = instance.value(j);
					index++;
				}
			}
			
			Instances tmp = new Instances(instances);
			tmp.clear();
			Instance instCopy = new DenseInstance(instance.weight(), values);
			instCopy.setDataset(tmp);
			
			return instCopy;
		}
		
		/**
		 * Because Fastmap saves only the image not the values of the attributes it used
		 * we can not use the old data directly to classify single instances to clusters.
		 * 
		 * To classify a single instance we do a new fastmap computation with only the instance and
		 * the old pivot elements.
		 * 
		 * After that we find the cluster with our fastmap result for x and y.
		 */
		@Override
		public double classifyInstance(Instance instance) {
			
			double ret = 0;
			try {
				// classinstance gets passed to classifier
				Instances traindata = ctraindata.get(0);
				Instance classInstance = createInstance(traindata, instance);

				// this one keeps the class attribute
				Instances traindata2 = ctraindata.get(1);  
				
				// remove class attribute before clustering
				Remove filter = new Remove();
				filter.setAttributeIndices("" + (traindata.classIndex() + 1));
				filter.setInputFormat(traindata);
				traindata = Filter.useFilter(traindata, filter);
				Instance clusterInstance = createInstance(traindata, instance);
				
				Fastmap FMAP = new Fastmap(2);
				EuclideanDistance dist = new EuclideanDistance(traindata);
				
				// we set our pivot indices [x=0,y=1][dimension]
				int[][] npivotindices = new int[2][2];
				npivotindices[0][0] = 1;
				npivotindices[1][0] = 2;
				npivotindices[0][1] = 3;
				npivotindices[1][1] = 4;
				
				// build temp dist matrix (2 pivots per dimension + 1 instance we want to classify)
				// the instance we want to classify comes first after that the pivot elements in the order defined above
				double[][] distmat = new double[2*FMAP.target_dims+1][2*FMAP.target_dims+1];
				distmat[0][0] = 0;
				distmat[0][1] = dist.distance(clusterInstance, this.cpivots.get((Integer)this.cpivotindices[0][0]));
				distmat[0][2] = dist.distance(clusterInstance, this.cpivots.get((Integer)this.cpivotindices[1][0]));
				distmat[0][3] = dist.distance(clusterInstance, this.cpivots.get((Integer)this.cpivotindices[0][1]));
				distmat[0][4] = dist.distance(clusterInstance, this.cpivots.get((Integer)this.cpivotindices[1][1]));
				
				distmat[1][0] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][0]), clusterInstance);
				distmat[1][1] = 0;
				distmat[1][2] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][0]), this.cpivots.get((Integer)this.cpivotindices[1][0]));
				distmat[1][3] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][0]), this.cpivots.get((Integer)this.cpivotindices[0][1]));
				distmat[1][4] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][0]), this.cpivots.get((Integer)this.cpivotindices[1][1]));
				
				distmat[2][0] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][0]), clusterInstance);
				distmat[2][1] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][0]), this.cpivots.get((Integer)this.cpivotindices[0][0]));
				distmat[2][2] = 0;
				distmat[2][3] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][0]), this.cpivots.get((Integer)this.cpivotindices[0][1]));
				distmat[2][4] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][0]), this.cpivots.get((Integer)this.cpivotindices[1][1]));
				
				distmat[3][0] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][1]), clusterInstance);
				distmat[3][1] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][1]), this.cpivots.get((Integer)this.cpivotindices[0][0]));
				distmat[3][2] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][1]), this.cpivots.get((Integer)this.cpivotindices[1][0]));
				distmat[3][3] = 0;
				distmat[3][4] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[0][1]), this.cpivots.get((Integer)this.cpivotindices[1][1]));

				distmat[4][0] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][1]), clusterInstance);
				distmat[4][1] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][1]), this.cpivots.get((Integer)this.cpivotindices[0][0]));
				distmat[4][2] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][1]), this.cpivots.get((Integer)this.cpivotindices[1][0]));
				distmat[4][3] = dist.distance(this.cpivots.get((Integer)this.cpivotindices[1][1]), this.cpivots.get((Integer)this.cpivotindices[0][1]));
				distmat[4][4] = 0;
				
				
				/* debug output: show biggest distance found within the new distance matrix
				double biggest = 0;
				for(int i=0; i < distmat.length; i++) {
					for(int j=0; j < distmat[0].length; j++) {
						if(biggest < distmat[i][j]) {
							biggest = distmat[i][j];
						}
					}
				}
				if(this.show_biggest) {
					Console.traceln(Level.INFO, String.format(""+clusterInstance));
					Console.traceln(Level.INFO, String.format("biggest distances: "+ biggest));
					this.show_biggest = false;
				}
				*/

				FMAP.setDistmat(distmat);
				FMAP.setPivots(npivotindices);
				FMAP.calculate();
				double[][] x = FMAP.getX();
				double[] proj = x[0];

				// debug output: show the calculated distance matrix, our result vektor for the instance and the complete result matrix
				/*
				Console.traceln(Level.INFO, "distmat:");
			    for(int i=0; i<distmat.length; i++){
			        for(int j=0; j<distmat[0].length; j++){
			        	Console.trace(Level.INFO, String.format("%20s", distmat[i][j]));
			        }
			        Console.traceln(Level.INFO, "");
			    }
			    
			    Console.traceln(Level.INFO, "vector:");
			    for(int i=0; i < proj.length; i++) {
			    	Console.trace(Level.INFO, String.format("%20s", proj[i]));
			    }
			    Console.traceln(Level.INFO, "");
			    
				Console.traceln(Level.INFO, "resultmat:");
			    for(int i=0; i<x.length; i++){
			        for(int j=0; j<x[0].length; j++){
			        	Console.trace(Level.INFO, String.format("%20s", x[i][j]));
			        }
			        Console.traceln(Level.INFO, "");
			    }
			    */
				
				// now we iterate over all clusters (well, boxes of sizes per cluster really) and save the number of the 
				// cluster in which we are
				int cnumber;
				int found_cnumber = -1;
				Iterator<Integer> clusternumber = this.csize.keySet().iterator();
				while ( clusternumber.hasNext() && found_cnumber == -1) {
					cnumber = clusternumber.next();
					
					// now iterate over the boxes of the cluster and hope we find one (cluster could have been removed)
					// or we are too far away from any cluster because of the fastmap calculation with the initial pivot objects
					for ( int box=0; box < this.csize.get(cnumber).size(); box++ ) { 
						Double[][] current = this.csize.get(cnumber).get(box);
						
						if(proj[0] >= current[0][0] && proj[0] <= current[0][1] &&  // x 
						   proj[1] >= current[1][0] && proj[1] <= current[1][1]) {  // y
							found_cnumber = cnumber;
						}
					}
				}
				
				// we want to count how often we are really inside a cluster
				//if ( found_cnumber == -1 ) {
				//	CNOTFOUND += 1;
				//}else {
				//	CFOUND += 1;
				//}

				// now it can happen that we do not find a cluster because we deleted it previously (too few instances)
				// or we get bigger distance measures from weka so that we are completely outside of our clusters.
				// in these cases we just find the nearest cluster to our instance and use it for classification.
				// to do that we use the EuclideanDistance again to compare our distance to all other Instances
				// then we take the cluster of the closest weka instance
				dist = new EuclideanDistance(traindata2);
				if( !this.ctraindata.containsKey(found_cnumber) ) { 
					double min_distance = Double.MAX_VALUE;
					clusternumber = ctraindata.keySet().iterator();
					while ( clusternumber.hasNext() ) {
						cnumber = clusternumber.next();
						for(int i=0; i < ctraindata.get(cnumber).size(); i++) {
							if(dist.distance(instance, ctraindata.get(cnumber).get(i)) <= min_distance) {
								found_cnumber = cnumber;
								min_distance = dist.distance(instance, ctraindata.get(cnumber).get(i));
							}
						}
					}
				}
				
				// here we have the cluster where an instance has the minimum distance between itself and the
				// instance we want to classify
				// if we still have not found a cluster we exit because something is really wrong
				if( found_cnumber == -1 ) {
					Console.traceln(Level.INFO, String.format("ERROR matching instance to cluster with full search!"));
					throw new RuntimeException("cluster not found with full search");
				}
				
				// classify the passed instance with the cluster we found and its training data
				ret = cclassifier.get(found_cnumber).classifyInstance(classInstance);
				
			}catch( Exception e ) {
				Console.traceln(Level.INFO, String.format("ERROR matching instance to cluster!"));
				throw new RuntimeException(e);
			}
			return ret;
		}
		
		@Override
		public void buildClassifier(Instances traindata) throws Exception {
			
			//Console.traceln(Level.INFO, String.format("found: "+ CFOUND + ", notfound: " + CNOTFOUND));
			this.show_biggest = true;
			
			cclassifier = new HashMap<Integer, Classifier>();
			ctraindata = new HashMap<Integer, Instances>();
			cpivots = new HashMap<Integer, Instance>();
			cpivotindices = new int[2][2];
			
			// 1. copy traindata
			Instances train = new Instances(traindata);
			Instances train2 = new Instances(traindata);  // this one keeps the class attribute
			
			// 2. remove class attribute for clustering
			Remove filter = new Remove();
			filter.setAttributeIndices("" + (train.classIndex() + 1));
			filter.setInputFormat(train);
			train = Filter.useFilter(train, filter);
			
			// 3. calculate distance matrix (needed for Fastmap because it starts at dimension 1)
			double biggest = 0;
			EuclideanDistance dist = new EuclideanDistance(train);
			double[][] distmat = new double[train.size()][train.size()];
			for( int i=0; i < train.size(); i++ ) {
				for( int j=0; j < train.size(); j++ ) {
					distmat[i][j] = dist.distance(train.get(i), train.get(j));
					if( distmat[i][j] > biggest ) {
						biggest = distmat[i][j];
					}
				}
			}
			//Console.traceln(Level.INFO, String.format("biggest distances: "+ biggest));
			
			// 4. run fastmap for 2 dimensions on the distance matrix
			Fastmap FMAP = new Fastmap(2);
			FMAP.setDistmat(distmat);
			FMAP.calculate();
			
			cpivotindices = FMAP.getPivots();
			
			double[][] X = FMAP.getX();
			distmat = new double[0][0];
			System.gc();
			
			// quadtree payload generation
			ArrayList<QuadTreePayload<Instance>> qtp = new ArrayList<QuadTreePayload<Instance>>();
		    
			// we need these for the sizes of the quadrants
			double[] big = {0,0};
			double[] small = {Double.MAX_VALUE,Double.MAX_VALUE};
			
			// set quadtree payload values and get max and min x and y values for size
		    for( int i=0; i<X.length; i++ ){
		    	if(X[i][0] >= big[0]) {
		    		big[0] = X[i][0];
		    	}
		    	if(X[i][1] >= big[1]) {
		    		big[1] = X[i][1];
		    	}
		    	if(X[i][0] <= small[0]) {
		    		small[0] = X[i][0];
		    	}
		    	if(X[i][1] <= small[1]) {
		    		small[1] = X[i][1];
		    	}
		        QuadTreePayload<Instance> tmp = new QuadTreePayload<Instance>(X[i][0], X[i][1], train2.get(i));
		        qtp.add(tmp);
		    }
		    
		    //Console.traceln(Level.INFO, String.format("size for cluster ("+small[0]+","+small[1]+") - ("+big[0]+","+big[1]+")"));
		    
		    // 5. generate quadtree
		    QuadTree TREE = new QuadTree(null, qtp);
		    QuadTree.size = train.size();
		    QuadTree.alpha = Math.sqrt(train.size());
		    QuadTree.ccluster = new ArrayList<ArrayList<QuadTreePayload<Instance>>>();
		    QuadTree.csize = new HashMap<Integer, ArrayList<Double[][]>>();
		    
		    //Console.traceln(Level.INFO, String.format("Generate QuadTree with "+ QuadTree.size + " size, Alpha: "+ QuadTree.alpha+ ""));
		    
		    // set the size and then split the tree recursively at the median value for x, y
		    TREE.setSize(new double[] {small[0], big[0]}, new double[] {small[1], big[1]});
		    
		    // recursive split und grid clustering eher static
		    TREE.recursiveSplit(TREE);
		    
		    // generate list of nodes sorted by density (childs only)
		    ArrayList<QuadTree> l = new ArrayList<QuadTree>(TREE.getList(TREE));
		    
		    // recursive grid clustering (tree pruning), the values are stored in ccluster
		    TREE.gridClustering(l);
		    
		    // wir iterieren durch die cluster und sammeln uns die instanzen daraus
		    //ctraindata.clear();
		    for( int i=0; i < QuadTree.ccluster.size(); i++ ) {
		    	ArrayList<QuadTreePayload<Instance>> current = QuadTree.ccluster.get(i);
		    	
		    	// i is the clusternumber
		    	// we only allow clusters with Instances > ALPHA, other clusters are not considered!
		    	//if(current.size() > QuadTree.alpha) {
		    	if( current.size() > 4 ) {
			    	for( int j=0; j < current.size(); j++ ) {
			    		if( !ctraindata.containsKey(i) ) {
			    			ctraindata.put(i, new Instances(train2));
			    			ctraindata.get(i).delete();
			    		}
			    		ctraindata.get(i).add(current.get(j).getInst());
			    	}
		    	}else{
		    		Console.traceln(Level.INFO, String.format("drop cluster, only: " + current.size() + " instances"));
		    	}
		    }
			
			// here we keep things we need later on
			// QuadTree sizes for later use (matching new instances)
			this.csize = new HashMap<Integer, ArrayList<Double[][]>>(QuadTree.csize);
		
			// pivot elements
			//this.cpivots.clear();
			for( int i=0; i < FMAP.PA[0].length; i++ ) {
				this.cpivots.put(FMAP.PA[0][i], (Instance)train.get(FMAP.PA[0][i]).copy());
			}
			for( int j=0; j < FMAP.PA[0].length; j++ ) {
				this.cpivots.put(FMAP.PA[1][j], (Instance)train.get(FMAP.PA[1][j]).copy());
			}
			
			
			/* debug output
			int pnumber;
			Iterator<Integer> pivotnumber = cpivots.keySet().iterator();
			while ( pivotnumber.hasNext() ) {
				pnumber = pivotnumber.next();
				Console.traceln(Level.INFO, String.format("pivot: "+pnumber+ " inst: "+cpivots.get(pnumber)));
			}
			*/
			
		    // train one classifier per cluster, we get the cluster number from the traindata
		    int cnumber;
			Iterator<Integer> clusternumber = ctraindata.keySet().iterator();
			//cclassifier.clear();
			
			//int traindata_count = 0;
			while ( clusternumber.hasNext() ) {
				cnumber = clusternumber.next();
				cclassifier.put(cnumber,setupClassifier());  // this is the classifier used for the cluster 
				cclassifier.get(cnumber).buildClassifier(ctraindata.get(cnumber));
				//Console.traceln(Level.INFO, String.format("classifier in cluster "+cnumber));
				//traindata_count += ctraindata.get(cnumber).size();
				//Console.traceln(Level.INFO, String.format("building classifier in cluster "+cnumber +"  with "+ ctraindata.get(cnumber).size() +" traindata instances"));
			}
			
			// add all traindata
			//Console.traceln(Level.INFO, String.format("traindata in all clusters: " + traindata_count));
		}
	}
	

	/**
	 * Payload for the QuadTree.
	 * x and y are the calculated Fastmap values.
	 * T is a weka instance.
	 */
	public class QuadTreePayload<T> {

		public double x;
		public double y;
		private T inst;
		
		public QuadTreePayload(double x, double y, T value) {
			this.x = x;
			this.y = y;
			this.inst = value;
		}
		
		public T getInst() {
			return this.inst;
		}
	}
	
	
	/**
	 * Fastmap implementation
	 * 
	 * Faloutsos, C., & Lin, K. I. (1995). 
	 * FastMap: A fast algorithm for indexing, data-mining and visualization of traditional and multimedia datasets 
	 * (Vol. 24, No. 2, pp. 163-174). ACM.
	 */
	public class Fastmap {
		
		/*N x k Array, at the end, the i-th row will be the image of the i-th object*/
		private double[][] X;
		
		/*2 x k pivot Array one pair per recursive call*/
		private int[][] PA;
		
		/*Objects we got (distance matrix)*/
		private double[][] O;
		
		/*column of X currently updated (also the dimension)*/
		private int col = 0;
		
		/*number of dimensions we want*/
		private int target_dims = 0;
		
		// if we already have the pivot elements
		private boolean pivot_set = false;
		

		public Fastmap(int k) {
			this.target_dims = k;
		}
		
		/**
		 * Sets the distance matrix
		 * and params that depend on this
		 * @param O
		 */
		public void setDistmat(double[][] O) {
			this.O = O;
			int N = O.length;
			this.X = new double[N][this.target_dims];
			this.PA = new int[2][this.target_dims];
		}
		
		/**
		 * Set pivot elements, we need that to classify instances
		 * after the calculation is complete (because we then want to reuse
		 * only the pivot elements).
		 * 
		 * @param pi
		 */
		public void setPivots(int[][] pi) {
			this.pivot_set = true;
			this.PA = pi;
		}
		
		/**
		 * Return the pivot elements that were chosen during the calculation
		 * 
		 * @return
		 */
		public int[][] getPivots() {
			return this.PA;
		}
		
		/**
		 * The distance function for euclidean distance
		 * 
		 * Acts according to equation 4 of the fastmap paper
		 *  
		 * @param x x index of x image (if k==0 x object)
		 * @param y y index of y image (if k==0 y object)
		 * @param kdimensionality
		 * @return distance
		 */
		private double dist(int x, int y, int k) {
			
			// basis is object distance, we get this from our distance matrix
			double tmp = this.O[x][y] * this.O[x][y]; 
			
			// decrease by projections
			for( int i=0; i < k; i++ ) {
				double tmp2 = (this.X[x][i] - this.X[y][i]);
				tmp -= tmp2 * tmp2;
			}
			
			return Math.abs(tmp);
		}

		/**
		 * Find the object farthest from the given index
		 * This method is a helper Method for findDistandObjects
		 * 
		 * @param index of the object 
		 * @return index of the farthest object from the given index
		 */
		private int findFarthest(int index) {
			double furthest = Double.MIN_VALUE;
			int ret = 0;
			
			for( int i=0; i < O.length; i++ ) {
				double dist = this.dist(i, index, this.col);
				if( i != index && dist > furthest ) {
					furthest = dist;
					ret = i;
				}
			}
			return ret;
		}
		
		/**
		 * Finds the pivot objects 
		 * 
		 * This method is basically algorithm 1 of the fastmap paper.
		 * 
		 * @return 2 indexes of the choosen pivot objects
		 */
		private int[] findDistantObjects() {
			// 1. choose object randomly
			Random r = new Random();
			int obj = r.nextInt(this.O.length);
			
			// 2. find farthest object from randomly chosen object
			int idx1 = this.findFarthest(obj);
			
			// 3. find farthest object from previously farthest object
			int idx2 = this.findFarthest(idx1);

			return new int[] {idx1, idx2};
		}
	
		/**
		 * Calculates the new k-vector values (projections)
		 * 
		 * This is basically algorithm 2 of the fastmap paper.
		 * We just added the possibility to pre-set the pivot elements because
		 * we need to classify single instances after the computation is already done.
		 * 
		 * @param dims dimensionality
		 */
		public void calculate() {
			
			for( int k=0; k < this.target_dims; k++ ) {
				// 2) choose pivot objects
				if ( !this.pivot_set ) {
					int[] pivots = this.findDistantObjects();
		
					// 3) record ids of pivot objects 
					this.PA[0][this.col] = pivots[0];
					this.PA[1][this.col] = pivots[1];
				}
				
				// 4) inter object distances are zero (this.X is initialized with 0 so we just continue)
				if( this.dist(this.PA[0][this.col], this.PA[1][this.col], this.col) == 0 ) {
					continue;
				}
				
				// 5) project the objects on the line between the pivots
				double dxy = this.dist(this.PA[0][this.col], this.PA[1][this.col], this.col);
				for( int i=0; i < this.O.length; i++ ) {
					
					double dix = this.dist(i, this.PA[0][this.col], this.col);
					double diy = this.dist(i, this.PA[1][this.col], this.col);
					
					double tmp = (dix + dxy - diy) / (2 * Math.sqrt(dxy));
					
					// save the projection
					this.X[i][this.col] = tmp;
				}
				
				this.col += 1;
			}
		}
		
		/**
		 * returns the result matrix of the projections
		 * 
		 * @return calculated result
		 */
		public double[][] getX() {
			return this.X;
		}
	}
}
