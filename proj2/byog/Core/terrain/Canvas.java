package byog.Core.terrain;

import byog.Core.coordinates.Location2D;

/**
 * A canvas is used to store the information of the background.
 */
public class Canvas {

    private final int width;
    private final int height;

    /**
     * Constructor with the width and height of the background.
     * @param width The width of the background.
     * @param height The height of the background.
     */
    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width.
     * @return The width of the background.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     * @return The height of the background.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Whether location is in canvas or not.
     * @param location A given location.
     * @return Whether location is in canvas or not.
     */
    public boolean contains(Location2D location) {
        if (location.getX() < 0 || location.getX() >= width) {
            return false;
        }

        if (location.getY() < 0 || location.getY() >= height) {
            return false;
        }

        return true;
    }
}
