package byog.Core.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import byog.Core.coordinates.Location2D;

/**
 * The AStar algorithm to find the shortest path.
 */
public class AStar {

    private final Random random;

    private final WorldMap worldMap;

    /**
     * Constructor with a 2-d int array and a pseudorandom generator.
     *
     * @param map A 2-d int array.
     * @param random A pseudorandom generator.
     */
    public AStar(int[][] map, Random random) {
        this.random = random;
        this.worldMap = new WorldMap(map);
    }

    /**
     * Connect two locations in the world map.
     *
     * @param start The start location.
     * @param end The end location.
     * @return The path found by AStar.
     */
    public Deque<Location2D> connect(Location2D start, Location2D end) {
        HashMap<Location2D, Integer> distTo = new HashMap<>();
        HashMap<Location2D, Location2D> parentOf = new HashMap<>();

        RandomDoubleMapPQ<Location2D, Integer> toSearch = new RandomDoubleMapPQ<>(random);

        distTo.put(start, 0);
        parentOf.put(start, null);
        toSearch.set(start, distTo.get(start) + worldMap.manhattanDistance(start, end));

        while (toSearch.size() > 0) {
            // travel a grid
            Location2D location = toSearch.removeRandomSmallest();
            if (location.equals(end)) {
                break;
            }
            // add closer neighbours of the grid
            for (Location2D neighbour : worldMap.getNeighbours(location)) {
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

    private Deque<Location2D> getPath(Location2D end, Map<Location2D, Location2D> parentOf) {
        Deque<Location2D> path = new ArrayDeque<>();
        for (; end != null; end = parentOf.get(end)) {
            path.addFirst(end);
        }
        return path;
    }

}
