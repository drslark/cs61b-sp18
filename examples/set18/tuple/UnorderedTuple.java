package tuple;

/**
 * It is used to compare unordered tuples.
 */
public class UnorderedTuple {

    private static final int HASH_BASE = 1201;

    private final int X;
    private final int Y;

    public UnorderedTuple(int x, int y) {
        if (x < y) {
            X = x;
            Y = y;
        } else {
            X = y;
            Y = x;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UnorderedTuple)) {
            return false;
        }

        UnorderedTuple otherTuple = (UnorderedTuple) other;
        return X == otherTuple.X && Y == otherTuple.Y;
    }

    @Override
    public int hashCode() {
        return HASH_BASE * X + Y;
    }
}
