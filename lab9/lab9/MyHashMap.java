package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Slark King
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        return (double) size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (get(key) == null) {
            size += 1;
            if (loadFactor() > MAX_LF) {
                resize(2 * buckets.length);
            }
        }
        int index = hash(key);
        buckets[index].put(key, value);
    }

    /** Resizes the hash map.
     */
    private void resize(int capacity) {
        ArrayMap<K, V>[] oldBuckets = buckets;

        buckets = new ArrayMap[capacity];
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }

        for (ArrayMap<K, V> oldBucket : oldBuckets) {
            for (K key : oldBucket) {
                int index = hash(key);
                buckets[index].put(key, oldBucket.get(key));
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (ArrayMap<K, V> arrayMap : buckets) {
            keySet.addAll(arrayMap.keySet());
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int index = hash(key);
        V returnValue = buckets[index].remove(key);
        if (returnValue != null) {
            size -= 1;
            if (loadFactor() < MAX_LF / 4) {
                resize(buckets.length / 2);
            }
        }
        return returnValue;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        V returnValue = buckets[index].remove(key, value);
        if (returnValue != null) {
            size -= 1;
            if (loadFactor() < MAX_LF / 4) {
                resize(buckets.length / 2);
            }
        }
        return returnValue;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        Map61B<String, Integer> map = new MyHashMap<>();
        map.put("hello", 5);
        map.put("cat", 10);
        map.put("fish", 22);
        map.put("zebra", 90);

        assertEquals(4, map.size());
        assertEquals(5, (int) map.get("hello"));

        assertTrue(map.containsKey("cat"));
        assertTrue(map.containsKey("fish"));

        map.remove("cat");
        map.remove("fish", 12);

        assertFalse(map.containsKey("cat"));
        assertTrue(map.containsKey("fish"));
        assertEquals(3, map.size());

        map.remove("fish", 22);
        assertFalse(map.containsKey("fish"));
        assertEquals(2, map.size());

        for (int i = 0; i < 49; i++) {
            map.put(Integer.toString(i), i);
        }

        assertEquals(5, (int) map.get("hello"));
        assertEquals(90, (int) map.get("zebra"));
        assertEquals(1, (int) map.get("1"));
        assertEquals(51, map.size());
    }
}
