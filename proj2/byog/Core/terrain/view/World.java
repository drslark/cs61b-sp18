package byog.Core.terrain.view;

import byog.Core.coordinate.Location;

/**
 * The world terrain.
 */
public interface World<E extends Location<E>> extends Terrain<E> {

    /**
     * Gets the location of the door in the world.
     *
     * @return The location of the door
     */
    E getDoorLocation();

}
