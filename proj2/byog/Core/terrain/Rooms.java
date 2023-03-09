package byog.Core.terrain;

import java.util.LinkedHashSet;
import java.util.Set;

import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.terrain.concrete.RectangularRoom;
import byog.Core.terrain.view.Room;

import static byog.Core.RandomUtils.uniform;

/**
 * Room management.
 */
public class Rooms {

    /**
     * Generates a set of random rooms.
     *
     * @param numRooms The desired number of random rooms.
     * @return A set of random rooms.
     */
    public static <E extends Location<E>> LinkedHashSet<Room<E>> generateRandomRooms(int numRooms) {
        LinkedHashSet<Room<E>> rooms = new LinkedHashSet<>();

        // generate random rooms
        for (int i = 0; i < numRooms; i++) {
            Room<E> room = Rooms.generateRandomRoom();
            if (!Rooms.isOverlap(room, rooms)) {
                rooms.add(room);
            }
        }

        return rooms;
    }

    /**
     * Whether one room is overlap with all existed rooms.
     *
     * @param room One room.
     * @param existedRooms Existed rooms.
     * @return Whether one room is overlap with all existed rooms.
     */
    public static <E extends Location<E>> boolean isOverlap(
        Room<E> room, Set<Room<E>> existedRooms
    ) {
        for (Room<E> existedRoom : existedRooms) {
            if (room.isOverlap(existedRoom)) {
                return true;
            }
        }

        return false;
    }

    private static <E extends Location<E>> Room<E> generateRandomRoom() {
        int[] shape = Context.shape();
        for (int i = 0; i < shape.length; i++) {
            if (shape[i] < 10) {
                throw new RuntimeException(
                    "Any size of the canvas shape must be larger than 10!"
                );
            }
        }

        int[] roomAnchor = new int[shape.length];
        int[] roomCenter = new int[shape.length];
        int[] roomShape = new int[shape.length];

        for (int i = 0; i < shape.length; i++) {
            int sizeLimit = shape[i] / 5;
            int lowerBound = 1;
            int upperBound = shape[i] - 1;

            int[] anchorCenterSize = new int[3];
            generateRandomAnchorCenterExtent(
                anchorCenterSize, sizeLimit, lowerBound, upperBound
            );

            roomAnchor[i] = anchorCenterSize[0];
            roomCenter[i] = anchorCenterSize[1];
            roomShape[i] = anchorCenterSize[2];
        }

        return new RectangularRoom<>(
            (E) Context.get(roomAnchor),
            (E) Context.get(roomCenter),
            roomShape
        );
    }

    private static void generateRandomAnchorCenterExtent(
        int[] anchorCenterExtent, int baseLimit, int lowerBound, int upperBound
    ) {
        baseLimit = Math.max(baseLimit % 2 == 0 ? baseLimit : baseLimit - 1, 1);
        int center = uniform(Context.random(), lowerBound, upperBound);

        if (Context.random().nextDouble() < 0.5) {
            // odd case
            int halfLimitInWidth = Math.min(center - lowerBound + 1, upperBound - center);
            halfLimitInWidth = Math.min(halfLimitInWidth, baseLimit / 2);

            int halfWidth = uniform(Context.random(), 0, halfLimitInWidth);
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

            int halfWidth = uniform(Context.random(), 0, halfLimitInWidth);
            int extent = halfWidth * 2 + 2;
            int anchor = leftLimitInWidth < rightLimitInWidth
                ? center - halfWidth
                : center - halfWidth - 1;

            anchorCenterExtent[0] = anchor;
            anchorCenterExtent[1] = center;
            anchorCenterExtent[2] = extent;
        }
    }

}
