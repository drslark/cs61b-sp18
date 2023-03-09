package byog.Core.tiles;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.coordinate.Location2D;
import byog.Core.terrain.view.Door;
import byog.Core.terrain.view.Hallway;
import byog.Core.terrain.view.Player;
import byog.Core.terrain.view.Room;
import byog.Core.terrain.view.World;
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
     * 
     * @param tiles The tiles to fill.
     * @param world The information of the world.
     */
    public static <E extends Location<E>> void fillWorld(TETile[][] tiles, World<E> world) {
        for (E location : world.getContent()) {
            tiles[location.getX()][location.getY()] = Tileset.FLOOR;
        }

        for (E location : world.getMargin()) {
            tiles[location.getX()][location.getY()] = Tileset.WALL;
        }

        E location = world.getDoorLocation();
        tiles[location.getX()][location.getY()] = Tileset.LOCKED_DOOR;
    }

    /**
     * Fills the tiles with a player.
     * 
     * @param tiles The tiles to fill.
     * @param player The information of the player.
     */
    public static <E extends Location<E>> void fillPlayer(TETile[][] tiles, Player<E> player) {
        E location = player.getLocation();
        tiles[location.getX()][location.getY()] = Tileset.PLAYER;
    }

    /**
     * Removes a player from the tiles.
     * 
     * @param tiles The tiles.
     * @param player The information of the player.
     */
    public static <E extends Location<E>> void removePlayer(TETile[][] tiles, Player<E> player) {
        E location = player.getLocation();
        tiles[location.getX()][location.getY()] = Tileset.FLOOR;
    }

    /**
     * Fills the tiles with rooms.
     * 
     * @param tiles The tiles to fill.
     * @param rooms The information of the rooms.
     */
    public static <E extends Location<E>> void fillRooms(TETile[][] tiles, Set<Room<E>> rooms) {
        for (Room<E> room : rooms) {
            fillRoom(tiles, room);
            fillRoomMargin(tiles, room);
        }
    }

    /**
     * Fills the tiles with hallways.
     * 
     * @param tiles The tiles to fill.
     * @param hallways The information of the hallways.
     */
    public static <E extends Location<E>> void fillHallways(
        TETile[][] tiles, Set<Hallway<E>> hallways
    ) {
        for (Hallway<E> hallway : hallways) {
            fillHallway(tiles, hallway);
            fillHallwayMargin(tiles, hallway);
        }
    }

    /**
     * Fills the tiles with a door.
     * 
     * @param tiles The tiles to fill.
     * @param door The information of the door.
     */
    public static <E extends Location<E>> void fillDoor(TETile[][] tiles, Door<E> door) {
        E location = door.getLocation();
        int x = location.getX();
        int y = location.getY();
        tiles[x][y] = Tileset.LOCKED_DOOR;
    }

    /**
     * Fills the tiles with a room.
     * 
     * @param tiles The tiles to fill.
     * @param room The information of the room.
     */
    public static <E extends Location<E>> void fillRoom(TETile[][] tiles, Room<E> room) {
        for (E location : room.getContent()) {
            int x = location.getX();
            int y = location.getY();
            if (tiles[x][y].equals(Tileset.NOTHING)) {
                tiles[x][y] = Tileset.FLOOR;
            } else if (tiles[x][y].equals(Tileset.WALL)) {
                tiles[x][y] = Tileset.FLOOR;
            } else {
                tiles[x][y] = TILE_TO_CHECK;
            }
        }
    }

    /**
     * Fills the tiles with the margin of a room.
     * 
     * @param tiles The tiles to fill.
     * @param room The information of the room.
     */
    public static <E extends Location<E>> void fillRoomMargin(TETile[][] tiles, Room<E> room) {
        for (E location : room.getMargin()) {
            int x = location.getX();
            int y = location.getY();
            if (tiles[x][y] == Tileset.NOTHING) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    /**
     * Fills the tiles with a hallway.
     * 
     * @param tiles The tiles to fill.
     * @param hallway The information of the hallway.
     */
    public static <E extends Location<E>> void fillHallway(
        TETile[][] tiles, Hallway<E> hallway
    ) {
        for (E location : hallway.getContent()) {
            tiles[location.getX()][location.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Fills the tiles with the margin of a hallway.
     *
     * @param tiles The tiles to fill.
     * @param hallway The information of the hallway.
     */
    public static <E extends Location<E>> void fillHallwayMargin(
        TETile[][] tiles, Hallway<E> hallway
    ) {
        for (E location : hallway.getMargin()) {
            int x = location.getX();
            int y = location.getY();
            if (tiles[x][y] == Tileset.NOTHING) {
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    /**
     * Fills the tiles with nothing.
     *
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
     * Fills the tiles with an Integer to TETile map.
     *
     * @param tiles The tiles to fill.
     * @param world A map that contains all information of the world.
     * @param tilesMap A map that maps integer to tile.
     */
    public static void fillWithWorld(
        TETile[][] tiles, Map<Location2D, Integer> world, Map<Integer, TETile> tilesMap
    ) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Location2D location = Context.get(i, j);
                int type = world.getOrDefault(location, 0);
                tiles[i][j] = tilesMap.get(type);
            }
        }
    }

}
