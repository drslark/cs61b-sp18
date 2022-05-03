package byog.lab5.graphics;

/**
 * Hexagon's location and shape information.
 */
public class Hexagon {

    /**
     * Upper bound and lower bound.
     */
    public static class Bounds {

        private final int upper;
        private final int lower;

        Bounds(int l, int u) {
            lower = l;
            upper = u;
        }

        /**
         * Gets the upper bound.
         * @return The upper bound.
         */
        public int getUpper() {
            return upper;
        }

        /**
         * Gets the lower bound.
         * @return The lower bound.
         */
        public int getLower() {
            return lower;
        }

    }

    private final int size;
    private final int width;
    private final int height;

    public Hexagon(int hexagonSize) {
        size = hexagonSize;
        width = 3 * hexagonSize - 2;
        height = 2 * hexagonSize;
    }

    /**
     * Gets the size.
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the width.
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the bounds of the column.
     *
     * @param x The column's index.
     * @return Column's bounds.
     */
    public Bounds getColumnBounds(int x) {

        if (x < size) {
            return new Bounds(size - 1 - x, height - (size - 1 - x));
        }

        if (x < 2 * size - 1) {
            return new Bounds(0, height);
        }

        return new Bounds(x - (2 * size - 2), height - (x - (2 * size - 2)));
    }

    /**
     * Gets the bounds of the row.
     *
     * @param y The row's index.
     * @return Row's bounds.
     */
    public Bounds getRowBounds(int y) {

        if (y < size) {
            return new Bounds(size - 1 - y, width - (size - 1 - y));
        }

        return new Bounds(y - size, width - (y - size));
    }

    public Position topRightOffset() {
        return new Position(2 * size - 1, size);
    }

    public Position bottomRightOffset() {
        return new Position(2 * size - 1, -size);
    }

}
