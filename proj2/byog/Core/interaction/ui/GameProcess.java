package byog.Core.interaction.ui;

import java.awt.Color;

import byog.Core.context.Context;
import byog.Core.context.GameInfo;
import byog.Core.coordinate.Location;
import byog.Core.interaction.utils.DrawUtils;
import byog.Core.persistence.Archiver;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Controls some processes of the game.
 */
class GameProcess {

    /**
     * Saves the game.
     */
    static <E extends Location<E>> void saveGame() {
        Archiver.save((GameInfo<E>) Context.gameInfo(), Context.archiveFile());

        showSlogan("GAME SAVED!");
    }

    /**
     * Ends the game.
     */
    static void endGame() {
        showSlogan("GOODBYE!");

        System.exit(0);
    }

    private static void showSlogan(String slogan) {
        StdDraw.setXscale();
        StdDraw.setYscale();

        StdDraw.clear(Color.BLACK);

        StdDraw.setFont(DrawUtils.BIG_ARIAL);
        StdDraw.text(0.5, 0.5, slogan);
        StdDraw.show();

        StdDraw.pause(1000);
    }

}
