package byog.Core.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import byog.Core.coordinate.Location;

/**
 * The AStar algorithm to find the shortest path.
 */
public class AStar<E extends Location<E>> {

    private final Random random;

    private final WorldMap<E> worldMap;

    /**
     * Constructor with a set of passableLocations and a pseudorandom generator.
     *
     * @param passableLocations A set of passableLocations.
     * @param random A pseudorandom generator.
     */
    public AStar(Set<E> passableLocations, Random random) {
        this.random = random;
        this.worldMap = new WorldMap<>(passableLocations);
    }

    /**
     * Connect two locations in the world map.
     *
     * @param start The start location.
     * @param end The end location.
     * @return The path found by AStar.
     */
    public Deque<E> connect(E start, E end) {
        HashMap<E, Integer> distTo = new HashMap<>();
        HashMap<E, E> parentOf = new HashMap<>();

        RandomDoubleMapPQ<E, Integer> toSearch = new RandomDoubleMapPQ<>(random);

        distTo.put(start, 0);
        parentOf.put(start, null);
        toSearch.set(start, distTo.get(start) + worldMap.manhattanDistance(start, end));

        while (toSearch.size() > 0) {
            // travel a grid
            E location = toSearch.removeRandomSmallest();
            if (location.equals(end)) {
                break;
            }
            // add closer neighbours of the grid
            for (E neighbour : worldMap.getNeighbours(location)) {
                // check if a neighbour to search is closer to start
                if (distTo.getOrDefault(neighbour, Integer.MAX_VALUE) <= distTo.get(location) + 1) {
                    continue;
                }

                // find a closer path for neighbour to search or find a new neighbour
                distTo.put(neighbour, distTo.get(location) + 1);
                parentOf.put(neighbour, location);
                toSearch.set(
                    neighbour, distTo.get(neighbour) + worldMap.manhattanDistance(neighbour, end)
                );
            }
        }

        return getPath(end, parentOf);
    }

    private Deque<E> getPath(E end, Map<E, E> parentOf) {
        Deque<E> path = new ArrayDeque<>();
        for (; end != null; end = parentOf.get(end)) {
            path.addFirst(end);
        }
        return path;
    }

}
