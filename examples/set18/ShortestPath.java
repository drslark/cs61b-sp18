import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edge.DirectedWeightedEdge;
import graph.DirectedGraph;
import pq.ExtrinsicUniqueMinPQ;
import tuple.OrderedTuple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Algorithms to get the shortest path.
 */
public class ShortestPath {

    /**
     * Dijkstra algorithm is a single-source multi-target shortest-path algorithm.
     */
    public static class Dijkstra {

        private final DirectedGraph G;
        private final int S;

        private final double[] distTo;
        private final int[] pathTo;

        public Dijkstra(DirectedGraph graph, int source) {
            G = graph;
            S = source;

            distTo = new double[G.V()];
            pathTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++) {
                distTo[v] = Double.POSITIVE_INFINITY;
                pathTo[v] = -1;
            }
            distTo[S] = 0.0;
            pathTo[S] = S;

            search();
        }

        private void search() {
            ExtrinsicUniqueMinPQ<Integer> fringe = new ExtrinsicUniqueMinPQ<>();
            fringe.add(S, distTo[S]);

            while (fringe.size() > 0) {
                int v = fringe.removeMin();

                for (DirectedWeightedEdge edge : G.adj(v)) {
                    relax(edge, fringe);
                }
            }
        }

        private void relax(DirectedWeightedEdge edge, ExtrinsicUniqueMinPQ<Integer> fringe) {
            int from = edge.from();
            int to = edge.to();

            if (distTo[to] <= distTo[from] + edge.weight()) {
                return;
            }

            distTo[to] = distTo[from] + edge.weight();
            pathTo[to] = from;
            if (fringe.contains(to)) {
                fringe.changePriority(to, distTo[to]);
            } else {
                fringe.add(to, distTo[to]);
            }
        }

        public double getDist(int t) {
            return distTo[t];
        }

