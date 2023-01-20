package edge;

/**
 * Directed Edge with a weight.
 */
public class DirectedWeightedEdge {

    private static final int HASH_BASE = 1201;

    private final int from;
    private final int to;

    private final double weight;

    public DirectedWeightedEdge(int f, int t, double w) {
        from = f;
        to = t;
        weight = w;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double weight() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DirectedWeightedEdge)) {
            return false;
        }

        DirectedWeightedEdge otherEdge = (DirectedWeightedEdge) other;
        return from == otherEdge.from && to == otherEdge.to;
    }

    @Override
    public int hashCode() {
        return HASH_BASE * HASH_BASE * from + HASH_BASE * to + Double.hashCode(weight);
    }

}
