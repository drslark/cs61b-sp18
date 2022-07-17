package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import byog.Core.algorithms.AStar;
import byog.Core.algorithms.AdjacencyListGraph;
import byog.Core.algorithms.Edge;
import byog.Core.algorithms.Prim;
import byog.Core.coordinates.Location2D;

/**
 * Hallway management.
 */
public class Hallways {

    /**
     * Generates random hallways with regard of a collection of rooms.
     *
     * @param canvas The canvas of the background.
     * @param rooms A collection of rooms.
     * @param random A pseudorandom generator.
     * @return Generated rooms.
     */
    public static Collection<Hallway> generateHallways(
        Canvas canvas, Collection<Room> rooms, Random random
    ) {
        List<Room> orderedRooms = new ArrayList<>(rooms);
        List<Edge> roomIndexPairs = getRoomIndexPairs(orderedRooms);
        int[][] templateMap = generateTemplateMap(canvas, orderedRooms);
        Collection<Hallway> hallways = connectRoomsWithHallways(
            orderedRooms, roomIndexPairs, templateMap, random
        );
        return hallways;
    }

    private static Collection<Hallway> connectRoomsWithHallways(
        List<Room> rooms, List<Edge> roomIndexPairs, int[][] templateMap, Random random
    ) {
        // Hallway paths
        List<Hallway> hallways = new ArrayList<>();

        // Connect rooms with hallways
        for (Edge edge : roomIndexPairs) {
            Room thisRoom = rooms.get(edge.either());
            Room thatRoom = rooms.get(edge.other());

            if (!Rooms.isMargin(thisRoom, thatRoom)) {
                // prepare for A*
                fillRoomsInTemplateMap(templateMap, new Room[]{thisRoom, thatRoom}, 0);

                AStar aStar = new AStar(templateMap, random);
                Deque<Location2D> locations = aStar.connect(
                        thisRoom.getCenterLocation(), thatRoom.getCenterLocation()
                );

                // cancel prepare for A*
                fillRoomsInTemplateMap(templateMap, new Room[]{thisRoom, thatRoom}, 1);
                hallways.add(new Hallway(locations));
            }
        }

        return hallways;
    }

    private static List<Edge> getRoomIndexPairs(List<Room> rooms) {
        int[][] adjacencyMatrixGraph = new int[rooms.size()][rooms.size()];
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i; j < rooms.size(); j++) {
                if (i == j) {
                    adjacencyMatrixGraph[i][j] = -1;
                } else {
                    Room ithRoom = rooms.get(i);
                    Room jthRoom = rooms.get(j);
                    int manhattanDistance = Location2D.manhattanDistance(
                            ithRoom.getCenterLocation(), jthRoom.getCenterLocation()
                    );
                    adjacencyMatrixGraph[i][j] = manhattanDistance;
                    adjacencyMatrixGraph[j][i] = manhattanDistance;
                }
            }
        }

        return new Prim(new AdjacencyListGraph(adjacencyMatrixGraph)).sortedEdges();
    }

    private static int[][] generateTemplateMap(Canvas canvas, List<Room> rooms) {
        // template map
        int[][] templateMap = new int[canvas.getWidth()][canvas.getHeight()];
        for (int i = 0; i < templateMap.length; i++) {
            templateMap[i][0] = -1;
            templateMap[i][canvas.getHeight() - 1] = -1;
        }

        // border can not access
        for (int i = 0; i < templateMap[0].length; i++) {
            templateMap[0][i] = -1;
            templateMap[canvas.getWidth() - 1][i] = -1;
        }

        for (Room room : rooms) {
            for (int i = 0; i < room.getWidth(); i++) {
                for (int j = 0; j < room.getHeight(); j++) {
                    templateMap[room.getAnchorX() + i][room.getAnchorY() + j] = 1;
                }
            }
        }

        return templateMap;
    }

    private static void fillRoomsInTemplateMap(int[][] templateMap, Room[] rooms, int value) {
        for (Room room : rooms) {
            for (int i = 0; i < room.getWidth(); i++) {
                for (int j = 0; j < room.getHeight(); j++) {
                    templateMap[room.getAnchorX() + i][room.getAnchorY() + j] = value;
                }
            }
        }
    }

}
