package byog.Core.context;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import byog.Core.coordinate.Location;
import byog.Core.interaction.User;
import byog.Core.terrain.view.Player;
import byog.Core.terrain.view.World;

/**
 * A context is used to store the global information.
 */
public class Context<E extends Location<E>> {

    private static Context defaultContext;

    private final int width;
    private final int height;
    private final int hudHeight;

    private final E origin;

    private final LinkedHashSet<E> content;
    private final LinkedHashSet<E> border;

    private final GameInfo<E> _gameInfo;
    private String archiveFile;

    /**
     * Constructor with the background width, background height,
     * background hud height, original location and coordinate shape.
     *
     * @param width The width of the background.
     * @param height The height of the background.
     * @param hudHeight The hud height of the background.
     * @param origin The origin of the location system.
     * @param shape The coordinate shape.
     */
    public Context(int width, int height, int hudHeight, E origin, int[] shape) {
        this.width = width;
        this.height = height;
        this.hudHeight = hudHeight;
        this.origin = origin;
        content = origin.traverseContent(shape);
        border = origin.traverseBorder(shape);
        _gameInfo = new GameInfo<>();
        this.archiveFile = null;

        if (Context.defaultContext == null) {
            Context.defaultContext = this;
        }
    }

    /**
     * Constructor with the background width, background height, background hud height,
     * original location, coordinate shape and archive path.
     *
     * @param width The width of the background.
     * @param height The height of the background.
     * @param hudHeight The hud height of the background.
     * @param origin The origin of the location system.
     * @param shape The coordinate shape.
     * @param archiveFile The archive path.
     */
    public Context(
        int width, int height, int hudHeight, E origin, int[] shape, String archiveFile
    ) {
        this(width, height, hudHeight, origin, shape);
        this.archiveFile = archiveFile;
    }

    /**
     * Gets the width.
     * 
     * @return The width of the background.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     * 
     * @return The height of the background.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the hud height.
     * 
     * @return The hud height of the background.
     */
    public int getHudHeight() {
        return hudHeight;
    }
    
    /**
     * Gets the original location.
     *
     * @return The original location.
     */
    public E getOrigin() {
        return origin;
    }

    /**
     * Gets the original location.
     *
     * @return The original location.
     */
    public int[] getShape() {
        return origin.getShape();
    }

    /**
     * Gets the specific location.
     *
     * @return The coordinate of the location.
     */
    public E getLocation(int... coordinate) {
        return origin.getLocation(coordinate);
    }

    /**
     * Gets the content of the coordinate system.
     *
     * @return The content of the coordinate system.
     */
    public Set<E> getContent() {
        return content;
    }

    /**
     * Gets the border of the coordinate system.
     *
     * @return The border of the coordinate system.
     */
    public Set<E> getBorder() {
        return border;
    }

    /**
     * Gets the pseudorandom generator.
     *
     * @return The pseudorandom generator.
     */
    public Random getRandom() {
        return _gameInfo.getUser().getRandom();
    }

    /**
     * Gets the game info.
     *
     * @return The game info.
     */
    public GameInfo<E> getGameInfo() {
        return _gameInfo;
    }

    /**
     * Sets the game info.
     *
     * @param gameInfo The game info.
     */
    public void overwriteGameInfo(GameInfo<E> gameInfo) {
        _gameInfo.setUser(gameInfo.getUser());
        _gameInfo.setWorld(gameInfo.getWorld());
        _gameInfo.setPlayer(gameInfo.getPlayer());
    }

    /**
     * Gets the user.
     *
     * @return The user.
     */
    public User getUser() {
        return _gameInfo.getUser();
    }

    /**
     * Gets the world.
     *
     * @return The world.
     */
    public World<E> getWorld() {
        return _gameInfo.getWorld();
    }

    /**
     * Gets the player.
     *
     * @return The player.
     */
    public Player<E> getPlayer() {
        return _gameInfo.getPlayer();
    }

    /**
     * Gets the archive file.
     *
     * @return The archive file.
     */
    public String getArchiveFile() {
        if (archiveFile == null) {
            throw new NullPointerException("The archiveFile has not been set!");
        }
        return archiveFile;
    }

    /**
     * Sets the global context.
     *
     * @param context The context to be set.
     */
    public static <T extends Location<T>> void setDefault(Context<T> context) {
        if (context == null) {
            throw new NullPointerException("A context can not be null!");
        }
        defaultContext = context;
    }

    /**
     * Gets the global width.
     *
     * @return The global width.
     */
    public static <T extends Location<T>> int width() {
        return ((Context<T>) defaultContext).getWidth();
    }

    /**
     * Gets the global height.
     *
     * @return The global height.
     */
    public static <T extends Location<T>> int height() {
        return ((Context<T>) defaultContext).getHeight();
    }

    /**
     * Gets the global hub height.
     *
     * @return The global hub height.
     */
    public static <T extends Location<T>> int hudHeight() {
        return ((Context<T>) defaultContext).getHudHeight();
    }

    /**
     * Gets the global original location.
     *
     * @return The global original location.
     */
    public static <T extends Location<T>> T origin() {
        return ((Context<T>) defaultContext).getOrigin();
    }

    /**
     * Gets the global shape.
     *
     * @return The global shape.
     */
    public static <T extends Location<T>> int[] shape() {
        return ((Context<T>) defaultContext).getShape();
    }

    /**
     * Gets the global pseudorandom generator.
     *
     * @return The global pseudorandom generator.
     */
    public static <T extends Location<T>> Random random() {
        return ((Context<T>) defaultContext).getRandom();
    }

    /**
     * Gets the location at the given coordinate in the global coordinate system.
     *
     * @param coordinate The given coordinate.
     * @return The location at the given coordinate.
     */
    public static <T extends Location<T>> T get(int... coordinate) {
        if (defaultContext == null) {
            throw new NullPointerException("The context has not been initialized!");
        }
        return ((Context<T>) defaultContext).getLocation(coordinate);
    }

    /**
     * Gets the content of the global coordinate system.
     *
     * @return The content of the global coordinate system.
     */
    public static <T extends Location<T>> Set<T> content() {
        return ((Context<T>) defaultContext).getContent();
    }

    /**
     * Gets the border of the global coordinate system.
     *
     * @return The border of the global coordinate system.
     */
    public static <T extends Location<T>> Set<T> border() {
        return ((Context<T>) defaultContext).getBorder();
    }

    /**
     * Gets the global game info.
     *
     * @return The global game info.
     */
    public static <T extends Location<T>> GameInfo<T> gameInfo() {
        return ((Context<T>) defaultContext).getGameInfo();
    }

    /**
     * Sets the global game info.
     *
     * @param gameInfo The global game info.
     */
    public static <T extends Location<T>> void setGameInfo(GameInfo<T> gameInfo) {
        ((Context<T>) defaultContext).overwriteGameInfo(gameInfo);
    }

    /**
     * Gets the user.
     *
     * @return The global user.
     */
    public static <T extends Location<T>> User user() {
        return ((Context<T>) defaultContext).getUser();
    }

    /**
     * Gets the global world.
     *
     * @return The global world.
     */
    public static <T extends Location<T>> World<T> world() {
        return ((Context<T>) defaultContext).getWorld();
    }

    /**
     * Gets the global player.
     *
     * @return The global player.
     */
    public static <T extends Location<T>> Player<T> player() {
        return ((Context<T>) defaultContext).getPlayer();
    }

    /**
     * Gets the global archive file.
     *
     * @return The global archive file.
     */
    public static <T extends Location<T>> String archiveFile() {
        return ((Context<T>) defaultContext).getArchiveFile();
    }

}
