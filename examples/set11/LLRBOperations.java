import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LLRBOperations {

    public static <Key extends Comparable<Key>> boolean contains(RBNode<Key> root, Key key) {
        return find(root, key) != null;
    }

    public static <Key extends Comparable<Key>> Key minKey(RBNode<Key> root) {
        RBNode<Key> minNode = min(root);
        if (minNode == null) {
            return null;
        }
        return minNode.key;
    }

    public static <Key extends Comparable<Key>> Key maxKey(RBNode<Key> root) {
        RBNode<Key> minNode = max(root);
        if (minNode == null) {
            return null;
        }
        return minNode.key;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> add(RBNode<Key> root, Key key) {
        if (root != null && !isRed(root.left) && !isRed(root.right)) {
            root.color = RBNode.RED;
        }
        root = insert(root, key);
        root.color = RBNode.BLACK;
        return root;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> removeMin(RBNode<Key> root) {
        if (root != null && !isRed(root.left) && !isRed(root.right)) {
            root.color = RBNode.RED;
        }
        root = deleteMin(root);
        if (root != null) {
            root.color = RBNode.BLACK;
        }
        return root;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> removeMax(RBNode<Key> root) {
        if (root != null && !isRed(root.left) && !isRed(root.right)) {
            root.color = RBNode.RED;
        }
        root = deleteMax(root);
        if (root != null) {
            root.color = RBNode.BLACK;
        }
        return root;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> remove(RBNode<Key> root, Key key) {
        if (root != null && !isRed(root.left) && !isRed(root.right)) {
            root.color = RBNode.RED;
        }
        root = delete(root, key);
        if (root != null) {
            root.color = RBNode.BLACK;
        }
        return root;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> find(RBNode<Key> node, Key key) {
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

    public static <Key extends Comparable<Key>> RBNode<Key> min(RBNode<Key> node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    public static <Key extends Comparable<Key>> RBNode<Key> max(RBNode<Key> node) {
        if (node == null) {
            return null;
        }

        if (node.right == null) {
            return node;
        }

        return max(node.right);
    }

    public static <Key extends Comparable<Key>> RBNode<Key> insert(RBNode<Key> node, Key key) {
        if (node == null) {
            return new RBNode<>(key, RBNode.RED);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        }

        node = balance(node);

        return node;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> deleteMin(RBNode<Key> node) {
        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return null;
        }

        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);

        node = balance(node);

        return node;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> deleteMax(RBNode<Key> node) {
        if (node == null) {
            return null;
        }

        if (isRed(node.left)) {
            node = rotateRight(node);
        }

        if (node.right == null) {
            return null;
        }

        if (!isRed(node.right) && !isRed(node.right.left)) {
            node = moveRedRight(node);
        }

        node.right = deleteMax(node.right);

        node = balance(node);

        return node;
    }

    public static <Key extends Comparable<Key>> RBNode<Key> delete(RBNode<Key> node, Key key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            if (node.left != null && !isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left)) {
                node = rotateRight(node);
            }

            if (node.right != null && !isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }

            if (key.compareTo(node.key) == 0) {
                if (node.right == null) {
                    return null;
                }

                RBNode<Key> rightMin = min(node.right);
                node = new RBNode<>(rightMin.key, node.left, deleteMin(node.right), node.color);
            } else {
                node.right = delete(node.right, key);
            }
        }

        node = balance(node);

        return node;
    }

    private static <Key extends Comparable<Key>> RBNode<Key> rotateLeft(RBNode<Key> node) {
        // node.right.color == RED
        RBNode<Key> root = node.right;
        node.right = root.left;
        root.left = node;
        root.color = node.color;
        node.color = RBNode.RED;
        return root;
    }

    private static <Key extends Comparable<Key>> RBNode<Key> rotateRight(RBNode<Key> node) {
        // node.left.color == RED
        RBNode<Key> root = node.left;
        node.left = root.right;
        root.right = node;
        root.color = node.color;
        node.color = RBNode.RED;
        return root;
    }

    private static <Key extends Comparable<Key>> RBNode<Key> moveRedLeft(RBNode<Key> node) {
        // node.left != null && !isRed(node.left) && !isRed(node.left.left)
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private static <Key extends Comparable<Key>> RBNode<Key> moveRedRight(RBNode<Key> node) {
        // node.right != null && !isRed(node.right) && !isRed(node.right.left)
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private static <Key extends Comparable<Key>> boolean isRed(RBNode<Key> node) {
        if (node == null) {
            return false;
        }
        return node.color == RBNode.RED;
    }

    private static <Key extends Comparable<Key>> void flipColors(RBNode<Key> node) {
        // assert node.color == RED && node.left.color == BLACK && node.right.color == BLACK or
        // assert node.color == BLACK && node.left.color == RED && node.right.color == RED
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private static <Key extends Comparable<Key>> RBNode<Key> balance(RBNode<Key> node) {
        if (!isRed(node.left) && isRed(node.right)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private static class RBNode<Key extends Comparable<Key>> {

        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private final Key key;
        private RBNode<Key> left;
        private RBNode<Key> right;
        private boolean color;

        private RBNode(Key k) {
            key = k;
        }

        private RBNode(Key k, boolean c) {
            key = k;
            color = c;
        }

        private RBNode(Key k, RBNode<Key> l, RBNode<Key> r, boolean c) {
            key = k;
            left = l;
            right = r;
            color = c;
        }
    }

    public static <Key extends Comparable<Key>> int height(RBNode<Key> root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /***************************************************************************
     *  Check integrity of red-black tree data structure.
     ***************************************************************************/
    public static <Key extends Comparable<Key>> boolean check(RBNode<Key> root) {
        if (!isBST(root)) {
            StdOut.println("Not in symmetric order");
        }
        if (!is23(root)) {
            StdOut.println("Not a 2-3 tree");
        }
        if (!isBalanced(root)) {
            StdOut.println("Not balanced");
        }
        return isBST(root) && is23(root) && isBalanced(root);
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private static <Key extends Comparable<Key>> boolean isBST(RBNode<Key> root) {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private static <Key extends Comparable<Key>> boolean isBST(RBNode<Key> x, Key min, Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private static <Key extends Comparable<Key>> boolean is23(RBNode<Key> root) {
        return is23(root, root);
    }

    private static <Key extends Comparable<Key>> boolean is23(RBNode<Key> x, RBNode<Key> root) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)) {
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }

    // do all paths from root to leaf have same number of black edges?
    private static <Key extends Comparable<Key>> boolean isBalanced(RBNode<Key> root) {
        int black = 0;     // number of black links on path from root to min
        RBNode<Key> x = root;
        while (x != null) {
            if (!isRed(x)) {
                black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private static <Key extends Comparable<Key>> boolean isBalanced(RBNode<Key> x, int black) {
        if (x == null) {
            return black == 0;
        }
        if (!isRed(x)) {
            black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /**
     * Unit tests the {@code LLRBOperations} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            RBNode<Integer> root = null;

            for (int j = 0; j < 1000; j++) {
                int num = random.nextInt(1000);
                root = add(root, num);
                assertTrue(contains(root, num));
                assertTrue(check(root));
            }

            int minHeight = (int) Math.ceil(Math.log(1000) / Math.log(2));
            int maxHeight = (int) Math.floor(Math.log(1000) / Math.log(3)) * 2 + 1;
            assertTrue(height(root) >= minHeight);
            assertTrue(height(root) <= maxHeight);

            if (random.nextBoolean()) {
                int min = minKey(root);
                assertTrue(contains(root, min));
                root = removeMin(root);
                assertFalse(contains(root, min));
            } else {
                int max = maxKey(root);
                assertTrue(contains(root, max));
                root = removeMax(root);
                assertFalse(contains(root, max));
            }

            for (int j = 0; j < 1000; j++) {
                int num = random.nextInt(1000);
                root = remove(root, num);
                assertFalse(contains(root, num));
                assertTrue(check(root));
            }
        }

        Integer[] keys = new Integer[]{1, 2, 3, 4, 5, 6};
        List<Integer> keysList = Arrays.asList(keys);
        for (int i = 0; i < 1000; i++) {
            RBNode<Integer> root = null;

            Collections.shuffle(keysList, random);
            for (int j : keysList) {
                root = add(root, j);
                assertTrue(check(root));
            }

            Collections.shuffle(keysList, random);
            for (int j : keysList) {
                root = remove(root, j);
                assertTrue(check(root));
            }
        }
    }

}
