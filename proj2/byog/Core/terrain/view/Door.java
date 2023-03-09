package byog.Core.terrain.view;

import byog.Core.coordinate.Location;

/**
 * The door terrain.
 */
public interface Door<E extends Location<E>> extends Terrain<E> {

    /**
     * Gets the location of the door.
     *
     * @return The door location.
     */
    E getLocation();

}
