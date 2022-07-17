package byog.Core.test;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

import byog.Core.Game;
import byog.TileEngine.TETile;

/**
 * Test the game.
 */
public class TestGame {

    /**
     * Test the game visually.
     */
    public static void testGameVisual() {
        String input = "N" + new Random().nextLong() + "S";

        Game game = new Game();
        game.getTERenderer().initialize(Game.WIDTH, Game.HEIGHT);
        TETile[][] tiles = game.playWithInputString(input);
        game.getTERenderer().renderFrame(tiles);
    }

    /**
     * Test the game twice with same seed.
     */
    @Test
    public void testGameRepeatable() {
        for (int i = 0; i < 5000; i++) {
            String input = "N" + new Random().nextLong() + "S";

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
        testGameVisual();
    }

}
