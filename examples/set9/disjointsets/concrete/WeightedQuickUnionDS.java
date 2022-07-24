package disjointsets.concrete;

import disjointsets.view.DisjointSets;

public class WeightedQuickUnionDS implements DisjointSets {

    private final int[] parent;

    private final int[] size;

    public WeightedQuickUnionDS(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    @Override
    public void connect(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        if (size[pRoot] <= size[qRoot]) {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
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

        return -size[p];
    }

    @Override
    public int sizeOf(int p) {
        int pRoot = find(p);
        return size[pRoot];
    }

    private int find(int p) {
        // loop variant: p >= 0 && p < parent.length && parent[p] >= 0 && start..child of p visited
        while (parent[p] != p) {
            p = parent[p];
        }
        return p;
    }

}
