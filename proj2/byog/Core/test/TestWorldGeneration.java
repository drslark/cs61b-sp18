package byog.Core.test;

import java.util.Random;

import byog.Core.Game;
import byog.Core.utils.RandomUtils;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test the game.
 */
public class TestWorldGeneration {

    /**
     * Test the world generation visually.
     */
    public static void testVisual() {
        String input = "N" + RandomUtils.nextPositiveLong(new Random()) + "S";

        TERenderer ter = new TERenderer();
        ter.initialize(Game.WIDTH, Game.HEIGHT);

        Game game = new Game();
        TETile[][] tiles = game.playWithInputString(input);
        ter.renderFrame(tiles);
    }

    /**
     * Test the world generation twice with same seed.
     */
    @Test
    public void testRepeatable() {
        for (int i = 0; i < 5000; i++) {
            String input = "N" + RandomUtils.nextPositiveLong(new Random()) + "S";

            Game game = new Game();
            TETile[][] firstTiles = game.playWithInputString(input);
            TETile[][] secondTiles = game.playWithInputString(input);
            compareTwoWorld(firstTiles, secondTiles);
        }
    }

    private void compareTwoWorld(TETile[][] firstWorld, TETile[][] secondWorld) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                assertEquals(firstWorld[i][j], secondWorld[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        testVisual();
    }

}
