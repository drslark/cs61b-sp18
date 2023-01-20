package edge;

import java.util.Comparator;

/**
 * Undirected Edge with a weight.
 */
public class UndirectedWeightedEdge {

    private static final int HASH_BASE = 1201;

    private final int one;
    private final int another;

    private final double weight;

    public UndirectedWeightedEdge(int i, int j, double w) {
        if (i <= j) {
            one = i;
            another = j;
        } else {
            one = j;
            another = i;
        }

        weight = w;
    }

    public int either() {
        return one;
    }

    public int other(int v) {
        if (v == one) {
            return another;
        }
        return one;
    }

    public double weight() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UndirectedWeightedEdge)) {
            return false;
        }

        UndirectedWeightedEdge otherEdge = (UndirectedWeightedEdge) other;
        return one == otherEdge.one && another == otherEdge.another;
    }

    @Override
    public int hashCode() {
        return HASH_BASE * HASH_BASE * one + HASH_BASE * another + Double.hashCode(weight);
    }

    public static class WeightComparator implements Comparator<UndirectedWeightedEdge> {

        @Override
        public int compare(UndirectedWeightedEdge edge, UndirectedWeightedEdge otherEdge) {
            double diff = edge.weight() - otherEdge.weight();

            if (diff < 0) {
                return -1;
            } else if (diff == 0) {
                return 0;
            } else {
                return 1;
            }
        }

    }

}
