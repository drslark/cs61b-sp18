package byog.Core.terrain.concrete;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import byog.Core.coordinate.Location;
import byog.Core.terrain.template.BasicTerrain;
import byog.Core.terrain.view.Hallway;

/**
 * The NarrowHallway is used to store information of the narrow hallway.
 */
public class NarrowHallway<E extends Location<E>> extends BasicTerrain<E>
    implements Hallway<E>, Serializable {
    
    private static final long serialVersionUID = 314L;

    private final int hashCode;

    private final LinkedHashSet<E> locations;
    private final LinkedHashSet<E> margin;

    /**
     * Constructor with the locations of the narrow hallway.
     *
     * @param locations The locations in the narrow hallway.
     */
    public NarrowHallway(LinkedHashSet<E> locations) {
        this.locations = locations;
        this.margin = super.generateMargin();

        hashCode = Arrays.hashCode(this.locations.toArray());
    }

    /**
     * Gets the locations of the narrow hallway.
     *
     * @return The locations of the narrow hallway.
     */
    protected Set<E> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    /**
     * Whether the given location is contained in the narrow hallway or not.
     *
     * @param location the given location.
     * @return Whether the given location is contained in the narrow hallway or not.
     */
    @Override
    public boolean contains(E location) {
        return locations.contains(location);
    }

    /**
     * Gets the locations of the content in a narrow hallway.
     *
     * @return The locations of the content in a narrow hallway.
     */
    @Override
    public Set<E> getContent() {
        return Collections.unmodifiableSet(locations);
    }

    /**
     * Gets the locations of the border in a narrow hallway.
     *
     * @return The locations of the border in a narrow hallway.
     */
    @Override
    public Set<E> getBorder() {
        return getLocations();
    }

    /**
     * Gets the locations of the margin in a narrow hallway.
     *
     * @return The locations of the margin in a narrow hallway.
     */
    @Override
    public Set<E> getMargin() {
        return Collections.unmodifiableSet(margin);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof NarrowHallway)) {
            return false;
        }

        NarrowHallway<E> thatNarrowHallway = (NarrowHallway<E>) that;

        return Arrays.equals(locations.toArray(), thatNarrowHallway.locations.toArray());
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
