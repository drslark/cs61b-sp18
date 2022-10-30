package trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A set implemented by trie.
 */
public class TrieSet {

    private final int r;

    private final Node root;

    /**
     * Constructor for TrieSet.
     *
     * @param R The size of the character set.
     */
    public TrieSet(int R) {
        r = R;
        root = new Node(r);
    }

    /**
     * Adds a string to the trie set.
     *
     * @param s The added string.
     */
    public void add(String s) {
        add(root, s, 0);
    }

    private Node add(Node node, String s, int i) {
        // node represents the last character of the current sub-string, "i" represents the index of the next character.
        if (node == null) {
            node = new Node(r);
        }

        if (i == s.length()) {
            node.isKey = true;
        } else {
            char nextChar = s.charAt(i);
            Node nextNode = add(node.next.get(nextChar), s, i + 1);
            node.next.put(nextChar, nextNode);
        }

        return node;
    }

    /**
     * Checks if the string is contained by the trie set.
     *
     * @param s The string to check.
     * @return If the string is contained
     */
    public boolean contains(String s) {
        Node node = get(root, s, 0);
        if (node == null) {
            return false;
        }
        return node.isKey;
    }

    private Node get(Node node, String s, int i) {
        // node represents the last character of the current sub-string, "i" represents the index of the next character.
        if (node == null) {
            return null;
        }

        if (i == s.length()) {
            return node;
        }

        char nextChar = s.charAt(i);
        Node nextNode = node.next.get(nextChar);

        return get(nextNode, s, i + 1);
    }

    /**
     * Collects all keys in a trie.
     *
     * @return All keys of a trie.
     */
    public List<String> collect() {
        List<String> keys = new ArrayList<>();
        collect(root, keys, new StringBuilder());
        return keys;
    }

    private void collect(Node node, List<String> keys, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }

        if (node.isKey) {
            keys.add(stringBuilder.toString());
        }

        for (char c : node.next.keys()) {
            stringBuilder.append(c);
            collect(node.next.get(c), keys, stringBuilder);
            int last = stringBuilder.length() - 1;
            stringBuilder.deleteCharAt(last);
        }
    }

    /**
     * Collects all keys start with a prefix in a trie.
     *
     * @return All keys start with a prefix
     */
    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();

        Node prefixNode = get(root, prefix, 0);

        StringBuilder stringBuilder = new StringBuilder(prefix);
        collect(prefixNode, keys, stringBuilder);

        return keys;
    }

    /**
     * Finds the longest prefix of the given string.
     *
     * @return A given string
     */
    public String longestPrefixOf(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        longestPrefixOf(root, s, 0, stringBuilder);
        return stringBuilder.toString();
    }

    private void longestPrefixOf(Node node, String s, int i, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }

        if (node.isKey) {
            int j = stringBuilder.length();
            while (j < i) {
                stringBuilder.append(s.charAt(j));
                j += 1;
            }
        }

        char nextChar = s.charAt(i);
        Node nextNode = node.next.get(nextChar);
        longestPrefixOf(nextNode, s, i + 1, stringBuilder);
    }

    public static void main(String[] args) {
        TrieSet trieSet = new TrieSet(128);

        assertFalse(trieSet.contains(""));

        trieSet.add("app");
        trieSet.add("apple");
        trieSet.add("application");
        assertTrue(trieSet.contains("app"));
        assertTrue(trieSet.contains("apple"));
        assertTrue(trieSet.contains("application"));
        assertFalse(trieSet.contains("APP"));
        assertFalse(trieSet.contains("applications"));

        trieSet.add("");
        assertTrue(trieSet.contains(""));

        trieSet = new TrieSet(128);
        trieSet.add("sad");
        trieSet.add("awls");
        trieSet.add("same");
        trieSet.add("a");
        trieSet.add("sam");
        trieSet.add("sap");
        assertEquals(
            new HashSet<>(Arrays.asList("sad", "awls", "same", "a", "sam", "sap")),
            new HashSet<>(trieSet.collect())
        );
        assertEquals(
            new HashSet<>(Arrays.asList("sad", "sam", "same", "sap")),
            new HashSet<>(trieSet.keysWithPrefix("sa"))
        );
        assertEquals("sam", trieSet.longestPrefixOf("sample"));
    }

}
