import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edge.DirectedWeightedEdge;
import graph.DirectedGraph;
import tuple.OrderedTuple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DirectedAcyclicGraph {

    /**
     * Topological Sort for DAG.
     */
    public static class TopologicalSort {

        private final DirectedGraph DAG;

        private final boolean[] marked;
        private final LinkedList<Integer> topologicalOrder;

        public TopologicalSort(DirectedGraph dag) {
            DAG = dag;

            marked = new boolean[DAG.V()];
            topologicalOrder = new LinkedList<>();

            for (int v = 0; v < DAG.V(); v++) {
                if (marked[v]) {
                    continue;
                }
                search(v);
            }
        }

        private void search(int v) {
            marked[v] = true;

            for (DirectedWeightedEdge edge : DAG.adj(v)) {
                int w = edge.to();
                if (marked[w]) {
                    continue;
                }

                search(w);
            }

            topologicalOrder.addFirst(v);
        }

        public List<Integer> order() {
            return topologicalOrder;
        }

    }

    /**
     * Finds the shortest paths tree for a dag.
     */
    public static class DAGShortestPaths {

        private final DirectedGraph DAG;
        private final int S;

        private final double[] distTo;
        private final int[] pathTo;

        public DAGShortestPaths(DirectedGraph dag, int source) {
            DAG = dag;
            S = source;

            distTo = new double[DAG.V()];
            pathTo = new int[DAG.V()];
            for (int v = 0; v < DAG.V(); v++) {
                distTo[v] = Double.POSITIVE_INFINITY;
                pathTo[v] = -1;
            }
            distTo[S] = 0.0;
            pathTo[S] = 0;

            search();
        }

        private void search() {
            List<Integer> topologicalOrder = new TopologicalSort(DAG).order();

            for (int v : topologicalOrder) {
                for (DirectedWeightedEdge edge : DAG.adj(v)) {
                    relax(edge);
                }
            }
        }

        private void relax(DirectedWeightedEdge edge) {
            int from = edge.from();
            int to = edge.to();

            if (distTo[to] <= distTo[from] + edge.weight()) {
                return;
            }

            distTo[to] = distTo[from] + edge.weight();
            pathTo[to] = from;
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
     * Finds the longest paths tree for a dag.
     */
    public static class DAGLongestPaths {

        private final DirectedGraph DAG;
        private final int S;

        private final double[] distTo;
        private final int[] pathTo;

        public DAGLongestPaths(DirectedGraph dag, int source) {
            DAG = dag;
            S = source;

            distTo = new double[DAG.V()];
            pathTo = new int[DAG.V()];
            for (int v = 0; v < DAG.V(); v++) {
                distTo[v] = Double.NEGATIVE_INFINITY;
                pathTo[v] = -1;
            }
            distTo[S] = 0.0;
            pathTo[S] = S;

            search();
        }

        private void search() {
            List<Integer> topologicalOrder = new TopologicalSort(DAG).order();

            for (int v : topologicalOrder) {
                for (DirectedWeightedEdge edge : DAG.adj(v)) {
                    relax(edge);
                }
            }
        }

        private void relax(DirectedWeightedEdge edge) {
            int from = edge.from();
            int to = edge.to();

            if (distTo[to] >= distTo[from] + edge.weight()) {
                return;
            }

            distTo[to] = distTo[from] + edge.weight();
            pathTo[to] = from;
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

    private static DirectedGraph randomDAG(Random random) {
        int v = 5 + random.nextInt(11);
        int e = v * (1 + random.nextInt(v));

        HashSet<OrderedTuple> existedTuples = new HashSet<>();
        DirectedGraph dag = new DirectedGraph(v);
        for (int i = 0; i < e; i++) {
            int from = random.nextInt(v);
            int to = random.nextInt(v);

            if (hasPath(dag, to, from)) {
                continue;
            }

            OrderedTuple tuple = new OrderedTuple(from, to);
            if (existedTuples.contains(tuple)) {
                continue;
            }
            existedTuples.add(tuple);

            DirectedWeightedEdge edge = new DirectedWeightedEdge(from, to, 1.0);
            dag.addEdge(edge);
        }

        return dag;
    }

    private static boolean hasPath(DirectedGraph graph, int from, int to) {
        boolean[] marked = new boolean[graph.V()];

        return hasPath(graph, from, to, marked);
    }

    private static boolean hasPath(DirectedGraph graph, int v, int target, boolean[] marked) {
        marked[v] = true;
        if (v == target) {
            return true;
        }

        for (DirectedWeightedEdge edge : graph.adj(v)) {
            int w = edge.to();

            if (!marked[w] && hasPath(graph, w, target, marked)) {
                return true;
            }
        }

        return false;
    }

    private static DirectedGraph reverseGraph(DirectedGraph graph) {
        DirectedGraph reversedGraph = new DirectedGraph(graph.V());

        for (int v = 0; v < graph.V(); v++) {
            for (DirectedWeightedEdge edge : graph.adj(v)) {
                DirectedWeightedEdge reversedEdge = reverseEdge(edge);
                reversedGraph.addEdge(reversedEdge);
            }
        }

        return reversedGraph;
    }

    private static DirectedWeightedEdge reverseEdge(DirectedWeightedEdge edge) {
        int from = edge.from();
        int to = edge.to();
        double weight = edge.weight();

        return new DirectedWeightedEdge(from, to, -weight);
    }

    private static DirectedGraph specificDAG() {
        DirectedGraph dag = new DirectedGraph(6);

        dag.addEdge(new DirectedWeightedEdge(0, 1, 2.0));
        dag.addEdge(new DirectedWeightedEdge(0, 3, 2.0));
        dag.addEdge(new DirectedWeightedEdge(1, 2, 6.0));
        dag.addEdge(new DirectedWeightedEdge(2, 4, 1.0));
        dag.addEdge(new DirectedWeightedEdge(2, 5, 2.0));
        dag.addEdge(new DirectedWeightedEdge(3, 1, 4.0));
        dag.addEdge(new DirectedWeightedEdge(3, 4, 3.0));
        dag.addEdge(new DirectedWeightedEdge(4, 5, 1.0));

        return dag;
    }

    private static void checkTopologicalOrder(DirectedGraph dag, List<Integer> order, Random random) {
        for (int i = 0; i < 100; i++) {
            int vIndex = random.nextInt(dag.V() - 1);
            int wIndex = vIndex + 1 + random.nextInt(dag.V() - 1 - vIndex);
            int v = order.get(vIndex);
            int w = order.get(wIndex);
            assertFalse(hasPath(dag, w, v));
        }
    }

    private static void testTopologicalSort(Random random) {
        DirectedGraph specificDAG = specificDAG();
        assertEquals(Arrays.asList(0, 3, 1, 2, 4, 5), new TopologicalSort(specificDAG).order());

        for (int i = 0; i < 50; i++) {
            DirectedGraph dag = randomDAG(random);
            List<Integer> order = new ArrayList<>(new TopologicalSort(dag).order());
            checkTopologicalOrder(dag, order, random);
        }
    }

    private static void checkDAGShortestPaths(DAGShortestPaths dagShortestPaths, ShortestPath.Dijkstra dijkstra, DirectedGraph graph) {
        for (int v = 0; v < graph.V(); v++) {
            assertEquals(dijkstra.getDist(v), dagShortestPaths.getDist(v), 0.000001);
        }
    }

    private static void testDAGShortestPaths(Random random) {
        DirectedGraph specificDAG = specificDAG();
        DAGShortestPaths specificDAGShortestPaths = new DAGShortestPaths(specificDAG, 0);
        assertEquals(8, (int) specificDAGShortestPaths.getDist(2));
        assertEquals(6, (int) specificDAGShortestPaths.getDist(5));
        assertEquals(5, (int) specificDAGShortestPaths.getDist(4));
        assertEquals(Arrays.asList(0, 1, 2), specificDAGShortestPaths.getPath(2));
        assertEquals(Arrays.asList(0, 3, 4, 5), specificDAGShortestPaths.getPath(5));
        assertEquals(Arrays.asList(0, 3, 4), specificDAGShortestPaths.getPath(4));

        for (int i = 0; i < 50; i++) {
            DirectedGraph dag = randomDAG(random);
            int source = random.nextInt(dag.V());

            DAGShortestPaths dagShortestPaths = new DAGShortestPaths(dag, source);
            ShortestPath.Dijkstra dijkstra = new ShortestPath.Dijkstra(dag, source);

            checkDAGShortestPaths(dagShortestPaths, dijkstra, dag);
        }
    }

    private static void checkDAGLongestPaths(DAGLongestPaths dagLongestPaths, DAGShortestPaths dagShortestPaths, DirectedGraph graph) {
        for (int v = 0; v < graph.V(); v++) {
            assertEquals(-dagShortestPaths.getDist(v), dagLongestPaths.getDist(v), 0.000001);
        }
    }

    private static void testDAGLongestPaths(Random random) {
        DirectedGraph specificDAG = reverseGraph(specificDAG());
        DAGLongestPaths specificDAGLongestPaths = new DAGLongestPaths(specificDAG, 0);
        assertEquals(0, (int) specificDAGLongestPaths.getDist(0));
        assertEquals(-2, (int) specificDAGLongestPaths.getDist(3));
        assertEquals(-2, (int) specificDAGLongestPaths.getDist(1));
        assertEquals(Arrays.asList(0), specificDAGLongestPaths.getPath(0));
        assertEquals(Arrays.asList(0, 3), specificDAGLongestPaths.getPath(3));
        assertEquals(Arrays.asList(0, 1), specificDAGLongestPaths.getPath(1));

        for (int i = 0; i < 50; i++) {
            DirectedGraph dag = randomDAG(random);
            DirectedGraph reversedDAG = reverseGraph(dag);
            int source = random.nextInt(dag.V());

            DAGLongestPaths dagLongestPaths = new DAGLongestPaths(dag, source);
            DAGShortestPaths dagShortestPaths = new DAGShortestPaths(reversedDAG, source);

            checkDAGLongestPaths(dagLongestPaths, dagShortestPaths, dag);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        testTopologicalSort(random);
        testDAGShortestPaths(random);
        testDAGLongestPaths(random);
    }

}
