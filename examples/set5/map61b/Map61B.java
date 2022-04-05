package map61b;

import java.util.List;

public interface Map61B<K, V> {

    /** Associates key with value */
    void put(K key, V value);

    /** Checks if map contains the key */
    boolean containsKey(K key);

    /** Returns value, assuming associated key exists. */
    V get(K key);

    /** Returns a list of all keys. */
    List<K> keys();

    /** Returns number of keys */
    int size();
}
