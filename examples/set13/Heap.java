import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Heap<Item> implements MinPQ<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] items;
    private int size;

    private final Comparator<Item> itemComparator;

    public Heap() {
        this(DEFAULT_CAPACITY, (Comparator<Item>) Comparator.naturalOrder());
    }

    public Heap(Comparator<Item> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    public Heap(int capacity, Comparator<Item> comparator) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater tha zero!");
        }

        items = (Item[]) new Object[capacity + 1];
        size = 0;

        itemComparator = comparator;
    }

    @Override
    public void add(Item x) {
        if (size == items.length - 1) {
            resize(2 * (items.length - 1));
        }

        size += 1;
        items[size] = x;

        swim(size);
    }

    @Override
    public Item getSmallest() {
        return items[1];
    }

    @Override
    public Item removeSmallest() {
        Item smallestItem = items[1];
        items[1] = items[size];
        items[size] = null;
        size -= 1;

        sink(1);

        if (size > 0 && size <= (items.length - 1) / 4) {
            resize((items.length - 1) / 2);
        }

        return smallestItem;
    }

    @Override
    public int size() {
        return size;
    }

    private void swim(int index) {
        if (index <= 1) {
            return;
        }

        if (itemComparator.compare(items[index], items[parentOf(index)]) < 0) {
            swap(index, parentOf(index));
            swim(parentOf(index));
        }
    }

    private void sink(int index) {
        int indexToSwap = leftChildOf(index);

        if (indexToSwap > size) {
            return;
        }

        if (indexToSwap + 1 <= size
            && itemComparator.compare(items[indexToSwap + 1], items[indexToSwap]) < 0
        ) {
            indexToSwap += 1;
        }

        if (itemComparator.compare(items[indexToSwap], items[index]) < 0) {
            swap(index, indexToSwap);
            sink(indexToSwap);
        }
    }

    private void swap(int thisIndex, int thatIndex) {
        Item tmpItem = items[thisIndex];
        items[thisIndex] = items[thatIndex];
        items[thatIndex] = tmpItem;
    }

    private int parentOf(int index) {
        return index / 2;
    }

    private int leftChildOf(int index) {
        return 2 * index;
    }

    private void resize(int capacity) {
        Item[] oldItems = items;
        items = (Item[]) new Object[capacity + 1];
        System.arraycopy(oldItems, 1, items, 1, size);
    }

    public static void main(String[] args) {
        MinPQ<Integer> minPQ = new Heap<>(1, (Comparator<Integer>) Comparator.naturalOrder());
        minPQ.add(3);
        assertEquals(3, (long) minPQ.removeSmallest());
        minPQ.add(3);

        Random random = new Random();
        minPQ = new Heap<>();

        for (int i = 1; i < 1001; i++) {
            minPQ.add(random.nextInt(1000));
            assertEquals(i, minPQ.size());
        }

        int prevNumber = -1;
        for (int i = 999; i >= 0; i--) {
            assertTrue(minPQ.getSmallest() >= prevNumber);
            prevNumber = minPQ.removeSmallest();
            assertEquals(i, minPQ.size());
        }
    }

}
