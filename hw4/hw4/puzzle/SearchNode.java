package hw4.puzzle;

/**
 * A SearchNode is used in a Solver to represent a sequence.
 */
class SearchNode implements Comparable<SearchNode> {

    private final WorldState S;
    private final int M;
    private final SearchNode P;

    SearchNode(WorldState state, int moves, SearchNode prev) {
        S = state;
        M = moves;
        P = prev;
    }

    WorldState curr() {
        return S;
    }

    int moves() {
        return M;
    }

    SearchNode prev() {
        return P;
    }

    @Override
    public int compareTo(SearchNode otherNode) {
        int priority = M + S.estimatedDistanceToGoal();
        int otherPriority = otherNode.M + otherNode.S.estimatedDistanceToGoal();

        return priority - otherPriority;
    }

}
