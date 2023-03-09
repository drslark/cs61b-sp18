package byog.Core.coordinate;

import java.util.List;

/**
 * An interceptor to process the coordinates.
 */
@FunctionalInterface
interface CoordinateProcessInterceptor {

    /**
     * Process the coordinates.
     *
     * @param start The coordinate of the start location.
     * @param shape The shape of the GenericLocation coordinate system.
     * @param center The coordinate of the center location.
     * @param coordinate The coordinate to process.
     * @param coordinates The set of processed coordinates.
     */
    void process(int[] start, int[] shape, int[] center, int[] coordinate, List<int[]> coordinates);

}
