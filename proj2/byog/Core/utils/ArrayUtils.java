package byog.Core.utils;

import java.util.Arrays;

/**
 * Utils to help process arrays.
 */
public class ArrayUtils {

    /**
     * Sum two arrays.
     *
     * @param thisArray One array.
     * @param thatArray The other array.
     * @return the summed array.
     */
    public static int[] sum(int[] thisArray, int[] thatArray) {
        if (thisArray.length != thatArray.length) {
            throw new RuntimeException("The arrays must have same length!");
        }

        int[] sumArray = new int[thatArray.length];
        for (int i = 0; i < thisArray.length; i++) {
            sumArray[i] = thisArray[i] + thatArray[i];
        }

        return sumArray;
    }

    /**
     * Generates an array full of a given value.
     *
     * @param dimension The array length.
     * @param value The given value.
     * @return An array full of a given value.
     */
    public static int[] full(int dimension, int value) {
        int[] fullArray = new int[dimension];
        Arrays.fill(fullArray, value);
        return fullArray;
    }

    /**
     * Counts the different values between two arrays.
     *
     * @param thisArray One array.
     * @param thatArray The other array.
     * @return The number of different values between two arrays.
     */
    public static int differentCount(int[] thisArray, int[] thatArray) {
        int count = 0;
        for (int i = 0; i < thisArray.length; i++) {
            if (thisArray[i] != thatArray[i]) {
                count += 1;
            }
        }

        return count;
    }

}
