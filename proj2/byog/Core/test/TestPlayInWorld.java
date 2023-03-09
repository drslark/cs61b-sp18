package byog.Core.test;

import java.util.Random;

import byog.Core.Game;
import byog.Core.utils.RandomUtils;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test the function playWithInputString.
 */
public class TestPlayInWorld {

    private static final String ACTIONS = "WASD";

    /**
     * Test the movements visually.
     */
    public static void testMovementsVisual() {
        String inputTemplate = "N" + RandomUtils.nextPositiveLong(new Random()) + "S";
        String action = "WWAASSDDWASDWSAD";

        TERenderer ter = new TERenderer();
        ter.initialize(Game.WIDTH, Game.HEIGHT);

        Game game = new Game();
        for (int i = 0; i < action.length() + 1; i++) {
            TETile[][] tiles = game.playWithInputString(inputTemplate + action.substring(0, i));
            ter.renderFrame(tiles);
            StdDraw.pause(1000);
        }
    }

    /**
     * Test the load process visually.
     */
    public static void testLoadVisual() {
        String inputTemplate = "N" + RandomUtils.nextPositiveLong(new Random()) + "S";
        String action = "AAAAAAAAAAAAAAAA:Q";

        TERenderer ter = new TERenderer();
        ter.initialize(Game.WIDTH, Game.HEIGHT);

        Game game = new Game();
        for (int i = 0; i < action.length() + 1; i++) {
            TETile[][] tiles = game.playWithInputString(inputTemplate + action.substring(0, i));
            ter.renderFrame(tiles);
            StdDraw.pause(1000);
        }

        String[] clearAndLoad = {"N", "L"};
        for (String input : clearAndLoad) {
            TETile[][] tiles = game.playWithInputString(input);
            ter.renderFrame(tiles);
            StdDraw.pause(1000);
        }

    }

    /**
     * Test playing in the world in some special cases.
     */
    @Test
    public void testSpecialCases() {
        String baseInput = "N999SDDDWWWDDD";
        String[][] inputSequences = {
            {"N999SDDD:Q", "LWWWDDD"},
            {"N999SDDD:Q", "LWWW:Q", "LDDD:Q"},
            {"N999SDDD:Q", "L:Q", "L:Q", "LWWWDDD"}
        };

        Game game = new Game();
        TETile[][] baseTitles = game.playWithInputString(baseInput);

        for (String[] inputSequence : inputSequences) {
            TETile[][] titles = null;
            for (String input : inputSequence) {
                titles = game.playWithInputString(input);
            }
            compareTwoWorld(baseTitles, titles);
        }
    }

    /**
     * Test playing in the world twice with same seed.
     */
    @Test
    public void testRepeatable() {
        for (int i = 0; i < 5000; i++) {
            Random random = new Random();
            String input = "N" + RandomUtils.nextPositiveLong(random) + "S"
                + randomActionSequence(8, random);

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

    private String randomActionSequence(int length, Random random) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(ACTIONS.charAt(random.nextInt(ACTIONS.length())));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        testMovementsVisual();
        testLoadVisual();
    }

}
