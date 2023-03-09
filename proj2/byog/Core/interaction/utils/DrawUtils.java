package byog.Core.interaction.utils;

import java.awt.Font;

import edu.princeton.cs.introcs.StdDraw;

/**
 * Utils to help draw the interface.
 */
public class DrawUtils {

    public static final Font BIG_ARIAL = new Font("Arial", Font.PLAIN, 25);

    public static final Font SMALL_ARIAL = new Font("Arial", Font.PLAIN, 18);

    public static final Font SMALL_MONACO = new Font("Monaco", Font.PLAIN, 20);

    private static Font cachedFont = StdDraw.getFont();

    /**
     * Clears the queue where stores the typed characters.
     */
    public static void clearKeyTypeCache() {
        while (StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
    }

    /**
     * Caches the current font.
     */
    public static void cacheFont() {
        cachedFont = StdDraw.getFont();
    }

    /**
     * Restores the current font.
     */
    public static void restoreFont() {
        StdDraw.setFont(cachedFont);
    }

}
