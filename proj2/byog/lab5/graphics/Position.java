package byog.lab5.graphics;

/** The position of the hexagon. */
public class Position {

    private final int x;
    private final int y;

    public Position(int X, int Y) {
        x = X;
        y = Y;
    }

    /**
     * Gets coordinate x.
     * @return Coordinate x.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets coordinate y.
     * @return Coordinate y.
     */
    public int getY() {
        return y;
    }

    /**
     * Adds another position to this one.
     *
     * @param other Other position.
     * @return A position sum.
     */
    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

}
