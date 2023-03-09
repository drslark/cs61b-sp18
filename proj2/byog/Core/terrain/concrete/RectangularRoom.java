package byog.Core.terrain.concrete;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import byog.Core.coordinate.Location;
import byog.Core.terrain.template.BasicTerrain;
import byog.Core.terrain.view.Room;

/**
 * The RectangularRoom is used to store information of the rectangular room.
 */
public class RectangularRoom<E extends Location<E>> extends BasicTerrain<E>
    implements Room<E>, Serializable {
    
    private static final long serialVersionUID = 314L;

    private final int hashCode;

    private final E anchor;
    private final E center;
    private final int[] _shape;

    private LinkedHashSet<E> content;
    private LinkedHashSet<E> border;
    private LinkedHashSet<E> margin;

    /**
     * Constructor with anchor, rectangle width and rectangle height.
     *
     * @param anchor The location of the anchor.
     * @param shape The width and height of the rectangular room.
     */
    public RectangularRoom(E anchor, int[] shape) {
        this.anchor = anchor;
        _shape = shape;

        this.center = initCenterLocation(anchor, shape);

        hashCode = Objects.hash(anchor, center, Arrays.hashCode(shape));
    }

    /**
     * Constructor with anchor, center, rectangle width and rectangle height.
     *
     * @param anchor The location of the anchor.
     * @param center The location of the center.
     * @param shape The width and height of the rectangular room.
     */
    public RectangularRoom(E anchor, E center, int[] shape) {
        this.anchor = anchor;
        this.center = center;
        _shape = shape;

        hashCode = Objects.hash(anchor, center, Arrays.hashCode(shape));
    }

    /**
     * Gets the location of the anchor.
     *
     * @return The location of the anchor.
     */
    public E getAnchor() {
        return anchor;
    }

    /**
     * Gets the location of the center.
     *
     * @return The location of the center.
     */
    @Override
    public E getCenter() {
        return center;
    }

    /**
     * Whether the given location is contained in the rectangular room or not.
     *
     * @param location The given location.
     * @return Whether the given location is contained in the rectangular room or not.
     */
    @Override
    public boolean contains(E location) {
        checkShape(location, _shape);
        for (int i = 0; i < _shape.length; i++) {
            if (
                location.get(i) < anchor.get(i)
                    ||
                    location.get(i) >= anchor.get(i) + _shape[i]
            ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the locations of the content in a rectangular room.
     *
     * @return The locations of the content in a rectangular room.
     */
    @Override
    public Set<E> getContent() {
        if (content == null) {
            content = anchor.traverseContent(_shape);
        }

        return Collections.unmodifiableSet(content);
    }

    /**
     * Gets the locations of the border in a rectangular room.
     *
     * @return The locations of the border in a rectangular room.
     */
    @Override
    public Set<E> getBorder() {
        if (border == null) {
            border = anchor.traverseBorder(_shape);
        }
        
        return Collections.unmodifiableSet(border);
    }

    /**
     * Gets the locations of the margin in a rectangular room.
     *
     * @return The locations of the margin in a rectangular room.
     */
    @Override
    public Set<E> getMargin() {
        if (margin == null) {
            margin = super.generateMargin();
        }
        
        return Collections.unmodifiableSet(margin);
    }

    /**
     * Whether this rectangular room is overlap with another.
     *
     * @param thatRoom another room.
     * @return Whether one room is overlap with another.
     */
    @Override
    public boolean isOverlap(Room<E> thatRoom) {
        if (thatRoom instanceof RectangularRoom) {
            boolean isOverlap = true;
            RectangularRoom<E> thatRectangularRoom = (RectangularRoom<E>) thatRoom;
            for (int i = 0; i < _shape.length; i++) {
                isOverlap = isOverlap && !
                    (
                        anchor.get(i)
                        >= thatRectangularRoom.anchor.get(i) + thatRectangularRoom._shape[i]
                        || anchor.get(i) + _shape[i] <= thatRectangularRoom.anchor.get(i)
                    );
            }
            return isOverlap;
        }

        Set<E> thatRoomContent = thatRoom.getContent();
        for (E location : getContent()) {
            if (thatRoomContent.contains(location)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether this rectangular room is margin with another.
     *
     * @param thatRoom another room.
     * @return Whether one room is margin with another.
     */
    @Override
    public boolean isMargin(Room<E> thatRoom) {
        if (thatRoom instanceof RectangularRoom) {
            RectangularRoom<E> thatRectangularRoom = (RectangularRoom<E>) thatRoom;

            int overlapCount = 0;
            int marginCount = 0;
            for (int i = 0; i < _shape.length; i++) {
                boolean isOverlapInDimension =
                    anchor.get(i)
                        >= thatRectangularRoom.anchor.get(i) + thatRectangularRoom._shape[i]
                        ||
                        anchor.get(i) + _shape[i] <= thatRectangularRoom.anchor.get(i);
                boolean isMarginInDimension =
                    anchor.get(i)
                        == thatRectangularRoom.anchor.get(i) + thatRectangularRoom._shape[i]
                        ||
                        anchor.get(i) + _shape[i] == thatRectangularRoom.anchor.get(i);

                if (isOverlapInDimension) {
                    overlapCount += 1;
                }

                if (isMarginInDimension) {
                    marginCount += 1;
                }
            }

            return marginCount == 1 && overlapCount == _shape.length - 1;
        }

        if (isOverlap(thatRoom)) {
            return false;
        }

        Set<E> thatRoomContent = thatRoom.getContent();
        for (E location : getMargin()) {
            if (thatRoomContent.contains(location)) {
                return true;
            }
        }

        return false;
    }

    private void checkShape(E location, int[] shape) {
        if (location.getDimension() != shape.length) {
            throw new RuntimeException("Invalid shape size!");
        }

        for (int i = 0; i < shape.length; i++) {
            if (shape[i] <= 0) {
                throw new RuntimeException("Invalid shape value!");
            }
        }
    }

    private E initCenterLocation(E anchorLocation, int[] shape) {
        int[] halfShape = new int[shape.length];
        for (int i = 0; i < shape.length; i++) {
            halfShape[i] = shape[i] / 2;
        }
        return anchorLocation.offset(halfShape);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof RectangularRoom)) {
            return false;
        }

        RectangularRoom<E> thatRectangularRoom = (RectangularRoom<E>) that;

        return anchor.equals(thatRectangularRoom.anchor)
            && center.equals(thatRectangularRoom.center)
            && Arrays.equals(_shape, thatRectangularRoom._shape);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
