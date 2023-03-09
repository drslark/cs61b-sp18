package byog.Core.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byog.Core.coordinate.Location;

/**
 * The world map is used to manage locations in AStar.
 */
class WorldMap<E extends Location<E>> {

    private final Set<E> locations;

    private final Map<E, Map<E, Integer>> cachedDistance;

    /**
     * Constructor with a set of locations.
     *
     * @param locations A set of locations.
     */
    WorldMap(Set<E> locations) {
        this.locations = locations;
        this.cachedDistance = new HashMap<>();
    }

    /**
     * Gets all neighbours of this location in the world map.
     *
     * @return All locations of the neighbours.
     */
    Set<E> getNeighbours(E location) {
        Set<E> neighbours = new HashSet<>();
        Set<E> closeNeighbours = location.getCloseNeighbours();

        for (E neighbour : closeNeighbours) {
            if (isPassable(location)) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    /**
     * Computes the manhattan distance between two locations.
     *
     * @param thisLocation One location.
     * @param thatLocation The other location.
     * @return The manhattan distance.
     */
    int manhattanDistance(E thisLocation, E thatLocation) {
        if (!cachedDistance.containsKey(thisLocation)
            || !cachedDistance.get(thisLocation).containsKey(thatLocation)) {
            cachedDistance.putIfAbsent(thisLocation, new HashMap<>());
            cachedDistance.get(thisLocation).put(
                thatLocation, thisLocation.manhattanDistance(thatLocation)
            );
        }

        return cachedDistance.get(thisLocation).get(thatLocation);
    }

    /**
     * To judge if one location is passable.
     *
     * @param location The given location.
     * @return If one location is passable.
     */
    boolean isPassable(E location) {
        return locations.contains(location);
    }

}
