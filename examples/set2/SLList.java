/**
 * An SLList is a list of integers, which hides the terrible truth
 * of the nakedness within.
 */
public class SLList<GloopGlop> implements List<GloopGlop> {

    private static class StuffNode<BleepBlorp> {
        public BleepBlorp item;
        public StuffNode<BleepBlorp> next;

        public StuffNode(BleepBlorp i, StuffNode<BleepBlorp> n) {
            item = i;
            next = n;
        }
    }

    /**
     * The first item (if it exists) is just behind sentinel
     */
    private final StuffNode<GloopGlop> sentinel;

    private StuffNode<GloopGlop> last;

    private int size;

    /**
     * Creates an empty list.
     */
    public SLList() {
        sentinel = new StuffNode<>(null, null);
        last = sentinel;
        size = 0;
    }

    public SLList(GloopGlop x) {
        sentinel = new StuffNode<>(null, null);
        sentinel.next = new StuffNode<>(x, null);
        last = sentinel.next;
        size = 1;
    }

    public SLList(GloopGlop[] x) {
        this();
        for (GloopGlop item : x) {
            addLast(item);
        }
    }

    /**
     * Adds x to the front of the list.
     *
     * @param x The added item.
     */
    public void addFirst(GloopGlop x) {
        sentinel.next = new StuffNode<>(x, sentinel.next);
        if (last == sentinel) {
            last = sentinel.next;
        }
        size += 1;
    }

    /**
     * Removes the first item from the list.
     *
     */
    public void removeFirst() {
        if (sentinel == last) {
            throw new RuntimeException("There are no items.");
        }

        if (sentinel.next == last) {
            last = sentinel;
        }

        sentinel.next = sentinel.next.next;

        size -= 1;
    }

    /**
     * Returns the first item in the list;
     *
     * @return The first item in the list.
     */
    public GloopGlop getFirst() {
        return sentinel.next.item;
    }

    /**
     * Adds x to the end of the list.
     *
     * @param x The added item.
     */
    public void addLast(GloopGlop x) {
        last.next = new StuffNode<>(x, null);
        last = last.next;
        size += 1;
    }

    /**
     * Removes the last item of the list.
     */
    public void removeLast() {
        if (last == sentinel) {
            throw new RuntimeException("There are no items.");
        }

        StuffNode<GloopGlop> lastPrev = sentinel;
        while (lastPrev.next != last) {
            lastPrev = lastPrev.next;
        }

        last = lastPrev;
        last.next = null;

        size -= 1;
    }

    /**
     * Inserts the given item at the given position.
     *
     * @param item The item to be inserted.
     * @param index The position to insert item at.
     */
    public void insert (GloopGlop item, int index) {
        if (index < 0) {
            throw new RuntimeException("index must be positive!");
        }

        StuffNode<GloopGlop> ptr = sentinel;
        for (int i = 0; i < index && ptr.next != null; i++) {
            ptr = ptr.next;
        }

        StuffNode<GloopGlop> node = new StuffNode<>(item, ptr.next);
        ptr.next = node;
        if (node.next == null) {
            last = node;
        }
        size += 1;
    }

    /**
     * Reverses the list.
     */
    public void reverseSaveMemoryWithReset() {
        if (sentinel.next == null) {
            return;
        }

        StuffNode<GloopGlop> node = sentinel.next;
        sentinel.next = null;

        while (node != null) {
            StuffNode<GloopGlop> rest = node.next;
            node.next = sentinel.next;
            sentinel.next = node;
            node = rest;
        }
    }

    /**
     * Reverses the list.
     */
    public void reverseSaveMemoryWithoutReset() {
        if (sentinel.next == null) {
            return;
        }

        StuffNode<GloopGlop> tail = sentinel.next;
        StuffNode<GloopGlop> node = sentinel.next.next;

        while (node != null) {
            tail.next = node.next;
            node.next = sentinel.next;
            sentinel.next = node;
            node = tail.next;
        }
    }

    /**
     * Reverses the list iteratively.
     */
    public void reverseIterative() {
        if (sentinel.next == null) {
            return;
        }

        StuffNode<GloopGlop> prev = null;
        StuffNode<GloopGlop> curr = sentinel.next;

        while (curr != null) {
            StuffNode<GloopGlop> next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        sentinel.next = prev;
    }

    /**
     * Reverses the list recursively.
     */
    public void reverseRecursive() {
        if (sentinel.next == null) {
            return;
        }

        sentinel.next = reverseRecursive(sentinel.next);
    }

    private static <GloopGlop> StuffNode<GloopGlop> reverseRecursive(StuffNode<GloopGlop> head) {
        if (head.next == null) {
            return head;
        }

        StuffNode<GloopGlop> newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the list.
     */
    public void display() {
        StuffNode<GloopGlop> p = sentinel.next;
        while (p != null) {
            System.out.print(p.item + ".");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        /* Creates an empty list. */
        SLList<Integer> L = new SLList<>();
        L.reverseRecursive();
        L.reverseIterative();
        L.display();
        L.addFirst(10);
        L.reverseRecursive();
        L.reverseIterative();
        L.display();
        L.addFirst(5);
        System.out.println(L.size());
        L.addLast(20);
        System.out.println(L.getFirst());
        System.out.println(L.size());
        L.display();
        L.addLast(50);
        L.display();
        L.removeFirst();
        L.display();
        L.removeLast();
        L.display();
        L.removeLast();
        L.display();
        L.removeLast();
        L.display();
        L.addLast(20);
        L.display();
        L.addLast(30);
        L.addLast(40);
        L.display();
        L.insert(10, 0);
        L.display();
        L.insert(50, 9);
        L.display();
        L.reverseRecursive();
        L.display();
        L.reverseIterative();
        L.display();
        L.reverseSaveMemoryWithReset();
        L.display();
        L.reverseSaveMemoryWithoutReset();
        L.display();
    }
}
