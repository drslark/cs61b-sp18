package hw4.puzzle;

public interface WorldState {

    /**
     * Provides an estimate of the number of moves to reach the goal.
     * Must be less than or equal to the correct distance.
     */
    int estimatedDistanceToGoal();

    /**
     * Provides an iterable of all the neighbors of this WorldState.
     */
    Iterable<WorldState> neighbors();

    /**
     * Checks if this WorldState is the goal.
     */
    default boolean isGoal() {
        return estimatedDistanceToGoal() == 0;
    }

}
