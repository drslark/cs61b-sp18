import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import disjointsets.concrete.WeightedQuickUnionDS;
import disjointsets.concrete.WeightedQuickUnionDSWithPathCompression;
import edge.UndirectedWeightedEdge;
import graph.UndirectedGraph;
import pq.ExtrinsicUniqueMinPQ;
import tuple.UnorderedTuple;

import static edge.UndirectedWeightedEdge.WeightComparator;
import static org.junit.Assert.assertTrue;

/**
 * Algorithms to get a minimum spanning tree.
 */
public class MinimumSpanningTree {

    /**
     * Lazy version of Prim.
     */
    public static class LazyPrim {

        private final UndirectedGraph G;

        private final boolean[] marked;
        private final Set<UndirectedWeightedEdge> mst;

        public LazyPrim(UndirectedGraph graph) {
            G = graph;

            marked = new boolean[G.V()];
            mst = new HashSet<>();

            scan();
        }

        private void scan() {
            PriorityQueue<UndirectedWeightedEdge> fringe = new PriorityQueue<>(new WeightComparator());

            visit(0, fringe);
            while (fringe.size() > 0) {
                UndirectedWeightedEdge edge = fringe.remove();

                int v = edge.either();
                int w = edge.other(v);

                if (marked[v] && marked[w]) {
                    continue;
                }

                mst.add(edge);

                if (!marked[v]) {
                    visit(v, fringe);
                } else {
                    visit(w, fringe);
                }
            }
        }

        private void visit(int v, PriorityQueue<UndirectedWeightedEdge> fringe) {
            marked[v] = true;

            for (UndirectedWeightedEdge edge : G.adj(v)) {
                int w = edge.other(v);

                if (marked[w]) {
                    continue;
                }

                fringe.add(edge);
            }
        }

        public Set<UndirectedWeightedEdge> getMst() {
            return mst;
        }

    }

    /**
     * Immediate version of Prim.
     */
    public static class Prim {

        private final UndirectedGraph G;

        private final double[] distTo;
        private final UndirectedWeightedEdge[] edgeTo;
        private final boolean[] marked;

        public Prim(UndirectedGraph graph) {
            G = graph;

            distTo = new double[G.V()];
            for (int v = 0; v < G.V(); v++) {
                distTo[v] = Double.POSITIVE_INFINITY;
            }
            distTo[0] = 0.0;

            edgeTo = new UndirectedWeightedEdge[G.V()];
            marked = new boolean[G.V()];

            scan();
        }

        private void scan() {
            ExtrinsicUniqueMinPQ<Integer> fringe = new ExtrinsicUniqueMinPQ<>();

            fringe.add(0, distTo[0]);
            while (fringe.size() > 0) {
                int v = fringe.removeMin();
                marked[v] = true;

                for (UndirectedWeightedEdge edge : G.adj(v)) {
                    int w = edge.other(v);
                    relax(w, edge, fringe);
                }
            }
        }

        private void relax(int v, UndirectedWeightedEdge edge, ExtrinsicUniqueMinPQ<Integer> fringe) {
            if (marked[v]) {
                return;
            }

            if (distTo[v] <= edge.weight()) {
                return;
            }

            distTo[v] = edge.weight();
            edgeTo[v] = edge;
            if (fringe.contains(v)) {
                fringe.changePriority(v, distTo[v]);
            } else {
                fringe.add(v, distTo[v]);
            }
        }

        public Set<UndirectedWeightedEdge> getMst() {
            Set<UndirectedWeightedEdge> mst = new HashSet<>();
            for (UndirectedWeightedEdge edge : edgeTo) {
                if (edge != null) {
                    mst.add(edge);
                }
            }

            return mst;
        }

    }

    /**
     * Kruskal.
     */
    public static class Kruskal {

        private final UndirectedGraph G;

        private final WeightedQuickUnionDSWithPathCompression ds;
        private final Set<UndirectedWeightedEdge> mst;

        public Kruskal(UndirectedGraph graph) {
            G = graph;

            ds = new WeightedQuickUnionDSWithPathCompression(G.V());
            mst = new HashSet<>();

            scan();
        }

        private void scan() {
            ExtrinsicUniqueMinPQ<UndirectedWeightedEdge> fringe = initFringe();

            while (fringe.size() > 0 && mst.size() < G.V() - 1) {
                UndirectedWeightedEdge edge = fringe.removeMin();

                int v = edge.either();
                int w = edge.other(v);
                if (!ds.isConnected(v, w)) {
                    ds.connect(v, w);
                    mst.add(edge);
                }
            }
        }

        private ExtrinsicUniqueMinPQ<UndirectedWeightedEdge> initFringe() {
            ExtrinsicUniqueMinPQ<UndirectedWeightedEdge> fringe = new ExtrinsicUniqueMinPQ<>();

            for (int v = 0; v < G.V(); v++) {
                for (UndirectedWeightedEdge edge : G.adj(v)) {
                    int w = edge.other(v);
                    if (v >= w) {
                        continue;
                    }
                    fringe.add(edge, edge.weight());
                }
            }

            return fringe;
        }

