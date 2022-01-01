public class ArrayMaxRecur {
    /**
     * Returns the maximum value's index from arr.
     */
    public static int argmax(int[] arr, int i, int j) {
        if (i > j) {
            return -1;
        }

        if (i == j) {
            return i;
        }

        int maxIdx = argmax(arr, i + 1, j);
        if (arr[i] > arr[maxIdx]) {
            return i;
        } else {
            return maxIdx;
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(numbers[argmax(numbers, 0, numbers.length - 1)]);
    }
}
