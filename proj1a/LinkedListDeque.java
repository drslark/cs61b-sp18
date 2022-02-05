public class LinkedListDeque<T> {

    private static class StuffNode<E> {
        E item;
        StuffNode<E> prev;
        StuffNode<E> next;

        public StuffNode(E i, StuffNode<E> p, StuffNode<E> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private final StuffNode<T> sentinel;

    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    @SuppressWarnings({"CopyConstructorMissesField", "rawtypes", "unchecked"})
    private LinkedListDeque(LinkedListDeque other) {
        this();

        for (StuffNode p = other.sentinel.next; p != other.sentinel; p = p.next) {
            addLast((T) p.item);
        }
    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item The item to be added.
     */
    public void addFirst(T item) {
        StuffNode<T> node = new StuffNode<>(item, sentinel, sentinel.next);
        sentinel.next.prev = node;
        sentinel.next = node;

        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item The item to be added.
     */
    public void addLast(T item) {
        StuffNode<T> node = new StuffNode<>(item, sentinel.prev, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;

        size += 1;
    }

    /**
     * If deque is empty.
     *
     * @return true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items in the deque.
     *
     * @return the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Print the items in the deque from first to last.
     */
    public void printDeque() {
        StringBuffer sb = new StringBuffer();

        for (StuffNode<T> p = sentinel.next; p != sentinel; p = p.next) {
            sb.append(p.item).append(" ");
        }

        if (sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb);
    }

    /**
     * Removes and returns the item at the front of the deque.
     *
     * @return the item at the front of the deque.
     */
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }

        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;

        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     *
     * @return the item at the back of the deque.
     */
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }

        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;

        return item;
    }

    /**
     * Gets the index-th item in the list.
     *
     * @param index the item's index.
     * @return the index-th item.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        StuffNode<T> p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }

        return p.item;
    }

    /**
     * Gets and returns the item at the given index in a recursive way.
     *
     * @param index the given index.
     * @return the item at the given index.
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return getRecursive(sentinel.next, index);
    }

    private T getRecursive(StuffNode<T> node, int index) {
        if (index == 0) {
            return node.item;
        }

        return getRecursive(node.next, index - 1);
    }
}
