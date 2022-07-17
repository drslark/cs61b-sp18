package byog.Core.tile;

import byog.Core.terrain.Canvas;
import byog.TileEngine.TETile;

/**
 * Tile array generator.
 */
public class TilesInitializer {

    /**
     * Generates a tile array with a canvas.
     * @param canvas The template canvas.
     * @return A tile array.
     */
    public static TETile[][] withCanvas(Canvas canvas) {
        return new TETile[canvas.getWidth()][canvas.getHeight()];
    }

}
