package byog.Core.test;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import byog.Core.algorithms.AStar;
import byog.Core.coordinates.Location2D;
import byog.Core.terrain.Canvas;
import byog.Core.tile.Filler;
import byog.Core.tile.TilesInitializer;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Test the AStar algorithm.
 */
public class TestAStar {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final Map<Integer, TETile> TILES_MAP = new HashMap<Integer, TETile>() {
        {
            put(0, Tileset.NOTHING);
            put(1, Tileset.WALL);
            put(2, Tileset.FLOOR);
        }
    };

    /**
     * A visual test for the AStar algorithm.
     */
    public static void testAStarVisual() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        TERenderer ter = new TERenderer();
        ter.initialize(canvas.getWidth(), canvas.getHeight());

        int[][] world = new int[canvas.getWidth()][canvas.getHeight()];

        generateWalls(world);
        generateStartAndEnd(world);
        connectExistedFloors(world);

        TETile[][] tiles = TilesInitializer.withCanvas(canvas);
        Filler.fillWithWorld(tiles, world, TILES_MAP);

        ter.renderFrame(tiles);
    }

    private static void generateWalls(int[][] world) {
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 15; j++) {
                world[i][j] = 1;
            }
        }

        for (int i = 15; i < 20; i++) {
            for (int j = 17; j < 30; j++) {
                world[i][j] = 1;
            }
        }
    }

    private static void generateStartAndEnd(int[][] world) {
        world[9][10] = 0;
        world[15][23] = 0;
    }

    private static void connectExistedFloors(int[][] world) {
        AStar aStar = new AStar(world, new Random());
        Deque<Location2D> path = aStar.connect(new Location2D(9, 10), new Location2D(15, 23));

        for (Location2D location : path) {
            world[location.getX()][location.getY()] = 2;
        }
    }

    public static void main(String[] args) {
        testAStarVisual();
    }

}
