package hw4.puzzle;

import java.util.HashMap;
import java.util.Map;

/**
 * Min Priority Queue that all items are unique and their priorities are provided extrinsically.
 */
public class ExtrinsicUniqueMinPQ<T, E extends Comparable<E>> {

    private static final int DEFAULT_CAPACITY = 8;

    private int size;

    private Node<T, E>[] nodes;
    private final Map<T, Integer> itemToIndex;

    public ExtrinsicUniqueMinPQ() {
        this(DEFAULT_CAPACITY);
    }

    public ExtrinsicUniqueMinPQ(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }

        capacity = 1 + capacity;
        nodes = (Node<T, E>[]) new Node[capacity];
        itemToIndex = new HashMap<>();
        size = 0;
    }

    /**
     * Returns true if the PQ contains the given item.
     */
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    /**
     * Inserts an item with the given priority value.
     */
    public void add(T item, E priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("PQ contains item!");
        }

        if (size == nodes.length - 1) {
            int capacity = Math.max(1 + (size + 1), 1 + 2 * (nodes.length - 1));
            resize(capacity);
        }

        size += 1;
        nodes[size] = new Node<>(item, priority);
        itemToIndex.put(item, size);
        swim(size);
    }

    /**
     * Removes and returns the minimum item.
     */
    public T removeMin() {
        if (size == 0) {
            throw new IllegalArgumentException("PQ is empty!");
        }

        Node<T, E> node = nodes[1];

        size -= 1;
        if (size > 0) {
            nodes[1] = nodes[size + 1];
            itemToIndex.put(nodes[size + 1].item(), 1);
            sink(1);
        }

        nodes[size + 1] = null;
        itemToIndex.remove(node.item());

        if (size < (nodes.length - 1) / 4) {
            int capacity = Math.max(1 + DEFAULT_CAPACITY, 1 + (nodes.length - 1) / 2);
            resize(capacity);
        }

        return node.item();
    }

    /**
     * Returns the number of itemToPriority in the PQ.
     */
    public int size() {
        return size;
    }

    /**
     * Changes the priority of the given item. Behavior undefined if the item doesn't exist.
     */
    public void changePriority(T item, E priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException("PQ does not contain item!");
        }

        int index = itemToIndex.get(item);
        nodes[index].changePriority(priority);

        swim(index);
        sink(index);
    }

    /**
     * Inserts an item with the given priority value if the item exists or
     * changes the priority of the given item. Behavior undefined if the item doesn't exist.
     */
    public void set(T item, E priority) {
        if (contains(item)) {
            changePriority(item, priority);
        } else {
            add(item, priority);
        }
    }

    private void resize(int capacity) {
        Node<T, E>[] newNodes = (Node<T, E>[]) new Node[capacity];
        System.arraycopy(nodes, 1, newNodes, 1, size);
        nodes = newNodes;
    }

    private int parentOf(int index) {
        return index / 2;
    }

    private int leftChildOf(int index) {
        return 2 * index;
    }

    private void swim(int index) {
        int parentIndex = parentOf(index);

        Node<T, E> node = nodes[index];

        while (parentIndex > 0) {
            if (node.priority().compareTo(nodes[parentIndex].priority()) >= 0) {
                break;
            }

            nodes[index] = nodes[parentIndex];
            itemToIndex.put(nodes[parentIndex].item(), index);

            index = parentIndex;
            parentIndex = parentOf(index);
        }

        nodes[index] = node;
        itemToIndex.put(node.item(), index);
    }

    private void sink(int index) {
        int exchangeIndex = leftChildOf(index);

        Node<T, E> node = nodes[index];

        while (exchangeIndex <= size) {
            if (
                exchangeIndex + 1 <= size
                    && nodes[exchangeIndex + 1].priority().
                    compareTo(nodes[exchangeIndex].priority()) < 0
            ) {
                exchangeIndex += 1;
            }

            if (node.priority().compareTo(nodes[exchangeIndex].priority()) <= 0) {
                break;
            }

            nodes[index] = nodes[exchangeIndex];
            itemToIndex.put(nodes[exchangeIndex].item(), index);

            index = exchangeIndex;
            exchangeIndex = leftChildOf(index);
        }

        nodes[index] = node;
        itemToIndex.put(node.item(), index);
    }

    private static class Node<M, N> {
        private final M item;
        private N priority;

        private Node(M t, N p) {
            item = t;
            priority = p;
        }

        private M item() {
            return item;
        }

        private N priority() {
            return priority;
        }

        private void changePriority(N p) {
            priority = p;
        }
    }

}
