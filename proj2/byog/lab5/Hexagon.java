package byog.lab5;

/**
 * Hexagon's location and shape information.
 */
class Hexagon {

    /**
     * Upper bound and lower bound.
     */
    static class Bounds {

        final int upper;
        final int lower;

        Bounds(int l, int u) {
            lower = l;
            upper = u;
        }

    }

    final int size;
    final int width;
    final int height;

    Hexagon(int hexagonSize) {
        size = hexagonSize;
        width = 3 * hexagonSize - 2;
        height = 2 * hexagonSize;
    }

    /**
     * Gets the bounds of the column.
     *
     * @param x The column's index.
     * @return Column's bounds.
     */
    Bounds getColumnBounds(int x) {

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
    Bounds getRowBounds(int y) {

        if (y < size) {
            return new Bounds(size - 1 - y, width - (size - 1 - y));
        }

        return new Bounds(y - size, width - (y - size));
    }

    Position topRightOffset() {
        return new Position(2 * size - 1, size);
    }

    Position bottomRightOffset() {
        return new Position(2 * size - 1, -size);
    }

}