        public List<Integer> getPath(int t) {
            if (pathTo[t] == -1) {
                return null;
            }

            LinkedList<Integer> path = new LinkedList<>();

            while (t != S) {
                path.addFirst(t);
                t = pathTo[t];
            }
            path.addFirst(S);

            return path;
        }

    }

    /**
     * AStar algorithm is a single-source single-target shortest-path algorithm.
     */
    public static class AStar {

        private final DirectedGraph G;
        private final int S;
        private final int T;
        private final double[] H;

        private final double[] D;
        private final int[] pathTo;

        public AStar(DirectedGraph graph, int source, int target, double[] heuristic) {
            if (graph.V() != heuristic.length) {
                throw new IllegalArgumentException("The graph and the heuristic mapping must be consistent!");
            }

            G = graph;
            S = source;
            T = target;
            H = heuristic;

            D = new double[G.V()];
            pathTo = new int[G.V()];
            for (int v = 0; v < G.V(); v++) {
                D[v] = Double.POSITIVE_INFINITY;
                pathTo[v] = -1;
            }
            D[S] = 0.0;
            pathTo[S] = S;

            search();
        }

        private void search() {
            ExtrinsicUniqueMinPQ<Integer> fringe = new ExtrinsicUniqueMinPQ<>();
            fringe.add(S, D[S] + H[S]);

            while (fringe.size() > 0) {
                int v = fringe.removeMin();
                if (v == T) {
                    break;
                }

                for (DirectedWeightedEdge edge : G.adj(v)) {
                    relax(edge, fringe);
                }
            }
        }

        private void relax(DirectedWeightedEdge edge, ExtrinsicUniqueMinPQ<Integer> fringe) {
            int from = edge.from();
            int to = edge.to();

            if (D[to] <= D[from] + edge.weight()) {
                return;
            }

            D[to] = D[from] + edge.weight();
            pathTo[to] = from;
            if (fringe.contains(to)) {
                fringe.changePriority(to, D[to] + H[to]);
            } else {
                fringe.add(to, D[to] + H[to]);
            }
        }

        public double getDist() {
            return D[T];
        }

        public List<Integer> getPath() {
            if (pathTo[T] == -1) {
                return null;
            }

            LinkedList<Integer> path = new LinkedList<>();

            int t = T;
            while (t != S) {
                path.addFirst(t);
                t = pathTo[t];
            }
            path.addFirst(S);

            return path;
        }

    }

    private static DirectedGraph randomGraph(Random random) {
        int v = 5 + random.nextInt(11);
        int e = v * (1 + random.nextInt(v));

        HashSet<OrderedTuple> existedTuples = new HashSet<>();
        DirectedGraph graph = new DirectedGraph(v);
        for (int i = 0; i < e; i++) {
            int from = random.nextInt(v);
            int to = random.nextInt(v);
            double weight = random.nextDouble();

            OrderedTuple tuple = new OrderedTuple(from, to);
            if (existedTuples.contains(tuple)) {
                continue;
            }
            existedTuples.add(tuple);

            DirectedWeightedEdge edge = new DirectedWeightedEdge(from, to, weight);
            graph.addEdge(edge);
        }

        return graph;
    }

    private static void checkDijkstra(Dijkstra dijkstra, DirectedGraph graph) {
        for (int v = 0; v < graph.V(); v++) {
            if (dijkstra.getDist(v) == Double.POSITIVE_INFINITY) {
                assertNull(dijkstra.getPath(v));
            } else {
                assertNotNull(dijkstra.getPath(v));
                assertTrue(dijkstra.getPath(v).size() > 0);
            }

            for (DirectedWeightedEdge edge : graph.adj(v)) {
                double fromDist = dijkstra.getDist(edge.from());
                double toDist = dijkstra.getDist(edge.to());
                assertTrue(toDist <= fromDist + edge.weight());
            }
        }
    }

    private static void testRandomDijkstra(Random random) {
        for (int i = 0; i < 50; i++) {
            DirectedGraph graph = randomGraph(random);
            int source = random.nextInt(graph.V());

            Dijkstra dijkstra = new Dijkstra(graph, source);
            checkDijkstra(dijkstra, graph);
        }
    }

    private static DirectedGraph specificGraph() {
        DirectedGraph graph = new DirectedGraph(7);
        graph.addEdge(new DirectedWeightedEdge(0, 1, 2.0));
        graph.addEdge(new DirectedWeightedEdge(0, 2, 1.0));
        graph.addEdge(new DirectedWeightedEdge(1, 2, 5.0));
        graph.addEdge(new DirectedWeightedEdge(1, 3, 11.0));
        graph.addEdge(new DirectedWeightedEdge(1, 4, 3.0));
        graph.addEdge(new DirectedWeightedEdge(2, 5, 15.0));
        graph.addEdge(new DirectedWeightedEdge(3, 4, 2.0));
        graph.addEdge(new DirectedWeightedEdge(4, 2, 1.0));
        graph.addEdge(new DirectedWeightedEdge(4, 5, 4.0));
        graph.addEdge(new DirectedWeightedEdge(4, 6, 5.0));
        graph.addEdge(new DirectedWeightedEdge(6, 3, 1.0));
        graph.addEdge(new DirectedWeightedEdge(6, 5, 1.0));

        return graph;
    }

    private static double[] generateHeuristic(DirectedGraph graph, int target) {
        double[] heuristic = new double[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
            heuristic[v] = vertexHeuristic(v, graph, target);
        }

        return heuristic;
    }

    private static double vertexHeuristic(int v, DirectedGraph graph, int target) {
        if (v == target) {
            return 0.0;
        }

        double minWeight = Double.POSITIVE_INFINITY;

        for (DirectedWeightedEdge edge : graph.adj(v)) {
            if (edge.weight() < minWeight) {
                minWeight = edge.weight();
            }
        }

        return minWeight;
    }

    private static void checkAStar(AStar aStar, Dijkstra dijkstra, int target) {
        if (aStar.getDist() == Double.POSITIVE_INFINITY) {
            assertNull(aStar.getPath());
        } else {
            assertNotNull(aStar.getPath());
            assertTrue(aStar.getPath().size() > 0);
        }

        assertEquals(dijkstra.getDist(target), aStar.getDist(), 0.000001);
    }

    private static void checkHeuristic(double[] heuristic, DirectedGraph graph, int target) {
        double[] distFrom = new double[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
            Dijkstra dijkstra = new Dijkstra(graph, v);
            distFrom[v] = dijkstra.getDist(target);
        }

        for (int v = 0; v < graph.V(); v++) {
            assertTrue(heuristic[v] <= distFrom[v]);

            for (DirectedWeightedEdge edge : graph.adj(v)) {
                int w = edge.to();
                assertTrue(heuristic[v] <= heuristic[w] + edge.weight());
            }
        }
    }

    private static void testSpecificAStar() {
        int source = 0;
        int target = 6;

        DirectedGraph graph = specificGraph();
        double[] heuristic = generateHeuristic(graph, target);

        AStar aStar = new AStar(graph, source, target, heuristic);

        List<Integer> path = Arrays.asList(0, 1, 4, 6);
        assertEquals(10.0, aStar.getDist(), 0.000001);
        assertEquals(path, aStar.getPath());
    }

    private static void testRandomAStar(Random random) {
        for (int i = 0; i < 50; i++) {
            DirectedGraph graph = randomGraph(random);
            int source = random.nextInt(graph.V());
            int target = random.nextInt(graph.V());
            double[] heuristic = generateHeuristic(graph, target);
            checkHeuristic(heuristic, graph, target);

            AStar aStar = new AStar(graph, source, target, heuristic);
            Dijkstra dijkstra = new Dijkstra(graph, source);

            checkAStar(aStar, dijkstra, target);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        testRandomDijkstra(random);
        testSpecificAStar();
        testRandomAStar(random);
    }

}
