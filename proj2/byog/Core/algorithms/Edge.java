package byog.Core.algorithms;

import java.util.Objects;

/**
 * A weighted edge object.
 */
public class Edge implements Comparable<Edge> {

    private final int hashCode;

    private final int w;
    private final int v;
    private final double weight;

    /**
     * Constructor with vertexes and weight.
     * @param w Some vertex.
     * @param v The other vertex.
     * @param weight The weight.
     */
    public Edge(int w, int v, double weight) {
        if (w < v) {
            this.w = w;
            this.v = v;
        } else {
            this.w = v;
            this.v = w;
        }

        this.weight = weight;

        hashCode = Objects.hash(w, v, weight);
    }

    /**
     * Gets vertex v.
     * @return Vertex v.
     */
    public int getV() {
        return v;
    }

    /**
     * Gets vertex w.
     * @return Vertex w.
     */
    public int getW() {
        return w;
    }

    /**
     * Gets some vertex.
     * @return Some vertex.
     */
    public int either() {
        return v;
    }

    /**
     * Gets the other vertex.
     * @return The other vertex.
     */
    public int other() {
        return w;
    }

    /**
     * Gets the other vertex out of the given one.
     * @param vertex The given vertex.
     * @return The other vertex.
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        }

        if (vertex == w) {
            return v;
        }

        throw new RuntimeException("Invalid vertex!");
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(weight, other.weight);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Edge)) {
            return false;
        }

        Edge otherEdge = (Edge) other;

        return w == otherEdge.w
            && v == otherEdge.v
            && weight == otherEdge.weight;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
