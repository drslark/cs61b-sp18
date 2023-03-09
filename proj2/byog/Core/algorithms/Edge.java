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
     *
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
     *
     * @return Vertex v.
     */
    public int getV() {
        return v;
    }

    /**
     * Gets vertex w.
     *
     * @return Vertex w.
     */
    public int getW() {
        return w;
    }

    /**
     * Gets one vertex.
     *
     * @return One vertex.
     */
    public int either() {
        return v;
    }

    /**
     * Gets the other vertex.
     *
     * @return The other vertex.
     */
    public int other() {
        return w;
    }

    /**
     * Gets the other vertex out of the given one.
     *
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
    public int compareTo(Edge that) {
        return Double.compare(weight, that.weight);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof Edge)) {
            return false;
        }

        Edge thatEdge = (Edge) that;

        return w == thatEdge.w
            && v == thatEdge.v
            && weight == thatEdge.weight;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
