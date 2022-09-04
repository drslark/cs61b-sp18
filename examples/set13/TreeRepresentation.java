public class TreeRepresentation {

    public static class Tree1A<Key> {
        Key k;
        Tree1A<Key> left;
        Tree1A<Key> middle;
        Tree1A<Key> right;
    }

    public static class Tree1B<Key> {
        Key k;
        Tree1B<Key>[] children;
    }

    public static class Tree1C<Key> {
        Key k;
        Tree1C<Key> favoredChild;
        Tree1C<Key> sibling;
    }

    public static class Tree2<Key> {
        Key[] keys;
        Tree2<Key> parents;
    }

    /* Only works for complete trees */
    public static class Tree3<Key> {
        Key[] keys;
    }

}
