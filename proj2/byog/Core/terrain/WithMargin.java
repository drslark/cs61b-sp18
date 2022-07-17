package byog.Core.terrain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import byog.Core.coordinates.Location;

/**
 * Terrains that with margins.
 */
public interface WithMargin<E extends Location<E>> {

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
    Collection<E> getContent();

    /**
     * Gets the border locations.
     *
     * @return The locations of the border.
     */
    Collection<E> getBorder();

    /**
     * Gets the margin locations.
     *
     * @return The locations of the margin.
     */
    default Collection<E> getMargin() {
        Set<E> marginLocations = new HashSet<>();
        for (E borderLocation : getBorder()) {
            for (E location : borderLocation.getNeighbours(1)) {
                if (!contains(location)) {
                    marginLocations.add(location);
                }
            }
        }
        return marginLocations;
    }

}
