/**
 * Allowing tracking and removal of the smallest item in a priority queue.
 */
public interface MinPQ<Item> {

    /**
     * Adds an item to the priority queue.
     *
     * @param x The item to add.
     */
    void add(Item x);

    /**
     * Returns the smallest item in the priority queue.
     *
     * @return The smallest item.
     */
    Item getSmallest();

    /**
     * Removes the smallest item in the priority queue.
     *
     * @return The smallest item.
     */
    Item removeSmallest();

    /**
     * Returns the size of the priority queue.
     *
     * @return The size.
     */
    int size();

}