        public Set<UndirectedWeightedEdge> getMst() {
            return mst;
        }

    }

    private static UndirectedGraph randomConnectedGraph(Random random) {
        int v = 5 + random.nextInt(11);
        int e = v * (1 + random.nextInt(v));

        UndirectedGraph graph = new UndirectedGraph(v);
        HashSet<UnorderedTuple> existedTuples = new HashSet<>();
        WeightedQuickUnionDS ds = new WeightedQuickUnionDS(v);
        for (int i = 0; i < e; i++) {
            UndirectedWeightedEdge edge = randomEdge(v, random);
            addEdgeIfNotExist(graph, edge, existedTuples, ds);
        }

        while (ds.sizeOf(0) < v) {
            UndirectedWeightedEdge edge = randomEdge(v, random);
            addEdgeIfNotExist(graph, edge, existedTuples, ds);
        }

        return graph;
    }

    private static UndirectedWeightedEdge randomEdge(int v, Random random) {
        int x = random.nextInt(v);
        int y = random.nextInt(v);
        double weight = random.nextDouble();

        return new UndirectedWeightedEdge(x, y, weight);
    }

    private static void addEdgeIfNotExist(
        UndirectedGraph graph, UndirectedWeightedEdge edge,
        HashSet<UnorderedTuple> existedTuples, WeightedQuickUnionDS ds
    ) {
        int x = edge.either();
        int y = edge.other(x);

        UnorderedTuple tuple = new UnorderedTuple(x, y);
        if (existedTuples.contains(tuple)) {
            return;
        }
        existedTuples.add(tuple);
        ds.connect(x, y);

        graph.addEdge(edge);
    }

    private static void checkMst(Set<UndirectedWeightedEdge> mst, UndirectedGraph graph, Random random) {
        for (int i = 0; i < 500; i++) {
            Set<Integer> split = randomSplit(graph.V(), random);
            Set<UndirectedWeightedEdge> crossingEdges = getCrossingEdges(graph, split);
            Set<UndirectedWeightedEdge> minEdges = getMinEdges(crossingEdges);
            assertTrue(containsMinEdge(mst, minEdges));
        }
    }

    private static Set<Integer> randomSplit(int v, Random random) {
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            all.add(i);
        }
        Collections.shuffle(all, random);

        int num = 1 + random.nextInt(v - 1);
        return new HashSet<>(all.subList(0, num));
    }

    private static Set<UndirectedWeightedEdge> getCrossingEdges(UndirectedGraph graph, Set<Integer> split) {
        Set<UndirectedWeightedEdge> crossingEdges = new HashSet<>();
        for (int v : split) {
            for (UndirectedWeightedEdge edge : graph.adj(v)) {
                int w = edge.other(v);

                if (!split.contains(w)) {
                    crossingEdges.add(edge);
                }
            }
        }

        return crossingEdges;
    }

    private static Set<UndirectedWeightedEdge> getMinEdges(Iterable<UndirectedWeightedEdge> edges) {
        Set<UndirectedWeightedEdge> minEdges = new HashSet<>();

        double minWeight = Double.POSITIVE_INFINITY;

        for (UndirectedWeightedEdge edge : edges) {
            if (edge.weight() == minWeight) {
                minEdges.add(edge);
            } else if (edge.weight() < minWeight) {
                minWeight = edge.weight();
                minEdges = new HashSet<>();
                minEdges.add(edge);
            }
        }

        return minEdges;
    }

    private static boolean containsMinEdge(Set<UndirectedWeightedEdge> mst, Set<UndirectedWeightedEdge> minEdges) {
        for (UndirectedWeightedEdge edge : minEdges) {
            if (mst.contains(edge)) {
                return true;
            }
        }
        return false;
    }

    private static void testPrim(Random random) {
        for (int i = 0; i < 100; i++) {
            UndirectedGraph graph = randomConnectedGraph(random);
            Set<UndirectedWeightedEdge> mst = new Prim(graph).getMst();
            checkMst(mst, graph, random);
        }
    }

    private static void testLazyPrim(Random random) {
        for (int i = 0; i < 100; i++) {
            UndirectedGraph graph = randomConnectedGraph(random);
            Set<UndirectedWeightedEdge> mst = new LazyPrim(graph).getMst();
            checkMst(mst, graph, random);
        }
    }

    private static void testKruskal(Random random) {
        for (int i = 0; i < 100; i++) {
            UndirectedGraph graph = randomConnectedGraph(random);
            Set<UndirectedWeightedEdge> mst = new Kruskal(graph).getMst();
            checkMst(mst, graph, random);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        testPrim(random);
        testLazyPrim(random);
        testKruskal(random);
    }

}
