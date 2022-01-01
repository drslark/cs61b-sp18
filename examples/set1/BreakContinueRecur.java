public class BreakContinueRecur {
    /**
     * Recursively Pos Sum.
     * 
     * @param a The input array.
     * @param i The index of the current element.
     * @param n The number of elements afterwards to be summed.
     */
    public static void windowPosSumRecur(int[] a, int i, int n) {
        if (i >= a.length) {
            return;
        }

        if (a[i] >= 0) {
            afterwardsSumRecur(a, i, n);
        }
        windowPosSumRecur(a, i + 1, n);
    }

    /**
     * Recursively Pos Sum.
     * 
     * @param a The input array.
     * @param i The index of the current element.
     * @param n The number of elements afterwards to be summed.
     */
    public static void afterwardsSumRecur(int[] a, int i, int n) {
        if (n <= 0) {
            return;
        }

        if (i + n < a.length) {
            a[i] += a[i + n];
        }

        afterwardsSumRecur(a, i, n - 1);
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSumRecur(a, 0, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}
