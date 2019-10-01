
package de.ugoe.cs.cpdp.util;

import java.util.Comparator;

/**
 * <p>
 * Utility functions for sorting.
 * </p>
 * 
 * @author Steffen Herbold
 */
public class SortUtils {

    /**
     * <p>
     * Implements a quick sort that sorts an index set together with the array.
     * </p>
     * 
     * @param <T>
     *            type for sorting
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     */
    public static <T extends Comparable<T>> void quicksort(T[] main, int[] index) {
    	quicksort(main, index, false);
    }

    /**
     * <p>
     * Implements a quick sort that sorts an index set together with the array.
     * </p>
     * 
     * @param <T>
     *            type for sorting
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     * @param descending
     *            defines the sorting order
     */
    public static <T extends Comparable<T>> void quicksort(T[] main,
                                                           int[] index,
                                                           boolean descending)
    {
    	Comparator<T> comparator = new Comparator<T>() {
			@Override
			public int compare(T first, T second) {
				return first.compareTo(second);
			}
    		
		};
		if(!descending) {
			comparator = comparator.reversed();
		}
        quicksort(main, index, comparator);
    }
    
    /**
     * <p>
     * Implements a quick sort that sorts an index set together with the array.
     * </p>
     * 
     * @param <T>
     *            type for sorting
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     * @param comparator
     *            defines the sorting order
     */
    public static <T> void quicksort(T[] main,
                                                           int[] index,
                                                           Comparator<T> comparator)
    {
        quicksort(main, index, 0, index.length - 1, comparator);
    }

    /**
     * <p>
     * internal quicksort implementation
     * </p>
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     * @param left
     *            defines the current partition
     * @param right
     *            defines the current partition
     * @param comparator
     *            defines the sorting order
     */
    private static <T> void quicksort(T[] main,
                                                            int[] index,
                                                            int left,
                                                            int right,
                                                            Comparator<T> comparator)
    {
        if (right <= left)
            return;
        int i = partition(main, index, left, right, comparator);
        quicksort(main, index, left, i - 1, comparator);
        quicksort(main, index, i + 1, right, comparator);
    }

    /**
     * <p>
     * internal partitioning of the quicksort implementation
     * </p>
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     * @param left
     *            defines the current partition
     * @param right
     *            defines the current partition
     * @param comparator
     *            defines the sorting order
     */
    private static <T> int partition(T[] main,
                                                           int[] index,
                                                           int left,
                                                           int right,
                                                           Comparator<T> comparator)
    {
        int i = left - 1;
        int j = right;
        while (true) {
            while (comparator.compare(main[++i], main[right])>0) {
                // find item on left to swap
                // a[right] acts as sentinel
            }
            while (comparator.compare(main[right], main[--j])>0) {
                // find item on right to swap
                if (j == left) {
                    break; // don't go out-of-bounds
                }
            }
            if (i >= j) {
                break; // check if pointers cross
            }
            swap(main, index, i, j); // swap two elements into place
        }
        swap(main, index, i, right); // swap with partition element
        return i;
    }

    /**
     * <p>
     * swaps to elements
     * </p>
     *
     * @param main
     *            the array that is sorted
     * @param index
     *            the index set for the array
     * @param i
     *            index of the first element
     * @param j
     *            index of the second element
     */
    private static <T> void swap(T[] main, int[] index, int i, int j) {
        T tmp = main[i];
        main[i] = main[j];
        main[j] = tmp;
        int b = index[i];
        index[i] = index[j];
        index[j] = b;
    }
}
