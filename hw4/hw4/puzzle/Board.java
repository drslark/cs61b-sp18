package hw4.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Board implements WorldState {

    private static final int BLANK = 0;

    private final int[][] tiles;

    private Integer cachedHamming;
    private Integer cachedManhattan;

    private Integer cachedHash;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j.
     */
    public Board(int[][] tiles) {
        if (tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("Tiles should be an N-by-N array!");
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] < 0 || tiles[i][j] >= tiles.length * tiles.length) {
                    throw new IllegalArgumentException(
                        "Each tile value should be greater than or equal to 0 and less than N-by-N!"
                    );
                }
            }
        }

        this.tiles = copyTiles(tiles);
        this.cachedHamming = null;
        this.cachedManhattan = null;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank).
     */
    public int tileAt(int i, int j) {
        if (!isValidLoc(i, j)) {
            throw new IndexOutOfBoundsException("Both i and j should be between 0 and N − 1!");
        }

        return tiles[i][j];
    }

    /**
     * Returns the board size N.
     */
    public int size() {
        return tiles.length;
    }

    /**
     * Returns the neighbors of the current board.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> neighbourStates = new ArrayList<>();

        int[] blankLoc = tileLoc(BLANK);
        int[][] offsets = new int[][]{
            {0, -1}, {0, 1}, {-1, 0}, {1, 0}
        };
        
        for (int[] offset : offsets) {
            int[] neighbourLoc = new int[]{
                blankLoc[0] + offset[0], blankLoc[1] + offset[1]
            };
            
            if (!isValidLoc(neighbourLoc[0], neighbourLoc[1])) {
                continue;
            }

            move(blankLoc, neighbourLoc);
            neighbourStates.add(new Board(tiles));
            move(neighbourLoc, blankLoc);
        }

        return neighbourStates;
    }

    /**
     * Hamming estimate.
     */
    public int hamming() {
        if (cachedHamming == null) {
            cachedHamming = 0;
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (tileAt(i, j) == BLANK) {
                        continue;
                    }

                    int[] tileGoalLoc = goalLoc(tileAt(i, j));

                    if (tileGoalLoc[0] != i || tileGoalLoc[1] != j) {
                        cachedHamming += 1;
                    }
                }
            }
        }

        return cachedHamming;
    }

    /**
     * Manhattan estimate.
     */
    public int manhattan() {
        if (cachedManhattan == null) {
            cachedManhattan = 0;
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (tileAt(i, j) == BLANK) {
                        continue;
                    }

                    int[] tileGoalLoc = goalLoc(tileAt(i, j));

                    cachedManhattan += Math.abs(tileGoalLoc[0] - i);
                    cachedManhattan += Math.abs(tileGoalLoc[1] - j);
                }
            }
        }

        return cachedManhattan;
    }

    /**
     * Estimated distance to goal.
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's.
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board yBoard = (Board) y;

        return Arrays.deepEquals(tiles, yBoard.tiles);
    }

    /**
     * Returns the hash-code of the board.
     */
    @Override
    public int hashCode() {
        if (cachedHash == null) {
            cachedHash = Arrays.deepHashCode(tiles);
        }

        return cachedHash;
    }

    /**
     * Returns the string representation of the board.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    private int[] tileLoc(int tile) {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == tile) {
                    return new int[]{i, j};
                }
            }
        }

        throw new RuntimeException("No blank square in the puzzle!");
    }

    private int[] goalLoc(int tile) {
        if (tile == 0) {
            return new int[]{size() - 1, size() - 1};
        }

        int[] loc = new int[2];

        loc[0] = (tile - 1) / size();
        loc[1] = (tile - 1) % size();

        return loc;
    }

    private boolean isValidLoc(int i, int j) {
        if (i < 0 || i >= size()) {
            return false;
        }

        if (j < 0 || j >= size()) {
            return false;
        }

        return true;
    }
    
    private void move(int[] blankLoc, int[] neighbourLoc) {
        tiles[blankLoc[0]][blankLoc[1]] = tileAt(neighbourLoc[0], neighbourLoc[1]);
        tiles[neighbourLoc[0]][neighbourLoc[1]] = 0;
    }

    private static int[][] copyTiles(int[][] tiles) {
        int[][] newTiles = new int[tiles.length][tiles[0].length];

        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, newTiles[i], 0, tiles[i].length);
        }

        return newTiles;
    }

}
