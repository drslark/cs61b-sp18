package alternate;

import java.util.Map;
import java.util.TreeMap;

/**
 * Node for AlternateTrieSet.
 */
class AlternateNode {

    boolean isKey;

    Map<Character, AlternateNode> next;

    AlternateNode() {
        isKey = false;
        next = new TreeMap<>();
    }

}
