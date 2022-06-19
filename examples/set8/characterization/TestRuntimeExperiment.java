package characterization;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestRuntimeExperiment {

    private static int[] makeArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private static boolean dup1(int[] sortedArray) {
        for (int i = 0; i < sortedArray.length; i++) {
            for (int j = i + 1; j < sortedArray.length; j++) {
                if (sortedArray[i] == sortedArray[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dup2(int[] sortedArray) {
        for (int i = 0; i < sortedArray.length - 1; i++) {
            if (sortedArray[i] == sortedArray[i + 1]) {
                return true;
            }
        }

        return false;
    }

    private static boolean dup3(int[] array) {
        int N = array.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dup4(int[] array) {
        int N = array.length;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void printParty(int N) {
        for (int i = 1; i <= N; i *= 2) {
            for (int j = 0; j < i; j++) {
                System.out.println("hello");
                int ZUG = 1 + 1;
            }
        }
    }

    private static int f3(int n) {
        if (n <= 1) {
            return 1;
        }
        return f3(n - 1) + f3(n - 1);
    }

    private static <E extends Comparable<E>> void validateNullPointer(E[] array) {
        if (array == null) {
            throw new NullPointerException("Array should not be null!");
        }
    }

    private static <E extends Comparable<E>> int binarySearchRecursive(E[] sorts, E x, int low, int high) {
        if (low == high) {
            return -1;
        }

        int middle = low + (high - low) / 2;
        int cmp = sorts[middle].compareTo(x);

        if (cmp == 0) {
            return middle;
        } else if (cmp < 0) {
            return binarySearchRecursive(sorts, x, middle + 1, high);
        } else {
            return binarySearchRecursive(sorts, x, low, middle);
        }

    }

    public static <E extends Comparable<E>> int binarySearchRecursive(E[] sorts, E x) {
        validateNullPointer(sorts);
        return binarySearchRecursive(sorts, x, 0, sorts.length);
    }

    public static <E extends Comparable<E>> int binarySearchIterative(E[] sorts, E x) {
        validateNullPointer(sorts);

        int low = 0;
        int high = sorts.length;

        while (low < high) {

            int middle = low + (high - low) / 2;
            int cmp = sorts[middle].compareTo(x);

            if (cmp == 0) {
                return middle;
            } else if (cmp < 0) {
                low = middle + 1;
            } else {
                high = middle;
            }

        }

        return -1;
    }

    private static <E extends Comparable<E>> void merge(E[] toSort, int low, int middle, int high, E[] temp) {
        if (high - low < 2 || middle == high) {
            return;
        }

        int i = low;
        int l = low;
        int r = middle;

        while (l < middle && r < high) {
            if (toSort[l].compareTo(toSort[r]) <= 0) {
                temp[i++] = toSort[l++];
            } else {
                temp[i++] = toSort[r++];
            }
        }

        while (l < middle) {
            temp[i++] = toSort[l++];
        }

        while (r < high) {
            temp[i++] = toSort[r++];
        }

        System.arraycopy(temp, low, toSort, low, high - low);
    }

    private static <E extends Comparable<E>> void mergeSortRecursive(E[] toSort, int low, int high, E[] temp) {
        if (high - low <= 1) {
            return;
        }

        int middle = low + (high - low) / 2;

        mergeSortRecursive(toSort, low, middle, temp);
        mergeSortRecursive(toSort, middle, high, temp);

        merge(toSort, low, middle, high, temp);
    }

    private static <E extends Comparable<E>> void mergeSortIterative(E[] toSort, int low, int high, E[] temp) {
        for (int i = 1; i < high; i *= 2) {
            for (int l = low; l < high; l += 2 * i) {
                int h = Math.min(l + 2 * i, high);
                int m = Math.min(l + i, high);
                merge(toSort, l, m, h, temp);
            }
        }
    }

    private static int getLow(int[] section) {
        return section[0];
    }

    private static int getMiddle(int[] section) {
        return (getLow(section) + getHigh(section)) / 2;
    }

    private static int getHigh(int[] section) {
        return section[1];
    }

    private static boolean needMerge(int[] section) {
        return getHigh(section) - getLow(section) >= 2;
    }

    private static int[] leftSection(int[] section) {
        return new int[]{getLow(section), getMiddle(section)};
    }

    private static int[] rightSection(int[] section) {
        return new int[]{getMiddle(section), getHigh(section)};
    }

    private static boolean equalTo(int[] section, int[] otherSection) {
        return section[0] == otherSection[0] && section[1] == otherSection[1];
    }

    private static boolean isLeftSection(int[] section, int[] testSection) {
        int[] leftSection = leftSection(testSection);
        return equalTo(section, leftSection);
    }

    private static boolean isRightSection(int[] section, int[] testSection) {
        int[] rightSection = rightSection(testSection);
        return equalTo(section, rightSection);
    }

    private static boolean isChildSection(int[] section, int[] testSection) {
        return isLeftSection(section, testSection) || isRightSection(section, testSection);
    }

    private static <E extends Comparable<E>> void mergeSortTraverse(E[] toSort, int low, int high, E[] temp) {
        int[] nullSection = {0, 0};
        int[] currSection = {low, high};
        int[] prevSection = nullSection;
        Deque<int[]> track = new ArrayDeque<>();

        while (needMerge(currSection) || !track.isEmpty()) {
            while (needMerge(currSection)) {
                track.addLast(currSection);
                currSection = leftSection(currSection);
            }

            currSection = track.removeLast();

            if (isRightSection(prevSection, currSection) || !needMerge(rightSection(currSection))) {
                merge(toSort, getLow(currSection), getMiddle(currSection), getHigh(currSection), temp);
                prevSection = currSection;
                currSection = nullSection;
            } else {
                track.addLast(currSection);
                currSection = rightSection(currSection);
            }
        }
    }

    private static <E extends Comparable<E>> void mergeSortTraverseGoto(E[] toSort, int low, int high, E[] temp) {
        int[] nullSection = {0, 0};
        int[] currSection = {low, high};
        int[] prevSection = nullSection;
        Deque<int[]> track = new ArrayDeque<>();
        track.addLast(currSection);

        while (!track.isEmpty()) {

            currSection = track.getLast();
            String stage = "null";

            if ((isChildSection(currSection, prevSection) || equalTo(prevSection, nullSection))) {
                stage = "left";
            } else if (equalTo(leftSection(currSection), prevSection)) {
                stage = "right";
            } else if (equalTo(rightSection(currSection), prevSection)) {
                stage = "root";
            }

            switch (stage) {
                case "left":
                    if (needMerge(leftSection(currSection))) {
                        track.addLast(leftSection(currSection));
                        prevSection = currSection;
                        break;
                    }

                case "right":
                    if (needMerge(rightSection(currSection))) {
                        track.addLast(rightSection(currSection));
                        prevSection = currSection;
                        break;
                    }

                default:
                    merge(toSort, getLow(currSection), getMiddle(currSection), getHigh(currSection), temp);
                    track.removeLast();
                    prevSection = currSection;
            }
        }
    }

    private static <E extends Comparable<E>> void mergeSortTraverseSkip(E[] toSort, int low, int high, E[] temp) {
        int[] nullSection = {0, 0};
        int[] currSection = {low, high};
        int[] prevSection = nullSection;
        Deque<int[]> track = new ArrayDeque<>();
        if (needMerge(currSection)) {
            track.addLast(currSection);
        }

        while (!track.isEmpty()) {

            currSection = track.getLast();

            if ((isChildSection(currSection, prevSection) || equalTo(prevSection, nullSection))) {
                if (needMerge(leftSection(currSection))) {
                    track.addLast(leftSection(currSection));
                    prevSection = currSection;
                } else {
                    prevSection = leftSection(currSection);
                }
            } else if (equalTo(leftSection(currSection), prevSection)) {
                if (needMerge(rightSection(currSection))) {
                    track.addLast(rightSection(currSection));
                    prevSection = currSection;
                } else {
                    prevSection = rightSection(currSection);
                }
            } else if (equalTo(rightSection(currSection), prevSection)) {
                merge(toSort, getLow(currSection), getMiddle(currSection), getHigh(currSection), temp);
                track.removeLast();
                prevSection = currSection;
            }
        }
    }

    public static <E extends Comparable<E>> void mergeSortRecursive(E[] toSort) {
        validateNullPointer(toSort);
        mergeSortRecursive(toSort, 0, toSort.length, (E[]) new Comparable[toSort.length]);
    }

    public static <E extends Comparable<E>> void mergeSortIterative(E[] toSort) {
        validateNullPointer(toSort);
        mergeSortIterative(toSort, 0, toSort.length, (E[]) new Comparable[toSort.length]);
    }

    public static <E extends Comparable<E>> void mergeSortTraverse(E[] toSort) {
        validateNullPointer(toSort);
        mergeSortTraverse(toSort, 0, toSort.length, (E[]) new Comparable[toSort.length]);
    }

    public static <E extends Comparable<E>> void mergeSortTraverseGoto(E[] toSort) {
        validateNullPointer(toSort);
        mergeSortTraverseGoto(toSort, 0, toSort.length, (E[]) new Comparable[toSort.length]);
    }

    public static <E extends Comparable<E>> void mergeSortTraverseSkip(E[] toSort) {
        validateNullPointer(toSort);
        mergeSortTraverseSkip(toSort, 0, toSort.length, (E[]) new Comparable[toSort.length]);
    }

    @Test
    public void testDup() {
        long timeStart, timeEnd;
        int[] array;

        array = makeArray(10000);
        timeStart = System.nanoTime();
        assertFalse(dup1(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup1 costs in a 10000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);

        timeStart = System.nanoTime();
        assertFalse(dup2(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup2 costs in a 10000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);

        array = makeArray(100000);
        timeStart = System.nanoTime();
        assertFalse(dup1(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup1 costs in a 100000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);

        timeStart = System.nanoTime();
        assertFalse(dup2(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup2 costs in a 100000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);


        array = makeArray(200000);
        timeStart = System.nanoTime();
        assertFalse(dup1(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup1 costs in a 200000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);

        timeStart = System.nanoTime();
        assertFalse(dup2(array));
        timeEnd = System.nanoTime();
        System.out.printf("dup2 costs in a 200000-length array: %2.5f%n", (timeEnd - timeStart) / 1e9);
    }

    @Test
    public void testPrintParty() {
        printParty(8);
    }

    @Test
    public void testF3() {
        f3(4);
    }

    @Test
    public void testBinarySearchRecursive() {
        for (BinarySearch binarySearch : new BinarySearch[]{
            TestRuntimeExperiment::binarySearchRecursive, TestRuntimeExperiment::binarySearchIterative
        }) {
            Integer[] sortedArray = {6, 13, 14, 25, 33, 43, 51, 53, 64, 72, 84, 93, 95, 96, 97};
            assertEquals(4, binarySearch.apply(sortedArray, 33));

            sortedArray = new Integer[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 99};
            assertEquals(-1, binarySearch.apply(sortedArray, 49));
            assertEquals(-1, binarySearch.apply(sortedArray, 5));
            assertEquals(-1, binarySearch.apply(sortedArray, 100));
            assertEquals(0, binarySearch.apply(sortedArray, 10));
            assertEquals(sortedArray.length - 1, binarySearch.apply(sortedArray, 99));
        }

    }

    @Test
    public void testMergeSort() {
        for (MergeSort mergeSort : new MergeSort[]{
            TestRuntimeExperiment::mergeSortRecursive,
            TestRuntimeExperiment::mergeSortIterative,
            TestRuntimeExperiment::mergeSortTraverse,
            TestRuntimeExperiment::mergeSortTraverseGoto,
            TestRuntimeExperiment::mergeSortTraverseSkip
        }) {
            Integer[] toSort = {9, 8, 7, 6, 5, 4, 3, 2, 1};
            Integer[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);

            toSort = new Integer[]{8, 4, 5, 7, 1, 3, 6, 2};
            sorted = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);

            toSort = new Integer[]{12, 11, 13, 5, 6, 7};
            sorted = new Integer[]{5, 6, 7, 11, 12, 13};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);

            toSort = new Integer[]{3, 4, 2, 1, 7, 5, 8, 9, 0, 6};
            sorted = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);

            toSort = new Integer[]{0};
            sorted = new Integer[]{0};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);

            toSort = new Integer[]{};
            sorted = new Integer[]{};
            mergeSort.apply(toSort);
            assertArrayEquals(sorted, toSort);
        }

    }

}

@FunctionalInterface
interface BinarySearch {
    <E extends Comparable<E>> int apply(E[] sorts, E x);
}

@FunctionalInterface
interface MergeSort {
    <E extends Comparable<E>> void apply(E[] sorts);
}
