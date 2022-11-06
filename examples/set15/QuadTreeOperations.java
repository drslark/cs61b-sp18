import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class QuadTreeOperations {

    private static final int HASH_BASE = 1201;

    private static class Point {

        double x;
        double y;

        static Position getRelativePosition(Point point, Point otherPoint) {
            byte positionValue = 0b00;
            if (otherPoint.y >= point.y) {
                positionValue |= 0b10;
            }
            if (otherPoint.x >= point.x) {
                positionValue |= 0b01;
            }
            return Position.getPosition(positionValue);
        }

        Point(double x, double y) {
            this.x = x;
            this.y = y;
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
            return x == otherPoint.x && y == otherPoint.y;
        }

        @Override
        public int hashCode() {
            return HASH_BASE * Double.hashCode(x) + Double.hashCode(y);
        }
    }

    private enum Position {

        TOP_RIGHT((byte) 0b11),
        TOP_LEFT((byte) 0b10),
        BOTTOM_LEFT((byte) 0b00),
        BOTTOM_RIGHT((byte) 0b01);

        static final Map<Byte, Position> POSITION_MAP = new HashMap<Byte, Position>() {
            {
                put((byte) 0b11, TOP_RIGHT);
                put((byte) 0b10, TOP_LEFT);
                put((byte) 0b00, BOTTOM_LEFT);
                put((byte) 0b01, BOTTOM_RIGHT);
            }
        };

        private final byte value;

        static Position getPosition(byte value) {
            if (!POSITION_MAP.containsKey(value)) {
                throw new IllegalArgumentException("Invalid position value!");
            }
            return POSITION_MAP.get(value);
        }

        Position(byte value) {
            this.value = value;
        }

        byte value() {
            return value;
        }

        boolean isTopSide() {
            return (this.value() & 0b10) > 0;
        }

        boolean isRightSide() {
            return (this.value() & 0b01) > 0;
        }

    }

    private static class Range {

        private double top = Double.POSITIVE_INFINITY;
        private double bottom = Double.NEGATIVE_INFINITY;
        private double left = Double.NEGATIVE_INFINITY;
        private double right = Double.POSITIVE_INFINITY;

        Range() {
        }

        Range(double top, double bottom, double left, double right) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        Range copy() {
            return new Range(top, bottom, left, right);
        }

        Range subRange(Position position, Point point) {
            Range subRange = copy();

            if (position.isTopSide()) {
                subRange.bottom = point.y;
            } else {
                subRange.top = point.y;
            }

            if (position.isRightSide()) {
                subRange.left = point.x;
            } else {
                subRange.right = point.x;
            }

            return subRange;
        }

        boolean intersect(Range range) {
            boolean intersectInX = !(left >= range.right || right <= range.left);
            boolean intersectInY = !(bottom >= range.top || top <= range.bottom);
            return intersectInX && intersectInY;
        }

        boolean contains(Point point) {
            boolean containsInX = point.x >= left && point.x < right;
            boolean containsInY = point.y >= bottom && point.y < top;
            return containsInX && containsInY;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof Range)) {
                return false;
            }

            Range otherRange = (Range) other;
            return top == otherRange.top && bottom == otherRange.bottom
                && left == otherRange.left && right == otherRange.right;
        }

        @Override
        public int hashCode() {
            int hashValue = 0;
            for (double value : new double[]{top, bottom, left, right}) {
                hashValue = HASH_BASE * hashValue + Double.hashCode(value);
            }

            return hashValue;
        }

    }

    private static class QuadTree {

        Point point;
        Range range;

        QuadTree[] children;

        QuadTree(Point point) {
            this.point = point;
            children = new QuadTree[4];
        }

        QuadTree(Point point, Range range) {
            this(point);
            this.range = range;
        }

    }

    static QuadTree insert(QuadTree quadTree, Point point, Range range) {
        if (quadTree == null) {
            return new QuadTree(point, range);
        }

        if (point.equals(quadTree.point)) {
            return quadTree;
        }

        Position position = Point.getRelativePosition(quadTree.point, point);
        int index = position.value();
        Range subRange = quadTree.range.subRange(position, quadTree.point);
        quadTree.children[index] = insert(quadTree.children[index], point, subRange);

        return quadTree;
    }

    static void rangeSearch(QuadTree quadTree, Range range, List<Point> pointsInRange) {
        if (quadTree == null) {
            return;
        }

        if (!quadTree.range.intersect(range)) {
            return;
        }

        if (range.contains(quadTree.point)) {
            pointsInRange.add(quadTree.point);
        }

        for (QuadTree child : quadTree.children) {
            rangeSearch(child, range, pointsInRange);
        }
    }

    private static void normalTest() {
        // check if subRange is right
        Range baseRange = new Range();
        Range topRightRange = new Range(Double.POSITIVE_INFINITY, 0, 0, Double.POSITIVE_INFINITY);
        Range topLeftRange = new Range(Double.POSITIVE_INFINITY, 0, Double.NEGATIVE_INFINITY, 0);
        Range bottomLeftRange = new Range(0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0);
        Range bottomRightRange = new Range(0, Double.NEGATIVE_INFINITY, 0, Double.POSITIVE_INFINITY);

        assertEquals(topRightRange, baseRange.subRange(Position.TOP_RIGHT, new Point(0, 0)));
        assertEquals(topLeftRange, baseRange.subRange(Position.TOP_LEFT, new Point(0, 0)));
        assertEquals(bottomLeftRange, baseRange.subRange(Position.BOTTOM_LEFT, new Point(0, 0)));
        assertEquals(bottomRightRange, baseRange.subRange(Position.BOTTOM_RIGHT, new Point(0, 0)));

        // check if rangeSearch is right
        Point[] points = {
            new Point(-1, -1), new Point(2, 2),
            new Point(0, 1), new Point(1, 0),
            new Point(-2, -2)
        };

        QuadTree quadTree = null;

        for (Point point : points) {
            quadTree = insert(quadTree, point, new Range());
        }

        List<Point> pointsInRange = new ArrayList<>();
        rangeSearch(quadTree, new Range(), pointsInRange);
        assertEquals(
            new HashSet<>(Arrays.asList(points)), new HashSet<>(pointsInRange)
        );

        pointsInRange = new ArrayList<>();
        rangeSearch(quadTree, new Range(3, 1, 1, 3), pointsInRange);
        assertEquals(
            new HashSet<>(Arrays.asList(points[1])), new HashSet<>(pointsInRange)
        );
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

        // check if rangeSearch is right
        for (int i = 0; i < 500; i++) {
            List<Point> points = new ArrayList<>();

            QuadTree quadTree = null;

            for (int j = 0; j < 1000; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point point = new Point(x, y);
                points.add(point);
                quadTree = insert(quadTree, point, new Range());
            }

            for (int j = 0; j < 200; j++) {
                double oneX = -10 + 20 * random.nextDouble();
                double otherX = -10 + 20 * random.nextDouble();
                double oneY = -10 + 20 * random.nextDouble();
                double otherY = -10 + 20 * random.nextDouble();
                Range searchRange = new Range(
                    Math.max(oneY, otherY), Math.min(oneY, otherY),
                    Math.min(oneX, otherX), Math.min(oneY, otherY)
                );

                List<Point> expectedPointsInRange = naiveRangeSearch(points, searchRange);
                List<Point> pointsInRange = new ArrayList<>();
                rangeSearch(quadTree, searchRange, pointsInRange);
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
