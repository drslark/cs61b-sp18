package map61b;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * An array based implementation of the Map61B class.
 */
public class ArrayMap<K, V> implements Map61B<K, V>, Iterable<ArrayMap.Entry<K, V>> {

    private final int CAPACITY = 10;

    private K[] keys;

    private V[] values;

    private int size;

    @SuppressWarnings("unchecked")
    public ArrayMap() {
        keys = (K[]) new Object[CAPACITY];
        values = (V[]) new Object[CAPACITY];
        size = 0;
    }

    /** Returns the index of the given key if it exists,
     * -1 otherwise.
     */
    private int keyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /** Associates key with value */
    @Override
    public void put(K key, V value) {
        int index = keyIndex(key);
        if (index > -1) {
            values[index] = value;
            return;
        }

        if (size == CAPACITY) {
            throw new RuntimeException("The map is full!");
        }

        keys[size] = key;
        values[size] = value;
        size++;
    }

    /** Checks if map contains the key */
    @Override
    public boolean containsKey(K key) {
        int index = keyIndex(key);
        return index > -1;
    }

    /** Returns value, assuming associated key exists. */
    @Override
    public V get(K key) {
        int index = keyIndex(key);
        if (index > -1) {
            return values[index];
        }
        return null;
    }

    /** Returns a list of all keys. */
    @Override
    public List<K> keys() {
        List<K> keyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            keyList.add(keys[i]);
        }
        return keyList;
    }

    /** Returns number of keys */
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new MapIterator<>(this);
    }

    private static class MapIterator<Key, Value> implements Iterator<Entry<Key, Value>> {

        private int currIndex;

        private final ArrayMap<Key, Value> arrayMap;

        public MapIterator(ArrayMap<Key, Value> arrayMap) {
            this.arrayMap = arrayMap;
        }

        @Override
        public boolean hasNext() {
            return currIndex < arrayMap.size;
        }

        @Override
        public Entry<Key, Value> next() {
            Entry<Key, Value> entry = new Entry<>(arrayMap.keys[currIndex], arrayMap.values[currIndex]);
            currIndex++;
            return entry;
        }
    }

    public static class Entry<Key, Value> {

        private final Key key;

        private final Value value;

        public Entry(Key k, Value v) {
            key = k;
            value = v;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

    }

    @Test
    public void test() {
        ArrayMap<Integer, Integer> arrayMap = new ArrayMap<>();
        arrayMap.put(2, 5);
        int expected = 5;
        assertEquals(expected, (int) arrayMap.get(2));
    }

    public static void main(String[] args) {
        ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
        arrayMap.put("horse", 3);
        arrayMap.put("fish", 9);
        arrayMap.put("house", 10);

        ArrayMap<String, Integer> theOtherArrayMap = new ArrayMap<>();
        theOtherArrayMap.put("hello", 5);
        theOtherArrayMap.put("syrups", 10);
        theOtherArrayMap.put("kingdom", 10);

        for (Entry<String, Integer> entry : theOtherArrayMap) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
