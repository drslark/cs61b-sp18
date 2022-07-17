package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import byog.Core.coordinates.Location2D;

/**
 * The Hallway is used to store information of the hallway.
 */
public class Hallway implements WithMargin<Location2D> {

    private final int hashCode;

    private final List<Location2D> locations;

    /**
     * Constructor with the locations of the hallway.
     *
     * @param locations The width of the background.
     */
    public Hallway(Collection<Location2D> locations) {
        this.locations = new ArrayList<>(locations);

        hashCode = Arrays.hashCode(locations.toArray());
    }

    /**
     * Gets the locations of the hallway.
     *
     * @return The locations of the hallway.
     */
    private Collection<Location2D> getLocations() {
        return new HashSet<>(locations);
    }

    /**
     * Whether the given location is contained in the hallway or not.
     * @param location the given location.
     * @return Whether the given location is contained in the hallway or not.
     */
    @Override
    public boolean contains(Location2D location) {
        return locations.contains(location);
    }

    /**
     * Gets the locations of the content.
     * @return The locations of the content.
     */
    @Override
    public Collection<Location2D> getContent() {
        return new HashSet<>(locations);
    }

    /**
     * Gets the locations of the border.
     * @return The locations of the border.
     */
    @Override
    public Collection<Location2D> getBorder() {
        return getLocations();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Hallway)) {
            return false;
        }

        Hallway otherHallway = (Hallway) other;

        return Arrays.equals(locations.toArray(), otherHallway.locations.toArray());
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
