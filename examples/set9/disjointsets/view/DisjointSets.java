package disjointsets.view;

public interface DisjointSets {

    // connects two items p and q
    void connect(int p, int q);

    // checks to see if two items are connected
    boolean isConnected(int p, int q);

    // Returns the parent of p. If p is the root of a tree, returns the
    // negative size of the tree for which p is the root.
    int parent(int p);

    // Returns the size of set p belongs to.
    int sizeOf(int p);

    // Returns the height of the vertex
    default int heightOf(int p) {
        int h = 0;

        // loop invariants: p >= 0 && p < vertex number && h = number of start..child of p
        while (parent(p) >= 0) {
            h += 1;
            p = parent(p);
        }

        return h;
    }

}
