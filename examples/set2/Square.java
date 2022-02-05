public class Square {

    /**
     * Create a new list whose integers are square from the input list (does this non-destructively).
     *
     * @param L A list of integers to be squared.
     * @return A new list squared from integers from the input list.
     */
    public static IntList square(IntList L) {
        IntList sentinelRes = new IntList(0, null);
        IntList ptrRes = sentinelRes;

        while (L != null) {
            ptrRes.rest = new IntList(L.first * L.first, null);
            L = L.rest;
            ptrRes = ptrRes.rest;
        }

        return sentinelRes.rest;
    }

    /**
     * Square the input list.
     *
     * @param L A list of integers to be squared.
     * @return The squared input list.
     */
    public static IntList squareDestructive(IntList L) {
        if (L == null) {
            return null;
        }
        L.first *= L.first;
        squareDestructive(L.rest);
        return L;
    }

    public static void main(String[] args) {
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);

        IntList LSquared = square(L);
        IntList.display(L);
        IntList.display(LSquared);

        IntList LSquaredDest = squareDestructive(L);
        IntList.display(L);
        IntList.display(LSquaredDest);
    }
}
