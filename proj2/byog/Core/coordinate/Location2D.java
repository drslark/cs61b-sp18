package byog.Core.coordinate;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import byog.Core.utils.ArrayUtils;

/**
 * The Location2D object is used to represent the position of 2-dimension.
 */
public class Location2D implements Location<Location2D>, Serializable {

    private static final long serialVersionUID = 314L;

    private final int hashCode;

    private static Location2D[][] locationCache = null;
    private static Location2D _origin = null;

    private static int[] _shape = null;
    private static int _dimension = 2;

    private static final int[] UP_OFFSET = {0, 1};
    private static final int[] DOWN_OFFSET = {0, -1};
    private static final int[] LEFT_OFFSET = {-1, 0};
    private static final int[] RIGHT_OFFSET = {1, 0};

    private static final int[][] NEIGHBOUR_OFFSETS = {
        UP_OFFSET, DOWN_OFFSET, LEFT_OFFSET, RIGHT_OFFSET, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };
    private static final int[][] CLOSE_NEIGHBOUR_OFFSETS = {
        UP_OFFSET, DOWN_OFFSET, LEFT_OFFSET, RIGHT_OFFSET
    };

    private final int[] _coordinate;

    private LinkedHashSet<Location2D> neighbours = null;
    private LinkedHashSet<Location2D> closeNeighbours = null;

