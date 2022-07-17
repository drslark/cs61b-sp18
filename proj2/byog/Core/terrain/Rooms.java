package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static byog.Core.RandomUtils.uniform;
import byog.Core.coordinates.Location2D;

/**
 * Room management.
 */
public class Rooms {

    /**
     * Generates some random rooms with regard of a canvas.
     *
     * @param canvas The canvas of the dungeon.
     * @param numRooms The desired number of random rooms.
     * @param random A pseudorandom generator.
     * @return Some random rooms.
     */
    public static Collection<Room> generateRandomRooms(Canvas canvas, int numRooms, Random random) {

        List<Room> rooms = new ArrayList<>();

        // generate random rooms
        for (int i = 0; i < numRooms; i++) {
            Room room = Rooms.generateRandomRoom(canvas, random);
            if (!Rooms.isOverlap(room, rooms)) {
                rooms.add(room);
            }
        }

        return rooms;
    }

    private static Room generateRandomRoom(Canvas canvas, Random random) {
        if (canvas.getWidth() < 10 || canvas.getHeight() < 10) {
            throw new RuntimeException(
                "The width and height of the canvas must be larger than 10!"
            );
        }

        int baseLimitInWidth = canvas.getWidth() / 5;
        int lowerBoundInWidth = 1;
        int upperBoundInWidth = canvas.getWidth() - 1;

        int[] xAnchorCenterWidth = new int[3];
        generateRandomAnchorCenterExtent(
            xAnchorCenterWidth, baseLimitInWidth, lowerBoundInWidth, upperBoundInWidth, random
        );

        int baseLimitInHeight = canvas.getHeight() / 5;
        int lowerBoundInHeight = 1;
        int upperBoundInHeight = canvas.getHeight() - 1;

        int[] yAnchorCenterWidth = new int[3];
        generateRandomAnchorCenterExtent(
            yAnchorCenterWidth, baseLimitInHeight, lowerBoundInHeight, upperBoundInHeight, random
        );

        return new Room(
            new Location2D(xAnchorCenterWidth[0], yAnchorCenterWidth[0]),
            new Location2D(xAnchorCenterWidth[1], yAnchorCenterWidth[1]),
            xAnchorCenterWidth[2], yAnchorCenterWidth[2]
        );
    }

    private static void generateRandomAnchorCenterExtent(
        int[] anchorCenterExtent, int baseLimit, int lowerBound, int upperBound, Random random
    ) {
        baseLimit = Math.max(baseLimit % 2 == 0 ? baseLimit : baseLimit - 1, 1);
        int center = uniform(random, lowerBound, upperBound);

        if (random.nextDouble() < 0.5) {
            // odd case
            int halfLimitInWidth = Math.min(center - lowerBound + 1, upperBound - center);
            halfLimitInWidth = Math.min(halfLimitInWidth, baseLimit / 2);

            int halfWidth = uniform(random, 0, halfLimitInWidth);
            int anchor = center - halfWidth;
            int extent = 2 * halfWidth + 1;

            anchorCenterExtent[0] = anchor;
            anchorCenterExtent[1] = center;
            anchorCenterExtent[2] = extent;
        } else {
            // even case
            int leftLimitInWidth = center - lowerBound + 1;
            int rightLimitInWidth = upperBound - center;
            int halfLimitInWidth = Math.min(leftLimitInWidth, rightLimitInWidth);
            halfLimitInWidth = Math.min(halfLimitInWidth, baseLimit / 2);

            int halfWidth = uniform(random, 0, halfLimitInWidth);
            int extent = halfWidth * 2 + 2;
            int anchor = leftLimitInWidth < rightLimitInWidth
                ? center - halfWidth
                : center - halfWidth - 1;

            anchorCenterExtent[0] = anchor;
            anchorCenterExtent[1] = center;
            anchorCenterExtent[2] = extent;
        }
    }

    /**
     * Whether one room is overlap with another.
     *
     * @param thisRoom One room.
     * @param thatRoom another room.
     * @return Whether one room is overlap with another.
     */
    public static boolean isOverlap(Room thisRoom, Room thatRoom) {
        boolean isXOverlap = !(
            thisRoom.getAnchorX() >= thatRoom.getAnchorX() + thatRoom.getWidth()
                || thisRoom.getAnchorX() + thisRoom.getWidth() <= thatRoom.getAnchorX()
        );

        boolean isYOverlap = !(
            thisRoom.getAnchorY() >= thatRoom.getAnchorY() + thatRoom.getHeight()
                || thisRoom.getAnchorY() + thisRoom.getHeight() <= thatRoom.getAnchorY()
        );

        return isXOverlap && isYOverlap;
    }

    /**
     * Whether one room is overlap with some existed rooms.
     *
     * @param room One room.
     * @param existedRooms Other rooms.
     * @return Whether one room is overlap with some existed rooms.
     */
    public static boolean isOverlap(Room room, Collection<Room> existedRooms) {
        for (Room existedRoom : existedRooms) {
            if (isOverlap(room, existedRoom)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether one room is margin with another.
     *
     * @param thisRoom One room.
     * @param thatRoom another room.
     * @return Whether one room is margin with another.
     */
    public static boolean isMargin(Room thisRoom, Room thatRoom) {
        boolean isXOverlap = !(
            thisRoom.getAnchorX() >= thatRoom.getAnchorX() + thatRoom.getWidth()
                || thisRoom.getAnchorX() + thisRoom.getWidth() <= thatRoom.getAnchorX()
        );

        boolean isXMargin =
            thisRoom.getAnchorX() == thatRoom.getAnchorX() + thatRoom.getWidth()
                || thisRoom.getAnchorX() + thisRoom.getWidth() == thatRoom.getAnchorX();

        boolean isYOverlap = !(
            thisRoom.getAnchorY() >= thatRoom.getAnchorY() + thatRoom.getHeight()
                || thisRoom.getAnchorY() + thisRoom.getHeight() <= thatRoom.getAnchorY()
        );

        boolean isYMargin =
            thisRoom.getAnchorY() == thatRoom.getAnchorY() + thatRoom.getHeight()
                || thisRoom.getAnchorY() + thisRoom.getHeight() == thatRoom.getAnchorY();

        return (isXMargin && isYOverlap) || (isYMargin && isXOverlap);
    }

}
