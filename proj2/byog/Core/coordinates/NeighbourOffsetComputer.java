package byog.Core.coordinates;

import java.util.Collection;

/**
 * A function template to process the offsets.
 */
@FunctionalInterface
interface NeighbourOffsetComputer {

    /**
     * Process the neighbour offsets.
     * @param neighbourOffsets The neighbour offsets.
     * @param increments An array that contains the increments.
     * @param pointers An array that points to the increment.
     */
    void process(
            Collection<LocationTemplate> neighbourOffsets, double[] increments, int[] pointers
    );

}
