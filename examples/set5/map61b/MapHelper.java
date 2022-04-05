package map61b;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to demonstrate how generic methods work in java.
 */
public class MapHelper {

    /* get(map, key) : Returns item in map if it exists. */
    public static <K, V> V get(Map61B<K, V> map61b, K key) {
        if (map61b.containsKey(key)) {
            return map61b.get(key);
        }
        return null;
    }

    /* maxKey(map) : Returns max of all keys, works only if x and y have comparable data. */
    public static <K extends Comparable<K>, V> K maxKey(Map61B<K, V> map61b) {
        List<K> keyList = map61b.keys();
        if (keyList.size() == 0) {
            return null;
        }

        K maxKey = keyList.get(0);
        for (K key : keyList) {
            if (key.compareTo(maxKey) > 0) {
                maxKey = key;
            }
        }

        return maxKey;
    }

    @Test
    public void testGet() {
        Map61B<String, Integer> map61b = new ArrayMap<>();
        map61b.put("horse", 3);
        map61b.put("fish", 9);
        map61b.put("house", 10);

        Integer actual = MapHelper.get(map61b, "fish");
        Integer expected = 9;
        assertEquals(expected, actual);

        actual = MapHelper.get(map61b, "awef");
        expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void testMaxKey() {
        Map61B<String, Integer> map61b = new ArrayMap<>();
        map61b.put("horse", 3);
        map61b.put("fish", 9);
        map61b.put("house", 10);

        String actual = MapHelper.maxKey(map61b);
        String expected = "house";
        assertEquals(expected, actual);
    }

}
