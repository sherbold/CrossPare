
package de.ugoe.cs.cpdp.dataselection;

import java.util.ArrayList;

import org.apache.commons.collections4.list.SetUniqueList;

import de.ugoe.cs.util.console.Console;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * <p>
 * Training data selection as a combination of Zimmermann et al. 2009
 * </p>
 * 
 * @author Steffen Herbold
 */
public class DecisionTreeSelection extends AbstractCharacteristicSelection {

    /*
     * @see de.ugoe.cs.cpdp.dataselection.SetWiseDataselectionStrategy#apply(weka.core.Instances,
     * org.apache.commons.collections4.list.SetUniqueList)
     */
    @Override
    public void apply(Instances testdata, SetUniqueList<Instances> traindataSet) {
        final Instances data = characteristicInstances(testdata, traindataSet);

        final ArrayList<String> attVals = new ArrayList<String>();
        attVals.add("same");
        attVals.add("more");
        attVals.add("less");
        final ArrayList<Attribute> atts = new ArrayList<Attribute>();
        for (int j = 0; j < data.numAttributes(); j++) {
            atts.add(new Attribute(data.attribute(j).name(), attVals));
        }
        atts.add(new Attribute("score"));
        Instances similarityData = new Instances("similarity", atts, 0);
        similarityData.setClassIndex(similarityData.numAttributes() - 1);

        try {
            Classifier classifier = new J48();
            for (int i = 0; i < traindataSet.size(); i++) {
                classifier.buildClassifier(traindataSet.get(i));
                for (int j = 0; j < traindataSet.size(); j++) {
                    if (i != j) {
                        double[] similarity = new double[data.numAttributes() + 1];
                        for (int k = 0; k < data.numAttributes(); k++) {
                            if (0.9 * data.get(i + 1).value(k) > data.get(j + 1).value(k)) {
                                similarity[k] = 2.0;
                            }
                            else if (1.1 * data.get(i + 1).value(k) < data.get(j + 1).value(k)) {
                                similarity[k] = 1.0;
                            }
                            else {
                                similarity[k] = 0.0;
                            }
                        }

                        Evaluation eval = new Evaluation(traindataSet.get(j));
                        eval.evaluateModel(classifier, traindataSet.get(j));
                        similarity[data.numAttributes()] = eval.fMeasure(1);
                        similarityData.add(new DenseInstance(1.0, similarity));
                    }
                }
            }
            REPTree repTree = new REPTree();
            repTree.buildClassifier(similarityData);

            Instances testTrainSimilarity = new Instances(similarityData);
            testTrainSimilarity.clear();
            for (int i = 0; i < traindataSet.size(); i++) {
                double[] similarity = new double[data.numAttributes() + 1];
                for (int k = 0; k < data.numAttributes(); k++) {
                    if (0.9 * data.get(0).value(k) > data.get(i + 1).value(k)) {
                        similarity[k] = 2.0;
                    }
                    else if (1.1 * data.get(0).value(k) < data.get(i + 1).value(k)) {
                        similarity[k] = 1.0;
                    }
                    else {
                        similarity[k] = 0.0;
                    }
                }
                testTrainSimilarity.add(new DenseInstance(1.0, similarity));
            }

            int bestScoringProductIndex = -1;
            double maxScore = Double.MIN_VALUE;
            for (int i = 0; i < traindataSet.size(); i++) {
                double score = repTree.classifyInstance(testTrainSimilarity.get(i));
                if (score > maxScore) {
                    maxScore = score;
                    bestScoringProductIndex = i;
                }
            }
            Instances bestScoringProduct = traindataSet.get(bestScoringProductIndex);
            traindataSet.clear();
            traindataSet.add(bestScoringProduct);
        }
        catch (Exception e) {
            Console.printerr("failure during DecisionTreeSelection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
