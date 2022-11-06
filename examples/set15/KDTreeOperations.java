import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeOperations {

    private static class Point {

        private final double[] coordinates;

        Point(double... cs) {
            coordinates = cs;
        }

        static double distance(Point point, Point otherPoint) {
            if (point == null || otherPoint == null) {
                return Double.POSITIVE_INFINITY;
            }

            double squareSum = 0.0;
            for (int i = 0; i < point.getDimension(); i++) {
                double diff = otherPoint.get(i) - point.get(i);
                squareSum += diff * diff;
            }
            return Math.sqrt(squareSum);
        }

        double get(int idx) {
            return coordinates[idx];
        }

        void set(int idx, double coordinate) {
            coordinates[idx] = coordinate;
        }

        int getDimension() {
            return coordinates.length;
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
            return Arrays.equals(coordinates, otherPoint.coordinates);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(coordinates);
        }
    }

    private static class Range {

        private final double[] lefts;

        private final double[] rights;

        Range(int dimension) {
            lefts = new double[dimension];
            rights = new double[dimension];
            Arrays.fill(lefts, Double.NEGATIVE_INFINITY);
            Arrays.fill(rights, Double.POSITIVE_INFINITY);
        }

        Range(Range range) {
            lefts = Arrays.copyOf(range.lefts, range.lefts.length);
            rights = Arrays.copyOf(range.rights, range.rights.length);
        }

        Range copy() {
            return new Range(this);
        }

        Range subRange(boolean isRight, int idx, double partition) {
            Range range = copy();
            if (isRight) {
                range.lefts[idx] = partition;
            } else {
                range.rights[idx] = partition;
            }
            return range;
        }

    }

    private static class Border {

        private final Double[] coordinates;

        Border(int dimension) {
            coordinates = new Double[dimension];
        }

        Border(Double[] cs) {
            coordinates = cs;
        }

        Double get(int idx) {
            return coordinates[idx];
        }

        void set(int idx, Double coordinate) {
            coordinates[idx] = coordinate;
        }

        double possibleShortest(Point target) {
            double squareSum = 0.0;
            for (int i = 0; i < getDimension(); i++) {
                if (coordinates[i] == null) {
                    continue;
                }
                double diff = target.get(i) - get(i);
                squareSum += diff * diff;
            }
            return Math.sqrt(squareSum);
        }

        int getDimension() {
            return coordinates.length;
        }

    }

    private static class KDTree {

        private final int index;

        Point point;

        Range range;

        KDTree left;

        KDTree right;

        KDTree(Point p, int i, Range r) {
            point = p;
            index = i;
            range = r;
        }

        int getIndex() {
            return index;
        }
    }

    public static KDTree insert(KDTree kdTree, Point point, int idx, Range range) {
        if (kdTree == null) {
            return new KDTree(point, idx, range);
        }

        if (point.equals(kdTree.point)) {
            return kdTree;
        }

        int nextIdx = (idx + 1) % point.getDimension();
        if (point.get(idx) < kdTree.point.get(idx)) {
            kdTree.left = insert(
                kdTree.left, point, nextIdx,
                kdTree.range.subRange(false, idx, kdTree.point.get(idx))
            );
        } else {
            kdTree.right = insert(
                kdTree.right, point, nextIdx,
                kdTree.range.subRange(true, idx, kdTree.point.get(idx))
            );
        }

        return kdTree;
    }

    public static Point nearest(
        KDTree kdTree, Point target, Point nearestPoint, Border border, boolean checkPrune
    ) {
        if (kdTree == null) {
            return nearestPoint;
        }

        if (checkPrune && border.possibleShortest(target) >= Point.distance(nearestPoint, target)) {
            return nearestPoint;
        }

        nearestPoint = nearer(target, nearestPoint, kdTree.point);

        int idx = kdTree.getIndex();

        KDTree goodSide, badSide;
        if (target.get(idx) < kdTree.point.get(idx)) {
            goodSide = kdTree.left;
            badSide = kdTree.right;
        } else {
            goodSide = kdTree.right;
            badSide = kdTree.left;
        }

        nearestPoint = nearest(goodSide, target, nearestPoint, border, false);

        Double originalCoordinate = border.get(idx);
        border.set(idx, kdTree.point.get(idx));
        nearestPoint = nearest(badSide, target, nearestPoint, border, true);
        border.set(idx, originalCoordinate);

        return nearestPoint;
    }

    private static Point nearer(Point target, Point point, Point otherPoint) {
        if (Point.distance(point, target) <= Point.distance(otherPoint, target)) {
            return point;
        }

        return otherPoint;
    }

    private static void normalTest() {
        // check if nearest is right
        int dimension = 2;

        Point[] points = {
            new Point(2, 3), new Point(4, 2),
            new Point(4, 2), new Point(4, 5),
            new Point(3, 3), new Point(1, 5),
            new Point(4, 4)
        };

        KDTree kdTree = null;
        for (Point point : points) {
            kdTree = insert(kdTree, point, 0, new Range(dimension));
        }

        Point nearestPoint = nearest(kdTree, new Point(0, 7), null, new Border(dimension), false);
        assertEquals(new Point(1, 5), nearestPoint);
        nearestPoint = nearest(kdTree, new Point(3, 3), null, new Border(dimension), false);
        assertEquals(new Point(3, 3), nearestPoint);
        nearestPoint = nearest(kdTree, new Point(3, 2), null, new Border(dimension), false);
        assertEquals(new Point(4, 2), nearestPoint);
        nearestPoint = nearest(kdTree, new Point(3, 8), null, new Border(dimension), false);
        assertEquals(new Point(4, 5), nearestPoint);
        nearestPoint = nearest(kdTree, new Point(5, 8), null, new Border(dimension), false);
        assertEquals(new Point(4, 5), nearestPoint);
        nearestPoint = nearest(kdTree, new Point(4, 3), null, new Border(dimension), false);
        assertEquals(new Point(4, 2), nearestPoint);
    }

    private static Point naiveNearest(List<Point> points, Point target) {
        double nearestDistance = Double.POSITIVE_INFINITY;
        Point nearestPoint = null;

        for (Point point : points) {
            double currentDistance = Point.distance(point, target);
            if (currentDistance < nearestDistance) {
                nearestDistance = currentDistance;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    private static void randomTest() {
        Random random = new Random();

        int dimension = 2;

        // check if nearest is right
        for (int i = 0; i < 500; i++) {
            List<Point> points = new ArrayList<>();

            KDTree kdTree = null;

            for (int j = 0; j < 1000; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point point = new Point(x, y);
                points.add(point);
                kdTree = insert(kdTree, point, 0, new Range(dimension));
            }

            for (int j = 0; j < 200; j++) {
                double x = -10 + random.nextDouble() * 20;
                double y = -10 + random.nextDouble() * 20;
                Point target = new Point(x, y);
                Point expectedNearest = naiveNearest(points, target);
                Point nearest = nearest(
                    kdTree, target, null, new Border(dimension), false
                );
                assertEquals(
                    Point.distance(expectedNearest, target),
                    Point.distance(nearest, target),
                    0.0000001
                );
            }
        }
    }

    public static void main(String[] args) {
        normalTest();
        randomTest();
    }

}
