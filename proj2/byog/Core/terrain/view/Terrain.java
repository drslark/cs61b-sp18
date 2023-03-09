package byog.Core.terrain.view;

import java.util.Set;

import byog.Core.coordinate.Location;

/**
 * The interface of all terrains.
 */
public interface Terrain<E extends Location<E>> {

    /**
     * Whether the location is contained or not.
     *
     * @return If the location is contained.
     */
    boolean contains(E location);

    /**
     * Gets the content locations.
     *
     * @return The locations of the content.
     */
    Set<E> getContent();

    /**
     * Gets the border locations.
     *
     * @return The locations of the border.
     */
    Set<E> getBorder();

    /**
     * Gets the margin locations.
     *
     * @return The locations of the margin.
     */
    Set<E> getMargin();

}
