package graph;

import java.util.ArrayList;
import java.util.List;

import edge.DirectedWeightedEdge;

/**
 * Directed Graph represented by an adjacency list.
 */
public class DirectedGraph {

    private final int V;
    private final List<DirectedWeightedEdge>[] adjacencyEdges;

    public DirectedGraph(int v) {
        V = v;
        adjacencyEdges = (List<DirectedWeightedEdge>[]) new List[V];
        for (int i = 0; i < V; i++) {
            adjacencyEdges[i] = new ArrayList<>();
        }
    }

    public int V() {
        return V;
    }

    public void addEdge(DirectedWeightedEdge edge) {
        int from = edge.from();
        adjacencyEdges[from].add(edge);
    }

    public List<DirectedWeightedEdge> adj(int f) {
        return adjacencyEdges[f];
    }

}
