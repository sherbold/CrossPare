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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import weka.core.Instance;
import de.ugoe.cs.cpdp.training.WekaLocalFQTraining.QuadTreePayload;

/**
 * <p>
 * QuadTree implementation.
 * </p>
 * <p>
 * QuadTree gets a list of instances and then recursively split them into 4 children For this it
 * uses the median of the 2 values x,y.
 * </p>
 * 
 * @author Alexander Trautsch
 */
public class QuadTree {

    /**
     * 1 parent or null
     */
    private QuadTree parent = null;

    /**
     * north-west quadrant
     */
    private QuadTree child_nw;

    /**
     * north-east quadrant
     */
    private QuadTree child_ne;

    /**
     * south-east quadrant
     */
    private QuadTree child_se;

    /**
     * south-west quadrant
     */
    private QuadTree child_sw;

    /**
     * helper list for child quadrant generation
     */
    private ArrayList<QuadTree> l = new ArrayList<QuadTree>();

    /**
     * debugging attribute
     */
    public int level = 0;

    /**
     * size of the quadrant in x-dimension
     */
    private double[] x;

    /**
     * size of the quadrant in y-dimension
     */
    private double[] y;

    /**
     * debugging parameter
     */
    public static boolean verbose = false;

    /**
     * global size of the QuadTree.
     */
    public static int size = 0;

    /**
     * recursion parameter alpha
     */
    public static double alpha = 0;

    /**
     * data for each cluster
     */
    public static ArrayList<ArrayList<QuadTreePayload<Instance>>> ccluster =
        new ArrayList<ArrayList<QuadTreePayload<Instance>>>();

    /**
     * cluster sizes (index is cluster number, {@link ArrayList} is list of boxes (x0,y0,x1,y1
     */
    public static HashMap<Integer, ArrayList<Double[][]>> csize =
        new HashMap<Integer, ArrayList<Double[][]>>();

    /**
     * data within this quadrant
     */
    private ArrayList<QuadTreePayload<Instance>> payload;

    /**
     * <p>
     * Constructor. Creates a new QuadTree.
     * </p>
     *
     * @param parent
     *            parent of this tree
     * @param payload
     *            data within the quadrant
     */
    public QuadTree(QuadTree parent, ArrayList<QuadTreePayload<Instance>> payload) {
        this.parent = parent;
        this.payload = payload;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String n = "";
        if (this.parent == null) {
            n += "rootnode ";
        }
        String level = new String(new char[this.level]).replace("\0", "-");
        n += level + " instances: " + this.getNumbers();
        return n;
    }

    /**
     * <p>
     * Returns the payload, used for clustering in the clustering list we only have children with
     * payload
     * </p>
     * 
     * @return payload the payload
     */
    public ArrayList<QuadTreePayload<Instance>> getPayload() {
        return this.payload;
    }

    /**
     * <p>
     * Calculate the density of this quadrant as
     * <ul>
     * <li>density = number of instances / global size (all instances)</li>
     * </ul>
     * 
     * @return density the density
     */
    public double getDensity() {
        double dens = 0;
        dens = (double) this.getNumbers() / QuadTree.size;
        return dens;
    }

