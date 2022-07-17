package byog.Core.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The prim algorithm to find the nearest vertex pairs.
 */
public class Prim {

    private final ArrayList<Edge> mst;
    private final boolean[] marked;
    private final PriorityQueue<Edge> pq;

    /**
     * Constructor with a graph in the form of adjacency list.
     * @param graph A graph in the form of adjacency list.
     */
    public Prim(AdjacencyListGraph graph) {
        mst = new ArrayList<>(graph.V());
        marked = new boolean[graph.V()];
        pq = new PriorityQueue<>();

        visit(graph, 0);
        while (mst.size() < graph.V() - 1) {
            // find the nearest vertex
            int vertex = nearestVertex();
            // visit all edges of the nearest vertex
            visit(graph, vertex);
        }
    }

    /**
     * Gets the edges in order.
     * @return The edges in order.
     */
    public List<Edge> sortedEdges() {
        List<Edge> edges = new ArrayList<>(mst);
        Collections.sort(edges);
        return edges;
    }

    private void visit(AdjacencyListGraph graph, int v) {
        marked[v] = true;
        for (Edge edge : graph.adjacent(v)) {
            int other = edge.other(v);
            if (!marked[other]) {
                pq.add(edge);
            }
        }
    }

    private int nearestVertex() {
        while (true) {
            Edge edge = pq.poll();
            int w = edge.either();
            int v = edge.other(w);
            if (!marked[w]) {
                mst.add(edge);
                return w;
            }
            if (!marked[v]) {
                mst.add(edge);
                return v;
            }
        }
    }

}
