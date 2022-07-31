import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BSTOperations {

    public static <Key extends Comparable<Key>> BST<Key> find(BST<Key> node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return find(node.left, key);
        } else {
            return find(node.right, key);
        }
    }

    public static <Key extends Comparable<Key>> BST<Key> insert(BST<Key> node, Key key) {
        if (node == null) {
            return new BST<>(key);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        }

        return node;
    }

    public static <Key extends Comparable<Key>> BST<Key> delete(BST<Key> node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            BST<Key> root = node;
            node = min(root.right);
            node.right = deleteMin(root.right);
            node.left = root.left;
        }

        return node;
    }

    private static <Key extends Comparable<Key>> BST<Key> min(BST<Key> node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    private static <Key extends Comparable<Key>> BST<Key> deleteMin(BST<Key> node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private static class BST<Key extends Comparable<Key>> {

        private final Key key;
        private BST<Key> left;
        private BST<Key> right;

        private BST(Key k, BST<Key> l, BST<Key> r) {
            key = k;
            left = l;
            right = r;
        }

        private BST(Key k) {
            key = k;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        Set<Integer> exists = new HashSet<>();

        BST<Integer> bstTree = null;
        for (int i = 0; i < 1000; i++) {
            int randomNum = random.nextInt(1000);
            bstTree = insert(bstTree, randomNum);
            exists.add(randomNum);
        }

        for (int num : exists) {
            assertEquals(num, (int) find(bstTree, num).key);
        }

        for (int i = 0; i < 1000; i++) {
            int randomNum = random.nextInt(1000);
            bstTree = delete(bstTree, randomNum);
            exists.remove(randomNum);
            assertEquals(null, find(bstTree, randomNum));
            for (int num : exists) {
                assertEquals(num, (int) find(bstTree, num).key);
            }
        }
    }

}
