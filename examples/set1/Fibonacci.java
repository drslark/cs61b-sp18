public class Fibonacci {

    /**
     * The first way to get an element in Fibonacci.
     *
     * @param n The index of the target element.
     * @return The nth element.
     */
    public static int fib1(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib1(n - 2) + fib1(n - 1);
    }

    /**
     * The second way to get an element in Fibonacci.
     *
     * @param n  The index of the target element.
     * @param k  The index of the current element.
     * @param f0 The first element.
     * @param f1 The second element.
     * @return The nth element.
     */
    public static int fib2(int n, int k, int f0, int f1) {
        if (k == n) {
            return f0;
        }
        return fib2(n, k + 1, f1, f0 + f1);
    }

    public static void main(String[] args) {
        System.out.println(fib1(6));
        System.out.println(fib2(6, 0, 0, 1));
    }
}
