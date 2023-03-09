package byog.Core.terrain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.terrain.concrete.SmallDoor;
import byog.Core.terrain.view.Door;
import byog.Core.terrain.view.Hallway;
import byog.Core.terrain.view.Room;
import byog.Core.terrain.view.Terrain;

/**
 * Door management.
 */
public class Doors {

    /**
     * Generates a random door with regard of a set of rooms and hallways.
     *
     * @param rooms A set of rooms.
     * @param hallways A set of hallways.
     * @return The generated door.
     */
    public static <E extends Location<E>> Door<E> generateRandomDoor(
        LinkedHashSet<Room<E>> rooms, LinkedHashSet<Hallway<E>> hallways
    ) {
        LinkedHashSet<Terrain<E>> terrains = new LinkedHashSet<Terrain<E>>() {
            {
                addAll(rooms);
                addAll(hallways);
            }
        };

        LinkedHashSet<E> content = getContent(terrains);
        LinkedHashSet<E> margin = getMargin(terrains, content);
        E doorLocation = generateRandomDoorLocation(content, margin);
        return new SmallDoor<>(doorLocation);
    }

    private static <E extends Location<E>> LinkedHashSet<E> getContent(
        LinkedHashSet<Terrain<E>> terrains
    ) {
        LinkedHashSet<E> content = new LinkedHashSet<>();

        for (Terrain<E> terrain : terrains) {
            content.addAll(terrain.getContent());
        }

        return content;
    }

    private static <E extends Location<E>> LinkedHashSet<E> getMargin(
        LinkedHashSet<Terrain<E>> terrains, LinkedHashSet<E> content
    ) {
        LinkedHashSet<E> margin = new LinkedHashSet<>();

        for (Terrain<E> terrain : terrains) {
            for (E location : terrain.getMargin()) {
                if (!content.contains(location)) {
                    margin.add(location);
                }
            }
        }

        return margin;
    }

    private static <E extends Location<E>> E generateRandomDoorLocation(
        LinkedHashSet<E> content, LinkedHashSet<E> margin
    ) {
        List<E> availableDoorLocations = new ArrayList<>();

        for (E marginLocation : margin) {
            int internalCount = 0;
            int externalCount = 0;
            for (E neighbourLocation : marginLocation.getCloseNeighbours()) {
                if (content.contains(neighbourLocation)) {
                    internalCount += 1;
                } else if (!margin.contains(neighbourLocation)) {
                    externalCount += 1;
                }
            }

            if (internalCount == 1 && externalCount == 1) {
                availableDoorLocations.add(marginLocation);
            }
        }

        E doorLocation = availableDoorLocations.get(
            Context.random().nextInt(availableDoorLocations.size())
        );

        return doorLocation;
    }

}
