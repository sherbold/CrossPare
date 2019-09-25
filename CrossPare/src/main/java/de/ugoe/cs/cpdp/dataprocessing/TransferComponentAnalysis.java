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

package de.ugoe.cs.cpdp.dataprocessing;

import java.util.Arrays;

import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.jama.JamaEigenvalue;
import org.ojalgo.matrix.jama.JamaEigenvalue.General;
import org.ojalgo.scalar.ComplexNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ojalgo.access.Access2D.Builder;
import org.ojalgo.array.Array1D;

import de.ugoe.cs.cpdp.util.SortUtils;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * <p>
 * TCA with a linear kernel after Pan et al. (Domain Adaptation via Transfer Component Analysis) and
 * used for defect prediction by Nam et al. (Transfer Defect Learning)
 * </p>
 * 
 * @author Steffen Herbold
 */
public class TransferComponentAnalysis implements IProcessesingStrategy {

	/**
     * Reference to the logger
     */
    private static final Logger LOGGER = LogManager.getLogger("main");
	
    /**
     * Dimension of the reduced data.
     */
    int reducedDimension = 5;

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.IParameterizable#setParameter(java.lang.String)
     */
    @Override
    public void setParameter(String parameters) {
        // dummy, paramters ignored
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ugoe.cs.cpdp.dataprocessing.IProcessesingStrategy#apply(weka.core.Instances,
     * weka.core.Instances)
     */
    @Override
    public void apply(Instances testdata, Instances traindata) {
        applyTCA(testdata, traindata);
    }

    /**
     * <p>
     * calculates the linear kernel function between two instances
     * </p>
     *
     * @param x1
     *            first instance
     * @param x2
     *            second instance
     * @return kernel value
     */
    private static double linearKernel(Instance x1, Instance x2) {
        double value = 0.0d;
        for (int j = 0; j < x1.numAttributes(); j++) {
            if (j != x1.classIndex()) {
                value += x1.value(j) * x2.value(j);
            }
        }
        return value;
    }

    /**
     * <p>
     * Applies TCA to the test and training data.
     * </p>
     *
     * @param testdata
     *            the test data
     * @param traindata
     *            the training data
     */
    @SuppressWarnings("boxing")
    private void applyTCA(Instances testdata, Instances traindata) {
        final int sizeTest = testdata.numInstances();
        final int sizeTrain = traindata.numInstances();
        final PrimitiveMatrix kernelMatrix = buildKernel(testdata, traindata);
        final PrimitiveMatrix kernelNormMatrix = buildKernelNormMatrix(sizeTest, sizeTrain); // L in
                                                                                             // the
        // paper
        final PrimitiveMatrix centerMatrix = buildCenterMatrix(sizeTest, sizeTrain); // H in the
                                                                                     // paper
        final double mu = 1.0; // default from the MATLAB implementation
        final PrimitiveMatrix muMatrix = buildMuMatrix(sizeTest, sizeTrain, mu);
        PrimitiveMatrix.FACTORY.makeEye(sizeTest + sizeTrain, sizeTest + sizeTrain);

        LOGGER.debug("creating optimization matrix (dimension " + (sizeTest + sizeTrain) + ")");
        final PrimitiveMatrix optimizationProblem = kernelMatrix.multiplyRight(kernelNormMatrix)
            .multiplyRight(kernelMatrix).add(muMatrix).invert().multiplyRight(kernelMatrix)
            .multiplyRight(centerMatrix).multiplyRight(kernelMatrix);
        LOGGER.debug("optimization matrix created, now solving eigenvalue problem");
        General eigenvalueDecomposition = new JamaEigenvalue.General();
        eigenvalueDecomposition.compute(optimizationProblem);
        LOGGER.debug("eigenvalue problem solved");

        Array1D<ComplexNumber> eigenvaluesArray = eigenvalueDecomposition.getEigenvalues();
        System.out.println(eigenvaluesArray.length);
        final Double[] eigenvalues = new Double[(int) eigenvaluesArray.length];
        final int[] index = new int[(int) eigenvaluesArray.length];
        // create kernel transformation matrix from eigenvectors
        for (int i = 0; i < eigenvaluesArray.length; i++) {
            eigenvalues[i] = eigenvaluesArray.doubleValue(i);
            index[i] = i;
        }
        SortUtils.quicksort(eigenvalues, index);

        final PrimitiveMatrix transformedKernel = kernelMatrix.multiplyRight(eigenvalueDecomposition
            .getV().selectColumns(Arrays.copyOfRange(index, 0, this.reducedDimension)));

        // update testdata and traindata
        for (int j = testdata.numAttributes() - 1; j >= 0; j--) {
            if (j != testdata.classIndex()) {
                testdata.deleteAttributeAt(j);
                traindata.deleteAttributeAt(j);
            }
        }
        for (int j = 0; j < this.reducedDimension; j++) {
            testdata.insertAttributeAt(new Attribute("kerneldim" + j), 1);
            traindata.insertAttributeAt(new Attribute("kerneldim" + j), 1);
        }
        for (int i = 0; i < sizeTrain; i++) {
            for (int j = 0; j < this.reducedDimension; j++) {
                traindata.instance(i).setValue(j + 1, transformedKernel.get(i, j));
            }
        }
        for (int i = 0; i < sizeTest; i++) {
            for (int j = 0; j < this.reducedDimension; j++) {
                testdata.instance(i).setValue(j + 1, transformedKernel.get(i + sizeTrain, j));
            }
        }
    }

