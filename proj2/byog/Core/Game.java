package byog.Core;

import java.util.Collection;
import java.util.Random;

import byog.Core.input.ActionParser;
import byog.Core.terrain.Canvas;
import byog.Core.terrain.Door;
import byog.Core.terrain.Doors;
import byog.Core.terrain.Hallway;
import byog.Core.terrain.Hallways;
import byog.Core.terrain.Room;
import byog.Core.terrain.Rooms;
import byog.Core.tile.Filler;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Returns the game's te-renderer.
     */
    public TERenderer getTERenderer() {
        return ter;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        ActionParser actionParser = new ActionParser(input);
        Random random = new Random(actionParser.getSeed());

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = new TETile[canvas.getWidth()][canvas.getHeight()];

        Collection<Room> rooms = Rooms.generateRandomRooms(canvas, 50, random);
        Collection<Hallway> hallways = Hallways.generateHallways(canvas, rooms, random);
        Door door = Doors.generateRandomDoor(rooms, hallways, random);

        Filler.fillBackGround(finalWorldFrame);
        Filler.fillRooms(finalWorldFrame, rooms);
        Filler.fillHallways(finalWorldFrame, hallways);
        Filler.fillDoor(finalWorldFrame, door);

        return finalWorldFrame;
    }
}
