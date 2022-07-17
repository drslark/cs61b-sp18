package byog.Core.coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * The LocationTemplate object is used to represent the position of any dimension.
 */
class LocationTemplate {

    private final int hashCode;

    private final double[] coordinates;
    private final int dimension;

    private final Collection<LocationTemplate> neighbourOffsets;
    private final Collection<LocationTemplate> closeNeighbourOffsets;

    private static final NeighbourOffsetComputer ALL_NEIGHBOUR_OFFSET_COMPUTER =
        (Collection<LocationTemplate> neighbourOffsets, double[] increments, int[] pointers) -> {
            int dimension = pointers.length;
            double[] offset = new double[dimension];
            for (int j = 0; j < dimension; j++) {
                offset[j] = increments[pointers[j]];
            }
            neighbourOffsets.add(new LocationTemplate(offset));
        };

    private static final NeighbourOffsetComputer CLOSE_NEIGHBOUR_OFFSET_COMPUTER =
        (Collection<LocationTemplate> neighbourOffsets, double[] increments, int[] pointers) -> {
            int dimension = pointers.length;

            double[] offset = new double[dimension];
            int nonzeroCount = 0;
            for (int j = 0; j < dimension; j++) {
                offset[j] = increments[pointers[j]];
                if (offset[j] != 0.0) {
                    nonzeroCount += 1;
                }
                if (nonzeroCount > 1) {
                    return;
                }
            }
            neighbourOffsets.add(new LocationTemplate(offset));
        };

    /**
     * Constructor with coordinates.
     *
     * @param coordinates The coordinates of any object.
     */
    LocationTemplate(double... coordinates) {
        this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
        dimension = coordinates.length;

        neighbourOffsets = new HashSet<>();
        closeNeighbourOffsets = new HashSet<>();

        hashCode = Arrays.hashCode(coordinates);
    }

    /**
     * Gets a copy of coordinates.
     *
     * @return A copy of coordinates of the location.
     */
    double[] getCoordinates() {
        return Arrays.copyOf(coordinates, coordinates.length);
    }

    /**
     * Gets a copy of a coordinate in some given dimension.
     *
     * @param d The given dimension.
     * @return A copy of a coordinate in some given dimension.
     */
    double getCoordinate(int d) {
        if (d >= dimension) {
            throw new RuntimeException("Invalid dimension!");
        }
        return coordinates[d];
    }

    /**
     * Gets the dimension of the location.
     *
     * @return The dimension.
     */
    int getDimension() {
        return dimension;
    }

    LocationTemplate offset(LocationTemplate offset) {
        double[] otherCoordinates = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            otherCoordinates[i] = coordinates[i] + offset.coordinates[i];
        }
        return new LocationTemplate(otherCoordinates);
    }

    /**
     * Computes the manhattan distance between two locations.
     *
     * @param thisLocation  Some location.
     * @param otherLocation The other location.
     * @return The manhattan distance.
     */
    static double manhattanDistance(LocationTemplate thisLocation, LocationTemplate otherLocation) {
        if (thisLocation.getDimension() != otherLocation.getDimension()) {
            throw new RuntimeException("The locations must have same dimensions!");
        }

        double distance = 0.0;
        for (int i = 0; i < thisLocation.getDimension(); i++) {
            distance += Math.abs(thisLocation.getCoordinate(i) - otherLocation.getCoordinate(i));
        }

        return distance;
    }

    /**
     * Gets all neighbours of the location.
     *
     * @param eps The distance to the neighbour.
     * @return All locations of the neighbours.
     */
    Collection<LocationTemplate> getNeighbours(double eps) {
        if (neighbourOffsets.isEmpty()) {
            addNeighbourOffsetsTemplate(
                LocationTemplate.ALL_NEIGHBOUR_OFFSET_COMPUTER, neighbourOffsets, eps
            );
        }

        List<LocationTemplate> neighbours = new ArrayList<>();
        for (LocationTemplate neighbourOffset : neighbourOffsets) {
            neighbours.add(offset(neighbourOffset));
        }

        return neighbours;
    }

    /**
     * Gets all close neighbours of the location.
     *
     * @param eps The distance to the close neighbour.
     * @return All locations of the close neighbours.
     */
    Collection<LocationTemplate> getCloseNeighbours(double eps) {
        if (closeNeighbourOffsets.isEmpty()) {
            addNeighbourOffsetsTemplate(
                LocationTemplate.CLOSE_NEIGHBOUR_OFFSET_COMPUTER, closeNeighbourOffsets, eps
            );
        }

        List<LocationTemplate> neighbours = new ArrayList<>();
        for (LocationTemplate neighbourOffset : closeNeighbourOffsets) {
            neighbours.add(offset(neighbourOffset));
        }

        return neighbours;
    }

    private void addNeighbourOffsetsTemplate(
        NeighbourOffsetComputer neighbourOffsetComputer,
        Collection<LocationTemplate> someNeighbourOffsets,
        double eps
    ) {

        double[] increments = new double[]{-eps, eps, 0};

        int total = (int) Math.pow(increments.length, dimension) - 1;
        int[] pointers = new int[dimension];
        int i = 0;
        int d = 0;

        while (i < total) {
            // search to the deepest
            while (d < dimension) {
                pointers[d] = 0;
                d += 1;
            }

            // record the location
            neighbourOffsetComputer.process(someNeighbourOffsets, increments, pointers);
            i += 1;

            // back to other paths
            d -= 1;
            while (pointers[d] == increments.length - 1) {
                d -= 1;
            }

            pointers[d] += 1;
            d += 1;
        }

    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof LocationTemplate)) {
            return false;
        }

        LocationTemplate otherLocation = (LocationTemplate) other;
        return Arrays.equals(coordinates, otherLocation.coordinates);
    }

}
