
package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.core.ChebyshevDistance;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.neighboursearch.LinearNNSearch;

/**
 * <p>
 * Implements a classifier with internal search based optimization of the training data. Most of the
 * code is copy/paste from the original replication kit.
 * 
 * We adopted the code to be a weka classifier, and only kept parts required for the training of the
 * classifier. All additional code, e.g., for data loading or evaluation is not included. The
 * original replication kit is available online: DOI: https://doi.org/10.5281/zenodo.804413 GitHub
 * (commit 2): https://github.com/rebvar/GIS
 * 
 * Because most of it is copy/paste from another location, there are only few comments.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SearchBasedSelectionClassifier extends AbstractClassifier
    implements ITestAwareClassifier
{

    /**  */
    private static final long serialVersionUID = -8561483112088170853L;

    private Instances testdata = null;

    private Classifier[] internalClassifiers = null;

    private int[] testDataSplits = null;

    int numParts = 5;

    int numValidationNeighbors = 3;

    public int popSize = 30;
    public double chrmSize = 0.02D;

    public int numGens = 20;

    public int sizeTopP = 2;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.wekaclassifier.ITestAwareClassifier#setTestdata(weka.core.Instances)
     */
    @SuppressWarnings("hiding")
    @Override
    public void setTestdata(Instances testdata) {
        this.testdata = testdata;
    }

    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        ArrayList<ArrayList<Prediction>> preds = new ArrayList<>();
        ArrayList<IChrm> pop = new ArrayList<>();

        Instances trainSet = new Instances(traindata);
        Instances testSet = new Instances(testdata);
        internalClassifiers = new Classifier[numParts];
        pop.clear();

        // create test partitions beforehand
        Instances[] testParts = new Instances[numParts];
        for (int k = 0; k < numParts; k++) {
            testParts[k] = new Instances(testSet, 0);
        }

        // create shuffled array
        int[] randIndex = shuffleArray(IntStream.range(0, testSet.size()).toArray());
        this.testDataSplits = new int[testdata.size()];
        for (int i = 0; i < testSet.size(); i++) {
            int curPart = (int) (i / Math.ceil(testSet.size() / (double) numParts));
            testParts[curPart].add(testSet.get(randIndex[i]));
            testDataSplits[randIndex[i]] = curPart;
        }

        preds.clear();
        double[] vals = null;

        HashSet<Integer> uinds = new HashSet<Integer>();
        for (int p = 0; p < numParts; p++) {
            ArrayList<Double> diffp = new ArrayList<>();
            pop.clear();

            Instances testPart = testParts[p];

            Instances vSet = DPLIB.NNFilterMulti(trainSet, testPart, numValidationNeighbors);

            int size = (int) (trainSet.numInstances() * chrmSize);

            for (int i = 0; i < popSize; i++) {
                Instances trSet = new Instances(trainSet, 0);
                int j = 0;
                while (j < size) {
                    int index = DPLIB.rnd.nextInt(trainSet.numInstances());
                    trSet.add(trainSet.instance(index));

                    if (!uinds.contains(index)) {
                        uinds.add(index);
                    }

                    j++;
                }
                NaiveBayes l = new NaiveBayes();
                l.buildClassifier(trSet);
                Evaluation evaluation = new Evaluation(trSet);
                evaluation.evaluateModel(l, vSet, new Object[0]);
                ArrayList<Prediction> vec = evaluation.predictions();
                vals = DPLIB.getResults(vec);
                vals = DPLIB.getMeasures(vals);

                pop.add(new GIS_Chrm(trSet, vals));
            }
            pop = DPLIB.POP_SORT(pop);

            int cnt = 0;
            int g = 0;
            for (g = 0; g < numGens; g++) {
                ArrayList<IChrm> popNEW = new ArrayList<>();

                for (int i = 0; i < sizeTopP; i++) {
                    popNEW.add(pop.get(i));
                }
                for (int i = 0; i < pop.size() - sizeTopP; i += 2) {
                    int idx1 = 0;
                    int idx2 = 0;
                    while (idx1 == idx2) {
                        if (cnt >= 3) {
                            idx1 = DPLIB.rnd.nextInt(pop.size());
                            idx2 = DPLIB.rnd.nextInt(pop.size());
                        }
                        else {
                            idx1 = GA.tornament(pop);
                            idx2 = GA.tornament(pop);
                            cnt++;
                        }
                    }
                    cnt = 0;
                    Instances ds1 = ((GIS_Chrm) pop.get(idx1)).ds;
                    Instances ds2 = ((GIS_Chrm) pop.get(idx2)).ds;

                    Instances[] ret = GA.crossOver(ds1, ds2);
                    ds1 = ret[0];
                    ds2 = ret[1];
                    ds1 = GA.Mutate(ds1);
                    ds2 = GA.Mutate(ds2);
                    popNEW.add(new GIS_Chrm(ds1, null));
                    popNEW.add(new GIS_Chrm(ds2, null));
                }
                for (int i = 0; i < popNEW.size(); i++) {
                    NaiveBayes l = new NaiveBayes();
                    l.buildClassifier(((GIS_Chrm) popNEW.get(i)).ds);
                    Evaluation evaluation = new Evaluation(((GIS_Chrm) popNEW.get(i)).ds);
                    evaluation.evaluateModel(l, vSet, new Object[0]);
                    ArrayList<Prediction> vec = evaluation.predictions();
                    vals = DPLIB.getResults(vec);
                    vals = DPLIB.getMeasures(vals);

                    ((GIS_Chrm) popNEW.get(i)).fitness = vals;
                }
                popNEW = DPLIB.POP_SORT(popNEW);
                boolean exit = false;
                int countComp = 0;

                popNEW = DPLIB.CombinePops(pop, popNEW);
                double diff = Math.abs(GA.GetMeanFittness(pop, countComp) -
                    GA.GetMeanFittness(popNEW, countComp));
                pop = popNEW;
                diffp.add(diff);
                if (diff < 1.0E-4D) {
                    exit = true;
                }
                if ((((GIS_Chrm) pop.get(0)).getFitness() > 0.0D) && (exit)) {
                    break;
                }
                exit = false;
            }

            // get best population and build classifier
            Instances tds = ((GIS_Chrm) pop.get(0)).ds;
            NaiveBayes l = new NaiveBayes();
            l.buildClassifier(tds);
            internalClassifiers[p] = l;
        }
    }

    @Override
    public double[] distributionForInstance(Instance instance) throws Exception {
        int splitIndex = -1;
        // check if instance is from the test data
        if( instance.dataset().relationName().equals(testdata.relationName()) && instance.dataset().size()==testdata.size()) {
            for (int i = 0; i < instance.dataset().size(); i++) {
                // use == to check for same
                // we do not just want equals
                if (instance.dataset().get(i) == instance) {
                    splitIndex = testDataSplits[i];
                    break;
                }
            }
        }
        // check if equal instance is in test data
        for (int i = 0; i < testdata.size(); i++) {
            int equalAtts = 0;
            for (int j = 0; j < instance.numAttributes(); j++) {
                if (j != instance.classIndex()) {
                    if (instance.value(j) == testdata.get(i).value(j)) {
                        equalAtts++;
                    }
                }
            }
            if (equalAtts == instance.numAttributes() - 1) {
                splitIndex = testDataSplits[i];
                break;
            }
        }

        return internalClassifiers[splitIndex].distributionForInstance(instance);
    }

    /**
     * <p>
     * Adopted from https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
     * </p>
     */
    private static int[] shuffleArray(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
        return array;
    }

    private interface IChrm {

        public double getFitness();

        public double getCustom();

        public double getGMEAN();

        public double getF();

    }

    private class GIS_Chrm implements IChrm {
        public Instances ds;
        public double[] fitness;

        @SuppressWarnings("hiding")
        public GIS_Chrm(Instances ds, double[] fitness) {
            this.ds = ds;
            this.fitness = fitness;

        }

        @Override
        public double getFitness() {
            return getCustom();
        }

        @Override
        public double getCustom() {

            return getF() * getGMEAN();

        }

        @Override
        public double getGMEAN() {
            return Math.sqrt(fitness[1] * fitness[0]);
        }

        @Override
        public double getF() {
            return fitness[3];
        }
    }

    private static class DPLIB {

        static double cf = 0.5;

        public static Random rnd = new Random(System.currentTimeMillis());

        public static double[] getResults(ArrayList<Prediction> vec) {
            double tp = 0.0, fp = 0.0, tn = 0.0, fn = 0.0;
            for (int p = 0; p < vec.size(); p++) {

                NominalPrediction np = (NominalPrediction) vec.get(p);

                double geo = np.actual();
                double res = np.distribution()[1];

                if (geo >= cf) {
                    geo = 1;
                }
                else {
                    geo = 0;
                }

                if (res >= cf) {
                    res = 1;
                }
                else {
                    res = 0;
                }

                if (geo == 0 && res == 0) {
                    tn += 1;
                }
                else if (geo > 0 && res > 0) {
                    tp += 1;
                }
                else if (geo == 0 && res > 0) {
                    fp += 1;
                }
                else if (geo > 0 && res == 0) {
                    fn += 1;
                }

            }

            return new double[]
                { tp, tn, fp, fn };
        }

        public static ArrayList<IChrm> POP_SORT(ArrayList<IChrm> hmm) {
            for (int i = 0; i < hmm.size() - 1; i++) {
                for (int j = i + 1; j < hmm.size() - 1; j++) {
                    if (hmm.get(i).getFitness() < hmm.get(j).getFitness()) {
                        IChrm tmp = hmm.get(i);
                        hmm.set(i, hmm.get(j));
                        hmm.set(j, tmp);
                    }
                }
            }
            return hmm;
        }

        public static ArrayList<IChrm> CombinePops(ArrayList<IChrm> hmm, ArrayList<IChrm> newHMM) {
            int i = 0;
            int j = 0;
            ArrayList<IChrm> ret = new ArrayList<IChrm>();
            while (ret.size() < hmm.size()) {
                if (hmm.get(i).getFitness() >= newHMM.get(j).getFitness()) {
                    ret.add(hmm.get(i));
                    i += 1;
                }
                else {
                    ret.add(newHMM.get(j));
                    j += 1;
                }
            }
            return ret;

        }

        public static double[] getMeasures(double[] vals) {
            double tp = vals[0], fp = vals[2], fn = vals[3];
            double prec = 0.0, accu = 0.0, recall = 0.0;
            if (tp + fn != 0) {
                recall = tp / (tp + fn);
            }

            if (tp + fp != 0) {
                prec = tp / (tp + fp);
            }

            accu = 0;

            double F = 0;
            if (prec + recall != 0) {
                F = (2.0 * prec * recall) / (prec + recall);
            }

            return new double[]
                { recall, prec, accu, F };

        }

        public static Instances NNFilterMulti(Instances train, Instances test, int count)
            throws Exception
        {

            if (count == 0) {
                count = 1;
            }
            Instances trainCopy = new Instances(train);
            Instances testCopy = new Instances(test);
            trainCopy.setClassIndex(-1);
            testCopy.setClassIndex(-1);
            trainCopy.deleteAttributeAt(trainCopy.numAttributes() - 1);
            testCopy.deleteAttributeAt(testCopy.numAttributes() - 1);
            Instances out = new Instances(train, 0);
            LinearNNSearch knn = new LinearNNSearch(trainCopy);
            knn.setDistanceFunction(new EuclideanDistance(trainCopy));

            Set<Integer> instSet = new HashSet<Integer>();

            for (int i = 0; i < test.numInstances(); i++) {
                Instances nearestInstances = knn.kNearestNeighbours(testCopy.instance(i), count);
                for (int j = 0; j < nearestInstances.numInstances(); j++) {
                    int ind = FindInstanceIndex(nearestInstances.instance(j), train);
                    if (ind < 0) {
                        throw new Exception("Instance Not Found");
                    }
                    if (instSet.contains(ind)) {
                        continue;
                    }
                    instSet.add(ind);
                    Instance finst = train.instance(ind);
                    out.add(finst);
                }

            }

            knn.setDistanceFunction(new ChebyshevDistance(trainCopy));

            for (int i = 0; i < test.numInstances(); i++) {
                Instances nearestInstances = knn.kNearestNeighbours(testCopy.instance(i), count);
                for (int j = 0; j < nearestInstances.numInstances(); j++) {
                    int ind = FindInstanceIndex(nearestInstances.instance(j), train);
                    if (ind < 0) {
                        throw new Exception("Instance Not Found");
                    }
                    if (instSet.contains(ind)) {
                        continue;
                    }
                    instSet.add(ind);
                    Instance finst = train.instance(ind);
                    out.add(finst);
                }

            }

            knn.setDistanceFunction(new ManhattanDistance(trainCopy));

            for (int i = 0; i < test.numInstances(); i++) {
                Instances nearestInstances = knn.kNearestNeighbours(testCopy.instance(i), count);
                for (int j = 0; j < nearestInstances.numInstances(); j++) {
                    int ind = FindInstanceIndex(nearestInstances.instance(j), train);
                    if (ind < 0) {
                        throw new Exception("Instance Not Found");
                    }
                    if (instSet.contains(ind)) {
                        continue;
                    }
                    instSet.add(ind);
                    Instance finst = train.instance(ind);
                    out.add(finst);
                }

            }

            return out;
        }

        public static int FindInstanceIndex(Instance ins, Instances set) {
            for (int i = 0; i < set.numInstances(); i++) {
                boolean eq = true;
                for (int j = 0; j < ins.numAttributes(); j++) {
                    if (ins.value(j) != set.instance(i).value(j)) {
                        eq = false;
                        break;

                    }
                }
                if (eq) {
                    return i;
                }
            }
            return -1;
        }

        public static HashSet<Integer> FindAllSimilarInstancesIndexes(int index, Instances set) {

            HashSet<Integer> indexSet = new HashSet<Integer>();
            Instance ins = set.instance(index);
            for (int i = 0; i < set.numInstances(); i++) {
                if (i == index) {
                    indexSet.add(i);
                    continue;
                }
                boolean eq = true;
                for (int j = 0; j < ins.numAttributes() - 1; j++) {
                    if (ins.value(j) != set.instance(i).value(j)) {
                        eq = false;
                        break;

                    }
                }
                if (eq) {
                    indexSet.add(i);
                }
            }
            return indexSet;
        }
    }

    private static class GA {

        static double mProb = 0.05;
        static int mCount = 1;

        public static double GetMeanFittness(ArrayList<IChrm> hmm, int count) {
            if (count == 0)
                count = hmm.size();
            double sum = 0;
            for (int i = 0; i < count; i++) {
                sum += hmm.get(i).getFitness();
            }
            return sum / count;
        }

        public static Instances Mutate(Instances ds) {

            double r2 = DPLIB.rnd.nextDouble();

            if (r2 <= mProb) {

                Set<Integer> rands = new HashSet<Integer>();
                int i = 0;

                while (i < mCount) {

                    int r1 = DPLIB.rnd.nextInt(ds.numInstances());
                    if (rands.size() == ds.numInstances())
                        return ds;

                    if (rands.contains(r1))
                        continue;
                    double instLabel = ds.instance(r1).classValue();

                    rands.add(r1);
                    ds.instance(r1).setClassValue(1 - instLabel);

                    HashSet<Integer> set = DPLIB.FindAllSimilarInstancesIndexes(r1, ds);
                    Iterator<Integer> iter = set.iterator();
                    while (iter.hasNext()) {
                        r1 = iter.next();
                        rands.add(r1);
                        ds.instance(r1).setClassValue(1 - instLabel);
                    }
                    i++;
                }

            }
            return ds;
        }

        public static int tornament(ArrayList<IChrm> hmm) {
            int[] vals = new int[2];
            for (int i = 0; i < vals.length; i++)
                vals[i] = DPLIB.rnd.nextInt(hmm.size());
            int maxInd = -1;
            double maxFit = 0;
            for (int i = 0; i < vals.length; i++) {
                if (hmm.get(i).getFitness() > maxFit) {
                    maxFit = hmm.get(i).getFitness();
                    maxInd = i;
                }
            }

            return maxInd;
        }

        public static Instances[] crossOver(Instances ds1, Instances ds2) {

            int ss = ds1.numInstances();
            int point1 = DPLIB.rnd.nextInt(ss);
            int point2 = point1;

            ds1.randomize(DPLIB.rnd);
            ds2.randomize(DPLIB.rnd);
            Instances ds1c = new Instances(ds1, 0);
            Instances ds2c = new Instances(ds2, 0);

            for (int i = 0; i < point1; i++) {
                ds1c.add(ds1.instance(i));

            }

            for (int i = 0; i < point2; i++) {
                ds2c.add(ds2.instance(i));

            }

            for (int i = point1; i < ds1.numInstances(); i++) {

                ds2c.add(ds1.instance(i));
            }

            for (int i = point2; i < ds2.numInstances(); i++) {
                ds1c.add(ds2.instance(i));

            }

            HashSet<Integer> pSet = new HashSet<Integer>();

            for (int i = 0; i < ds1c.numInstances(); i++) {
                if (pSet.contains(i))
                    continue;
                HashSet<Integer> tmp = DPLIB.FindAllSimilarInstancesIndexes(i, ds1c);
                double lbl = 0;
                Integer[] t = tmp.toArray(new Integer[0]);
                int index = -1;
                for (int j = 0; j < t.length; j++) {

                    index = t[j];
                    lbl += ds1c.instance(index).classValue();
                    pSet.add(index);
                }

                lbl = lbl / (t.length);
                if (lbl >= 0.5)
                    lbl = 1;
                else
                    lbl = 0;

                ds1c.instance(i).setClassValue(lbl);
                for (int j = 0; j < t.length; j++) {

                    index = t[j];
                    ds1c.instance(index).setClassValue(lbl);
                }
            }

            pSet.clear();
            for (int i = 0; i < ds2c.numInstances(); i++) {
                if (pSet.contains(i))
                    continue;
                HashSet<Integer> tmp = DPLIB.FindAllSimilarInstancesIndexes(i, ds2c);
                double lbl = 0;
                Integer[] t = tmp.toArray(new Integer[0]);
                int index;
                for (int j = 0; j < t.length; j++) {

                    index = t[j];
                    lbl += ds2c.instance(index).classValue();
                    pSet.add(index);
                }

                lbl = lbl / (t.length);
                if (lbl >= 0.5)
                    lbl = 1;
                else
                    lbl = 0;

                ds2c.instance(i).setClassValue(lbl);
                for (int j = 0; j < t.length; j++) {

                    index = t[j];
                    ds2c.instance(index).setClassValue(lbl);
                }
            }

            return new Instances[]
                { ds1c, ds2c };
        }

    }

}
