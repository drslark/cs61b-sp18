package byog.Core.terrain;

import java.util.Collection;
import java.util.HashSet;

import byog.Core.coordinates.Location2D;

/**
 * The Door is used to store information of the door.
 */
public class Door implements WithMargin<Location2D> {

    private final int hashCode;

    private final Location2D location;

    /**
     * Constructor with location.
     * @param location The location of the door.
     */
    public Door(Location2D location) {
        this.location = location;

        hashCode = location.hashCode();
    }

    /**
     * Whether the given loc is contained in the door or not.
     * @param loc the given loc.
     * @return Whether the given loc is contained in the door or not.
     */
    @Override
    public boolean contains(Location2D loc) {
        return loc == this.location;
    }

    /**
     * Gets the locations of the content.
     * @return The locations of the content.
     */
    @Override
    public Collection<Location2D> getContent() {
        return new HashSet<Location2D>() {
            {
                add(location);
            }
        };
    }

    /**
     * Gets the locations of the border.
     * @return The locations of the border.
     */
    @Override
    public Collection<Location2D> getBorder() {
        return new HashSet<Location2D>() {
            {
                add(location);
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Door)) {
            return false;
        }

        Door otherDoor = (Door) other;

        return location.equals(otherDoor.location);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
