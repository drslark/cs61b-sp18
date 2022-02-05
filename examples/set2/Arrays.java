public class Arrays {

    /**
     * Inserts an item at the given position of an array.
     * @param arr The given array.
     * @param item The item to be inserted.
     * @param position The position to insert at.
     * @return The result array.
     */
    public static int[] insert(int[] arr, int item, int position) {
        int[] res = new int[arr.length + 1];
        System.arraycopy(arr, 0, res, 0, arr.length);

        int i;
        for (i = arr.length; i > position; i--) {
            res[i] = res[i - 1];
        }
        res[i] = item;
        return res;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Reserves an array in-place.
     *
     * @param arr The array to be reserved.
     */
    public static void reserve(int[] arr) {
        int i = 0;
        int j = arr.length - 1;
        while (i <= j) {
            swap(arr, i, j);
            i += 1;
            j -= 1;
        }
    }

    private static int sum(int[] arr) {
        int s = 0;
        for (int num: arr) {
            s += num;
        }
        return s;
    }

    /**
     * replaces the number at index i with arr[i] copies of itself.
     *
     * @param arr The array to be replicated.
     * @return The replicated array.
     */
    public static int[] replicate(int[] arr) {
        int[] res = new int[sum(arr)];
        int index = 0;
        for (int num: arr) {
            for (int i = 0; i < num; i++) {
                res[index] = num;
                index += 1;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {5, 9, 14, 15};
        int[] res1 = insert(arr1, 6, 2);
        System.out.println(java.util.Arrays.toString(res1));
        int[] arr2 = {1, 2, 3};
        reserve(arr2);
        System.out.println(java.util.Arrays.toString(arr2));
        int[] arr3 = {3, 2, 1};
        int[] res3 = replicate(arr3);
        System.out.println(java.util.Arrays.toString(res3));
    }
}
