package trie;

/**
 * Node for TrieSet.
 */
class Node {

    boolean isKey;

    DataIndexCharMap<Node> next;

    Node(int R) {
        isKey = false;
        next = new DataIndexCharMap<>(R);
    }

}
