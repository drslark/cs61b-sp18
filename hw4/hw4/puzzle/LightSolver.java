package hw4.puzzle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A light A* puzzle solver.
 */
public class LightSolver {

    private final LinkedList<WorldState> S;

    /**
     * Constructor which solves the puzzle, computing everything necessary
     * for moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
     */
    public LightSolver(WorldState initial) {
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
        Map<WorldState, Integer> movesTo = new HashMap<>();
        Map<WorldState, WorldState> prevStateOf = new HashMap<>();

        ExtrinsicUniqueMinPQ<WorldState, Integer> fringe = new ExtrinsicUniqueMinPQ<>();

        movesTo.put(initial, 0);
        prevStateOf.put(initial, null);
        fringe.set(initial, movesTo.get(initial) + initial.estimatedDistanceToGoal());

        while (fringe.size() > 0) {
            WorldState worldState = fringe.removeMin();

            if (worldState.isGoal()) {
                record(worldState, prevStateOf);
                break;
            }

            int stateMoves = movesTo.get(worldState);

            for (WorldState neighbourState : worldState.neighbors()) {
                int neighbourMoves = movesTo.getOrDefault(neighbourState, Integer.MAX_VALUE);
                if (neighbourMoves <= stateMoves + 1) {
                    continue;
                }

                movesTo.put(neighbourState, stateMoves + 1);
                prevStateOf.put(neighbourState, worldState);
                fringe.set(
                    neighbourState,
                    movesTo.get(neighbourState) + neighbourState.estimatedDistanceToGoal()
                );
            }
        }
    }

    private void record(WorldState finalState, Map<WorldState, WorldState> prevState) {
        while (finalState != null) {
            S.addFirst(finalState);
            finalState = prevState.get(finalState);
        }
    }

}
