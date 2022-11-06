import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UniformPartitioningOperations {

    private static final int HASH_BASE = 1201;

    private abstract static class TwoDInt {

        private final int x;
        private final int y;

        TwoDInt(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

    }

    private abstract static class TwoDDouble {

        private final double x;
        private final double y;

        TwoDDouble(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double getX() {
            return x;
        }

        double getY() {
            return y;
        }

    }

    private static class Index extends TwoDInt {

        private static final int[][] OFFSETS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
        };

        Index(int x, int y) {
            super(x, y);
        }

        List<Index> getNeighbours(Partitions partitions) {
            List<Index> neighbours = new ArrayList<>();
            for (int[] offset : OFFSETS) {
                int x = getX() + offset[0];
                if (x < 0 || x >= partitions.getX()) {
                    continue;
                }
                int y = getY() + offset[1];
                if (y < 0 || y >= partitions.getY()) {
                    continue;
                }

                neighbours.add(new Index(x, y));
            }
            return neighbours;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof Index)) {
                return false;
            }

            Index otherIndex = (Index) other;
            return getX() == otherIndex.getX() && getY() == otherIndex.getY();
        }

        @Override
        public int hashCode() {
            return HASH_BASE * getX() + getY();
        }
    }

    private static class Partitions extends TwoDInt {

        Partitions(int x, int y) {
            super(x, y);
        }

    }

    private static class Point extends TwoDDouble {

        Point(double x, double y) {
            super(x, y);
        }

        static double distance(Point point, Point otherPoint) {
            if (point == null || otherPoint == null) {
                return Double.POSITIVE_INFINITY;
            }

            double xDiff = otherPoint.getX() - point.getX();
            double yDiff = otherPoint.getY() - point.getY();
            return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof Point)) {
                return false;
            }

            Point otherPoint = (Point) other;
            return getX() == otherPoint.getX() && getY() == otherPoint.getY();
        }

        @Override
        public int hashCode() {
            return HASH_BASE * Double.hashCode(getX()) + Double.hashCode(getY());
        }
    }

    private static class Bounds extends TwoDDouble {

        Bounds(double x, double y) {
            super(x, y);
        }

    }

    private static class Intervals extends TwoDDouble {

        Intervals(double x, double y) {
            super(x, y);
        }

    }

    private static class Range {

        private final Bounds lower;
        private final Bounds upper;

        Range(Bounds lower, Bounds upper) {
            this.lower = lower;
            this.upper = upper;
        }

        double possibleShortest(Point point) {
            double xDiff = 0;
            if (point.getX() < lower.getX()) {
                xDiff = point.getX() - lower.getX();
            } else if (point.getX() > upper.getX()) {
                xDiff = point.getX() - upper.getX();
            }

            double yDiff = 0;
            if (point.getY() < lower.getY()) {
                yDiff = point.getY() - lower.getY();
            } else if (point.getY() > upper.getY()) {
                yDiff = point.getY() - upper.getY();
            }

            return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        }

        boolean contains(Point point) {
            if (point == null) {
                return false;
            }

            if (point.getX() < lower.getX() || point.getX() >= upper.getX()) {
                return false;
            }

            if (point.getY() < lower.getY() || point.getY() >= upper.getY()) {
                return false;
            }

            return true;
        }

    }

    private static class Space {

        private final Range range;
        private final Partitions partitions;

        private final Intervals intervals;
        private final Range[][] subRanges;

        Space(Range range, Partitions partitions) {
            this.range = range;

            this.partitions = partitions;

            double xInterval = (range.upper.getX() - range.lower.getX()) / partitions.getX();
            double yInterval = (range.upper.getY() - range.lower.getY()) / partitions.getY();

            this.intervals = new Intervals(xInterval, yInterval);

            double xBase = range.lower.getX();
            double yBase = range.lower.getY();
            this.subRanges = new Range[partitions.getX()][partitions.getY()];
            for (int i = 0; i < partitions.getX(); i++) {
                for (int j = 0; j < partitions.getY(); j++) {
                    Bounds lower = new Bounds(xBase + i * xInterval, yBase + j * yInterval);
                    Bounds upper = new Bounds(xBase + (i + 1) * xInterval, yBase + (j + 1) * yInterval);
                    subRanges[i][j] = new Range(lower, upper);
                }
            }
        }

        Index spatialHash(Point point) {
            int x = (int) ((point.getX() - range.lower.getX()) / intervals.getX());
            int y = (int) ((point.getY() - range.lower.getY()) / intervals.getY());

            if (x < 0 || x >= partitions.getX()) {
                throw new IllegalArgumentException("Exceeds the space in direction x!");
            }

            if (y < 0 || y >= partitions.getY()) {
                throw new IllegalArgumentException("Exceeds the space in direction y!");
            }

            return new Index(x, y);
        }

        Range getSubRange(Index index) {
            return subRanges[index.getX()][index.getY()];
        }

    }

    public static class UniformPartitioning {

        private final Space space;
        private final List<Point>[][] buckets;

        public UniformPartitioning(Space space) {
            this.space = space;

            Partitions partitions = space.partitions;
            buckets = (List<Point>[][]) new List[partitions.getX()][partitions.getY()];
            for (int i = 0; i < partitions.getX(); i++) {
                for (int j = 0; j < partitions.getY(); j++) {
                    buckets[i][j] = new ArrayList<>();
                }
            }
        }

        public void insert(Point point) {
            Index index = space.spatialHash(point);
            buckets[index.getX()][index.getY()].add(point);
        }

        List<Point> getBucket(Index index) {
            return new ArrayList<>(buckets[index.getX()][index.getY()]);
        }

        public Point nearest(Point target) {
            Point nearestPoint = null;
            double nearestDistance = Double.POSITIVE_INFINITY;

            Index nearestIndex = space.spatialHash(target);
            Set<Index> marked = new HashSet<>();
            marked.add(nearestIndex);

            Deque<Index> toSearch = new LinkedList<>();
            toSearch.add(nearestIndex);

            while (!toSearch.isEmpty()) {
                Index index = toSearch.poll();

                Range range = space.getSubRange(index);

                if (range.possibleShortest(target) >= nearestDistance) {
                    continue;
                }

                for (Point point : getBucket(index)) {
                    double currDistance = Point.distance(point, target);
                    if (currDistance < nearestDistance) {
                        nearestPoint = point;
                        nearestDistance = currDistance;
                    }
                }

                toSearch.addAll(neighboursToSearch(index, marked));
            }

            return nearestPoint;
        }

        public List<Point> rangeSearch(Range range) {
            List<Point> pointsInRange = new ArrayList<>();

            Point startPoint = new Point(range.lower.getX(), range.lower.getY());
            Index startIndex = space.spatialHash(startPoint);

            Point endPoint = new Point(range.upper.getX(), range.upper.getY());
            Index endIndex = space.spatialHash(endPoint);

            for (int i = startIndex.getX(); i <= endIndex.getX(); i++) {
                for (int j = startIndex.getY(); j <= endIndex.getY(); j++) {
                    if (
                        i != startIndex.getX() && i != endIndex.getX()
                            && j != startIndex.getY() && j != endIndex.getY()
                    ) {
                        pointsInRange.addAll(buckets[i][j]);
                        continue;
                    }

                    for (Point points : buckets[i][j]) {
                        if (range.contains(points)) {
                            pointsInRange.add(points);
                        }
                    }
                }
            }

            return pointsInRange;
        }

        private List<Index> neighboursToSearch(Index index, Set<Index> marked) {
            List<Index> toSearch = new ArrayList<>();
            for (Index neighbour : index.getNeighbours(space.partitions)) {
                if (marked.contains(neighbour)) {
                    continue;
                }
                marked.add(neighbour);
                toSearch.add(neighbour);
            }

            return toSearch;
        }

    }

    private static void normalTest() {
        // check if nearest is right
        Point[] points = {
            new Point(2, 3), new Point(4, 2),
            new Point(4, 2), new Point(4, 5),
            new Point(3, 3), new Point(1, 5),
            new Point(4, 4)
        };

        Range range = new Range(new Bounds(0, 0), new Bounds(9, 9));
        Partitions partitions = new Partitions(3, 3);
        Space space = new Space(range, partitions);
        UniformPartitioning uniformPartitioning = new UniformPartitioning(space);

        for (Point point : points) {
            uniformPartitioning.insert(point);
        }

        Point nearestPoint = uniformPartitioning.nearest(new Point(0, 7));
        assertEquals(new Point(1, 5), nearestPoint);
        nearestPoint = uniformPartitioning.nearest(new Point(3, 3));
        assertEquals(new Point(3, 3), nearestPoint);
        nearestPoint = uniformPartitioning.nearest(new Point(3, 2));
        assertEquals(new Point(4, 2), nearestPoint);
        nearestPoint = uniformPartitioning.nearest(new Point(3, 8));
        assertEquals(new Point(4, 5), nearestPoint);
        nearestPoint = uniformPartitioning.nearest(new Point(5, 8));
        assertEquals(new Point(4, 5), nearestPoint);
        nearestPoint = uniformPartitioning.nearest(new Point(4, 3));
        assertEquals(new Point(3, 3), nearestPoint);

        // check if rangeSearch is right
        points = new Point[]{
            new Point(-1, -1), new Point(2, 2),
            new Point(0, 1), new Point(1, 0),
            new Point(-2, -2)
        };

        range = new Range(new Bounds(-3, -3), new Bounds(3, 3));
        partitions = new Partitions(3, 3);
        space = new Space(range, partitions);
        uniformPartitioning = new UniformPartitioning(space);

        for (Point point : points) {
            uniformPartitioning.insert(point);
        }

        List<Point> pointsInRange = uniformPartitioning.rangeSearch(
            new Range(new Bounds(-2.5, -2.5), new Bounds(2.5, 2.5))
        );
        assertEquals(
            new HashSet<>(Arrays.asList(points)), new HashSet<>(pointsInRange)
        );

        pointsInRange = uniformPartitioning.rangeSearch(
            new Range(new Bounds(1, 1), new Bounds(2.5, 2.5))
        );
        assertEquals(
            new HashSet<>(Arrays.asList(points[1])), new HashSet<>(pointsInRange)
        );
    }

    private static Point naiveNearest(List<Point> points, Point target) {
        double nearestDistance = Double.POSITIVE_INFINITY;
        Point nearestPoint = null;

        for (Point point : points) {
            double currDistance = Point.distance(point, target);
            if (currDistance < nearestDistance) {
                nearestDistance = currDistance;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    private static List<Point> naiveRangeSearch(List<Point> points, Range range) {
        List<Point> pointsInRange = new ArrayList<>();

        for (Point point : points) {
            if (range.contains(point)) {
                pointsInRange.add(point);
            }
        }

        return pointsInRange;
    }

    private static void randomTest() {
        Random random = new Random();

        // check if nearest is right
        for (int i = 0; i < 500; i++) {
            List<Point> points = new ArrayList<>();

            Range range = new Range(new Bounds(-10, -10), new Bounds(10, 10));
            Partitions partitions = new Partitions(5 + random.nextInt(5), 5 + random.nextInt(5));
            Space space = new Space(range, partitions);
            UniformPartitioning uniformPartitioning = new UniformPartitioning(space);

            for (int j = 0; j < 1000; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point point = new Point(x, y);
                points.add(point);
                uniformPartitioning.insert(point);
            }

            for (int j = 0; j < 200; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point target = new Point(x, y);
                Point expectedNearest = naiveNearest(points, target);
                Point nearest = uniformPartitioning.nearest(target);
                assertEquals(
                    Point.distance(expectedNearest, target),
                    Point.distance(nearest, target),
                    0.0000001
                );
            }
        }

        // check if rangeSearch is right
        for (int i = 0; i < 500; i++) {
            List<Point> points = new ArrayList<>();

            Range range = new Range(new Bounds(-10, -10), new Bounds(10, 10));
            Partitions partitions = new Partitions(5 + random.nextInt(5), 5 + random.nextInt(5));
            Space space = new Space(range, partitions);
            UniformPartitioning uniformPartitioning = new UniformPartitioning(space);

            for (int j = 0; j < 1000; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point point = new Point(x, y);
                points.add(point);
                uniformPartitioning.insert(point);
            }

            for (int j = 0; j < 200; j++) {
                double oneX = -10 + 20 * random.nextDouble();
                double otherX = -10 + 20 * random.nextDouble();
                double oneY = -10 + 20 * random.nextDouble();
                double otherY = -10 + 20 * random.nextDouble();
                Range searchRange = new Range(
                    new Bounds(Math.min(oneX, otherX), Math.min(oneY, otherY)),
                    new Bounds(Math.max(oneX, otherX), Math.max(oneY, otherY))
                );

                List<Point> expectedPointsInRange = naiveRangeSearch(points, searchRange);
                List<Point> pointsInRange = uniformPartitioning.rangeSearch(searchRange);
                assertEquals(
                    new HashSet<>(expectedPointsInRange), new HashSet<>(pointsInRange)
                );
            }
        }
    }

    public static void main(String[] args) {
        normalTest();
        randomTest();
    }

}
