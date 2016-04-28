package de.ugoe.cs.cpdp.util;

public class SortUtils {

    public static <T extends Comparable<T>> void quicksort(T[] main, int[] index) {
        quicksort(main, index, 0, index.length - 1, false);
    }
    
    public static <T extends Comparable<T>> void quicksort(T[] main, int[] index, boolean descending) {
        quicksort(main, index, 0, index.length - 1, descending);
    }

    // quicksort a[left] to a[right]
    private static <T extends Comparable<T>> void quicksort(T[] a, int[] index, int left, int right, boolean descending) {
        if (right <= left)
            return;
        int i = partition(a, index, left, right, descending);
        quicksort(a, index, left, i - 1, descending);
        quicksort(a, index, i + 1, right, descending);
    }

    // partition a[left] to a[right], assumes left < right
    private static <T extends Comparable<T>> int partition(T[] a, int[] index, int left, int right, boolean descending) {
        int i = left - 1;
        int j = right;
        while (true) {
            while (compare(a[++i], a[right], descending)) // find item on left to swap
            ; // a[right] acts as sentinel
            while (compare(a[right], a[--j], descending)) // find item on right to swap
                if (j == left)
                    break; // don't go out-of-bounds
            if (i >= j)
                break; // check if pointers cross
            exch(a, index, i, j); // swap two elements into place
        }
        exch(a, index, i, right); // swap with partition element
        return i;
    }

    // is x < y ?
    private static <T extends Comparable<T>> boolean compare(T x, T y, boolean descending) {
        if( descending ) {
            return x.compareTo(y)>0;
        } else {
            return x.compareTo(y)<0;
        }
    }

    // exchange a[i] and a[j]
    private static <T extends Comparable<T>> void exch(T[] a, int[] index, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
        int b = index[i];
        index[i] = index[j];
        index[j] = b;
    }
}
