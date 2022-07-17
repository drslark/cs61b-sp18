package byog.Core.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import byog.Core.algorithms.AdjacencyListGraph;
import byog.Core.algorithms.Edge;
import byog.Core.algorithms.Prim;

/**
 * Test the Prim algorithm.
 */
public class TestPrim {

    /**
     * Test Prim by connect some graph.
     */
    @Test
    public void testPrim() {
        int[][] adjacencyMatrixGraph = new int[][]{
                {-1, 2, -1, 6, -1},
                {2, -1, 3, 8, 5},
                {-1, 3, -1, -1, 7},
                {6, 8, -1, -1, 9},
                {-1, 5, 7, 9, -1}
        };
        
        Prim prim = new Prim(new AdjacencyListGraph(adjacencyMatrixGraph));
        List<Edge> mstSortedEdges = prim.sortedEdges();

        assertEquals(adjacencyMatrixGraph.length - 1, mstSortedEdges.size());
        assertEquals(new Edge(0, 1, 2), mstSortedEdges.get(0));
        assertEquals(new Edge(1, 2, 3), mstSortedEdges.get(1));
        assertEquals(new Edge(1, 4, 5), mstSortedEdges.get(2));
        assertEquals(new Edge(0, 3, 6), mstSortedEdges.get(3));
    }

}
