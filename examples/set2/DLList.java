public class DLList<GloopGlop> implements LastAdder<GloopGlop> {

    private static class StuffNode<BleepBlorp> {
        public BleepBlorp item;
        public StuffNode<BleepBlorp> prev;
        public StuffNode<BleepBlorp> next;

        public StuffNode(BleepBlorp i, StuffNode<BleepBlorp> p, StuffNode<BleepBlorp> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    /**
     * The first item (if it exists) is just behind sentinel
     */
    private final StuffNode<GloopGlop> sentinel;

    private int size;

    /**
     * Creates an empty list.
     */
    public DLList() {
        sentinel = new StuffNode<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    public DLList(GloopGlop x) {
        sentinel = new StuffNode<>(null, null, null);
        StuffNode<GloopGlop> node = new StuffNode<>(x, sentinel, sentinel);
        sentinel.prev = node;
        sentinel.next = node;

        size = 1;
    }

    /**
     * Adds x to the front of the list.
     *
     * @param x The added item.
     */
    public void addFirst(GloopGlop x) {
        StuffNode<GloopGlop> node = new StuffNode<>(x, sentinel, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;

        size += 1;
    }

    /**
     * Returns the first item in the list;
     *
     * @return The first item in the list.
     */
    public GloopGlop getFirst() {
        if (size <= 0) {
            throw new RuntimeException("There are no items.");
        }
        return sentinel.next.item;
    }

    /**
     * Add x to the end of the list.
     *
     * @param x The added item.
     */
    public void addLast(GloopGlop x) {
        StuffNode<GloopGlop> node = new StuffNode<>(x, sentinel.prev, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;
    }

    /**
     * Get the last item of the list.
     *
     * @return the last item of the list.
     */
    public GloopGlop getLast() {
        if (size <= 0) {
            throw new RuntimeException("There are no items.");
        }
        return sentinel.prev.item;
    }

    /**
     * Remove the last item of the list.
     */
    public void removeLast() {
        if (size <= 0) {
            throw new RuntimeException("There are no items.");
        }
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
    }

    /**
     * Return the size of the list.
     *
     * @return the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Print the list.
     *
     */
    public void display() {
        StuffNode<GloopGlop> p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + ".");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        /* Creates a list of one integer, namely 15. */
        DLList<Integer> L = new DLList<>();
        L.addFirst(10);
        L.addFirst(5);
        System.out.println(L.size());
        L.addLast(20);
        System.out.println(L.getFirst());
        System.out.println(L.size());
        L.display();
        L.addLast(50);
        L.display();
        L.removeLast();
        L.display();
        L.removeLast();
        L.display();
        L.removeLast();
        L.display();
        L.removeLast();
        L.display();
        L.addLast(20);
        L.display();
    }

}
