package byog.Core.coordinates;

import java.util.Collection;

/**
 * Location interface which manages neighbours.
 * @param <E> Data structure to represent neighbours.
 */
public interface Location<E extends Location<E>> {

    /**
     * Neighbours of current location.
     * @param eps The location interval.
     * @return A collection of neighbours.
     */
    Collection<E> getNeighbours(int eps);

    /**
     * Close neighbours of current location.
     * @param eps The location interval.
     * @return A collection of close neighbours.
     */
    Collection<E> getCloseNeighbours(int eps);

}
