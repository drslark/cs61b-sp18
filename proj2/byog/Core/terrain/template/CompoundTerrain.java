package byog.Core.terrain.template;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import byog.Core.coordinate.Location;

/**
 * The CompoundTerrain is a template of all compound terrains.
 */
public abstract class CompoundTerrain<E extends Location<E>> extends BasicTerrain<E>
    implements Serializable {

    private static final long serialVersionUID = 314L;

    private LinkedHashSet<E> content = null;
    private LinkedHashSet<E> border = null;
    private LinkedHashSet<E> margin = null;

    /**
     * Gets the locations of the content in a compound terrain.
     *
     * @return The locations of the content in a compound terrain.
     */
    @Override
    public Set<E> getContent() {
        if (content == null) {
            throw new RuntimeException("Content has not been set!");
        }

        return Collections.unmodifiableSet(content);
    }

    /**
     * Gets the locations of the border in a compound terrain.
     *
     * @return The locations of the border in a compound terrain.
     */
    @Override
    public Set<E> getBorder() {
        if (border == null) {
            throw new RuntimeException("Border has not been set!");
        }

        return Collections.unmodifiableSet(border);
    }

    /**
     * Gets the locations of the margin in a compound terrain.
     *
     * @return The locations of the margin in a compound terrain.
     */
    @Override
    public Set<E> getMargin() {
        if (margin == null) {
            throw new RuntimeException("Margin has not been set!");
        }

        return Collections.unmodifiableSet(margin);
    }

    protected void setContent(LinkedHashSet<E> content) {
        this.content = content;
    }

    protected void setBorder(LinkedHashSet<E> border) {
        this.border = border;
    }

    protected void setMargin(LinkedHashSet<E> margin) {
        this.margin = margin;
    }

    /**
     * Specifies the order of location initialization.
     */
    protected abstract void initializeLocations();

    protected abstract LinkedHashSet<E> generateContent();

    protected abstract LinkedHashSet<E> generateBorder();

}
