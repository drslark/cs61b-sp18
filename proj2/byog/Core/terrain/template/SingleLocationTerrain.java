package byog.Core.terrain.template;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import byog.Core.coordinate.Location;

/**
 * The SingleLocationTerrain is a template of all single-location terrains.
 */
public abstract class SingleLocationTerrain<E extends Location<E>> extends BasicTerrain<E>
    implements Serializable {

    private static final long serialVersionUID = 314L;

    private final E _location;
    private final LinkedHashSet<E> _content;
    private final LinkedHashSet<E> _margin;

    /**
     * Constructor with location.
     *
     * @param location The location of the single-location terrain.
     */
    public SingleLocationTerrain(E location) {
        _location = location;
        _content = new LinkedHashSet<E>() {
            {
                add(_location);
            }
        };
        _margin = super.generateMargin();
    }

    /**
     * Gets the location of the single-location terrain.
     *
     * @return The location of the single-location terrain.
     */
    public E getLocation() {
        return _location;
    }

    /**
     * Whether the given location is contained in the single-location terrain or not.
     *
     * @param location the given location.
     * @return Whether the given location is contained in the single-location or not.
     */
    @Override
    public boolean contains(E location) {
        return _location.equals(location);
    }

    /**
     * Gets the locations of the content in a single-location terrain.
     *
     * @return The locations of the content in a single-location terrain.
     */
    @Override
    public Set<E> getContent() {
        return Collections.unmodifiableSet(_content);
    }

    /**
     * Gets the locations of the border in a single-location terrain.
     *
     * @return The locations of the border in a single-location terrain.
     */
    @Override
    public Set<E> getBorder() {
        return Collections.unmodifiableSet(_content);
    }

    /**
     * Gets the locations of the margin in a single-location terrain.
     *
     * @return The locations of the margin in a single-location terrain.
     */
    @Override
    public Set<E> getMargin() {
        return Collections.unmodifiableSet(_margin);
    }

}
