package byog.Core.terrain.view;

import byog.Core.coordinate.Location;

/**
 * The player terrain.
 */
public interface Player<E extends Location<E>> extends Terrain<E> {

    /**
     * Gets the location of the player.
     *
     * @return The player location.
     */
    E getLocation();

}
