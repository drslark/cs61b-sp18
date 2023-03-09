package byog.Core.coordinate;

import java.util.Arrays;

/**
 * The Coordinate object is used as the key in maps that store locations.
 */
public class Coordinate {

    private final int[] _coordinate;
    private final int _dimension;

    private final int hashCode;

    /**
     * Constructor with the coordinate of the location.
     *
     * @param coordinate The coordinate of the location.
     */
    public Coordinate(int... coordinate) {
        _coordinate = Arrays.copyOf(coordinate, coordinate.length);
        _dimension = coordinate.length;
        this.hashCode = Arrays.hashCode(coordinate);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof Coordinate)) {
            return false;
        }

        Coordinate thatCoordinate = (Coordinate) that;
        return Arrays.equals(_coordinate, thatCoordinate._coordinate);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
