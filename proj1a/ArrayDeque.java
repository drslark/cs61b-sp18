public class ArrayDeque<T> {

    private static final int DEFAULT_SIZE = 8;

    private T[] items;

    private int startIndex;

    private int endIndex;

    private int size;

    @SuppressWarnings("unchecked")
    private ArrayDeque(int capacity) {
        items = (T[]) new Object[capacity];
        startIndex = 0;
        endIndex = capacity - 1;
        size = 0;
    }

    public ArrayDeque() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings({"rawtypes"})
    private ArrayDeque(ArrayDeque other) {
        this(Math.max(other.size + 1, other.size + (other.size >> 2)));

        copyItems(other.items, other.startIndex, other.size, items);
        size = other.size;
        endIndex = size - 1;
    }

    private static <T> void copyItems(T[] srcItems, int srcStartIndex, int size, T[] dstItems) {
        if (dstItems.length < size) {
            throw new RuntimeException(
                    "Can not copy items to a smaller list!"
            );
        }

        int sizeAfterStart = Math.min(srcItems.length - srcStartIndex, size);
        System.arraycopy(srcItems, srcStartIndex, dstItems, 0, sizeAfterStart);
        System.arraycopy(srcItems, 0, dstItems, sizeAfterStart, size - sizeAfterStart);
    }

    /**
     * Resizes the list with a given capacity.
     *
     * @param capacity The given capacity.
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        if (capacity < size) {
            throw new RuntimeException(
                    "Can not resize the list to a capacity smaller than the number of items!"
            );
        }

        T[] tmp = (T[]) new Object[capacity];
        copyItems(items, startIndex, size, tmp);
        items = tmp;
        startIndex = 0;
        endIndex = size - 1;
    }

    /**
     * Gets the actual index in a circular list.
     *
     * @param index The raw index.
     * @return The actual index.
     */
    private int getActualIndex(int index) {
        if (index < 0) {
            throw new RuntimeException("Index must be non-negative!");
        }

        index = index + startIndex;
        return index < items.length ? index : index - items.length;
    }

    /**
     * Increments some value.
     */
    private int increment(int val) {
        val += 1;
        return val < items.length ? val : val - items.length;
    }

    /**
     * Decrements some value.
     */
    private int decrement(int val) {
        val -= 1;
        return val >= 0 ? val : val + items.length;
    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item The item to be added.
     */
    public void addFirst(T item) {
        if (size == items.length) {
            int capacity = Math.max(size + 1, size + (size >> 2));
            resize(capacity);
        }

        startIndex = decrement(startIndex);

        items[startIndex] = item;

        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item The item to be added.
     */
    public void addLast(T item) {
        if (size == items.length) {
            int capacity = Math.max(size + 1, size + (size >> 2));
            resize(capacity);
        }

        endIndex = increment(endIndex);

        items[endIndex] = item;

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
        for (int i = 0; i < size; i++) {
            sb.append(items[getActualIndex(i)]).append(" ");
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
        if (size == 0) {
            return null;
        }

        if (size - 1 < (items.length >> 2)) {
            int capacity = Math.max(1, items.length >> 1);
            resize(capacity);
        }

        T item = items[startIndex];
        items[startIndex] = null;

        startIndex = increment(startIndex);
        size -= 1;
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     *
     * @return the item at the back of the deque.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        if (size - 1 < items.length >> 2) {
            int capacity = Math.max(1, items.length >> 2);
            resize(capacity);
        }

        T item = items[endIndex];
        items[endIndex] = null;

        endIndex = decrement(endIndex);
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

        return items[getActualIndex(index)];
    }
}
