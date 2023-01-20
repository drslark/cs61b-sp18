package graph;

import java.util.ArrayList;
import java.util.List;

import edge.UndirectedWeightedEdge;

/**
 * Undirected Graph represented by an adjacency list.
 */
public class UndirectedGraph {

    private final int V;
    private final List<UndirectedWeightedEdge>[] adjacencyEdges;

    public UndirectedGraph(int v) {
        V = v;
        adjacencyEdges = (List<UndirectedWeightedEdge>[]) new List[V];
        for (int i = 0; i < V; i++) {
            adjacencyEdges[i] = new ArrayList<>();
        }
    }

    public int V() {
        return V;
    }

    public void addEdge(UndirectedWeightedEdge edge) {
        int one = edge.either();
        int another = edge.other(one);
        adjacencyEdges[one].add(edge);
        adjacencyEdges[another].add(edge);
    }

    public List<UndirectedWeightedEdge> adj(int f) {
        return adjacencyEdges[f];
    }

}
