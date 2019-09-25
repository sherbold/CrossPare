//   Copyright 2015 Georg-August-Universität Göttingen, Germany
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

package de.ugoe.cs.cpdp.util;

/**
 * <p>
 * Helper class that provides methods to simplify working with arrays.
 * </p>
 * 
 * @author Steffen Herbold
 * @version 1.0
 */
final public class ArrayUtils {

	/**
	 * <p>
	 * Private constructor to prevent initializing of the class.
	 * </p>
	 */
	private ArrayUtils() {

	}

	/**
	 * <p>
	 * Finds the highest element in an array. If multiple elements have the
	 * maximum value, the index of the first one is returned; null-values are
	 * ignored. In case the parameter array is null, has length 0 or contains
	 * only null-values, -1 is returned.
	 * </p>
	 * 
	 * @param <T>
	 * @param array
	 *            the array
	 * @return index of the element with the highest value, -1 in case of an
	 *         invalid parameter
	 */
	@SuppressWarnings("unchecked")
	public static <T> int findMax(Comparable<T>[] array) {
		int maxIndex = -1;
		T maxElement = null;
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] != null) {
					if (maxElement == null
							|| array[i].compareTo(maxElement) > 0) {
						maxElement = (T) array[i];
						maxIndex = i;
					}
				}
			}
		}
		return maxIndex;
	}
	
	/**
	 * <p>
	 * Finds the highest element in an array. If multiple elements have the
	 * maximum value, the index of the first one is returned; null-values are
	 * ignored. In case the parameter array is null, has length 0 or contains
	 * only null-values, -1 is returned.
	 * </p>
	 * 
	 * @param array
	 *            the array
	 * @return index of the element with the highest value, -1 in case of an
	 *         invalid parameter
	 */
	public static int findMax(double[] array) {
		int maxIndex = -1;
		double maxElement = -Double.MAX_VALUE;
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] > maxElement) {
					maxElement = array[i];
					maxIndex = i;
				}
			}
		}
		return maxIndex;
	}
}