
package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Arrays;


import org.apache.commons.math3.util.MathArrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ojalgo.access.Access2D.Builder;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.jama.JamaEigenvalue;
import org.ojalgo.matrix.jama.JamaEigenvalue.General;
import org.ojalgo.matrix.jama.JamaMatrix;

import de.ugoe.cs.cpdp.util.WekaUtils;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * Implements unsupervised spectral clustering classification.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SpectralClusteringClassifier extends AbstractClassifier {

    /**  */
    private static final long serialVersionUID = 1L;
    
    /**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");

    /**
     * Transformed values used for classification
     */
    private PrimitiveMatrix eigenTransformed = null;

    /**
     * Defines if negative or positive eigenvalues lead to a classification as defective
     */
    boolean negativeDefective = true;

    /**
     * Remember training data for classification
     */
    private Instances traindataCopy = null;

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
     */
    @Override
    public void buildClassifier(Instances traindata) throws Exception {
        this.traindataCopy = traindata;

        // build adjacency matrix
        PrimitiveMatrix adjecencyMatrix = createAdjecencyMatrix(traindata);
        PrimitiveMatrix rowSumSQRTs = createRowSumSQRTs(adjecencyMatrix);
        PrimitiveMatrix identityMatrix = createIdentityMatrix(traindata.size());

        PrimitiveMatrix symmetricLaplaceMatrix = identityMatrix
            .subtract(adjecencyMatrix.multiplyLeft(rowSumSQRTs).multiplyRight(rowSumSQRTs));

        General eigenvalueDecomposition = new JamaEigenvalue.General();
        eigenvalueDecomposition.compute(symmetricLaplaceMatrix);
        LOGGER.debug("eigenvalue problem solved");

        JamaMatrix eigenvectors = eigenvalueDecomposition.getV();

        Builder<PrimitiveMatrix> secondSmallestBuilder =
            PrimitiveMatrix.getBuilder(1, traindata.size());
        for (int i = 0; i < traindata.size(); i++) {
            secondSmallestBuilder.set(i, eigenvectors.doubleValue(i, eigenvectors.getColDim() - 2));
        }
        PrimitiveMatrix secondSmallest = secondSmallestBuilder.build();
        // JamaMatrix secondSmallest = eigenvectors.getColumnsRange(eigenvectors.getColDim()-2,
        // eigenvectors.getColDim()-1);
        PrimitiveMatrix eigenSquared = secondSmallest.multiplyElements(secondSmallest);
        double sum = 0.0;
        for (int i = 0; i < traindata.size(); i++) {
            sum += eigenSquared.doubleValue(i);
        }

        this.eigenTransformed = secondSmallest.divide(sum);

        double sumNeg = 0.0;
        int numNeg = 0;
        double sumPos = 0.0;
        int numPos = 0;
        for (int i = 0; i < traindata.size(); i++) {
            double[] curValues = WekaUtils.instanceValues(traindata.get(i));
            for (int j = 0; j < curValues.length; j++) {
                if (eigenTransformed.doubleValue(i) < 0.0) {
                    sumNeg += curValues[j];
                    numNeg++;
                }
                else {
                    sumPos += curValues[j];
                    numPos++;
                }
            }
        }
        double meanNeg = sumNeg / numNeg;
        double meanPos = sumPos / numPos;
        if (meanNeg > meanPos) {
            negativeDefective = true;
        }
        else {
            negativeDefective = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see weka.classifiers.AbstractClassifier#classifyInstance(weka.core.Instance)
     */
    @Override
    public double classifyInstance(Instance instance) throws Exception {
        int index = -1;
        for (int i = 0; i < instance.dataset().size(); i++) {
            // use == to check for same
            // we do not just want equals
            if (Arrays.equals(WekaUtils.instanceValues(instance),
                              WekaUtils.instanceValues(traindataCopy.get(i))))
            {
                index = i;
                break;
            }
        }
        if (index == -1) {
            LOGGER.error("SpectralClusteringClassifier only work with test data as training data");
        }

        double classification;
        if (negativeDefective && eigenTransformed.doubleValue(index) < 0.0) {
            classification = 1.0;
        }
        else if (!negativeDefective && eigenTransformed.doubleValue(index) > 0.0) {
            classification = 1.0;
        }
        else {
            classification = 0.0;
        }
        return classification;
    }

    /**
     * <p>
     * Creates the adjecency matrix with the euclidean distances between instances
     * </p>
     *
     * @param traindata
     *            the training data
     * @return the adjecency matrix
     */
    private static PrimitiveMatrix createAdjecencyMatrix(Instances traindata) {
        Builder<PrimitiveMatrix> adjecencyBuilder =
            PrimitiveMatrix.getBuilder(traindata.size(), traindata.size());

        for (int i = 0; i < traindata.size(); i++) {
            for (int j = 0; j < traindata.size(); j++) {
                if (i == j) {
                    adjecencyBuilder.set(i, j, 0.0);
                }
                else {
                    double distance =
                        MathArrays.distance(WekaUtils.instanceValues(traindata.get(i)),
                                            WekaUtils.instanceValues(traindata.get(j)));
                    if (distance < 0.0) {
                        distance = 0.0;
                    }
                    adjecencyBuilder.set(i, j, distance);
                }
            }
        }

        return adjecencyBuilder.build();
    }

    /**
     * <p>
     * Creates the diagonal matrix with the sqrt of the row sums.
     * </p>
     *
     * @param adjecencyMatrix
     *            matrix used as foundation
     * @return diagonal with sqrt of row sums
     */
    private static PrimitiveMatrix createRowSumSQRTs(PrimitiveMatrix adjecencyMatrix) {
        int nRows = (int) adjecencyMatrix.countRows();
        Builder<PrimitiveMatrix> laplaceBuilder = PrimitiveMatrix.getBuilder(nRows, nRows);

        for (int i = 0; i < nRows; i++) {
            double rowSum = 0.0;
            for (int j = 0; j < nRows; j++) {
                rowSum += adjecencyMatrix.get(i, j);
            }
            laplaceBuilder.set(i, i, Math.sqrt(rowSum));
        }

        return laplaceBuilder.build();
    }

    /**
     * <p>
     * Creates an identity matrix
     * </p>
     *
     * @param dim
     *            dimension
     * @return identity matrix
     */
    private static PrimitiveMatrix createIdentityMatrix(int dim) {
        Builder<PrimitiveMatrix> identityBuilder = PrimitiveMatrix.getBuilder(dim, dim);

        for (int i = 0; i < dim; i++) {
            identityBuilder.set(i, i, 1);
        }

        return identityBuilder.build();
    }
}
