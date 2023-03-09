package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import byog.Core.algorithms.AStar;
import byog.Core.algorithms.AdjacencyListGraph;
import byog.Core.algorithms.Edge;
import byog.Core.algorithms.Prim;
import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.terrain.concrete.NarrowHallway;
import byog.Core.terrain.view.Hallway;
import byog.Core.terrain.view.Room;

/**
 * Hallway management.
 */
public class Hallways {

    /**
     * Generates random hallways with regard of a set of rooms.
     *
     * @param rooms A set of rooms.
     * @return The generated rooms.
     */
    public static <E extends Location<E>> LinkedHashSet<Hallway<E>> generateRandomHallways(
        LinkedHashSet<Room<E>> rooms
    ) {
        List<Room<E>> orderedRooms = new ArrayList<>(rooms);
        List<Edge> roomIndexPairs = getRoomIndexPairs(orderedRooms);
        LinkedHashSet<Hallway<E>> hallways = connectRoomsWithHallways(
            orderedRooms, roomIndexPairs
        );
        return hallways;
    }

    private static <E extends Location<E>> LinkedHashSet<Hallway<E>> connectRoomsWithHallways(
        List<Room<E>> rooms, List<Edge> roomIndexPairs
    ) {
        // hallway paths
        LinkedHashSet<Hallway<E>> hallways = new LinkedHashSet<>();

        // Connect rooms with hallways
        LinkedHashSet<E> skeletonLocations = getSkeletonLocations(rooms);
        for (Edge edge : roomIndexPairs) {
            Room<E> thisRoom = rooms.get(edge.either());
            Room<E> thatRoom = rooms.get(edge.other());

            if (!thisRoom.isMargin(thatRoom)) {
                List<Room<E>> includedRooms = new ArrayList<Room<E>>() {
                    {
                        add(thisRoom);
                        add(thatRoom);
                    }
                };
                LinkedHashSet<E> passableLocations = getPassableLocations(
                    skeletonLocations, includedRooms
                );
                AStar<E> aStar = new AStar<>(passableLocations, Context.random());
                Deque<E> locations = aStar.connect(
                    thisRoom.getCenter(), thatRoom.getCenter()
                );
                hallways.add(new NarrowHallway<>(new LinkedHashSet<>(locations)));
            }
        }
        return hallways;
    }

    private static <E extends Location<E>> List<Edge> getRoomIndexPairs(List<Room<E>> rooms) {
        int[][] adjacencyMatrixGraph = new int[rooms.size()][rooms.size()];
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i; j < rooms.size(); j++) {
                if (i == j) {
                    adjacencyMatrixGraph[i][j] = -1;
                } else {
                    Room<E> ithRoom = rooms.get(i);
                    Room<E> jthRoom = rooms.get(j);
                    int manhattanDistance =
                        ithRoom.getCenter().manhattanDistance(jthRoom.getCenter());
                    adjacencyMatrixGraph[i][j] = manhattanDistance;
                    adjacencyMatrixGraph[j][i] = manhattanDistance;
                }
            }
        }

        return new Prim(new AdjacencyListGraph(adjacencyMatrixGraph)).sortedEdges();
    }

    private static <E extends Location<E>> LinkedHashSet<E> getSkeletonLocations(
        List<Room<E>> allRooms
    ) {
        LinkedHashSet<E> skeletonLocations = new LinkedHashSet<>(Context.content());
        skeletonLocations.removeAll((Set<E>) Context.border());
        for (Room<E> room : allRooms) {
            skeletonLocations.removeAll(room.getContent());
        }

        return skeletonLocations;
    }

    private static <E extends Location<E>> LinkedHashSet<E> getPassableLocations(
        LinkedHashSet<E> skeletonLocations, List<Room<E>> includedRooms
    ) {
        LinkedHashSet<E> passableLocations = new LinkedHashSet<>(skeletonLocations);
        for (Room<E> room : includedRooms) {
            passableLocations.addAll(room.getContent());
        }
        return passableLocations;
    }

}
