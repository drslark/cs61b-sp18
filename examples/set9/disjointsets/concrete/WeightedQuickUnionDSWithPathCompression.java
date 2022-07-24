package disjointsets.concrete;

import disjointsets.view.DisjointSets;

public class WeightedQuickUnionDSWithPathCompression implements DisjointSets {

    private final int[] parent;

    /* Creates a WeightedQuickUnionDSWithPathCompression data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public WeightedQuickUnionDSWithPathCompression(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex!");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    @Override
    public int sizeOf(int v1) {
        return -parent[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (root1 == root2) {
            return;
        }

        if (parent[root1] >= parent[root2]) {
            parent[root2] += parent[root1];
            parent[root1] = root2;
        } else {
            parent[root1] += parent[root2];
            parent[root2] = root1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);

        int root = vertex;
        // loop variant: root >= 0 && root < parent.length && vertex..child of root visited
        while (parent[root] >= 0) {
            root = parent[root];
        }

        // loop variant: vertex >= 0 && vertex < parent.length && start..child of vertex visited
        while (parent[vertex] >= 0) {
            int nextVertex = parent[vertex];
            parent[vertex] = root;
            vertex = nextVertex;
        }

        return root;
    }

    @Override
    public void connect(int p, int q) {
        union(p, q);
    }

    @Override
    public boolean isConnected(int p, int q) {
        return connected(p, q);
    }
}
