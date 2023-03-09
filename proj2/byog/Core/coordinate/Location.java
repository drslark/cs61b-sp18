package byog.Core.coordinate;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Interface which manages locations.
 */
public interface Location<E extends Location<E>> {

    /**
     * Gets the coordinate ith value.
     *
     * @param i The index of the value.
     * @return The coordinate ith value.
     */
    int get(int i);

    /**
     * Gets the coordinate x value.
     *
     * @return The coordinate x value.
     */
    int getX();

    /**
     * Gets the coordinate y value.
     *
     * @return The coordinate y value.
     */
    int getY();

    /**
     * Gets the shape of the coordinate system.
     *
     * @return The shape of the coordinate system.
     */
    int[] getShape();

    /**
     * Gets the dimension of the coordinate system.
     *
     * @return The dimension of the coordinate system.
     */
    int getDimension();

    /**
     * Computes the manhattan distance between two locations.
     *
     * @param thatLocation The other location.
     * @return The manhattan distance.
     */
    int manhattanDistance(E thatLocation);

    /**
     * Gets the location corresponding to the given coordinate in the coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    E getLocation(int... coordinate);

    /**
     * A new location with an offset from this coordinate.
     *
     * @param offsetCoordinate The offset coordinate.
     * @return A new location with an offset from this coordinate.
     */
    E offset(int... offsetCoordinate);

    /**
     * Traverse content in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The content locations of the region.
     */
    LinkedHashSet<E> traverseContent(int[] shape);

    /**
     * Traverse border in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The border locations of the region.
     */
    LinkedHashSet<E> traverseBorder(int[] shape);

    /**
     * Gets all neighbours of this location.
     *
     * @return All locations of the neighbours.
     */
    Set<E> getNeighbours();

    /**
     * Gets all close neighbours of this location.
     *
     * @return All locations of the close neighbours.
     */
    Set<E> getCloseNeighbours();

    /**
     * Gets the up offset coordinate.
     *
     * @return The up offset coordinate.
     */
    int[] upOffset();

    /**
     * Gets the down offset coordinate.
     *
     * @return The down offset coordinate.
     */
    int[] downOffset();

    /**
     * Gets the left offset coordinate.
     *
     * @return The left offset coordinate.
     */
    int[] leftOffset();

    /**
     * Gets the right offset coordinate.
     *
     * @return The right offset coordinate.
     */
    int[] rightOffset();

}
