package byog.Core.coordinate;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import byog.Core.utils.ArrayUtils;


/**
 * The GenericLocation object is used to represent the position of any dimension.
 */
public class GenericLocation implements Location<GenericLocation>, Serializable {
    
    private static final long serialVersionUID = 314L;

    private final int hashCode;

    private static Map<Coordinate, GenericLocation> locationCache = null;
    private static GenericLocation _origin = null;

    private static int[] _shape = null;
    private static int _dimension;

    private final int[] _coordinate;

    private LinkedHashSet<GenericLocation> neighbours = null;
    private LinkedHashSet<GenericLocation> closeNeighbours = null;

    /**
     * A traverse template to traverse in the GenericLocation coordinate system.
     *
     * @param start The coordinate of the start location.
     * @param shape The shape of the GenericLocation coordinate system.
     * @param center The coordinate of the center location.
     * @param coordinateProcessInterceptor An interceptor to process the coordinates.
     * @return The expected coordinates.
     */
    private static List<int[]> traverseTemplate(
        int[] start, int[] shape, int[] center,
        CoordinateProcessInterceptor coordinateProcessInterceptor
    ) {
        int[] end = ArrayUtils.sum(start, shape);
        int[] coordinate = new int[shape.length];

        List<int[]> coordinates = new ArrayList<>();
        int d = 0;

        while (d >= 0) {
            // search to the deepest
            while (d < shape.length) {
                coordinate[d] = start[d];
                d += 1;
            }

            coordinateProcessInterceptor.process(
                start, shape, center,
                Arrays.copyOf(coordinate, coordinate.length),
                coordinates
            );

            // back to other paths
            d -= 1;
            while (d >= 0 && coordinate[d] == end[d] - 1) {
                d -= 1;
            }

            if (d >= 0) {
                coordinate[d] += 1;
                d += 1;
            }
        }

        return coordinates;
    }

    private static final CoordinateProcessInterceptor CONTENT_PROCESS_INTERCEPTOR =
        (int[] start, int[] shape, int[] center, int[] coordinate, List<int[]> coordinates) -> {
            coordinates.add(coordinate);
        };

    private static final CoordinateProcessInterceptor BORDER_PROCESS_INTERCEPTOR =
        (int[] start, int[] shape, int[] center, int[] coordinate, List<int[]> coordinates) -> {
            for (int i = 0; i < coordinate.length; i++) {
                if (coordinate[i] == start[i] || coordinate[i] == start[i] + shape[i] - 1) {
                    coordinates.add(coordinate);
                    break;
                }
            }
        };

    private static final CoordinateProcessInterceptor NEIGHBOUR_PROCESS_INTERCEPTOR =
        (int[] start, int[] shape, int[] center, int[] coordinate, List<int[]> coordinates) -> {
            if (Arrays.equals(coordinate, center)) {
                return;
            }
            coordinates.add(coordinate);
        };

    private static final CoordinateProcessInterceptor CLOSE_NEIGHBOUR_PROCESS_INTERCEPTOR =
        (int[] start, int[] shape, int[] center, int[] coordinate, List<int[]> coordinates) -> {
            if (ArrayUtils.differentCount(coordinate, center) != 1) {
                return;
            }

            coordinates.add(coordinate);
        };

    /**
     * Initialize the GenericLocation coordinate system.
     *
     * @param shape The shape of the GenericLocation coordinate system.
     */
    public static void initialize(int... shape) {
        GenericLocation._shape = shape;
        _dimension = shape.length;

        int[] origin = new int[_dimension];
        List<int[]> coordinates = traverseTemplate(
            origin, shape, null, CONTENT_PROCESS_INTERCEPTOR
        );

        locationCache = new HashMap<>();
        for (int[] coordinate : coordinates) {
            locationCache.put(new Coordinate(coordinate), new GenericLocation(coordinate));
        }

        GenericLocation._origin = locationCache.get(new Coordinate(origin));
    }

    /**
     * Gets the original location of the GenericLocation coordinate system.
     *
     * @return The original location of the GenericLocation coordinate system.
     */
    public static GenericLocation origin() {
        if (_origin == null) {
            throw new RuntimeException("GenericLocation has not been initialized!");
        }
        return _origin;
    }

    /**
     * Gets the shape of the GenericLocation coordinate system.
     *
     * @return The shape of the GenericLocation coordinate system.
     */
    public static int[] shape() {
        if (_shape == null) {
            throw new RuntimeException("GenericLocation has not been initialized!");
        }
        return Arrays.copyOf(_shape, _dimension);
    }

