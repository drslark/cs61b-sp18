import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edge.DirectedWeightedEdge;
import graph.DirectedGraph;

import static org.junit.Assert.assertEquals;

public class DynamicProgramming {

    /**
     * Computes the length of the longest increasing subsequence by converting the raw sequence to a dag.
     */
    public static int longestIncreasingSubsequenceDAG(List<Integer> sequence) {
        int longestDist = Integer.MIN_VALUE;

        DirectedGraph dag = sequenceToDAG(sequence);

        for (int v = 0; v < dag.V(); v++) {
            DirectedAcyclicGraph.DAGLongestPaths dagLongestPaths = new DirectedAcyclicGraph.DAGLongestPaths(dag, v);
            int longestDistStartsAtV = (int) getLongestDist(dag, dagLongestPaths);
            if (longestDistStartsAtV > longestDist) {
                longestDist = longestDistStartsAtV;
            }
        }

        return longestDist + 1;
    }

    /**
     * Computes the length of the longest increasing subsequence by converting the raw sequence to a dag then dynamic programming.
     */
    public static int longestIncreasingSubsequenceDAGDP(List<Integer> sequence) {
        DirectedGraph dag = sequenceToDAG(sequence);

        int[] longestSequenceStartsAt = new int[dag.V()];
        for (int v = 0; v < dag.V(); v++) {
            longestSequenceStartsAt[v] = 1;
        }

        for (int v = dag.V() - 1; v >= 0; v--) {
            for (DirectedWeightedEdge edge : dag.adj(v)) {
                int w = edge.to();

                if (longestSequenceStartsAt[w] + 1 > longestSequenceStartsAt[v]) {
                    longestSequenceStartsAt[v] = longestSequenceStartsAt[w] + 1;
                }
            }
        }

        return max(longestSequenceStartsAt);
    }

    /**
     * Computes the length of the longest increasing subsequence by dynamic programming.
     */
    public static int longestIncreasingSubsequenceDP(List<Integer> sequence) {
        int[] longestSequenceEndsAt = new int[sequence.size()];
        for (int i = 0; i < sequence.size(); i++) {
            longestSequenceEndsAt[i] = 1;
        }

        for (int i = 0; i < sequence.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (sequence.get(j) < sequence.get(i)) {
                    if (longestSequenceEndsAt[j] + 1 > longestSequenceEndsAt[i]) {
                        longestSequenceEndsAt[i] = longestSequenceEndsAt[j] + 1;
                    }
                }
            }
        }

        return max(longestSequenceEndsAt);
    }

    private static DirectedGraph sequenceToDAG(List<Integer> sequence) {
        DirectedGraph dag = new DirectedGraph(sequence.size());

        for (int i = 0; i < sequence.size(); i++) {
            for (int j = i + 1; j < sequence.size(); j++) {
                if (sequence.get(i) < sequence.get(j)) {
                    DirectedWeightedEdge edge = new DirectedWeightedEdge(i, j, 1.0);
                    dag.addEdge(edge);
                }
            }
        }

        return dag;
    }

    private static double getLongestDist(DirectedGraph dag, DirectedAcyclicGraph.DAGLongestPaths dagLongestPaths) {
        double longestDist = Double.NEGATIVE_INFINITY;

        for (int v = 0; v < dag.V(); v++) {
            double distEndsAtV = dagLongestPaths.getDist(v);

            if (distEndsAtV > longestDist) {
                longestDist = distEndsAtV;
            }
        }

        return longestDist;
    }

    private static int max(int[] array) {
        int maxVal = Integer.MIN_VALUE;

        for (int item : array) {
            if (item > maxVal) {
                maxVal = item;
            }
        }

        return maxVal;
    }

    private static List<Integer> specificSequence() {
        return Arrays.asList(8, 2, 9, 4, 5, 7, 3);
    }

    private static void testLongestIncreasingSubsequence(Random random) {
        List<Integer> specificSequence = specificSequence();
        assertEquals(4, longestIncreasingSubsequenceDAG(specificSequence));

        for (int i = 0; i < 50; i++) {
            List<Integer> sequence = new ArrayList<>(20);
            for (int j = 0; j < 20; j++) {
                sequence.add(random.nextInt(20));
            }

            int lengthDAG = longestIncreasingSubsequenceDAG(sequence);
            int lengthDAGDP = longestIncreasingSubsequenceDAGDP(sequence);
            int lengthDP = longestIncreasingSubsequenceDP(sequence);

            assertEquals(lengthDAG, lengthDAGDP);
            assertEquals(lengthDAG, lengthDP);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        testLongestIncreasingSubsequence(random);
    }

}