    /**
     * Initialize the Location2D coordinate system.
     *
     * @param shape The shape of the Location2D coordinate system.
     */
    public static void initialize(int... shape) {
        if (shape.length != _dimension) {
            throw new RuntimeException("Invalid shape for Location2D!");
        }

        Location2D._shape = shape;

        locationCache = new Location2D[shape[0]][shape[1]];
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                locationCache[i][j] = new Location2D(i, j);
            }
        }

        Location2D._origin = locationCache[0][0];
    }

    /**
     * Gets the original location of the Location2D coordinate system.
     *
     * @return The original location of the Location2D coordinate system.
     */
    public static Location2D origin() {
        if (_origin == null) {
            throw new RuntimeException("Location2D has not been initialized!");
        }
        return _origin;
    }

    /**
     * Gets the shape of the Location2D coordinate system.
     *
     * @return The shape of the Location2D coordinate system.
     */
    public static int[] shape() {
        if (_shape == null) {
            throw new RuntimeException("Location2D has not been initialized!");
        }
        return Arrays.copyOf(_shape, _dimension);
    }

    /**
     * Gets the location corresponding to the given coordinate in the Location2D coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    public static Location2D locateAt(int... coordinate) {
        if (locationCache == null) {
            throw new RuntimeException("Location2D has not been initialized!");
        }
        return locationCache[coordinate[0]][coordinate[1]];
    }

    /**
     * Traverse content in the given region.
     *
     * @param start The start coordinate of the region.
     * @param shape The shape coordinate of the region.
     * @return The content locations of the region.
     */
    public static LinkedHashSet<Location2D> traverseContent(int[] start, int[] shape) {
        checkDimension(start.length);
        checkDimension(shape.length);

        LinkedHashSet<Location2D> content = new LinkedHashSet<>();
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                content.add(locationCache[start[0] + i][start[1] + j]);
            }
        }

        return content;
    }

    /**
     * Traverse border in the given region.
     *
     * @param start The start coordinate of the region.
     * @param shape The shape coordinate of the region.
     * @return The border locations of the region.
     */
    public static LinkedHashSet<Location2D> traverseBorder(int[] start, int[] shape) {
        checkDimension(start.length);
        checkDimension(shape.length);

        LinkedHashSet<Location2D> border = new LinkedHashSet<>();
        for (int i = 0; i < shape[0]; i++) {
            border.add(locationCache[start[0] + i][start[1]]);
            border.add(locationCache[start[0] + i][start[1] + shape[1] - 1]);
        }

        for (int i = 0; i < shape[1]; i++) {
            border.add(locationCache[start[0]][start[1] + i]);
            border.add(locationCache[start[0] + shape[0] - 1][start[1] + i]);
        }

        return border;
    }

    /**
     * Constructor with coordinate x and coordinate y.
     *
     * @param x Coordinate x.
     * @param y Coordinate y.
     */
    private Location2D(int x, int y) {
        _coordinate = new int[]{x, y};

        hashCode = Arrays.hashCode(_coordinate);
    }

    /**
     * Gets the coordinate ith value.
     *
     * @param i The index of the value.
     * @return The coordinate ith value.
     */
    @Override
    public int get(int i) {
        if (i >= _dimension) {
            throw new RuntimeException("Invalid dimension!");
        }
        return _coordinate[i];
    }

    /**
     * Gets the coordinate x value.
     *
     * @return The coordinate x value.
     */
    @Override
    public int getX() {
        return get(0);
    }

    /**
     * Gets the coordinate y value.
     *
     * @return The coordinate y value.
     */
    @Override
    public int getY() {
        return get(1);
    }

    /**
     * Gets the shape of the Location2D coordinate system.
     *
     * @return The shape of the Location2D coordinate system.
     */
    @Override
    public int[] getShape() {
        return shape();
    }

    /**
     * Gets the dimension of the Location2D coordinate system.
     *
     * @return The dimension of the Location2D coordinate system.
     */
    @Override
    public int getDimension() {
        return _dimension;
    }

    /**
     * Computes the manhattan distance between two locations.
     *
     * @param thatLocation The other location.
     * @return The manhattan distance.
     */
    @Override
    public int manhattanDistance(Location2D thatLocation) {
        checkDimension(thatLocation.getDimension());

        int distance = 0;
        for (int i = 0; i < getDimension(); i++) {
            distance += Math.abs(get(i) - thatLocation.get(i));
        }

        return distance;
    }

    /**
     * Gets the location corresponding to the given coordinate in the Location2D coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    @Override
    public Location2D getLocation(int... coordinate) {
        return locateAt(coordinate);
    }

    /**
     * A new location with an offset from this coordinate.
     *
     * @param offsetCoordinate The offset coordinate.
     * @return A new location with an offset from this coordinate.
     */
    @Override
    public Location2D offset(int... offsetCoordinate) {
        checkDimension(offsetCoordinate.length);
        int[] destinationCoordinate = ArrayUtils.sum(_coordinate, offsetCoordinate);
        if (!isInShape(destinationCoordinate)) {
            throw new RuntimeException("Invalid offset!");
        }
        return locationCache[destinationCoordinate[0]][destinationCoordinate[1]];
    }

    /**
     * Traverse content in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The content locations of the region.
     */
    @Override
    public LinkedHashSet<Location2D> traverseContent(int[] shape) {
        return traverseContent(_coordinate, shape);
    }

    /**
     * Traverse border in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The border locations of the region.
     */
    @Override
    public LinkedHashSet<Location2D> traverseBorder(int[] shape) {
        return traverseBorder(_coordinate, shape);
    }

    /**
     * Gets all neighbours of this location.
     *
     * @return All locations of the neighbours.
     */
    @Override
    public Set<Location2D> getNeighbours() {
        if (neighbours == null) {
            neighbours = new LinkedHashSet<>();
            for (int[] offsetCoordinate : NEIGHBOUR_OFFSETS) {
                int[] neighbourCoordinate = ArrayUtils.sum(_coordinate, offsetCoordinate);
                if (!isInShape(neighbourCoordinate)) {
                    continue;
                }
                neighbours.add(locationCache[neighbourCoordinate[0]][neighbourCoordinate[1]]);
            }
        }

        return Collections.unmodifiableSet(neighbours);
    }

    /**
     * Gets all close neighbours of this location.
     *
     * @return All locations of the close neighbours.
     */
    @Override
    public Set<Location2D> getCloseNeighbours() {
        if (closeNeighbours == null) {
            closeNeighbours = new LinkedHashSet<>();
            for (int[] offsetCoordinate : CLOSE_NEIGHBOUR_OFFSETS) {
                int[] neighbourCoordinate = ArrayUtils.sum(_coordinate, offsetCoordinate);
                if (!isInShape(neighbourCoordinate)) {
                    continue;
                }
                closeNeighbours.add(locationCache[neighbourCoordinate[0]][neighbourCoordinate[1]]);
            }
        }

        return Collections.unmodifiableSet(closeNeighbours);
    }

    /**
     * Gets the up offset coordinate.
     *
     * @return The up offset coordinate.
     */
    @Override
    public int[] upOffset() {
        return Arrays.copyOf(UP_OFFSET, _dimension);
    }

    /**
     * Gets the down offset coordinate.
     *
     * @return The down offset coordinate.
     */
    @Override
    public int[] downOffset() {
        return Arrays.copyOf(DOWN_OFFSET, _dimension);
    }

    /**
     * Gets the left offset coordinate.
     *
     * @return The left offset coordinate.
     */
    @Override
    public int[] leftOffset() {
        return Arrays.copyOf(LEFT_OFFSET, _dimension);
    }

    /**
     * Gets the right offset coordinate.
     *
     * @return The right offset coordinate.
     */
    @Override
    public int[] rightOffset() {
        return Arrays.copyOf(RIGHT_OFFSET, _dimension);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof Location2D)) {
            return false;
        }

        Location2D thatLocation2D = ((Location2D) that);
        return Arrays.equals(_coordinate, thatLocation2D._coordinate);
    }

    private static boolean isInShape(int[] coordinate) {
        for (int i = 0; i < _dimension; i++) {
            if (coordinate[i] < 0 || coordinate[i] >= _shape[i]) {
                return false;
            }
        }
        return true;
    }

    private static void checkDimension(int inputDimension) {
        if (_dimension != inputDimension) {
            throw new RuntimeException("Invalid dimension!");
        }
    }

    private Object writeReplace() {
        return new SerializationProxy(_coordinate);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required!");
    }

    /**
     * A proxy to do serialization for Location2D object.
     */
    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 314L;

        private final int[] _coordinate;

        SerializationProxy(int... coordinate) {
            _coordinate = coordinate;
        }

        private Object readResolve() {
            return Location2D.locateAt(_coordinate);
        }

    }

}