    /**
     * Gets the location corresponding to the given coordinate
     * in the GenericLocation coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    public static GenericLocation locateAt(int... coordinate) {
        if (locationCache == null) {
            throw new RuntimeException("GenericLocation has not been initialized!");
        }

        Coordinate coordinateKey = new Coordinate(coordinate);
        if (!locationCache.containsKey(coordinateKey)) {
            throw new RuntimeException("Invalid coordinate!");
        }

        return locationCache.get(coordinateKey);
    }

    /**
     * Traverse content in the given region.
     *
     * @param start The start coordinate of the region.
     * @param shape The shape coordinate of the region.
     * @return The content locations of the region.
     */
    public static LinkedHashSet<GenericLocation> traverseContent(int[] start, int[] shape) {
        checkDimension(start.length);
        checkDimension(shape.length);

        List<int[]> coordinates = traverseTemplate(start, shape, null, CONTENT_PROCESS_INTERCEPTOR);

        LinkedHashSet<GenericLocation> content = new LinkedHashSet<>();
        for (int[] coordinate : coordinates) {
            content.add(locationCache.get(new Coordinate(coordinate)));
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
    public static LinkedHashSet<GenericLocation> traverseBorder(int[] start, int[] shape) {
        checkDimension(start.length);
        checkDimension(shape.length);

        List<int[]> coordinates = traverseTemplate(start, shape, null, BORDER_PROCESS_INTERCEPTOR);

        LinkedHashSet<GenericLocation> content = new LinkedHashSet<>();
        for (int[] coordinate : coordinates) {
            content.add(locationCache.get(new Coordinate(coordinate)));
        }

        return content;
    }

    /**
     * Constructor with coordinate.
     *
     * @param coordinate The coordinate.
     */
    private GenericLocation(int... coordinate) {
        checkDimension(coordinate.length);
        _coordinate = Arrays.copyOf(coordinate, coordinate.length);

        hashCode = Arrays.hashCode(coordinate);
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
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Gets the coordinate y value.
     *
     * @return The coordinate y value.
     */
    @Override
    public int getY() {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Gets the shape of the GenericLocation coordinate system.
     *
     * @return The shape of the GenericLocation coordinate system.
     */
    @Override
    public int[] getShape() {
        return shape();
    }

    /**
     * Gets the dimension of the GenericLocation coordinate system.
     *
     * @return The dimension of the GenericLocation coordinate system.
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
    public int manhattanDistance(GenericLocation thatLocation) {
        checkDimension(thatLocation.getDimension());

        int distance = 0;
        for (int i = 0; i < getDimension(); i++) {
            distance += Math.abs(get(i) - thatLocation.get(i));
        }

        return distance;
    }

    /**
     * Gets the location corresponding to the given coordinate
     * in the GenericLocation coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    @Override
    public GenericLocation getLocation(int... coordinate) {
        return locateAt(coordinate);
    }

    /**
     * A new location with an offset from this coordinate.
     *
     * @param offsetCoordinate The offset coordinate.
     * @return A new location with an offset from this coordinate.
     */
    @Override
    public GenericLocation offset(int... offsetCoordinate) {
        checkDimension(offsetCoordinate.length);
        int[] destinationCoordinate = ArrayUtils.sum(_coordinate, _coordinate);
        return locationCache.get(new Coordinate(destinationCoordinate));
    }

    /**
     * Traverse content in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The content locations of the region.
     */
    @Override
    public LinkedHashSet<GenericLocation> traverseContent(int[] shape) {
        return traverseContent(_coordinate, shape);
    }

    /**
     * Traverse border in the given region starts at this location.
     *
     * @param shape The shape coordinate of the region.
     * @return The border locations of the region.
     */
    @Override
    public LinkedHashSet<GenericLocation> traverseBorder(int[] shape) {
        return traverseBorder(_coordinate, shape);
    }

    /**
     * Gets all neighbours of this location.
     *
     * @return All locations of the neighbours.
     */
    @Override
    public Set<GenericLocation> getNeighbours() {
        if (neighbours == null) {
            int[] start = ArrayUtils.sum(_coordinate, ArrayUtils.full(_dimension, -1));
            int[] shape = ArrayUtils.full(_dimension, 3);
            List<int[]> coordinates = traverseTemplate(
                start, shape, _coordinate, NEIGHBOUR_PROCESS_INTERCEPTOR
            );

            neighbours = new LinkedHashSet<>();
            for (int[] coordinate : coordinates) {
                Coordinate coordinateKey = new Coordinate(coordinate);
                if (!locationCache.containsKey(coordinateKey)) {
                    continue;
                }
                neighbours.add(locationCache.get(coordinateKey));
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
    public Set<GenericLocation> getCloseNeighbours() {
        if (closeNeighbours == null) {
            int[] start = ArrayUtils.sum(_coordinate, ArrayUtils.full(_dimension, -1));
            int[] shape = ArrayUtils.full(_dimension, 3);
            List<int[]> coordinates = traverseTemplate(
                start, shape, _coordinate, CLOSE_NEIGHBOUR_PROCESS_INTERCEPTOR
            );

            closeNeighbours = new LinkedHashSet<>();
            for (int[] coordinate : coordinates) {
                Coordinate coordinateKey = new Coordinate(coordinate);
                if (!locationCache.containsKey(coordinateKey)) {
                    continue;
                }
                closeNeighbours.add(locationCache.get(coordinateKey));
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
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Gets the down offset coordinate.
     *
     * @return The down offset coordinate.
     */
    @Override
    public int[] downOffset() {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Gets the left offset coordinate.
     *
     * @return The left offset coordinate.
     */
    @Override
    public int[] leftOffset() {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Gets the right offset coordinate.
     *
     * @return The right offset coordinate.
     */
    @Override
    public int[] rightOffset() {
        throw new RuntimeException("Not implemented!");
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

        if (!(that instanceof GenericLocation)) {
            return false;
        }

        GenericLocation thatLocation = (GenericLocation) that;
        return Arrays.equals(_coordinate, thatLocation._coordinate);
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
     * A proxy to do serialization for GenericLocation object.
     */
    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 314L;

        private final int[] _coordinate;

        SerializationProxy(int... coordinate) {
            _coordinate = coordinate;
        }

        private Object readResolve() {
            return GenericLocation.locateAt(_coordinate);
        }

    }

}
