import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GraphTraversalOperations {

    /**
     * A Graph object represented by adjacency list.
     */
    public static class Graph {

        private final int V;

        private final List<Integer>[] adjacencies;

        public Graph(int v) {
            V = v;
            adjacencies = (List<Integer>[]) new List[V];
            for (int i = 0; i < V; i++) {
                adjacencies[i] = new ArrayList<>();
            }
        }

        public void addEdge(int s, int t) {
            adjacencies[s].add(t);
            adjacencies[t].add(s);
        }

        public Iterable<Integer> adj(int s) {
            return adjacencies[s];
        }

        public Iterable<Integer> reversedAdj(int s) {
            List<Integer> reversedAdjacency = new ArrayList<>(adjacencies[s]);
            Collections.reverse(reversedAdjacency);
            return reversedAdjacency;
        }

        public int V() {
            return V;
        }

    }

    /**
     * An Edge object.
     */
    static class Edge {

        private static final int HASH_BASE = 1201;

        final int S;
        final int T;

        Edge(int s, int t) {
            if (s == t) {
                throw new IllegalArgumentException("s should not be equal to t!");
            }

            if (s < t) {
                S = s;
                T = t;
            } else {
                S = t;
                T = s;
            }
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
            return S == otherEdge.S && T == otherEdge.T;
        }

        @Override
        public int hashCode() {
            return HASH_BASE * S + T;
        }

    }

    /**
     * A shared Paths object with pathTo method and hasEdgeTo method.
     */
    abstract static class Paths {

        private final int S;
        final int[] edgeTo;
        final boolean[] marked;

        Paths(Graph g, int s) {
            S = s;
            marked = new boolean[g.V()];
            edgeTo = new int[g.V()];
        }

        public List<Integer> pathTo(int t) {
            List<Integer> path = new ArrayList<>();

            int i = t;
            while (i != S) {
                path.add(i);
                i = edgeTo[i];
            }
            path.add(S);

            Collections.reverse(path);
            return path;
        }

        public boolean hasEdgeTo(int t) {
            return marked[t];
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof Paths)) {
                return false;
            }

            Paths otherPaths = (Paths) other;
            return Arrays.equals(marked, otherPaths.marked) && Arrays.equals(edgeTo, otherPaths.edgeTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Arrays.hashCode(marked), Arrays.hashCode(edgeTo));
        }

    }

    /**
     * A DepthFirstPaths object that find paths from source s on a graph g in a depth-first way.
     */
    public static class DepthFirstPaths extends Paths {

        private DepthFirstPaths(Graph g, int s) {
            super(g, s);
        }

        public static DepthFirstPaths build(Graph g, int s, String type) {
            DepthFirstPaths depthFirstPaths = new DepthFirstPaths(g, s);

            switch (type) {
                case "raw":
                    depthFirstPaths.edgeTo[s] = -1;
                    depthFirstPaths.dfs(g, s);
                    break;
                case "processVertex":
                    depthFirstPaths.dfsProcessVertex(g, -1, s);
                    break;
                case "processEdge":
                    depthFirstPaths.edgeTo[s] = -1;
                    depthFirstPaths.marked[s] = true;
                    depthFirstPaths.dfsProcessEdge(g, s);
                    break;
                case "iterative":
                    depthFirstPaths.dfsIterative(g, s);
                    break;
                case "iterativeProcessVertex":
                    depthFirstPaths.dfsIterativeProcessVertex(g, s);
                    break;
                default:
                    depthFirstPaths = null;
            }

            return depthFirstPaths;
        }

        private void dfs(Graph g, int s) {
            // at the viewpoint of vertex
            marked[s] = true;

            for (int i : g.adj(s)) {
                // at the viewpoint of adjacency edge
                if (!marked[i]) {
                    // at the viewpoint of adjacency edge to an unmarked vertex
                    edgeTo[i] = s;
                    dfs(g, i);
                }
            }
        }

        private void dfsProcessVertex(Graph g, int s, int t) {
            if (marked[t]) {
                return;
            }

            // t is not marked
            edgeTo[t] = s;
            // mark exactly after process
            marked[t] = true;

            for (int i : g.adj(t)) {
                dfsProcessVertex(g, t, i);
            }
        }

        private void dfsProcessEdge(Graph g, int s) {
            for (int i : g.adj(s)) {
                if (!marked[i]) {
                    edgeTo[i] = s;
                    marked[i] = true;
                    dfsProcessEdge(g, i);
                }
            }
        }

        private void dfsIterative(Graph g, int s) {
            Deque<Integer> fringe = new LinkedList<>();

            edgeTo[s] = -1;
            fringe.addLast(s);

            while (!fringe.isEmpty()) {
                s = fringe.removeLast();
                if (marked[s]) {
                    continue;
                }
                marked[s] = true;

                for (int i : g.reversedAdj(s)) {
                    if (!marked[i]) {
                        // update path to i
                        edgeTo[i] = s;
                        fringe.addLast(i);
                    }
                }
            }
        }

        private void dfsIterativeProcessVertex(Graph g, int s) {
            int t;

            Deque<Integer> fringe = new LinkedList<>();
            Deque<Integer> parents = new LinkedList<>();

            fringe.addLast(s);
            parents.addLast(-1);

            while (!fringe.isEmpty()) {
                t = fringe.removeLast();
                s = parents.removeLast();

                if (marked[t]) {
                    continue;
                }

                edgeTo[t] = s;
                marked[t] = true;

                for (int i : g.reversedAdj(t)) {
                    fringe.addLast(i);
                    parents.addLast(t);
                }
            }
        }

    }

    /**
     * A BreadthFirstPaths object that find paths from source s on a graph g in a breadth-first way.
     */
    public static class BreadthFirstPaths extends Paths {

        private BreadthFirstPaths(Graph g, int s) {
            super(g, s);
        }

        public static BreadthFirstPaths build(Graph g, int s, String type) {
            BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(g, s);

            switch (type) {
                case "raw":
                    breadthFirstPaths.bfs(g, s);
                    break;
                case "processVertex":
                    breadthFirstPaths.bfsProcessVertex(g, s);
                    break;
                default:
                    breadthFirstPaths = null;
            }

            return breadthFirstPaths;
        }

        private void bfs(Graph g, int s) {
            Deque<Integer> fringe = new LinkedList<>();

            edgeTo[s] = -1;
            marked[s] = true;
            fringe.addLast(s);

            while (!fringe.isEmpty()) {
                s = fringe.removeFirst();

                for (int i : g.adj(s)) {
                    if (!marked[i]) {
                        edgeTo[i] = s;
                        marked[i] = true;
                        fringe.addLast(i);
                    }
                }
            }
        }

        private void bfsProcessVertex(Graph g, int s) {
            int t;

            Deque<Integer> fringe = new LinkedList<>();
            Deque<Integer> parents = new LinkedList<>();

            fringe.addLast(s);
            parents.addLast(-1);

            while (!fringe.isEmpty()) {
                t = fringe.removeFirst();
                s = parents.removeFirst();

                if (marked[t]) {
                    continue;
                }
                edgeTo[t] = s;
                marked[t] = true;

                for (int i : g.adj(t)) {
                    fringe.addLast(i);
                    parents.addLast(t);
                }
            }
        }

    }

    private static void normalTest() {
        Graph graph = new Graph(9);
        int[][] edges = new int[][]{
            {0, 1}, {1, 2}, {1, 4}, {2, 5}, {3, 4}, {4, 5}, {5, 6}, {5, 8}, {6, 7}
        };
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        for (int source = 0; source < graph.V(); source++) {
            DepthFirstPaths dfs = DepthFirstPaths.build(graph, source, "raw");
            DepthFirstPaths dfsProcessVertex = DepthFirstPaths.build(graph, source, "processVertex");
            DepthFirstPaths dfsProcessEdge = DepthFirstPaths.build(graph, source, "processEdge");
            DepthFirstPaths dfsIterative = DepthFirstPaths.build(graph, source, "iterative");
            DepthFirstPaths dfsIterativeProcessVertex = DepthFirstPaths.build(graph, source, "iterativeProcessVertex");

            assertEquals(dfs, dfsProcessVertex);
            assertEquals(dfs, dfsProcessEdge);
            assertEquals(dfs, dfsIterative);
            assertEquals(dfs, dfsIterativeProcessVertex);

            BreadthFirstPaths bfs = BreadthFirstPaths.build(graph, source, "raw");
            BreadthFirstPaths bfsProcessVertex = BreadthFirstPaths.build(graph, source, "processVertex");

            assertEquals(bfs, bfsProcessVertex);
        }
    }

    private static Graph generateRandomGraph() {
        Random random = new Random();

        int v = random.nextInt(10) + 5;
        Graph graph = new Graph(v);

        int e = 1 + random.nextInt(100);
        Set<Edge> edges = new HashSet<>();

        for (int i = 0; i < e; i++) {
            int s = random.nextInt(v);
            int t = random.nextInt(v);
            if (s != t) {
                edges.add(new Edge(s, t));
            }
        }

        for (Edge edge : edges) {
            graph.addEdge(edge.S, edge.T);
        }

        return graph;
    }

    private static void randomTest() {
        Graph graph = generateRandomGraph();

        for (int source = 0; source < graph.V(); source++) {
            DepthFirstPaths dfs = DepthFirstPaths.build(graph, source, "raw");
            DepthFirstPaths dfsProcessVertex = DepthFirstPaths.build(graph, source, "processVertex");
            DepthFirstPaths dfsProcessEdge = DepthFirstPaths.build(graph, source, "processEdge");
            DepthFirstPaths dfsIterative = DepthFirstPaths.build(graph, source, "iterative");
            DepthFirstPaths dfsIterativeProcessVertex = DepthFirstPaths.build(graph, source, "iterativeProcessVertex");

            assertEquals(dfs, dfsProcessVertex);
            assertEquals(dfs, dfsProcessEdge);
            assertEquals(dfs, dfsIterative);
            assertEquals(dfs, dfsIterativeProcessVertex);

            BreadthFirstPaths bfs = BreadthFirstPaths.build(graph, source, "raw");
            BreadthFirstPaths bfsProcessVertex = BreadthFirstPaths.build(graph, source, "processVertex");

            assertEquals(bfs, bfsProcessVertex);
        }
    }

    public static void main(String[] args) {
        normalTest();
        randomTest();
    }

}
