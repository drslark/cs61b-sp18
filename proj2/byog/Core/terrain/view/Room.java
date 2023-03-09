package byog.Core.terrain.view;

import byog.Core.coordinate.Location;

/**
 * The room terrain.
 */
public interface Room<E extends Location<E>> extends Terrain<E> {

    E getCenter();

    /**
     * Whether one room is overlap with another.
     *
     * @param thatRoom another room.
     * @return Whether one room is overlap with another.
     */
    boolean isOverlap(Room<E> thatRoom);

    /**
     * Whether one room is margin with another.
     *
     * @param thatRoom another room.
     * @return Whether one room is margin with another.
     */
    boolean isMargin(Room<E> thatRoom);

}
