public interface List61B<GloopGlop> {

    /**
     * Adds x to the front of the list.
     *
     * @param x The added item.
     */
    void addFirst(GloopGlop x);

    /**
     * Adds x to the end of the list.
     *
     * @param x The added item.
     */
    void addLast(GloopGlop x);

    /**
     * Returns the first item in the list;
     *
     * @return The first item in the list.
     */
    GloopGlop getFirst();

    /**
     * Returns the last item in the list;
     *
     * @return The last item in the list.
     */
    GloopGlop getLast();


    /**
     * Removes the last item of the list.
     */
    GloopGlop removeLast();

    /**
     * Returns the index's item.
     *
     * @param index The item's position.
     * @return The needed item.
     */
    GloopGlop get(int index);

    /**
     * Inserts the given item at the given position.
     *
     * @param item  The item to be inserted.
     * @param index The position to insert item at.
     */
    void insert(GloopGlop item, int index);

    /**
     * Returns the size of the list.
     *
     * @return the size of the list.
     */
    int size();

    /**
     * Prints out the entire list.
     */
    default void print() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

}
