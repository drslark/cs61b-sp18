package byog.Core.context;

import java.io.Serializable;

import byog.Core.coordinate.Location;
import byog.Core.interaction.User;
import byog.Core.persistence.Archiver;
import byog.Core.terrain.view.Player;
import byog.Core.terrain.view.World;

/**
 * All game info that should be archived, includes user state, world content,
 * and the player content.
 */
public class GameInfo<E extends Location<E>> implements Serializable {

    private static final long serialVersionUID = 314L;

    private User user;
    private World<E> world;
    private Player<E> player;

    /**
     * Loads the archive file to get game info.
     *
     * @return The game info archived in the file.
     */
    public static <E extends Location<E>> GameInfo<E> loadGameInfo() {
        return Archiver.load(Context.archiveFile());
    }

    /**
     * Gets the user state.
     *
     * @return The user state.
     */
    public User getUser() {
        if (user == null) {
            throw new NullPointerException("The user has not been set!");
        }
        return user;
    }

    /**
     * Gets the world content.
     *
     * @return The world content.
     */
    public World<E> getWorld() {
        if (world == null) {
            throw new NullPointerException("The world has not been set!");
        }
        return world;
    }

    /**
     * Gets the player content.
     *
     * @return The player content.
     */
    public Player<E> getPlayer() {
        if (player == null) {
            throw new NullPointerException("The player has not been set!");
        }
        return player;
    }

    /**
     * Sets the user state.
     *
     * @param user The user state.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the world content.
     *
     * @param world The world content.
     */
    public void setWorld(World<E> world) {
        this.world = world;
    }

    /**
     * Sets the player content.
     *
     * @param player The player content.
     */
    public void setPlayer(Player<E> player) {
        this.player = player;
    }

}
