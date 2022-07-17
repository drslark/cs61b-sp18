package byog.Core.terrain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import byog.Core.coordinates.Location2D;

/**
 * The Room is used to store information of the room.
 */
public class Room implements WithMargin<Location2D> {

    private final int hashCode;

    private final Location2D anchorLocation;
    private final Location2D centerLocation;

    private final int width;
    private final int height;

    /**
     * Constructor with anchorLocation, width and height.
     *
     * @param anchorLocation The location of the anchor point.
     * @param width The width of the room.
     * @param height The height of the room.
     */
    public Room(Location2D anchorLocation, int width, int height) {
        this.anchorLocation = anchorLocation;
        this.width = width;
        this.height = height;

        this.centerLocation = this.anchorLocation.offset(width / 2, height / 2);

        hashCode = Objects.hash(anchorLocation, centerLocation, width, height);
    }

    /**
     * Constructor with anchorLocation, centerLocation, width and height.
     *
     * @param anchorLocation The location of the anchor point.
     * @param centerLocation The location of the center point.
     * @param width The width of the room.
     * @param height The height of the room.
     */
    public Room(Location2D anchorLocation, Location2D centerLocation, int width, int height) {
        this.anchorLocation = anchorLocation;
        this.centerLocation = centerLocation;
        this.width = width;
        this.height = height;

        hashCode = Objects.hash(anchorLocation, centerLocation, width, height);
    }

    /**
     * Gets the location of the anchor point.
     *
     * @return The location of the anchor point.
     */
    public Location2D getAnchorLocation() {
        return anchorLocation;
    }

    /**
     * Gets the location of the center point.
     *
     * @return The location of the center point.
     */
    public Location2D getCenterLocation() {
        return centerLocation;
    }

    /**
     * Gets the coordinate x of the anchor point.
     *
     * @return The coordinate x of the anchor point.
     */
    public int getAnchorX() {
        return anchorLocation.getX();
    }

    /**
     * Gets the coordinate y of the anchor point.
     *
     * @return The coordinate y of the anchor point.
     */
    public int getAnchorY() {
        return anchorLocation.getY();
    }

    /**
     * Gets the coordinate x of the center point.
     *
     * @return The coordinate x of the center point.
     */
    public int getCenterX() {
        return centerLocation.getX();
    }

    /**
     * Gets the coordinate y of the center point.
     *
     * @return The coordinate y of the center point.
     */
    public int getCenterY() {
        return centerLocation.getY();
    }

    /**
     * Gets the width of the room.
     *
     * @return The width of the room.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the room.
     *
     * @return The height of the room.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Whether the given location is contained in the room or not.
     *
     * @param location the given location.
     * @return Whether the given location is contained in the room or not.
     */
    @Override
    public boolean contains(Location2D location) {
        if (
            location.getX() < anchorLocation.getX()
                || location.getX() >= anchorLocation.getX() + width
        ) {
            return false;
        }

        if (
            location.getY() < anchorLocation.getY()
                || location.getY() >= anchorLocation.getY() + height
        ) {
            return false;
        }

        return true;
    }

    /**
     * Gets the locations of the content.
     *
     * @return The locations of the content.
     */
    @Override
    public Collection<Location2D> getContent() {
        Set<Location2D> contentLocations = new HashSet<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                contentLocations.add(anchorLocation.offset(i, j));
            }
        }

        return contentLocations;
    }

    /**
     * Gets the locations of the border.
     *
     * @return The locations of the border.
     */
    @Override
    public Collection<Location2D> getBorder() {
        Set<Location2D> borderLocations = new HashSet<>();
        for (int i = 0; i < width; i++) {
            borderLocations.add(anchorLocation.offset(i, 0));
            borderLocations.add(anchorLocation.offset(i, height - 1));
        }
        for (int i = 0; i < height; i++) {
            borderLocations.add(anchorLocation.offset(0, i));
            borderLocations.add(anchorLocation.offset(width - 1, i));
        }
        return borderLocations;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Room)) {
            return false;
        }

        Room otherRoom = (Room) other;

        return anchorLocation.equals(otherRoom.anchorLocation)
            && centerLocation.equals(otherRoom.centerLocation)
            && width == otherRoom.width
            && height == otherRoom.height;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