    /**
     * <p>
     * sets the size coordinates of the quadrant
     * </p>
     *
     * @param x
     *            x-dimension
     * @param y
     *            y-dimension
     */
    public void setSize(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <p>
     * returns the size of the quadrant
     * </p>
     *
     * @return size of the current quadrant
     */
    public double[][] getSize() {
        return new double[][]
            { this.x, this.y };
    }

    /**
     * <p>
     * returns the size of the quadrant
     * </p>
     *
     * @return size of the current quadrant
     */
    public Double[][] getSizeDouble() {
        Double[] tmpX = new Double[2];
        Double[] tmpY = new Double[2];

        tmpX[0] = this.x[0];
        tmpX[1] = this.x[1];

        tmpY[0] = this.y[0];
        tmpY[1] = this.y[1];

        return new Double[][]
            { tmpX, tmpY };
    }

    /**
     * <p>
     * calculates the median for the x axis
     * </p>
     * 
     * @return median for x
     */
    private double getMedianForX() {
        double med_x = 0;

        Collections.sort(this.payload, new Comparator<QuadTreePayload<Instance>>() {
            @Override
            public int compare(QuadTreePayload<Instance> x1, QuadTreePayload<Instance> x2) {
                return Double.compare(x1.x, x2.x);
            }
        });

        if (this.payload.size() % 2 == 0) {
            int mid = this.payload.size() / 2;
            med_x = (this.payload.get(mid).x + this.payload.get(mid + 1).x) / 2;
        }
        else {
            int mid = this.payload.size() / 2;
            med_x = this.payload.get(mid).x;
        }

        if (QuadTree.verbose) {
            System.out.println("sorted:");
            for (int i = 0; i < this.payload.size(); i++) {
                System.out.print("" + this.payload.get(i).x + ",");
            }
            System.out.println("median x: " + med_x);
        }
        return med_x;
    }

    /**
     * <p>
     * calculates the median for the y axis
     * </p>
     * 
     * @return median for y
     */
    private double getMedianForY() {
        double med_y = 0;

        Collections.sort(this.payload, new Comparator<QuadTreePayload<Instance>>() {
            @Override
            public int compare(QuadTreePayload<Instance> y1, QuadTreePayload<Instance> y2) {
                return Double.compare(y1.y, y2.y);
            }
        });

        if (this.payload.size() % 2 == 0) {
            int mid = this.payload.size() / 2;
            med_y = (this.payload.get(mid).y + this.payload.get(mid + 1).y) / 2;
        }
        else {
            int mid = this.payload.size() / 2;
            med_y = this.payload.get(mid).y;
        }

        if (QuadTree.verbose) {
            System.out.println("sorted:");
            for (int i = 0; i < this.payload.size(); i++) {
                System.out.print("" + this.payload.get(i).y + ",");
            }
            System.out.println("median y: " + med_y);
        }
        return med_y;
    }

    /**
     * <p>
     * Returns the number of instances in the payload
     * </p>
     * 
     * @return number of instances
     */
    public int getNumbers() {
        int number = 0;
        if (this.payload != null) {
            number = this.payload.size();
        }
        return number;
    }

    /**
     * <p>
     * Calculate median values of payload for x, y and split into 4 sectors
     * </p>
     * 
     * @return Array of QuadTree nodes (4 childs)
     * @throws Exception
     *             if we would run into an recursive loop
     */
    public QuadTree[] split() throws Exception {

        double medx = this.getMedianForX();
        double medy = this.getMedianForY();

        // Payload lists for each child
        ArrayList<QuadTreePayload<Instance>> nw = new ArrayList<QuadTreePayload<Instance>>();
        ArrayList<QuadTreePayload<Instance>> sw = new ArrayList<QuadTreePayload<Instance>>();
        ArrayList<QuadTreePayload<Instance>> ne = new ArrayList<QuadTreePayload<Instance>>();
        ArrayList<QuadTreePayload<Instance>> se = new ArrayList<QuadTreePayload<Instance>>();

        // sort the payloads to new payloads
        // here we have the problem that payloads with the same values are sorted
        // into the same slots and it could happen that medx and medy = size_x[1] and size_y[1]
        // in that case we would have an endless loop
        for (int i = 0; i < this.payload.size(); i++) {

            QuadTreePayload<Instance> item = this.payload.get(i);

            // north west
            if (item.x <= medx && item.y >= medy) {
                nw.add(item);
            }

            // south west
            else if (item.x <= medx && item.y <= medy) {
                sw.add(item);
            }

            // north east
            else if (item.x >= medx && item.y >= medy) {
                ne.add(item);
            }

            // south east
            else if (item.x >= medx && item.y <= medy) {
                se.add(item);
            }
        }

        // if we assign one child a payload equal to our own (see problem above)
        // we throw an exceptions which stops the recursion on this node
        if (nw.equals(this.payload)) {
            throw new Exception("payload equal");
        }
        if (sw.equals(this.payload)) {
            throw new Exception("payload equal");
        }
        if (ne.equals(this.payload)) {
            throw new Exception("payload equal");
        }
        if (se.equals(this.payload)) {
            throw new Exception("payload equal");
        }

        this.child_nw = new QuadTree(this, nw);
        this.child_nw.setSize(new double[]
            { this.x[0], medx }, new double[]
            { medy, this.y[1] });
        this.child_nw.level = this.level + 1;

        this.child_sw = new QuadTree(this, sw);
        this.child_sw.setSize(new double[]
            { this.x[0], medx }, new double[]
            { this.y[0], medy });
        this.child_sw.level = this.level + 1;

        this.child_ne = new QuadTree(this, ne);
        this.child_ne.setSize(new double[]
            { medx, this.x[1] }, new double[]
            { medy, this.y[1] });
        this.child_ne.level = this.level + 1;

        this.child_se = new QuadTree(this, se);
        this.child_se.setSize(new double[]
            { medx, this.x[1] }, new double[]
            { this.y[0], medy });
        this.child_se.level = this.level + 1;

        this.payload = null;
        return new QuadTree[]
            { this.child_nw, this.child_ne, this.child_se, this.child_sw };
    }

    /**
     * <p>
     * creates the children of a QuadTree and recursively splits them as well
     * </p>
     *
     * @param q
     *            tree that is split
     */
    public static void recursiveSplit(QuadTree q) {
        if (QuadTree.verbose) {
            System.out.println("splitting: " + q);
        }
        if (q.getNumbers() < QuadTree.alpha) {
            return;
        }
        else {
            // exception is thrown if we would run into an endless loop (see comments in split())
            try {
                QuadTree[] childs = q.split();
                recursiveSplit(childs[0]);
                recursiveSplit(childs[1]);
                recursiveSplit(childs[2]);
                recursiveSplit(childs[3]);
            }
            catch (Exception e) {
                return;
            }
        }
    }

    /**
     * <p>
     * returns an list of children sorted by density
     * </p>
     * 
     * @param q
     *            QuadTree
     */
    private void generateList(QuadTree q) {

        // we only have all childs or none at all
        if (q.child_ne == null) {
            this.l.add(q);
        }

        if (q.child_ne != null) {
            this.generateList(q.child_ne);
        }
        if (q.child_nw != null) {
            this.generateList(q.child_nw);
        }
        if (q.child_se != null) {
            this.generateList(q.child_se);
        }
        if (q.child_sw != null) {
            this.generateList(q.child_sw);
        }
    }

    /**
     * <p>
     * Checks if passed QuadTree is neighboring to us
     * </p>
     * 
     * @param q
     *            QuadTree
     * @return true if passed QuadTree is a neighbor
     */
    public boolean isNeighbour(QuadTree q) {
        boolean is_neighbour = false;

        double[][] our_size = this.getSize();
        double[][] new_size = q.getSize();

        // X is i=0, Y is i=1
        for (int i = 0; i < 2; i++) {
            // we are smaller than q
            // -------------- q
            // ------- we
            if (our_size[i][0] >= new_size[i][0] && our_size[i][1] <= new_size[i][1]) {
                is_neighbour = true;
            }
            // we overlap with q at some point
            // a) ---------------q
            // ----------- we
            // b) --------- q
            // --------- we
            if ((our_size[i][0] >= new_size[i][0] && our_size[i][0] <= new_size[i][1]) ||
                (our_size[i][1] >= new_size[i][0] && our_size[i][1] <= new_size[i][1]))
            {
                is_neighbour = true;
            }
            // we are larger than q
            // ---- q
            // ---------- we
            if (our_size[i][1] >= new_size[i][1] && our_size[i][0] <= new_size[i][0]) {
                is_neighbour = true;
            }
        }

        if (is_neighbour && QuadTree.verbose) {
            System.out.println(this + " neighbour of: " + q);
        }

        return is_neighbour;
    }

    /**
     * <p>
     * Perform pruning and clustering of the quadtree
     * </p>
     * <p>
     * Pruning according to: Tim Menzies, Andrew Butcher, David Cok, Andrian Marcus, Lucas Layman,
     * Forrest Shull, Burak Turhan, Thomas Zimmermann,
     * "Local versus Global Lessons for Defect Prediction and Effort Estimation," IEEE Transactions
     * on Software Engineering, vol. 39, no. 6, pp. 822-834, June, 2013
     * </p>
     * <ol>
     * <li>get list of leaf quadrants</li>
     * <li>sort by their density</li>
     * <li>set stop_rule to 0.5*highest Density in the list</li>
     * <li>merge all nodes with a density > stop_rule to the new cluster and remove all from list
     * </li>
     * <li>repeat</li>
     * </ol>
     * 
     * @param list
     *            List of QuadTree (children only)
     */
    public void gridClustering(ArrayList<QuadTree> list) {

        if (list.size() == 0) {
            return;
        }

        double stop_rule;
        QuadTree biggest;
        QuadTree current;

        // current clusterlist
        ArrayList<QuadTreePayload<Instance>> current_cluster;

        // remove list (for removal of items after scanning of the list)
        ArrayList<Integer> remove = new ArrayList<Integer>();

        // 1. find biggest, and add it
        biggest = list.get(list.size() - 1);
        stop_rule = biggest.getDensity() * 0.5;

        current_cluster = new ArrayList<QuadTreePayload<Instance>>();
        current_cluster.addAll(biggest.getPayload());

        // remove the biggest because we are starting with it
        remove.add(list.size() - 1);

        ArrayList<Double[][]> tmpSize = new ArrayList<Double[][]>();
        tmpSize.add(biggest.getSizeDouble());

        // check the items for their density
        for (int i = list.size() - 1; i >= 0; i--) {
            current = list.get(i);

            // 2. find neighbors with correct density
            // if density > stop_rule and is_neighbour add to cluster and remove from list
            if (current.getDensity() > stop_rule && !current.equals(biggest) &&
                current.isNeighbour(biggest))
            {
                current_cluster.addAll(current.getPayload());

                // add it to remove list (we cannot remove it inside the loop because it would move
                // the index)
                remove.add(i);

                // get the size
                tmpSize.add(current.getSizeDouble());
            }
        }

        // 3. remove our removal candidates from the list
        for (Integer item : remove) {
            list.remove((int) item);
        }

        // 4. add to cluster
        QuadTree.ccluster.add(current_cluster);

        // 5. add sizes of our current (biggest) this adds a number of sizes (all QuadTree Instances
        // belonging to this cluster)
        // we need that to classify test instances to a cluster later
        Integer cnumber = new Integer(QuadTree.ccluster.size() - 1);
        if (QuadTree.csize.containsKey(cnumber) == false) {
            QuadTree.csize.put(cnumber, tmpSize);
        }

        // repeat
        this.gridClustering(list);
    }

    /**
     * <p>
     * debugging function that prints information about the QuadTree
     * </p>
     *
     */
    public void printInfo() {
        System.out.println("we have " + ccluster.size() + " clusters");

        for (int i = 0; i < ccluster.size(); i++) {
            System.out.println("cluster: " + i + " size: " + ccluster.get(i).size());
        }
    }

    /**
     * <p>
     * Helper Method to get a sorted list (by density) for all children
     * </p>
     * 
     * @param q
     *            QuadTree
     * @return Sorted ArrayList of quadtrees
     */
    public ArrayList<QuadTree> getList(QuadTree q) {
        this.generateList(q);

        Collections.sort(this.l, new Comparator<QuadTree>() {
            @Override
            public int compare(QuadTree x1, QuadTree x2) {
                return Double.compare(x1.getDensity(), x2.getDensity());
            }
        });

        return this.l;
    }
}
