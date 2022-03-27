public interface Deque<Item> {

    /**
     * Adds an item of type T to the front of the deque.
     */
    void addFirst(Item item);

    /**
     * Adds an item of type T to the back of the deque.
     */
    void addLast(Item item);

    /**
     * Returns true if deque is empty, false otherwise.
     */
    default boolean isEmpty() {
        return size() > 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    int size();

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    void printDeque();

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    Item removeFirst();

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    Item removeLast();

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    Item get(int index);
}