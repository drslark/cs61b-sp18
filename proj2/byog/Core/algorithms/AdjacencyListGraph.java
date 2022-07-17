package byog.Core.algorithms;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A graph in the form of adjacency list.
 */
public class AdjacencyListGraph {

    private final List<Edge>[] edges;
    private final int v;

    private int e;

    /**
     * Constructor with a graph in the form of adjacency matrix.
     * @param adjacencyMatrixGraph A graph in the form of adjacency matrix.
     */
    public AdjacencyListGraph(int[][] adjacencyMatrixGraph) {
        v = adjacencyMatrixGraph.length;
        edges = (List<Edge>[]) new List[v];

        for (int i = 0; i < v; i++) {
            edges[i] = new LinkedList<>();
        }

        for (int i = 0; i < adjacencyMatrixGraph.length; i++) {
            for (int j = 0; j < adjacencyMatrixGraph[i].length; j++) {
                if (adjacencyMatrixGraph[i][j] > 0) {
                    Edge edge = new Edge(i, j, adjacencyMatrixGraph[i][j]);
                    addEdge(edge);
                }
            }
        }
    }

    /**
     * Gets the number of vertexes.
     * @return The number of vertexes.
     */
    public int V() {
        return v;
    }

    /**
     * Gets the number of edges.
     * @return The number of edges.
     */
    public int E() {
        return e;
    }

    /**
     * Gets the edges which are adjacent to the given vertex.
     * @param vertex A given vertex.
     * @return A collection of edges which are adjacent to the given vertex.
     */
    public Collection<Edge> adjacent(int vertex) {
        return new LinkedList<>(edges[vertex]);
    }

    private void addEdge(Edge edge) {
        edges[edge.getV()].add(edge);
        edges[edge.getW()].add(edge);
        e += 1;
    }

}