    /**
     * <p>
     * Creates the kernel matrix of the test and training data
     * </p>
     *
     * @param testdata
     *            the test data
     * @param traindata
     *            the training data
     * @return kernel matrix
     */
    private static PrimitiveMatrix buildKernel(Instances testdata, Instances traindata) {
        final int kernelDim = traindata.numInstances() + testdata.numInstances();

        Builder<PrimitiveMatrix> kernelBuilder = PrimitiveMatrix.getBuilder(kernelDim, kernelDim);
        // built upper left quadrant (source, source)
        for (int i = 0; i < traindata.numInstances(); i++) {
            for (int j = 0; j < traindata.numInstances(); j++) {
                kernelBuilder.set(i, j, linearKernel(traindata.get(i), traindata.get(j)));
            }
        }

        // built upper right quadrant (source, target)
        for (int i = 0; i < traindata.numInstances(); i++) {
            for (int j = 0; j < testdata.numInstances(); j++) {
                kernelBuilder.set(i, j + traindata.numInstances(),
                                  linearKernel(traindata.get(i), testdata.get(j)));
            }
        }

        // built lower left quadrant (target, source)
        for (int i = 0; i < testdata.numInstances(); i++) {
            for (int j = 0; j < traindata.numInstances(); j++) {
                kernelBuilder.set(i + traindata.numInstances(), j,
                                  linearKernel(testdata.get(i), traindata.get(j)));
            }
        }

        // built lower right quadrant (target, target)
        for (int i = 0; i < testdata.numInstances(); i++) {
            for (int j = 0; j < testdata.numInstances(); j++) {
                kernelBuilder.set(i + traindata.numInstances(), j + traindata.numInstances(),
                                  linearKernel(testdata.get(i), testdata.get(j)));
            }
        }
        return kernelBuilder.build();
    }

    /**
     * <p>
     * Calculates the kernel norm matrix, i.e., the matrix which is used for matrix multiplication
     * to calculate the kernel norm.
     * </p>
     *
     * @param dimTest
     *            dimension of the test data
     * @param sizeTrain
     *            number of instances of the training data
     * @return kernel norm matrix
     */
    private static PrimitiveMatrix buildKernelNormMatrix(final int dimTest, final int sizeTrain) {
        final double trainSquared = 1.0 / (sizeTrain * (double) sizeTrain);
        final double testSquared = 1.0 / (dimTest * (double) dimTest);
        final double trainTest = -1.0 / (sizeTrain * (double) dimTest);
        Builder<PrimitiveMatrix> kernelNormBuilder =
            PrimitiveMatrix.getBuilder(sizeTrain + dimTest, sizeTrain + dimTest);

        // built upper left quadrant (source, source)
        for (int i = 0; i < sizeTrain; i++) {
            for (int j = 0; j < sizeTrain; j++) {
                kernelNormBuilder.set(i, j, trainSquared);
            }
        }

        // built upper right quadrant (source, target)
        for (int i = 0; i < sizeTrain; i++) {
            for (int j = 0; j < dimTest; j++) {
                kernelNormBuilder.set(i, j + sizeTrain, trainTest);
            }
        }

        // built lower left quadrant (target, source)
        for (int i = 0; i < dimTest; i++) {
            for (int j = 0; j < sizeTrain; j++) {
                kernelNormBuilder.set(i + sizeTrain, j, trainTest);
            }
        }

        // built lower right quadrant (target, target)
        for (int i = 0; i < dimTest; i++) {
            for (int j = 0; j < dimTest; j++) {
                kernelNormBuilder.set(i + sizeTrain, j + sizeTrain, testSquared);
            }
        }
        return kernelNormBuilder.build();
    }

    /**
     * <p>
     * Creates the center matrix
     * </p>
     *
     * @param sizeTest
     *            number of instances of the test data
     * @param sizeTrain
     *            number of instances of the training data
     * @return center matrix
     */
    private static PrimitiveMatrix buildCenterMatrix(final int sizeTest, final int sizeTrain) {
        Builder<PrimitiveMatrix> centerMatrix =
            PrimitiveMatrix.getBuilder(sizeTest + sizeTrain, sizeTest + sizeTrain);
        for (int i = 0; i < centerMatrix.countRows(); i++) {
            centerMatrix.set(i, i, -1.0 / (sizeTest + sizeTrain));
        }
        return centerMatrix.build();
    }

    /**
     * <p>
     * Builds the mu-Matrix for offsetting values.
     * </p>
     *
     * @param sizeTest
     *            number of instances of the test data
     * @param sizeTrain
     *            number of instances of the training data
     * @param mu
     *            mu parameter
     * @return mu-Matrix
     */
    private static PrimitiveMatrix buildMuMatrix(final int sizeTest,
                                          final int sizeTrain,
                                          final double mu)
    {
        Builder<PrimitiveMatrix> muMatrix =
            PrimitiveMatrix.getBuilder(sizeTest + sizeTrain, sizeTest + sizeTrain);
        for (int i = 0; i < muMatrix.countRows(); i++) {
            muMatrix.set(i, i, mu);
        }
        return muMatrix.build();
    }
}
