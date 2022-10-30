package alternate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A set implemented by alternate trie.
 */
public class AlternateTrieSet {

    private final int r;

    private final AlternateNode root;

    /**
     * Constructor for AlternateTrieSet.
     *
     * @param R The size of the character set.
     */
    public AlternateTrieSet(int R) {
        r = R;
        root = new AlternateNode();
    }

    /**
     * Adds a string to the alternate trie set.
     *
     * @param s The added string.
     */
    public void add(String s) {
        int i = 0;
        AlternateNode node = root;

        while (true) {
            // node represents the last character of the current sub-string, "i" represents the index of the next character.
            if (i == s.length()) {
                node.isKey = true;
                break;
            }

            char nextChar = s.charAt(i);
            checkCharacter(nextChar);
            node.next.putIfAbsent(nextChar, new AlternateNode());

            node = node.next.get(nextChar);
            i += 1;
        }
    }

    /**
     * Checks if the string is contained by the alternate trie set.
     *
     * @param s The string to check.
     * @return If the string is contained
     */
    public boolean contains(String s) {
        int i = 0;
        AlternateNode node = root;

        while (true) {
            // node represents the last character of the current sub-string, "i" represents the index of the next character.
            if (node == null) {
                return false;
            }

            if (i == s.length()) {
                return node.isKey;
            }

            node = node.next.get(s.charAt(i));
            i += 1;
        }
    }

    private void checkCharacter(char c) {
        if (c >= r) {
            throw new IllegalArgumentException("Invalid character for AlternateTrieSet!");
        }
    }

    public static void main(String[] args) {
        AlternateTrieSet alternateTrieSet = new AlternateTrieSet(128);

        assertFalse(alternateTrieSet.contains(""));

        alternateTrieSet.add("app");
        alternateTrieSet.add("apple");
        alternateTrieSet.add("application");
        assertTrue(alternateTrieSet.contains("app"));
        assertTrue(alternateTrieSet.contains("apple"));
        assertTrue(alternateTrieSet.contains("application"));
        assertFalse(alternateTrieSet.contains("APP"));
        assertFalse(alternateTrieSet.contains("applications"));

        alternateTrieSet.add("");
        assertTrue(alternateTrieSet.contains(""));
    }

}

