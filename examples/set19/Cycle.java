import disjointsets.concrete.WeightedQuickUnionDSWithPathCompression;
import edge.DirectedWeightedEdge;
import edge.UndirectedWeightedEdge;
import graph.DirectedGraph;
import graph.UndirectedGraph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Cycle detection.
 */
public class Cycle {

    public static boolean hasUndirectedCycleDFS(UndirectedGraph graph) {
        boolean[] marked = new boolean[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (marked[v]) {
                continue;
            }

            if (hasUndirectedCycleDFS(graph, v, -1, marked)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasUndirectedCycleDFS(UndirectedGraph graph, int v, int f, boolean[] marked) {
        marked[v] = true;

        for (UndirectedWeightedEdge edge : graph.adj(v)) {
            int w = edge.other(v);

            if (w == f) {
                continue;
            }

            if (marked[w]) {
                return true;
            }

            if (hasUndirectedCycleDFS(graph, w, v, marked)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasUndirectedCycleDS(UndirectedGraph graph) {
        WeightedQuickUnionDSWithPathCompression ds = new WeightedQuickUnionDSWithPathCompression(graph.V());

        for (int v = 0; v < graph.V(); v++) {
            for (UndirectedWeightedEdge edge : graph.adj(v)) {
                int w = edge.other(v);
                if (w < v) {
                    continue;
                }

                if (ds.isConnected(v, w)) {
                    return true;
                }
                ds.connect(v, w);
            }
        }

        return false;
    }

    public static boolean hasDirectedCycleDFS(DirectedGraph graph) {
        boolean[] marked = new boolean[graph.V()];
        boolean[] onPath = new boolean[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (marked[v]) {
                continue;
            }

            if (hasDirectedCycleDFS(graph, v, marked, onPath)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasDirectedCycleDFS(DirectedGraph graph, int v, boolean[] marked, boolean[] onPath) {
        marked[v] = true;
        onPath[v] = true;

        for (DirectedWeightedEdge edge : graph.adj(v)) {
            int w = edge.to();

            if (onPath[w]) {
                return true;
            }

            if (!marked[w] && hasDirectedCycleDFS(graph, w, marked, onPath)) {
                return true;
            }

        }

        onPath[v] = false;
        return false;
    }

    private static UndirectedGraph specificUndirectedGraphWithoutCycle() {
        UndirectedGraph graph = new UndirectedGraph(7);
        graph.addEdge(new UndirectedWeightedEdge(0, 1, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(0, 2, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(1, 3, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(3, 6, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(4, 5, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(5, 6, 1.0));

        return graph;
    }

    private static UndirectedGraph specificUndirectedGraphWithCycle() {
        UndirectedGraph graph = new UndirectedGraph(7);
        graph.addEdge(new UndirectedWeightedEdge(0, 1, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(0, 2, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(1, 3, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(3, 6, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(4, 5, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(4, 6, 1.0));
        graph.addEdge(new UndirectedWeightedEdge(5, 6, 1.0));

        return graph;
    }

    private static DirectedGraph specificDirectedGraphWithoutCycle() {
        DirectedGraph graph = new DirectedGraph(5);
        graph.addEdge(new DirectedWeightedEdge(0, 1, 1.0));
        graph.addEdge(new DirectedWeightedEdge(0, 4, 1.0));
        graph.addEdge(new DirectedWeightedEdge(2, 0, 1.0));
        graph.addEdge(new DirectedWeightedEdge(2, 3, 1.0));
        graph.addEdge(new DirectedWeightedEdge(3, 4, 1.0));

        return graph;
    }

    private static DirectedGraph specificDirectedGraphWithCycle() {
        DirectedGraph graph = new DirectedGraph(4);
        graph.addEdge(new DirectedWeightedEdge(0, 1, 1.0));
        graph.addEdge(new DirectedWeightedEdge(1, 2, 1.0));
        graph.addEdge(new DirectedWeightedEdge(2, 3, 1.0));
        graph.addEdge(new DirectedWeightedEdge(3, 1, 1.0));

        return graph;
    }

    private static void testHasUndirectedCycleDFS() {
        UndirectedGraph graphWithoutCycle = specificUndirectedGraphWithoutCycle();
        assertFalse(hasUndirectedCycleDFS(graphWithoutCycle));

        UndirectedGraph graphWithCycle = specificUndirectedGraphWithCycle();
        assertTrue(hasUndirectedCycleDFS(graphWithCycle));
    }

    private static void testHasUndirectedCycleDS() {
        UndirectedGraph graphWithoutCycle = specificUndirectedGraphWithoutCycle();
        assertFalse(hasUndirectedCycleDS(graphWithoutCycle));

        UndirectedGraph graphWithCycle = specificUndirectedGraphWithCycle();
        assertTrue(hasUndirectedCycleDS(graphWithCycle));
    }

    private static void testHasDirectedCycleDFS() {
        DirectedGraph graphWithoutCycle = specificDirectedGraphWithoutCycle();
        assertFalse(hasDirectedCycleDFS(graphWithoutCycle));

        DirectedGraph graphWithCycle = specificDirectedGraphWithCycle();
        assertTrue(hasDirectedCycleDFS(graphWithCycle));
    }

    public static void main(String[] args) {
        testHasUndirectedCycleDFS();
        testHasUndirectedCycleDS();
        testHasDirectedCycleDFS();
    }

}
