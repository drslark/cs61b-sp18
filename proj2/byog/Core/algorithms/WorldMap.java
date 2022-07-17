package byog.Core.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import byog.Core.coordinates.Location2D;

/**
 * The world map is used to manage locations in AStar.
 */
class WorldMap {

    private final int[][] map;

    private final Map<Location2D, Map<Location2D, Integer>> cachedDistance;

    /**
     * Constructor with a 2-d int array.
     *
     * @param map A 2-d int array.
     */
    WorldMap(int[][] map) {
        this.map = map;
        this.cachedDistance = new HashMap<>();
    }

    /**
     * Gets all neighbours of this location in the world map.
     *
     * @return All locations of the neighbours.
     */
    Set<Location2D> getNeighbours(Location2D location) {
        Set<Location2D> neighbours = new HashSet<>();
        Collection<Location2D> closeNeighbours = location.getCloseNeighbours(1);

        for (Location2D neighbour : closeNeighbours) {
            if (isInMap(neighbour) && isPassable(neighbour)) {
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
    int manhattanDistance(Location2D thisLocation, Location2D thatLocation) {
        if (!cachedDistance.containsKey(thisLocation)
            || !cachedDistance.get(thisLocation).containsKey(thatLocation)) {
            cachedDistance.putIfAbsent(thisLocation, new HashMap<>());
            cachedDistance.get(thisLocation).put(
                thatLocation, Location2D.manhattanDistance(thisLocation, thatLocation)
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
    boolean isPassable(Location2D location) {
        return map[location.getX()][location.getY()] == 0;
    }

    /**
     * To judge if some location is in the world map.
     *
     * @param location The given location.
     * @return If one location is in the world map.
     */
    boolean isInMap(Location2D location) {
        if (location.getX() < 0 || location.getX() >= map.length) {
            return false;
        }

        if (location.getY() < 0 || location.getY() >= map[0].length) {
            return false;
        }

        return true;
    }

}
