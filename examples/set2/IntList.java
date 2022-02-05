public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public IntList(int[] items) {
        if (items.length == 0) {
            throw new RuntimeException("There should be at least one item!");
        }

        IntList intList = null;
        for (int i = items.length - 1; i >= 0; i--) {
            intList = new IntList(items[i], intList);
        }

        first = intList.first;
        rest = intList.rest;
    }

    /**
     * Returns the size of the list using recursion.
     *
     * @return The size of the list.
     */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return rest.size() + 1;
    }

    /**
     * Returns the size of the list using iteration.
     *
     * @return the size of the list.
     */
    public int iterativeSize() {
        int total = 0;
        IntList p = this;

        while (p != null) {
            total += 1;
            p = p.rest;
        }
        return total;
    }

    /**
     * Returns the nth item's first of the list using recursion.
     *
     * @param n The index of the target item.
     * @return The nth item's first.
     */
    public int get(int n) {
        if (n == 0) {
            return first;
        }

        return rest.get(n - 1);
    }

    /**
     * Returns the nth item's first of the list using iteration.
     * 
     * @param n The index of the target item.
     * @return The nth item's first.
     */
    public int iterativeGet(int n) {
        IntList p = this;
        while (n > 0) {
            p = p.rest;
            n--;
        }
        return p.first;
    }

    /**
     * Adds an item to the first location.
     *
     * @param n The item to be added.
     */
    public void addFirst(int n) {
        this.rest = new IntList(first, rest);
        this.first = n;
    }

    /**
     * Squares all the items, then adds an item to the first location.
     *
     * @param n The item to be added.
     */
    public void squareThenAdd(int n) {
        IntList intList = new IntList(0, this);

        while (intList.rest != null) {
            intList = intList.rest;
            intList.rest = new IntList(intList.first * intList.first, intList.rest);
            intList = intList.rest;
        }

        intList.rest = new IntList(n, null);
    }

    /**
     * Returns the first item;
     *
     * @return The first item.
     */
    public int getFirst() {
        return first;
    }

    /**
     * Returns a new list with all items incremented by x.
     *
     * @param L The input list.
     * @param x The incremented number.
     * @return A new list.
     */
    public static IntList incrementList(IntList L, int x) {
        if (L == null) {
            return null;
        }

        return new IntList(L.first + x, incrementList(L.rest, x));
    }

    /**
     * Returns a list with all items incremented by x. Change items in-place.
     *
     * @param L The input list.
     * @param x The incremented number.
     * @return A list.
     */
    public static IntList incrementListDestructive(IntList L, int x) {
        if (L == null) {
            return null;
        }
        L.first += x;
        incrementListDestructive(L.rest, x);
        return L;
    }

    /**
     * Displays a list.
     *
     * @param L The list to be displayed.
     */
    public static void display(IntList L) {
        if (L == null) {
            System.out.print(".");
            System.out.println();
            return;
        }
        System.out.print(L.first + ".");
        display(L.rest);
    }

    /**
     * Adds the adjacent items if they are equal.
     *
     */
    public void addAdjacent() {
        if (rest == null) {
            return;
        }

        while (rest.first == first) {
            first += rest.first;
            rest = rest.rest;
        }

        rest.addAdjacent();
    }

    public static void main(String[] args) {
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);

        System.out.println(L.size());
        System.out.println(L.iterativeSize());

        System.out.println(L.get(2));
        System.out.println(L.iterativeGet(2));

        IntList p = incrementList(L, 1);
        display(L);
        display(p);

        L.addFirst(0);
        System.out.println(L.getFirst());

        p = incrementListDestructive(L, 1);
        display(L);
        display(p);

        L = new IntList(new int[]{1, 1, 2, 3});
        display(L);
        L.addAdjacent();
        display(L);

        L = new IntList(new int[]{1, 2});
        display(L);
        L.squareThenAdd(5);
        display(L);
        L.squareThenAdd(7);
        display(L);
    }
}
