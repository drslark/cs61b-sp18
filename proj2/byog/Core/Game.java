package byog.Core;

import byog.Core.context.Context;
import byog.Core.coordinate.Location2D;
import byog.Core.interaction.string.StringInterface;
import byog.Core.interaction.ui.MainMenuInterface;
import byog.Core.interaction.ui.InteractiveInterface;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int HUD_HEIGHT = 5;

    public static final int[] SHAPE = {WIDTH, HEIGHT};
    public static final String ARCHIVE_FILE = "archive.txt";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        Location2D.initialize(SHAPE);

        Context<Location2D> context = new Context<>(
            WIDTH, HEIGHT, HUD_HEIGHT, Location2D.origin(), SHAPE, ARCHIVE_FILE
        );
        Context.setDefault(context);

        MainMenuInterface<Location2D> mainMenuInterface = new MainMenuInterface<>();
        mainMenuInterface.renderStartMenu();

        InteractiveInterface<Location2D> interactiveInterface = new InteractiveInterface<>();
        interactiveInterface.renderInteractiveInterface();
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
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        Location2D.initialize(Game.SHAPE);

        Context<Location2D> context = new Context<>(
            WIDTH, HEIGHT, HUD_HEIGHT, Location2D.origin(), SHAPE, ARCHIVE_FILE
        );
        Context.setDefault(context);

        StringInterface<Location2D> stringInterface = new StringInterface<>();

        return stringInterface.process(input);
    }

}
