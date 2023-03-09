package byog.Core.terrain.concrete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import byog.Core.coordinate.Location;
import byog.Core.terrain.template.CompoundTerrain;
import byog.Core.terrain.view.Door;
import byog.Core.terrain.view.Hallway;
import byog.Core.terrain.view.Room;
import byog.Core.terrain.view.Terrain;
import byog.Core.terrain.view.World;

/**
 * The DungeonWorld is used to store information of the dungeon world.
 */
public class DungeonWorld<E extends Location<E>> extends CompoundTerrain<E>
    implements World<E>, Serializable {

    private static final long serialVersionUID = 314L;

    private final int hashCode;

    private final LinkedHashSet<Room<E>> rooms;
    private final LinkedHashSet<Hallway<E>> hallways;
    private final Door<E> door;

    /**
     * Constructor with rooms, hallways and door.
     *
     * @param rooms A set of dungeon rooms.
     * @param hallways A set of dungeon hallways.
     * @param door A dungeon door.
     */
    public DungeonWorld(
        LinkedHashSet<Room<E>> rooms, LinkedHashSet<Hallway<E>> hallways, Door<E> door
    ) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.door = door;

        initializeLocations();

        hashCode = generateHashCode();
    }

    /**
     * Gets the dungeon rooms.
     *
     * @return All dungeon rooms.
     */
    public Set<Room<E>> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    /**
     * Gets the dungeon hallways.
     *
     * @return All dungeon hallways.
     */
    public Set<Hallway<E>> getHallways() {
        return Collections.unmodifiableSet(hallways);
    }

    /**
     * Gets the dungeon door.
     *
     * @return The dungeon door.
     */
    public Door<E> getDoor() {
        return door;
    }

    /**
     * Gets the location of the door in the dungeons world.
     *
     * @return The location of the door
     */
    public E getDoorLocation() {
        return getDoor().getLocation();
    }

    @Override
    protected void initializeLocations() {
        setContent(generateContent());
        setBorder(generateBorder());
        setMargin(generateMargin());
    }

    @Override
    protected LinkedHashSet<E> generateContent() {
        List<Terrain<E>> terrains = new ArrayList<>();
        terrains.addAll(rooms);
        terrains.addAll(hallways);

        LinkedHashSet<E> content = new LinkedHashSet<>();

        for (Terrain<E> terrain : terrains) {
            content.addAll(terrain.getContent());
        }

        return content;
    }

    @Override
    protected LinkedHashSet<E> generateMargin() {
        List<Terrain<E>> terrains = new ArrayList<>();
        terrains.addAll(rooms);
        terrains.addAll(hallways);

        LinkedHashSet<E> margin = new LinkedHashSet<>();

        for (Terrain<E> terrain : terrains) {
            for (E marginLocation : terrain.getMargin()) {
                if (!getContent().contains(marginLocation)) {
                    margin.add(marginLocation);
                }
            }
        }

        return margin;
    }

    @Override
    protected LinkedHashSet<E> generateBorder() {
        LinkedHashSet<E> border = new LinkedHashSet<>();
        for (E contentLocation : getContent()) {
            for (E neighbourLocation : contentLocation.getCloseNeighbours()) {
                if (!getContent().contains(neighbourLocation)) {
                    border.add(neighbourLocation);
                    break;
                }
            }
        }
        return border;
    }

    private int generateHashCode() {
        int tempHashCode = 0;
        for (Room<E> room : rooms) {
            tempHashCode += room.hashCode();
        }
        for (Hallway<E> hallway : hallways) {
            tempHashCode += hallway.hashCode();
        }
        tempHashCode += door.hashCode();
        return tempHashCode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof DungeonWorld)) {
            return false;
        }

        DungeonWorld<E> thatDungeonWorld = (DungeonWorld<E>) that;

        return rooms.equals(thatDungeonWorld.rooms)
            && hallways.equals(thatDungeonWorld.hallways)
            && door.equals(thatDungeonWorld.door);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
