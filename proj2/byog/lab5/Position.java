package byog.lab5;

/** The position of the hexagon. */
class Position {

    final int x;
    final int y;

    Position(int offsetX, int offsetY) {
        x = offsetX;
        y = offsetY;
    }

    /**
     * Adds another position to this one.
     *
     * @param other Other position.
     * @return A position sum.
     */
    Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

}
