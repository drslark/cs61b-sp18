package byog.Core.tile;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

import byog.Core.coordinates.Location2D;
import byog.Core.terrain.Door;
import byog.Core.terrain.Hallway;
import byog.Core.terrain.Room;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Fill the tiles with different terrains.
 */
public class Filler {

    private static final TETile TILE_TO_CHECK = new TETile(
            '?', Color.white, Color.black, "overlapped tile"
    );

    /**
     * Fills the tiles with rooms.
     * @param tiles The tiles to fill.
     * @param rooms The information of the rooms.
     */
    public static void fillRooms(TETile[][] tiles, Collection<Room> rooms) {
        for (Room room : rooms) {
            fillRoom(tiles, room);
            fillRoomMargin(tiles, room);
        }
    }

    /**
     * Fills the tiles with hallways.
     * @param tiles The tiles to fill.
     * @param hallways The information of the hallways.
     */
    public static void fillHallways(TETile[][] tiles, Collection<Hallway> hallways) {
        for (Hallway hallway : hallways) {
            fillHallway(tiles, hallway);
            fillHallwayMargin(tiles, hallway);
        }
    }

    /**
     * Fills the tiles with a door.
     * @param tiles The tiles to fill.
     * @param door The information of the door.
     */
    public static void fillDoor(TETile[][] tiles, Door door) {
        for (Location2D location : door.getContent()) {
            int x = location.getX();
            int y = location.getY();
            tiles[x][y] = Tileset.LOCKED_DOOR;
        }
    }

    /**
     * Fills the tiles with a room.
     * @param tiles The tiles to fill.
     * @param room The information of the room.
     */
    public static void fillRoom(TETile[][] tiles, Room room) {
        for (int i = 0; i < room.getWidth(); i++) {
            for (int j = 0; j < room.getHeight(); j++) {
                int x = room.getAnchorX() + i;
                int y = room.getAnchorY() + j;
                if (tiles[x][y].equals(Tileset.NOTHING)) {
                    tiles[x][y] = Tileset.FLOOR;
                } else if (tiles[x][y].equals(Tileset.WALL)) {
                    tiles[x][y] = Tileset.FLOOR;
                } else {
                    tiles[x][y] = TILE_TO_CHECK;
                }
            }
        }
    }

    /**
     * Fills the tiles with the margin of a room.
     * @param tiles The tiles to fill.
     * @param room The information of the room.
     */
    public static void fillRoomMargin(TETile[][] tiles, Room room) {
        for (Location2D location : room.getMargin()) {
            int x = location.getX();
            int y = location.getY();
            if (tiles[x][y] == Tileset.NOTHING) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    /**
     * Fills the tiles with a hallway.
     * @param tiles The tiles to fill.
     * @param hallway The information of the hallway.
     */
    public static void fillHallway(TETile[][] tiles, Hallway hallway) {
        for (Location2D location : hallway.getContent()) {
            tiles[location.getX()][location.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Fills the tiles with the margin of a hallway.
     * @param tiles The tiles to fill.
     * @param hallway The information of the hallway.
     */
    public static void fillHallwayMargin(TETile[][] tiles, Hallway hallway) {
        for (Location2D location : hallway.getMargin()) {
            int x = location.getX();
            int y = location.getY();
            if (tiles[x][y] == Tileset.NOTHING) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    /**
     * Fills the tiles with nothing.
     * @param tiles The tiles to fill.
     */
    public static void fillBackGround(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Fills the tiles with an integer array.
     * @param tiles The tiles to fill.
     * @param world An integer array that contains all information of the tiles.
     * @param tilesMap A map that maps integer to tile.
     */
    public static void fillWithWorld(
            TETile[][] tiles, int[][] world, Map<Integer, TETile> tilesMap
    ) {
        assert tiles.length == world.length;
        assert tiles[0].length == world[0].length;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int region = world[i][j];
                if (tilesMap.containsKey(region)) {
                    tiles[i][j] = tilesMap.get(region);
                }
            }
        }
    }

}
