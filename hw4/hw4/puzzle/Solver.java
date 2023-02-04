package hw4.puzzle;

import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;

/**
 * An A* puzzle solver.
 */
public class Solver {

    private final LinkedList<WorldState> S;

    /**
     * Constructor which solves the puzzle, computing everything necessary
     * for moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        S = new LinkedList<>();

        solve(initial);
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.
     */
    public int moves() {
        return S.size() - 1;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState to the solution.
     */
    public Iterable<WorldState> solution() {
        return S;
    }

    private void solve(WorldState initial) {
        SearchNode initialNode = new SearchNode(initial, 0, null);

        MinPQ<SearchNode> fringe = new MinPQ<>();
        fringe.insert(initialNode);

        while (!fringe.isEmpty()) {
            SearchNode searchNode = fringe.delMin();
            WorldState worldState = searchNode.curr();
            WorldState prevState = searchNode.prev() != null ? searchNode.prev().curr() : null;

            if (worldState.isGoal()) {
                record(searchNode);
                break;
            }

            for (WorldState neighbourState : worldState.neighbors()) {
                if (neighbourState.equals(prevState)) {
                    continue;
                }

                SearchNode neighbourNode =
                    new SearchNode(neighbourState, searchNode.moves() + 1, searchNode);
                fringe.insert(neighbourNode);
            }
        }
    }

    private void record(SearchNode finalNode) {
        while (finalNode != null) {
            S.addFirst(finalNode.curr());
            finalNode = finalNode.prev();
        }
    }

}
