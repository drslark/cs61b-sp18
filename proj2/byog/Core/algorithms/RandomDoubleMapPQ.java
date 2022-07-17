package byog.Core.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Priority queue using a TreeMap and a HashMap where objects have a priority
 * that is provided extrinsically, i.e. are supplied as an argument during insertion
 * and can be changed using the changePriority method.
 */
public class RandomDoubleMapPQ<T, E extends Comparable<E>> {

    private final Random random;

    private final TreeMap<E, Set<T>> priorityToItem;
    private final HashMap<T, E> itemToPriority;

    /**
     * Constructor of a RandomDoubleMapPQ, which consists of a TreeMap and a HashMap.
     */
    public RandomDoubleMapPQ() {
        this(new Random());
    }

    /**
     * Constructor with a random number generator.
     *
     * @param r A random number generator.
     */
    public RandomDoubleMapPQ(Random r) {
        random = r;
        priorityToItem = new TreeMap<>();
        itemToPriority = new HashMap<>();
    }

    private T getRandomItem(Set<T> s) {
        Iterator<T> iterator = s.iterator();
        int randomIndex = random.nextInt(s.size());

        for (int i = 0; i < randomIndex; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    /**
     * Inserts an item with the given priority value if the item exists or
     * changes the priority of the given item. Behavior undefined if the item doesn't exist.
     *
     * @param item The item to insert.
     * @param priority The priority corresponding to the item.
     */
    public void set(T item, E priority) {
        if (contains(item)) {
            changePriority(item, priority);
        } else {
            add(item, priority);
        }
    }

    /**
     * Inserts an item with the given priority value.
     *
     * @param item The item to add.
     * @param priority The priority corresponding to the item.
     */
    public void add(T item, E priority) {
        if (itemToPriority.containsKey(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        if (!priorityToItem.containsKey(priority)) {
            priorityToItem.put(priority, new HashSet<T>());
        }
        Set<T> itemsWithPriority = priorityToItem.get(priority);
        itemsWithPriority.add(item);
        itemToPriority.put(item, priority);
    }

    /**
     * Returns true if the PQ contains the given item.
     *
     * @param item The specified item.
     * @return Whether the item is contained or not.
     */
    public boolean contains(T item) {
        return itemToPriority.containsKey(item);
    }

    /**
     * Returns one of the smallest items randomly.
     *
     * @return The smallest item. if there are multiple smallest item, pick one of them randomly.
     */
    public T getRandomSmallest() {
        if (itemToPriority.size() == 0) {
            throw new NoSuchElementException("PQ is empty.");
        }
        Set<T> itemsWithLowestPriority = priorityToItem.get(priorityToItem.firstKey());
        return getRandomItem(itemsWithLowestPriority);
    }

    /**
     * Removes and returns one of the smallest items randomly.
     *
     * @return The smallest item. if there are multiple smallest item, pick one of them randomly.
     */
    public T removeRandomSmallest() {
        if (itemToPriority.size() == 0) {
            throw new NoSuchElementException("PQ is empty.");
        }

        E lowestPriority = priorityToItem.firstKey();

        Set<T> itemsWithLowestPriority = priorityToItem.get(lowestPriority);
        T item = getRandomItem(itemsWithLowestPriority);

        itemsWithLowestPriority.remove(item);
        if (itemsWithLowestPriority.size() == 0) {
            priorityToItem.remove(lowestPriority);
        }
        itemToPriority.remove(item);
        return item;
    }

    /**
     * Changes the priority of the given item. Behavior undefined if the item doesn't exist.
     *
     * @param item The item to add.
     * @param priority The priority corresponding to the item.
     */
    public void changePriority(T item, E priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException(item + " not in PQ.");
        }

        E oldP = itemToPriority.get(item);
        Set<T> itemsWithOldPriority = priorityToItem.get(oldP);
        itemsWithOldPriority.remove(item);

        if (itemsWithOldPriority.size() == 0) {
            priorityToItem.remove(oldP);
        }

        itemToPriority.remove(item);
        add(item, priority);
    }

    /**
     * Returns the number of items in the PQ.
     *
     * @return The number of items.
     */
    public int size() {
        return itemToPriority.size();
    }

}
