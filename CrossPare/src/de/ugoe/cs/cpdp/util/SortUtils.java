
package de.ugoe.cs.cpdp.util;

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
        quicksort(main, index, 0, index.length - 1, false);
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
        quicksort(main, index, 0, index.length - 1, descending);
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
     * @param descending
     *            defines the sorting order
     */
    private static <T extends Comparable<T>> void quicksort(T[] main,
                                                            int[] index,
                                                            int left,
                                                            int right,
                                                            boolean descending)
    {
        if (right <= left)
            return;
        int i = partition(main, index, left, right, descending);
        quicksort(main, index, left, i - 1, descending);
        quicksort(main, index, i + 1, right, descending);
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
     * @param descending
     *            defines the sorting order
     */
    private static <T extends Comparable<T>> int partition(T[] main,
                                                           int[] index,
                                                           int left,
                                                           int right,
                                                           boolean descending)
    {
        int i = left - 1;
        int j = right;
        while (true) {
            while (compare(main[++i], main[right], descending)) // find item on left to swap
            ; // a[right] acts as sentinel
            while (compare(main[right], main[--j], descending)) // find item on right to swap
                if (j == left)
                    break; // don't go out-of-bounds
            if (i >= j)
                break; // check if pointers cross
            swap(main, index, i, j); // swap two elements into place
        }
        swap(main, index, i, right); // swap with partition element
        return i;
    }

    /**
     * <p>
     * helper function for comparator evaluation
     * </p>
     *
     * @param x
     *            first element that is compared
     * @param y
     *            second element that is compared
     * @param descending
     *            defines the sorting order
     * @return true if x is larger than y and descending is true or y is larger than x and
     *         descending is false
     */
    private static <T extends Comparable<T>> boolean compare(T x, T y, boolean descending) {
        if (descending) {
            return x.compareTo(y) > 0;
        }
        else {
            return x.compareTo(y) < 0;
        }
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
    private static <T extends Comparable<T>> void swap(T[] main, int[] index, int i, int j) {
        T tmp = main[i];
        main[i] = main[j];
        main[j] = tmp;
        int b = index[i];
        index[i] = index[j];
        index[j] = b;
    }
}
