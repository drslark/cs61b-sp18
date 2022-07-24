package disjointsets.concrete;

import disjointsets.view.DisjointSets;

public class QuickFindDS implements DisjointSets {

    private final int[] id;

    public QuickFindDS(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public int parent(int p) {
        if (id[p] != p) {
            return id[p];
        }

        return -sizeOf(p);
    }

    @Override
    public int sizeOf(int p) {
        int size = 0;

        int pid = id[p];
        for (int i : id) {
            if (i == pid) {
                size += 1;
            }
        }

        return size;
    }

}
