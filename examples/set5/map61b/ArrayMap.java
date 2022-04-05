package map61b;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * An array based implementation of the Map61B class.
 */
public class ArrayMap<K, V> implements Map61B<K, V> {

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
    }
}
