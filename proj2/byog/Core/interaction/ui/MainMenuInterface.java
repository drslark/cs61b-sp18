package byog.Core.interaction.ui;

import java.awt.Color;
import java.util.Random;

import byog.Core.context.Context;
import byog.Core.context.GameInfo;
import byog.Core.coordinate.Location;
import byog.Core.interaction.User;
import byog.Core.interaction.constant.Command;
import byog.Core.interaction.utils.DrawUtils;
import byog.Core.terrain.Players;
import byog.Core.terrain.Worlds;
import edu.princeton.cs.introcs.StdDraw;

/**
 * The MainMenuInterface is to use control the main manu of the game.
 */
public class MainMenuInterface<E extends Location<E>> {

    private long prevTime;
    private boolean isFlashOn;

    /**
     * Constructor of the main menu interface.
     */
    public MainMenuInterface() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        Context.gameInfo().setUser(new User());

        prevTime = System.nanoTime();
        isFlashOn = false;
    }

    /**
     * Render the start menu.
     */
    public void renderStartMenu() {
        displayStartMenu();
        StdDraw.show();
        StdDraw.pause(20);

        DrawUtils.clearKeyTypeCache();

        Command command;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char key = StdDraw.nextKeyTyped();
            command = Context.gameInfo().getUser().parseKeyToCommand(key);

            if (!(command == Command.NULL)) {
                break;
            }
        }

        if (command == Command.NEW_GAME) {
            renderSeedPrompt();
        } else if (command == Command.LOAD_GAME) {
            try {
                Context.setGameInfo((GameInfo<E>) GameInfo.loadGameInfo());
                Players.setPassableLocations(Context.world().getContent());
                Players.initializeMovements();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                GameProcess.endGame();
            }
        } else {
            GameProcess.endGame();
        }

    }

    private void displayStartMenu() {
        StdDraw.clear(Color.BLACK);

        StdDraw.setFont(DrawUtils.BIG_ARIAL);
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");

        StdDraw.setFont(DrawUtils.SMALL_ARIAL);
        StdDraw.text(0.5, 0.55, "New Game (N)");
        StdDraw.text(0.5, 0.5, "Load Game (L)");
        StdDraw.text(0.5, 0.45, "Quit (Q)");
    }

    /**
     * Render the seed prompt.
     */
    private void renderSeedPrompt() {
        StringBuilder seedCache = new StringBuilder();

        StdDraw.setFont(DrawUtils.SMALL_MONACO);

        DrawUtils.clearKeyTypeCache();

        while (true) {
            renderSeedInfo(seedCache);

            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }

            char key = StdDraw.nextKeyTyped();
            Command command = Context.gameInfo().getUser().parseKeyToCommand(key);

            if (command == Command.CONFIRM_SEED) {
                break;
            }

            if (Character.isDigit(key)) {
                if (seedCache.length() < 18) {
                    seedCache.append(key);
                }
            }
        }

        long seed = seedCache.length() == 0
            ? new Random().nextLong() : Long.parseLong(seedCache.toString());
        initializeGameInfo(seed);
    }

    private void initializeGameInfo(long seed) {
        Context.user().setRandom(seed);
        Context.gameInfo().setWorld(Worlds.generateRandomWorld());
        Players.setPassableLocations(Context.world().getContent());
        Context.gameInfo().setPlayer(Players.generateRandomPlayer());
        Players.initializeMovements();
    }

    private void renderSeedInfo(StringBuilder seedCache) {
        StdDraw.clear(Color.BLACK);

        displaySeedHint();
        displaySeed(seedCache);

        StdDraw.show();
        StdDraw.pause(20);
    }

    private void displaySeedHint() {
        StdDraw.text(0.5, 0.55, "Please enter a seed: (\"s\" to finish)");
    }

    private void displaySeed(StringBuilder seedCache) {
        if (isFlashOn) {
            StdDraw.text(0.5, 0.5, seedCache + "|");
        } else {
            StdDraw.text(0.5, 0.5, seedCache.toString());
        }

        long currTime = System.nanoTime();
        if ((currTime - prevTime) / 1e9 > 0.5) {
            isFlashOn = !isFlashOn;
            prevTime = currTime;
        }
    }

}
