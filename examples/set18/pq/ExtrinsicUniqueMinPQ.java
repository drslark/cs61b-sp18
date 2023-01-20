package pq;

import java.util.HashMap;
import java.util.Map;

/**
 * Min Priority Queue that all items are unique and their priority can be specified.
 */
public class ExtrinsicUniqueMinPQ<T> {

    private static final int DEFAULT_CAPACITY = 8;

    private int size;

    private Node<T>[] nodes;
    private final Map<T, Integer> itemToIndex;

    public ExtrinsicUniqueMinPQ() {
        this(DEFAULT_CAPACITY);
    }

    public ExtrinsicUniqueMinPQ(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }

        capacity = 1 + capacity;
        nodes = (Node<T>[]) new Node[capacity];
        itemToIndex = new HashMap<>();
        size = 0;
    }

    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    public void add(T item, double priority) {
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

    public T removeMin() {
        if (size == 0) {
            throw new IllegalArgumentException("PQ is empty!");
        }

        Node<T> node = nodes[1];

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

    public int size() {
        return size;
    }

    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException("PQ does not contain item!");
        }

        int index = itemToIndex.get(item);
        nodes[index].changePriority(priority);

        swim(index);
        sink(index);
    }

    private void resize(int capacity) {
        Node<T>[] newNodes = (Node<T>[]) new Node[capacity];
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

        Node<T> node = nodes[index];

        while (parentIndex > 0) {
            if (nodes[parentIndex].priority() <= node.priority()) {
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

        Node<T> node = nodes[index];

        while (exchangeIndex <= size) {
            if (
                exchangeIndex + 1 <= size
                    && nodes[exchangeIndex + 1].priority() < nodes[exchangeIndex].priority()
            ) {
                exchangeIndex += 1;
            }

            if (node.priority() <= nodes[exchangeIndex].priority()) {
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

    private static class Node<E> {
        private final E item;
        private double priority;

        private Node(E t, double p) {
            item = t;
            priority = p;
        }

        private E item() {
            return item;
        }

        private double priority() {
            return priority;
        }

        private void changePriority(double p) {
            priority = p;
        }
    }

}
