import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class GraphRepresentations {

    public interface GraphRepresentation {

        void addEdge(int s, int t);

        Iterable<Integer> adj(int s);

        void printGraph();

        boolean hasEdge(int s, int t);

    }

    public static class AdjacencyMatrix implements GraphRepresentation {

        private final int V;

        private final int[][] adjacencies;

        public AdjacencyMatrix(int v) {
            V = v;
            adjacencies = new int[V][V];
        }

        @Override
        public void addEdge(int s, int t) {
            adjacencies[s][t] = 1;
            adjacencies[t][s] = 1;
        }

        @Override
        public Iterable<Integer> adj(int s) {
            List<Integer> adjVertices = new ArrayList<>();
            for (int v = 0; v < V; v++) {
                if (adjacencies[s][v] == 1) {
                    adjVertices.add(v);
                }
            }
            return adjVertices;
        }

        @Override
        public void printGraph() {
            for (int s = 0; s < V; s++) {
                for (int t : adj(s)) {
                    System.out.println(s + "-" + t);
                }
            }
        }

        @Override
        public boolean hasEdge(int s, int t) {
            return adjacencies[s][t] == 1;
        }

    }

    public static class SetOfEdges implements GraphRepresentation {

        private static class Edge {

            private static final int HASH_BASE = 1201;

            private final int S;

            private final int T;

            private Edge(int s, int t) {
                S = s;
                T = t;
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

        private final int V;

        private final Set<Edge> edges;

        public SetOfEdges(int v) {
            V = v;
            edges = new HashSet<>();
        }

        @Override
        public void addEdge(int s, int t) {
            edges.add(new Edge(s, t));
            edges.add(new Edge(t, s));
        }

        @Override
        public Iterable<Integer> adj(int s) {
            List<Integer> adjVertices = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.S == s) {
                    adjVertices.add(edge.T);
                }
            }
            return adjVertices;
        }

        @Override
        public void printGraph() {
            for (Edge edge : edges) {
                System.out.println(edge.S + "-" + edge.T);
            }
        }

        @Override
        public boolean hasEdge(int s, int t) {
            for (Edge edge : edges) {
                if (edge.S == s && edge.T == t) {
                    return true;
                }
            }
            return false;
        }

    }

    public static class AdjacencyList implements GraphRepresentation {

        private final int V;

        private final List<Integer>[] adjacencies;

        public AdjacencyList(int v) {
            V = v;
            adjacencies = (List<Integer>[]) new List[V];
            for (int s = 0; s < V; s++) {
                adjacencies[s] = new ArrayList<>();
            }
        }

        @Override
        public void addEdge(int s, int t) {
            adjacencies[s].add(t);
            adjacencies[t].add(s);
        }

        @Override
        public Iterable<Integer> adj(int s) {
            return adjacencies[s];
        }

        @Override
        public void printGraph() {
            for (int s = 0; s < V; s++) {
                for (int t : adj(s)) {
                    System.out.println(s + "-" + t);
                }
            }
        }

        @Override
        public boolean hasEdge(int s, int t) {
            return adjacencies[s].contains(t);
        }

    }

    public static void main(String[] args) {
        System.out.println("AdjacencyMatrix:");
        GraphRepresentation graphRepresentation = new AdjacencyMatrix(6);
        graphRepresentation.addEdge(1, 2);
        graphRepresentation.addEdge(1, 3);
        graphRepresentation.addEdge(1, 4);
        graphRepresentation.addEdge(2, 5);
        graphRepresentation.addEdge(3, 4);
        assertTrue(graphRepresentation.hasEdge(1, 2));
        assertTrue(graphRepresentation.hasEdge(1, 3));
        assertTrue(graphRepresentation.hasEdge(1, 4));
        assertTrue(graphRepresentation.hasEdge(5, 2));
        assertTrue(graphRepresentation.hasEdge(4, 3));
        graphRepresentation.printGraph();

        System.out.println("SetOfEdges:");
        graphRepresentation = new SetOfEdges(6);
        graphRepresentation.addEdge(1, 2);
        graphRepresentation.addEdge(1, 3);
        graphRepresentation.addEdge(1, 4);
        graphRepresentation.addEdge(2, 5);
        graphRepresentation.addEdge(3, 4);
        assertTrue(graphRepresentation.hasEdge(1, 2));
        assertTrue(graphRepresentation.hasEdge(1, 3));
        assertTrue(graphRepresentation.hasEdge(1, 4));
        assertTrue(graphRepresentation.hasEdge(5, 2));
        assertTrue(graphRepresentation.hasEdge(4, 3));
        graphRepresentation.printGraph();

        System.out.println("AdjacencyList:");
        graphRepresentation = new AdjacencyList(6);
        graphRepresentation.addEdge(1, 2);
        graphRepresentation.addEdge(1, 3);
        graphRepresentation.addEdge(1, 4);
        graphRepresentation.addEdge(2, 5);
        graphRepresentation.addEdge(3, 4);
        assertTrue(graphRepresentation.hasEdge(1, 2));
        assertTrue(graphRepresentation.hasEdge(1, 3));
        assertTrue(graphRepresentation.hasEdge(1, 4));
        assertTrue(graphRepresentation.hasEdge(5, 2));
        assertTrue(graphRepresentation.hasEdge(4, 3));
        graphRepresentation.printGraph();
    }

}
