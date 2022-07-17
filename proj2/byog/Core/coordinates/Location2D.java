package byog.Core.coordinates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Location2D object is used to represent the position of 2-dimension.
 */
public class Location2D implements Location<Location2D> {

    private final int hashCode;

    private final LocationTemplate location;

    /**
     * Constructor with coordinate x and coordinate y.
     *
     * @param x Coordinate x.
     * @param y Coordinate y.
     */
    public Location2D(int x, int y) {
        location = new LocationTemplate(x, y);

        hashCode = location.hashCode();
    }

    /**
     * Constructor with a location.
     *
     * @param location A LocationTemplate object with a dimension of 2.
     */
    private Location2D(LocationTemplate location) {
        if (location.getDimension() != 2) {
            throw new RuntimeException(
                String.format(
                    "Input location must have a dimension of 2, which has %d!",
                    location.getDimension()
                )
            );
        }
        this.location = location;

        hashCode = location.hashCode();
    }

    /**
     * A new location with an offset from the location.
     *
     * @param offsetX The offset in coordinate x.
     * @param offsetY The offset in coordinate y.
     * @return A new location with an offset.
     */
    public Location2D offset(int offsetX, int offsetY) {
        return new Location2D(getX() + offsetX, getY() + offsetY);
    }

    /**
     * Gets the coordinate x.
     *
     * @return The coordinate x.
     */
    public int getX() {
        return (int) location.getCoordinate(0);
    }

    /**
     * Gets the coordinate y.
     * @return The coordinate y.
     */
    public int getY() {
        return (int) location.getCoordinate(1);
    }

    /**
     * Computes the manhattan distance between two locations.
     *
     * @param thisLocation Some location.
     * @param otherLocation The other location.
     * @return The manhattan distance.
     */
    public static int manhattanDistance(Location2D thisLocation, Location2D otherLocation) {
        return (int) LocationTemplate.manhattanDistance(
                thisLocation.location, otherLocation.location
        );
    }

    /**
     * Gets all neighbours of the location.
     *
     * @param eps The distance to the neighbour.
     * @return All locations of the neighbours.
     */
    @Override
    public Collection<Location2D> getNeighbours(int eps) {
        List<Location2D> neighbours = new ArrayList<>();
        for (LocationTemplate loc : this.location.getNeighbours(eps)) {
            neighbours.add(new Location2D(loc));
        }
        return neighbours;
    }

    /**
     * Gets all close neighbours of the location.
     *
     * @param eps The distance to the close neighbour.
     * @return All close locations of the neighbours.
     */
    @Override
    public Collection<Location2D> getCloseNeighbours(int eps) {
        List<Location2D> neighbours = new ArrayList<>();
        for (LocationTemplate loc : this.location.getCloseNeighbours(eps)) {
            neighbours.add(new Location2D(loc));
        }
        return neighbours;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Location2D)) {
            return false;
        }

        Location2D otherLocation2D = ((Location2D) other);
        return location.equals(otherLocation2D.location);
    }

}
