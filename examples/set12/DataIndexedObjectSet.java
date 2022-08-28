import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataIndexedObjectSet {

    private static final double MAX_LF = 1.5;

    private List<Object>[] present;

    private int size;

    public DataIndexedObjectSet() {
        this(31);
    }

    public DataIndexedObjectSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater tha zero!");
        }

        present = (List<Object>[]) new List[capacity];
        size = 0;
    }

    public void add(Object o) {
        if (contains(o)) {
            return;
        }

        if ((double) (size + 1) / present.length >= MAX_LF) {
            resize(2 * present.length);
        }

        int index = indexOfChain(o);
        if (present[index] == null) {
            present[index] = new LinkedList<>();
        }

        present[index].add(o);
        size += 1;
    }

    public Object remove(Object o) {
        if (!contains(o)) {
            return null;
        }

        int index = indexOfChain(o);
        present[index].remove(o);
        size -= 1;

        if (size > 0 && (double) size / present.length <= MAX_LF / 4) {
            resize(present.length / 2);
        }

        return o;
    }

    public boolean contains(Object o) {
        int index = indexOfChain(o);
        if (present[index] == null) {
            return false;
        }

        return present[index].contains(o);
    }

    public int size() {
        return size;
    }

    private int indexOfChain(Object o) {
        int r = o.hashCode() % present.length;
        return r < 0 ? r + present.length : r;
    }

    private void resize(int chains) {
        List<Object>[] old = present;
        present = (List<Object>[]) new List[chains];

        for (List<Object> list : old) {
            if (list == null) {
                continue;
            }

            for (Object o : list) {
                int index = indexOfChain(o);
                if (present[index] == null) {
                    present[index] = new LinkedList<>();
                }
                present[index].add(o);
            }
        }
    }

    public static void main(String[] args) {
        DataIndexedObjectSet dataIndexedObjectSet = new DataIndexedObjectSet(1);
        dataIndexedObjectSet.add("a");
        dataIndexedObjectSet.remove("a");
        assertFalse(dataIndexedObjectSet.contains("a"));
        dataIndexedObjectSet.add("a");
        assertTrue(dataIndexedObjectSet.contains("a"));

        dataIndexedObjectSet = new DataIndexedObjectSet();
        dataIndexedObjectSet.add("cat");
        dataIndexedObjectSet.add("dog");
        assertFalse(dataIndexedObjectSet.contains("z"));
        assertTrue(dataIndexedObjectSet.contains("dog"));
        assertEquals(2, dataIndexedObjectSet.size());

        for (int i = 0; i < 100; i++) {
            dataIndexedObjectSet.add(i);
        }

        assertEquals(102, dataIndexedObjectSet.size());
        for (int i = 0; i < 100; i++) {
            assertTrue(dataIndexedObjectSet.contains(i));
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(i, dataIndexedObjectSet.remove(i));
            assertFalse(dataIndexedObjectSet.contains(i));
        }
        assertEquals(2, dataIndexedObjectSet.size());

    }

}
