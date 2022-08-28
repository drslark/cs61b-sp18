package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Slark King
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        } else if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }

        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        } else if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }

        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /** Traverses the BST to get all keys.
     */
    private void keySetHelper(Node p, Set<K> keySet) {
        if (p == null) {
            return;
        }

        keySetHelper(p.left, keySet);
        keySet.add(p.key);
        keySetHelper(p.right, keySet);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySetHelper(root, keySet);
        return keySet;
    }

    /** Returns a Node with min key.
     */
    private Node minHelper(Node p) {
        if (p == null) {
            return null;
        }

        if (p.left == null) {
            return p;
        }

        return minHelper(p.left);
    }

    /** Returns a BSTMap root with min node removed.
     */
    private Node removeMinHelper(Node p) {
        if (p == null) {
            return null;
        }

        if (p.left == null) {
            size -= 1;
            return p.right;
        }

        p.left = removeMinHelper(p.left);
        return p;
    }

    /** Returns a BSTMap rooted in p with KEY removed.
     */
    private Node removeKeyHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeKeyHelper(key, p.left);
        } else if (cmp > 0) {
            p.right = removeKeyHelper(key, p.right);
        } else {
            if (p.left == null) {
                p = p.right;
                size -= 1;
            } else if (p.right == null) {
                p = p.left;
                size -= 1;
            } else {
                Node rightMinNode = minHelper(p.right);
                Node root = new Node(rightMinNode.key, rightMinNode.value);
                root.left = p.left;
                root.right = removeMinHelper(p.right);
                p = root;
            }
        }

        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }

        V returnValue = get(key);
        if (returnValue == null) {
            return null;
        }

        root = removeKeyHelper(key, root);
        return returnValue;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }

        V returnValue = get(key);
        if (returnValue == null || !returnValue.equals(value)) {
            return null;
        }

        root = removeKeyHelper(key, root);
        return returnValue;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        Map61B<String, Integer> map = new BSTMap<>();
        map.put("hello", 5);
        map.remove("hello");

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
    }
}
