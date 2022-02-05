// Invariants:
// the position of the item to be inserted is always size.
// The position of the last item is always size - 1.
// the number of the items is always size.
public class AList<GloopGlop> implements List<GloopGlop> {

    private GloopGlop[] items;

    private int size;

    /**
     * Creates an empty list of size 100.
     */
    public AList() {
        this(100);
    }

    /**
     * Creates a list of the input capacity.
     *
     * @param capacity The capacity of the list.
     */
    @SuppressWarnings("unchecked")
    public AList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater tha zero!");
        }

        items = (GloopGlop[]) new Object[capacity];
        size = 0;
    }

    /**
     * Resizes the underlying array to the target size.
     *
     * @param capacity The target size.
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        GloopGlop[] tmp = (GloopGlop[]) new Object[capacity];
        System.arraycopy(items, 0, tmp, 0, size);
        items = tmp;
    }

    /**
     * Inserts item into the back of the list.
     *
     * @param item The item to insert.
     */
    public void addLast(GloopGlop item) {
        if (size == items.length) {
            int expectedCapacity = Math.max(size + 1, items.length + (items.length >> 2));
            resize(expectedCapacity);
        }

        items[size] = item;
        size += 1;
    }

    /**
     * Gets the item from back of the list.
     *
     * @return The item at the back of the list.
     */
    public GloopGlop getLast() {
        if (size == 0) {
            throw new RuntimeException("The list is empty!");
        }

        return items[size - 1];
    }

    /**
     * Deletes and returns the item from back of the list.
     *
     * @return the item at the back of the list.
     */
    public GloopGlop removeLast() {
        if (size == 0) {
            throw new RuntimeException("The list is empty!");
        }

        if (size - 1 > 0 && size - 1 <= items.length >> 2) {
            int expectedCapacity = Math.max(size, items.length >> 1);
            resize(expectedCapacity);
        }

        GloopGlop item = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        return item;
    }

    /**
     * Gets the item at the given index in the list.
     *
     * @param index the index of the item.
     * @return The item at the given index.
     */
    public GloopGlop get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return items[index];
    }

    /**
     * Gets the number of items in the list.
     *
     * @return The items' number in the list.
     */
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        AList<String> aList = new AList<>(1);
        System.out.println(aList.size());
        aList.addLast("a");
        aList.removeLast();
        aList.addLast("a");
        aList.addLast("b");
        aList.addLast("c");
        aList.addLast("d");
        aList.addLast("e");
        System.out.println(aList.size());
        System.out.println(aList.get(0));
        System.out.println(aList.getLast());
    }
}
