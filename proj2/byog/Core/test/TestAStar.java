package byog.Core.test;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import byog.Core.algorithms.AStar;
import byog.Core.context.Context;
import byog.Core.coordinate.Location2D;
import byog.Core.tiles.Filler;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Test the AStar algorithm.
 */
public class TestAStar {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final int HUD_HEIGHT = 0;

    private static final int[] SHAPE = {WIDTH, HEIGHT};

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
        Location2D.initialize(SHAPE);

        Context<Location2D> context = new Context<>(
            WIDTH, HEIGHT, HUD_HEIGHT, Location2D.origin(), SHAPE
        );
        Context.setDefault(context);

        TERenderer ter = new TERenderer();
        ter.initialize(context.getWidth(), context.getHeight());

        Map<Location2D, Integer> world = new HashMap<>();

        generateWalls(world);
        generateStartAndEnd(world);
        connectExistedFloors(world);

        TETile[][] tiles = new TETile[Context.width()][Context.height()];
        Filler.fillWithWorld(tiles, world, TILES_MAP);

        ter.renderFrame(tiles);
    }

    private static void generateWalls(Map<Location2D, Integer> world) {
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 15; j++) {
                world.put(Context.get(i, j), 1);
            }
        }

        for (int i = 15; i < 20; i++) {
            for (int j = 17; j < 30; j++) {
                world.put(Context.get(i, j), 1);
            }
        }
    }

    private static void generateStartAndEnd(Map<Location2D, Integer> world) {
        world.put(Context.get(9, 10), 0);
        world.put(Context.get(15, 23), 0);
    }

    private static Set<Location2D> generatePassableLocations(Map<Location2D, Integer> world) {
        Set<Location2D> passableLocations = new HashSet<>();

        for (Location2D location : (Set<Location2D>) Context.content()) {
            if (world.getOrDefault(location, 0) == 0) {
                passableLocations.add(location);
            }
        }

        return passableLocations;
    }

    private static void connectExistedFloors(Map<Location2D, Integer> world) {
        Set<Location2D> passableLocations = generatePassableLocations(world);
        AStar<Location2D> aStar = new AStar<>(passableLocations, new Random());
        Deque<Location2D> path = aStar.connect(Context.get(9, 10), Context.get(15, 23));

        for (Location2D location : path) {
            world.put(location, 2);
        }
    }

    public static void main(String[] args) {
        testAStarVisual();
    }

}
