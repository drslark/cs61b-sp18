package byog.Core.terrain.template;

import java.io.Serializable;
import java.util.LinkedHashSet;

import byog.Core.coordinate.Location;
import byog.Core.terrain.view.Terrain;

/**
 * The BasicTerrain is a template of all terrains.
 */
public abstract class BasicTerrain<E extends Location<E>> implements Terrain<E>, Serializable {

    private static final long serialVersionUID = 314L;

    /**
     * Whether the given location is contained in the terrain or not.
     *
     * @param location the given location.
     * @return Whether the given location is contained in the terrain or not.
     */
    @Override
    public boolean contains(E location) {
        return getContent().contains(location);
    }

    /**
     * Gets the locations of the margin.
     *
     * @return The locations of the margin.
     */
    protected LinkedHashSet<E> generateMargin() {
        LinkedHashSet<E> margin = new LinkedHashSet<>();
        for (E borderLocation : getBorder()) {
            for (E location : borderLocation.getNeighbours()) {
                if (!contains(location)) {
                    margin.add(location);
                }
            }
        }

        return margin;
    }

}
