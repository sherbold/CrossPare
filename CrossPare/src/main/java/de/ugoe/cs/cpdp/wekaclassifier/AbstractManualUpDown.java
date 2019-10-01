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

package de.ugoe.cs.cpdp.wekaclassifier;

import java.util.Comparator;
import java.util.stream.IntStream;

import de.ugoe.cs.cpdp.util.SortUtils;
import weka.classifiers.AbstractClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Implements the logic for ManualUp and ManualDown from the paper "How Far We
 * Have Progressed in the Journey? An Examination of Cross-Project Defect
 * Prediction" by Zhou et al.
 * 
 * @author sherbold
 */
public abstract class AbstractManualUpDown extends AbstractClassifier {

	/**
	 * default serialization id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ranks of each instance ordered by size
	 */
	private int[] ranks = null;

	/**
	 * Attribute with the size_index
	 */
	private Attribute index = null;

	/**
	 * Comparator for sorting instances by their size
	 * 
	 * @author sherbold
	 */
	private class SizeComparator implements Comparator<Instance> {

		/**
		 * Index of the size attribute
		 */
		public int sizeAttributeIndex = 0;

		/**
		 * Creates a new SizeComparator.
		 * 
		 * @param sizeAttributeIndex index of the size attribute
		 */
		public SizeComparator(int sizeAttributeIndex) {
			this.sizeAttributeIndex = sizeAttributeIndex;
		}

		/**
		 * <ul>
		 * <li>-1 if size of first is less than size of second or if sizes are equal but
		 * class of first is less than class of second</li>
		 * <li>1 if size of first is greater than size of second of if sizes are equal
		 * but class of first is greater than class of second</li>
		 * <li>0 if size and class are equal</li>
		 * </ul>
		 * 
		 * @param first  first instance
		 * @param second second instance
		 * @return result of comparison
		 */
		@Override
		public int compare(Instance first, Instance second) {
			if (first.value(sizeAttributeIndex) > second.value(sizeAttributeIndex)) {
				return 1;
			} else if (first.value(sizeAttributeIndex) < second.value(sizeAttributeIndex)) {
				return -1;
			} else {
				// size is equal, compare on class value as tie breaker
				if (first.classValue() > second.classValue()) {
					return 1;
				} else if (first.classValue() < second.classValue()) {
					return -1;
				}
			}
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see weka.classifiers.Classifier#buildClassifier(weka.core.Instances)
	 */
	@SuppressWarnings("boxing")
	@Override
	public void buildClassifier(Instances traindata) throws Exception {
		if (traindata.attribute("size") == null) {
			throw new RuntimeException("only works for data that has an attribute called 'size'");
		}
		if (traindata.attribute("instance_index") == null) {
			throw new RuntimeException("only works for data that has an attribute called 'instance_index'");
		}
		this.index = traindata.attribute("instance_index");
		for (int i = 0; i < traindata.size(); i++) {
			if (i != (int) traindata.get(i).value(this.index)) {
				throw new RuntimeException("instance index is not a real index: instance " + i + " has a value of "
						+ traindata.get(i).value(this.index) + " for the instance_index");
			}
		}
		ranks = IntStream.range(0, traindata.size()).toArray();
		int sizeIndex = traindata.attribute("size").index();
		SizeComparator comparator = new SizeComparator(sizeIndex);
		SortUtils.quicksort(traindata.toArray(new Instance[0]), ranks, comparator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see weka.classifiers.Classifier#distributionForInstance(weka.core.Instances)
	 */
	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		double score = ranks[(int) instance.value(index)] / (ranks.length - 1.0d);
		return getScoreArray(score);
	}

	/**
	 * Defines the scoring array returned by
	 * {@link #distributionForInstance(Instance)}.
	 * 
	 * @param score score according to the rank of the instance
	 * @return scoring array
	 */
	public abstract double[] getScoreArray(double score);
}
