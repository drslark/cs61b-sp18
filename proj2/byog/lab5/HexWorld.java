package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    /**
     * Checks if a hexagon is valid.
     *
     * @param hexagon Information about a hexagon.
     * @param position The x-coordinate and y-coordinate of a hexagon.
     */
    private static void validateLocation(Hexagon hexagon, Position position) {
        if (hexagon.size <= 1) {
            throw new RuntimeException(
                    String.format("Hexagon size should be greater than 1, got %d!", hexagon.size)
            );
        }

        if (position.x < 0) {
            throw new RuntimeException(
                    String.format(
                            "Hexagon's leftmost should be greater than 0, got %d!",
                            position.x
                    )
            );
        }

        if (position.x + hexagon.width - 1 >= WIDTH) {
            throw new RuntimeException(
                    String.format(
                            "Hexagon's rightmost should be less than %d, got %d!",
                            WIDTH,
                            position.x + hexagon.width - 1
                    )
            );
        }

        if (position.y + hexagon.height - 1 >= HEIGHT) {
            throw new RuntimeException(
                    String.format(
                            "Hexagon's upmost should be less than %d, got %d!",
                            HEIGHT,
                            position.y + hexagon.height - 1
                    )
            );
        }

        if (position.y < 0) {
            throw new RuntimeException(
                    String.format(
                            "Hexagon's bottommost should be greater than 0, got %d!",
                            position.y
                    )
            );
        }
    }

    /**
     * Fills backgrounds in some tiles.
     *
     * @param tiles The tiles to fill.
     */
    private static void fillBackground(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Fills a hexagon in some tiles.
     *
     * @param tiles The tiles to fill.
     */
    private static void fillHexagon(
            TETile[][] tiles, Hexagon hexagon, Position position, TETile tile, Random random
    ) {
        for (int i = 0; i < hexagon.width; i++) {
            Hexagon.Bounds yBound = hexagon.getColumnBounds(i);
            for (int j = yBound.lower; j < yBound.upper; j++) {
                TETile tileToFill =
                        random == null ? tile : TETile.colorVariant(tile, 50, 50, 50, random);
                tiles[position.x + i][position.y + j] = tileToFill;
            }
        }
    }

    private static Position[] generateHexagonOffsets(int hexagonSize) {
        Hexagon hexagon = new Hexagon(hexagonSize);
        int width = hexagon.width;
        int height = hexagon.height;

        int[] nums = {3, 4, 5, 4, 3};
        Position startPosition = new Position(0, 2 * hexagonSize);

        Position[] hexagonOffsets = new Position[19];

        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i]; j++) {
                hexagonOffsets[index] = new Position(startPosition.x, startPosition.y + j * height);
                index++;
            }

            if (i < nums.length / 2) {
                startPosition = startPosition.add(hexagon.bottomRightOffset());
            } else {
                startPosition = startPosition.add(hexagon.topRightOffset());
            }
        }

        return hexagonOffsets;
    }

    /**
     * Generates a random tile.
     * @param random A random number generator.
     * @return A random tile.
     */
    private static TETile generateRandomtile(Random random) {
        random = random == null ? new Random() : random;
        int tileNum = random.nextInt(7);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.SAND;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * Adds a hexagon in some tiles.
     *
     * @param tiles The tiles to fill.
     * @param hexagon Information about a hexagon.
     * @param position The position of a hexagon.
     * @param tile The tile of a hexgon.
     * @param random A random number generator.
     */
    public static void addHexagon(
            TETile[][] tiles, Hexagon hexagon, Position position, TETile tile, Random random
    ) {
        validateLocation(hexagon, position);
        fillHexagon(tiles, hexagon, position, tile, random);
    }

    /**
     * Adds some hexagons in some tiles.
     *
     * @param tiles The tiles to fill.
     * @param hexagon Information about a hexagon.
     * @param startPosition The start position of a hexagon.
     * @param random A random number generator.
     */
    public static void addHexagons(
            TETile[][] tiles, Hexagon hexagon, Position startPosition, Random random
    ) {
        Position[] hexagonOffsets = generateHexagonOffsets(hexagon.size);
        for (Position hexagonOffset : hexagonOffsets) {
            TETile randomTile = generateRandomtile(random);
            addHexagon(tiles, hexagon, startPosition.add(hexagonOffset), randomTile, random);
        }
    }

    @Test
    public void testColumns() {
        Hexagon.Bounds columnBounds;

        Hexagon hexagonSizeThree = new Hexagon(3);

        columnBounds = hexagonSizeThree.getColumnBounds(0);
        assertEquals(2, columnBounds.upper - columnBounds.lower);
        columnBounds = hexagonSizeThree.getColumnBounds(2);
        assertEquals(6, columnBounds.upper - columnBounds.lower);
        columnBounds = hexagonSizeThree.getColumnBounds(3);
        assertEquals(6, columnBounds.upper - columnBounds.lower);

        Hexagon hexagonSizeFour = new Hexagon(4);

        columnBounds = hexagonSizeFour.getColumnBounds(5);
        assertEquals(8, columnBounds.upper - columnBounds.lower);
        columnBounds = hexagonSizeFour.getColumnBounds(7);
        assertEquals(6, columnBounds.upper - columnBounds.lower);
        columnBounds = hexagonSizeFour.getColumnBounds(8);
        assertEquals(4, columnBounds.upper - columnBounds.lower);
    }

    @Test
    public void testRows() {
        Hexagon.Bounds rowBounds;

        Hexagon hexagonSizeThree = new Hexagon(3);

        rowBounds = hexagonSizeThree.getRowBounds(0);
        assertEquals(3, rowBounds.upper - rowBounds.lower);
        rowBounds = hexagonSizeThree.getRowBounds(1);
        assertEquals(5, rowBounds.upper - rowBounds.lower);
        rowBounds = hexagonSizeThree.getRowBounds(2);
        assertEquals(7, rowBounds.upper - rowBounds.lower);

        Hexagon hexagonSizeFour = new Hexagon(4);

        rowBounds = hexagonSizeFour.getRowBounds(4);
        assertEquals(10, rowBounds.upper - rowBounds.lower);
        rowBounds = hexagonSizeFour.getRowBounds(6);
        assertEquals(6, rowBounds.upper - rowBounds.lower);
        rowBounds = hexagonSizeFour.getRowBounds(7);
        assertEquals(4, rowBounds.upper - rowBounds.lower);
    }

    @Test
    public void testHexagon() {
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        fillBackground(tiles);
        addHexagon(tiles, new Hexagon(4), new Position(0, 0), Tileset.FLOWER, null);

        assertEquals(tiles[1][1], Tileset.NOTHING);
        assertEquals(tiles[2][0], Tileset.NOTHING);
        assertEquals(tiles[7][7], Tileset.NOTHING);
        assertEquals(tiles[0][8], Tileset.NOTHING);

        assertEquals(tiles[3][0], Tileset.FLOWER);
        assertEquals(tiles[0][3], Tileset.FLOWER);
        assertEquals(tiles[6][7], Tileset.FLOWER);
        assertEquals(tiles[7][6], Tileset.FLOWER);
        assertEquals(tiles[7][1], Tileset.FLOWER);
    }

    @Test
    public void testHexagons() {
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        fillBackground(tiles);
        addHexagons(tiles, new Hexagon(4), new Position(0, 0), null);

        assertEquals(tiles[7][6], Tileset.NOTHING);
        assertEquals(tiles[11][3], Tileset.NOTHING);
        assertEquals(tiles[31][32], Tileset.NOTHING);
        assertEquals(tiles[28][35], Tileset.NOTHING);
        assertEquals(tiles[28][4], Tileset.NOTHING);

        assertNotEquals(tiles[7][7], Tileset.NOTHING);
        assertNotEquals(tiles[11][4], Tileset.NOTHING);
        assertNotEquals(tiles[31][31], Tileset.NOTHING);
        assertNotEquals(tiles[28][34], Tileset.NOTHING);
        assertNotEquals(tiles[28][5], Tileset.NOTHING);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        Random random = new Random();

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        fillBackground(tiles);
        addHexagons(tiles, new Hexagon(4), new Position(1, 1), random);
        addHexagon(tiles, new Hexagon(4), new Position(40, 42), Tileset.FLOWER, random);

        ter.renderFrame(tiles);
    }

}
