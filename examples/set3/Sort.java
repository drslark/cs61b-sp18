public class Sort {

    /**
     * Sorts items destructively.
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        // selection sort the rest (using recursion?)
        sort(array, 0);
    }

    /**
     * Sorts array starting from start destructively.
     */
    private static <T extends Comparable<T>> void sort(T[] array, int start) {
        if (start == array.length) {
            return;
        }

        // find the smallest item
        int smallestIndex = findSmallest(array, start);
        // move it to the front
        swap(array, start, smallestIndex);
        sort(array, start + 1);
    }

    /**
     * Returns the index of the smallest item in array starting from start.
     */
    public static <T extends Comparable<T>> int findSmallest(T[] array, int start) {
        if (array.length == 0) {
            return -1;
        }

        int smallestIndex = start;
        for (int i = start; i < array.length; i++) {
            if (array[i].compareTo(array[smallestIndex]) < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    /** Sorts items destructively. */
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
