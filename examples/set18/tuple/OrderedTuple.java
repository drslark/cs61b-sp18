package tuple;

/**
 * It is used to compare ordered tuples.
 */
public class OrderedTuple {

    private static final int HASH_BASE = 1201;

    private final int X;
    private final int Y;

    public OrderedTuple(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof OrderedTuple)) {
            return false;
        }

        OrderedTuple otherTuple = (OrderedTuple) other;
        return X == otherTuple.X && Y == otherTuple.Y;
    }

    @Override
    public int hashCode() {
        return HASH_BASE * X + Y;
    }
}
