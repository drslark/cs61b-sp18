package byog.Core.terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import byog.Core.context.Context;
import byog.Core.coordinate.Location;
import byog.Core.interaction.constant.Command;
import byog.Core.terrain.concrete.SmallPlayer;
import byog.Core.terrain.view.Player;

/**
 * Player management.
 */
public class Players {

    private static List passableLocations = null;

    private static Map<Command, int[]> movements = null;

    /**
     * Sets global passable locations.
     *
     * @param passableLocations All passable locations.
     */
    public static <E extends Location<E>> void setPassableLocations(Set<E> passableLocations) {
        Players.passableLocations = new ArrayList<>(passableLocations);
    }

    /**
     * Gets global passable locations.
     *
     * @return All passable locations.
     */
    public static <E extends Location<E>> List<E> getPassableLocations() {
        if (passableLocations == null) {
            throw new RuntimeException("Passable locations have not been set!");
        }
        return Collections.unmodifiableList((List<E>) passableLocations);
    }

    /**
     * Generates a random player in the random passable locations.
     */
    public static <E extends Location<E>> Player<E> generateRandomPlayer() {
        E location = (E) passableLocations.get(Context.random().nextInt(passableLocations.size()));
        return new SmallPlayer<>(location);
    }

    /**
     * Initialize the movements in the global coordinate system.
     */
    public static void initializeMovements() {
        movements = new HashMap<Command, int[]>() {
            {
                put(Command.MOVE_UP, Context.origin().upOffset());
                put(Command.MOVE_DOWN, Context.origin().downOffset());
                put(Command.MOVE_LEFT, Context.origin().leftOffset());
                put(Command.MOVE_RIGHT, Context.origin().rightOffset());
            }
        };
    }

    /**
     * Gets the movement with regard of a command.
     *
     * @param command A user command.
     * @return Movement with regard of the input command.
     */
    public static int[] getMovement(Command command) {
        if (movements == null) {
            throw new RuntimeException("Movements have not been initialized!");
        }

        return movements.get(command);
    }

    /**
     * Moves a player to a new location.
     *
     * @param player The player to move.
     * @param movement The movement of the player.
     * @return The player at a new location.
     */
    public static <E extends Location<E>> Player<E> move(Player<E> player, int[] movement) {
        E location = player.getLocation();

        if (!passableLocations.contains(location)) {
            throw new RuntimeException("Invalid player!");
        }

        E steppedLocation = location.offset(movement);
        if (!passableLocations.contains(steppedLocation)) {
            return player;
        }

        return new SmallPlayer<>(steppedLocation);
    }

}
