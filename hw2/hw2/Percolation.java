package hw2;

import java.util.ArrayList;
import java.util.List;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Percolation {

    private final int worldSize;

    private final byte[] status;

    private final WeightedQuickUnionUF disjointSets;

    private boolean isPercolated;

    private int numOpenSites;

    private final int[][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Invalid N!");
        }
        worldSize = N;
        status = new byte[N * N];
        disjointSets = new WeightedQuickUnionUF(N * N);
        isPercolated = false;
        numOpenSites = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);
        if (isOpen(row, col)) {
            return;
        }

        int idx = location2idx(row, col);
        List<int[]> neighbours = getNeighbours(row, col);

        if (isInTop(row)) {
            status[idx] |= 0b10;
        }
        if (isInBottom(row)) {
            status[idx] |= 0b1;
        }
        status[idx] |= 0b100;

        byte componentStatus = status[idx];

        for (int[] neighbour : neighbours) {
            if (!isValidSize(neighbour[0]) || !isValidSize(neighbour[1])) {
                continue;
            }
            if (!isOpen(neighbour[0], neighbour[1])) {
                continue;
            }

            int neighbourIdx = location2idx(neighbour[0], neighbour[1]);

            componentStatus |= status[disjointSets.find(neighbourIdx)];
            disjointSets.union(idx, neighbourIdx);
        }

        status[disjointSets.find(idx)] |= componentStatus;

        if ((status[disjointSets.find(idx)] & 0b11) == 0b11) {
            isPercolated = true;
        }
        numOpenSites += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);

        int idx = location2idx(row, col);
        return (status[disjointSets.find(idx)] & 0b100) == 0b100;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);

        int idx = location2idx(row, col);
        return (status[disjointSets.find(idx)] & 0b10) == 0b10;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    private List<int[]> getNeighbours(int row, int col) {
        List<int[]> neighbours = new ArrayList<>();

        for (int[] offset : offsets) {
            int[] neighbour = {row + offset[0], col + offset[1]};
            neighbours.add(neighbour);
        }

        return neighbours;
    }

    private int location2idx(int row, int col) {
        return row * worldSize + col;
    }

    private boolean isValidSize(int x) {
        return x >= 0 && x < worldSize;
    }

    private void validateInput(int row, int col) {
        for (int x : new int[]{row, col}) {
            if (!isValidSize(x)) {
                throw new IndexOutOfBoundsException("Input out of range!");
            }
        }
    }

    private boolean isInTop(int row) {
        return row == 0;
    }

    private boolean isInBottom(int row) {
        return row == worldSize - 1;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        percolation.open(0, 0);
        percolation.open(3, 0);
        assertTrue(percolation.isOpen(0, 0));
        assertTrue(percolation.isFull(0, 0));
        assertTrue(percolation.isOpen(3, 0));
        assertFalse(percolation.isFull(3, 0));
        assertFalse(percolation.percolates());

        percolation.open(1, 0);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        assertTrue(percolation.percolates());
        assertEquals(7, percolation.numberOfOpenSites());
    }
}
