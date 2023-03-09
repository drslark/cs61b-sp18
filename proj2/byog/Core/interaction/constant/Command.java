package byog.Core.interaction.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * The Command object is to represent the user command.
 */
public enum Command {

    NEW_GAME('n'),
    LOAD_GAME('l'),
    QUIT_GAME('q'),
    DIGIT_0('0'),
    DIGIT_1('1'),
    DIGIT_2('2'),
    DIGIT_3('3'),
    DIGIT_4('4'),
    DIGIT_5('5'),
    DIGIT_6('6'),
    DIGIT_7('7'),
    DIGIT_8('8'),
    DIGIT_9('9'),
    CONFIRM_SEED('s'),
    MOVE_UP('w'),
    MOVE_DOWN('s'),
    MOVE_LEFT('a'),
    MOVE_RIGHT('d'),
    WAIT(':'),
    NULL(null);

    /**
     * Commands in the initial state.
     */
    private static final Map<Character, Command> GAME_INITIAL_MAP =
        new HashMap<Character, Command>() {
            {
                put(NEW_GAME.value, NEW_GAME);
                put(LOAD_GAME.value, LOAD_GAME);
                put(QUIT_GAME.value, QUIT_GAME);
            }
        };

    /**
     * Commands in the seed state.
     */
    private static final Map<Character, Command> GAME_SEED_MAP = new HashMap<Character, Command>() {
        {
            put(DIGIT_0.value, DIGIT_0);
            put(DIGIT_1.value, DIGIT_1);
            put(DIGIT_2.value, DIGIT_2);
            put(DIGIT_3.value, DIGIT_3);
            put(DIGIT_4.value, DIGIT_4);
            put(DIGIT_5.value, DIGIT_5);
            put(DIGIT_6.value, DIGIT_6);
            put(DIGIT_7.value, DIGIT_7);
            put(DIGIT_8.value, DIGIT_8);
            put(DIGIT_9.value, DIGIT_9);
            put(CONFIRM_SEED.value, CONFIRM_SEED);
        }
    };

    /**
     * Commands in the play state.
     */
    private static final Map<Character, Command> GAME_PLAY_MAP = new HashMap<Character, Command>() {
        {
            put(MOVE_UP.value, MOVE_UP);
            put(MOVE_DOWN.value, MOVE_DOWN);
            put(MOVE_LEFT.value, MOVE_LEFT);
            put(MOVE_RIGHT.value, MOVE_RIGHT);
            put(WAIT.value, WAIT);
        }
    };

    /**
     * Commands in the wait state.
     */
    private static final Map<Character, Command> GAME_WAIT_MAP = new HashMap<Character, Command>() {
        {
            put(MOVE_UP.value, MOVE_UP);
            put(MOVE_DOWN.value, MOVE_DOWN);
            put(MOVE_LEFT.value, MOVE_LEFT);
            put(MOVE_RIGHT.value, MOVE_RIGHT);
            put(QUIT_GAME.value, QUIT_GAME);
        }
    };

    private final Character value;

    /**
     * Constructor with a character which user types.
     */
    Command(Character value) {
        this.value = value;
    }

    /**
     * Gets the typed character of the command.
     *
     * @return The typed character
     */
    public Character getValue() {
        return value;
    }

    /**
     * Converts a typed character to a Command object in the initial state.
     *
     * @param key A typed character
     * @return Corresponding Command object.
     */
    public static Command getInitialCommand(char key) {
        return GAME_INITIAL_MAP.getOrDefault(Character.toLowerCase(key), NULL);
    }

    /**
     * Converts a typed character to a Command object in the seed state.
     *
     * @param key A typed character
     * @return Corresponding Command object.
     */
    public static Command getSeedCommand(char key) {
        return GAME_SEED_MAP.getOrDefault(Character.toLowerCase(key), NULL);
    }

    /**
     * Converts a typed character to a Command object in the play state.
     *
     * @param key A typed character
     * @return Corresponding Command object.
     */
    public static Command getPlayCommand(char key) {
        return GAME_PLAY_MAP.getOrDefault(Character.toLowerCase(key), NULL);
    }

    /**
     * Converts a typed character to a Command object in the wait state.
     *
     * @param key A typed character
     * @return Corresponding Command object.
     */
    public static Command getWaitCommand(char key) {
        return GAME_WAIT_MAP.getOrDefault(Character.toLowerCase(key), NULL);
    }

}
