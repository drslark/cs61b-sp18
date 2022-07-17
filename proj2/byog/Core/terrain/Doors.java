package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import byog.Core.coordinates.Location2D;

/**
 * Door management.
 */
public class Doors {

    /**
     * Generates a random door with regard of a collection of rooms and hallways.
     *
     * @param rooms A collection of rooms.
     * @param hallways A collection of hallways.
     * @param random A pseudorandom generator.
     * @return Generated door.
     */
    public static Door generateRandomDoor(
            Collection<Room> rooms, Collection<Hallway> hallways, Random random
    ) {
        Set<Location2D> contentLocations = new HashSet<>();
        Set<Location2D> marginLocations = new HashSet<>();

        Collection<WithMargin<Location2D>> terrains = new HashSet<>();
        terrains.addAll(rooms);
        terrains.addAll(hallways);

        getBordersAndMargins(terrains, contentLocations, marginLocations);

        Location2D doorLocation = generateRandomDoorLocation(
                contentLocations, marginLocations, random
        );

        return new Door(doorLocation);
    }

    private static Location2D generateRandomDoorLocation(
            Set<Location2D> contentLocations, Set<Location2D> marginLocations, Random random
    ) {
        List<Location2D> availableDoorLocations = new ArrayList<>();

        for (Location2D marginLocation : marginLocations) {
            int internalCount = 0;
            int externalCount = 0;
            for (Location2D neighbourLocation : marginLocation.getCloseNeighbours(1)) {
                if (contentLocations.contains(neighbourLocation)) {
                    internalCount += 1;
                } else if (!marginLocations.contains(neighbourLocation)) {
                    externalCount += 1;
                }
            }

            if (internalCount == 1 && externalCount == 1) {
                availableDoorLocations.add(marginLocation);
            }
        }

        Location2D doorLocation = availableDoorLocations.get(
                random.nextInt(availableDoorLocations.size())
        );

        return doorLocation;
    }

    private static <E extends WithMargin<Location2D>> void getBordersAndMargins(
            Collection<E> terrains,
            Set<Location2D> contentLocations,
            Set<Location2D> marginLocations
    ) {
        for (E terrain: terrains) {
            contentLocations.addAll(terrain.getContent());
        }

        for (E terrain: terrains) {
            for (Location2D marginLocation : terrain.getMargin()) {
                if (!contentLocations.contains(marginLocation)) {
                    marginLocations.add(marginLocation);
                }
            }
        }
    }

}
