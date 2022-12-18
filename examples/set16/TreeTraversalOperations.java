public class TreeTraversalOperations {

    static class Node {

        char key;

        Node left;
        Node right;

        Node(char key) {
            this.key = key;
        }

        Node(char key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }

    }

    public static void levelOrder(Node root) {
        int h = height(root);
        for (int i = 0; i < h; i++) {
            visitLevel(root, i);
        }
    }

    private static void visitLevel(Node root, int level) {
        if (root == null) {
            return;
        }

        if (level <= 0) {
            System.out.print(' ');
            System.out.print(root.key);
        } else {
            visitLevel(root.left, level - 1);
            visitLevel(root.right, level - 1);
        }
    }

    public static int height(Node root) {
        if (root == null) {
            return 0;
        }

        int lHeight = height(root.left);
        int rHeight = height(root.right);
        return Math.max(lHeight, rHeight) + 1;
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(' ');
        System.out.print(root.key);

        preOrder(root.left);
        preOrder(root.right);
    }

    public static void InOrder(Node root) {
        if (root == null) {
            return;
        }

        preOrder(root.left);

        System.out.print(' ');
        System.out.print(root.key);

        preOrder(root.right);
    }

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }

        preOrder(root.left);
        preOrder(root.right);

        System.out.print(' ');
        System.out.print(root.key);
    }

    public static void main(String[] args) {
        Node left = new Node('A');
        Node right = new Node('C');
        Node root = new Node('B', left, right);
        left = new Node('E');
        right = new Node('G');
        right = new Node('F', left, right);
        left = root;
        root = new Node('D', left, right);

        System.out.print("Level-order traversal:");
        levelOrder(root);
        System.out.println();

        System.out.print("Pre-order traversal:");
        preOrder(root);
        System.out.println();

        System.out.print("In-order traversal:");
        InOrder(root);
        System.out.println();

        System.out.print("Post-order traversal:");
        postOrder(root);
        System.out.println();
    }

}
