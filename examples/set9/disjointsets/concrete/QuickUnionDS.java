package disjointsets.concrete;

import disjointsets.view.DisjointSets;

public class QuickUnionDS implements DisjointSets {

    private final int[] parent;

    public QuickUnionDS(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    @Override
    public void connect(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        parent[pRoot] = qRoot;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int parent(int p) {
        if (parent[p] != p) {
            return parent[p];
        }

        return -sizeOf(p);
    }

    @Override
    public int sizeOf(int p) {
        int pRoot = find(p);

        int size = 0;
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == pRoot) {
                size += 1;
            }
        }

        return size;
    }

    private int find(int p) {
        // loop variant: p >= 0 && p < parent.length && parent[p] >= 0 && start..child of p visited
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }

}
