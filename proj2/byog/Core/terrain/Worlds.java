package byog.Core.terrain;

import java.util.LinkedHashSet;

import byog.Core.coordinate.Location;
import byog.Core.terrain.concrete.DungeonWorld;
import byog.Core.terrain.view.Door;
import byog.Core.terrain.view.Hallway;
import byog.Core.terrain.view.Room;
import byog.Core.terrain.view.World;

/**
 * World management.
 */
public class Worlds {

    /**
     * Generates a random world.
     *
     * @return A random world.
     */
    public static <E extends Location<E>> World<E> generateRandomWorld() {
        LinkedHashSet<Room<E>> rooms = Rooms.generateRandomRooms(50);
        LinkedHashSet<Hallway<E>> hallways = Hallways.generateRandomHallways(rooms);
        Door<E> door = Doors.generateRandomDoor(rooms, hallways);

        World<E> world = new DungeonWorld<>(rooms, hallways, door);

        return world;
    }

}
