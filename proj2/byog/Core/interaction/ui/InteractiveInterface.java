package byog.Core.interaction.ui;

import java.awt.Color;

import byog.Core.Game;
import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.interaction.constant.Command;
import byog.Core.interaction.utils.DrawUtils;
import byog.Core.terrain.Players;
import byog.Core.terrain.view.Player;
import byog.Core.terrain.view.World;
import byog.Core.tiles.Filler;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

/**
 * The InteractiveInterface is to use control the play interface of the game.
 */
public class InteractiveInterface<E extends Location<E>> {

    private final TERenderer ter;
    private final TETile[][] worldFrame;

    /**
     * Constructor of the interactive interface.
     */
    public InteractiveInterface() {
        ter = new TERenderer();

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        ter.initialize(Context.width(), Context.height() + Context.hudHeight());
        worldFrame = new TETile[Context.width()][Context.height()];
        Filler.fillBackGround(worldFrame);
        Filler.fillWorld(worldFrame, (World<E>) Context.world());
    }

    /**
     * Render the interactive interface. User starts to play.
     */
    public void renderInteractiveInterface() {
        DrawUtils.clearKeyTypeCache();
        while (true) {
            renderWorld();
            renderDescription();
        }
    }

    private void renderWorld() {
        Filler.removePlayer(worldFrame, (Player<E>) Context.player());
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            Command command = Context.user().parseKeyToCommand(key);
            if (command == Command.QUIT_GAME) {
                try {
                    GameProcess.saveGame();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }

                GameProcess.endGame();
            } else if (!(command == Command.NULL)) {
                movePlayer(command);
            }
        }
        Filler.fillPlayer(worldFrame, (Player<E>) Context.player());
        ter.renderFrame(worldFrame);
    }

    private void renderDescription() {
        String description = getDescription();
        displayDescription(description);

        StdDraw.show();
        StdDraw.pause(20);
    }

    private String getDescription() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String description = "";
        if (x < Game.WIDTH && y < Game.HEIGHT && !worldFrame[x][y].equals(Tileset.NOTHING)) {
            description = worldFrame[x][y].description();
        }

        return description;
    }

    private void displayDescription(String description) {
        DrawUtils.cacheFont();

        StdDraw.setFont(DrawUtils.SMALL_MONACO);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(1, 32, description);

        DrawUtils.restoreFont();
    }

    private void movePlayer(Command command) {
        Context.gameInfo().setPlayer(
            Players.move((Player) Context.player(), Players.getMovement(command))
        );
    }

}
