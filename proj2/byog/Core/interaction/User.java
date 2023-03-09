package byog.Core.interaction;

import java.io.Serializable;
import java.util.Random;

import byog.Core.interaction.constant.Command;
import byog.Core.interaction.constant.State;

/**
 * The User object stores the game state and controls the game process.
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 314L;

    private State state = State.INITIAL;
    private Random random;

    /**
     * Gets the pseudorandom generator of the game.
     *
     * @return The pseudorandom generator of the game.
     */
    public Random getRandom() {
        if (this.random == null) {
            throw new NullPointerException("Random has not been set!");
        }
        return random;
    }

    /**
     * Sets the pseudorandom generator to the game.
     *
     * @param seed A pseudorandom generator.
     */
    public void setRandom(long seed) {
        if (this.random != null) {
            throw new RuntimeException("Random has been set!");
        }
        this.random = new Random(seed);
    }

    /**
     * Gets the state of the game.
     *
     * @return The state generator of the game.
     */
    public State getState() {
        return state;
    }

    /**
     * Maps the character which user typed to a command.
     *
     * @param key The typed character.
     * @return Command to control the game.
     */
    public Command parseKeyToCommand(char key) {
        if (state == State.INITIAL) {
            return parseInitialKey(key);
        } else if (state == State.WAIT_FOR_SEED) {
            return parseSeedKey(key);
        } else if (state == State.PLAY) {
            return parsePlayKey(key);
        } else if (state == State.WAIT_FOR_COMMAND) {
            return parseWaitKey(key);
        }

        return Command.NULL;
    }

    private Command parseInitialKey(char key) {
        Command command = Command.getInitialCommand(key);
        if (command == Command.NEW_GAME) {
            state = State.WAIT_FOR_SEED;
        }
        if (command == Command.LOAD_GAME) {
            state = State.PLAY;
        }

        return command;
    }

    private Command parseSeedKey(char key) {
        Command command = Command.getSeedCommand(key);
        if (command == Command.CONFIRM_SEED) {
            state = State.PLAY;
        }

        return command;
    }

    private Command parsePlayKey(char key) {
        Command command = Command.getPlayCommand(key);
        if (command == Command.WAIT) {
            state = State.WAIT_FOR_COMMAND;
            return Command.NULL;
        }

        return command;
    }

    private Command parseWaitKey(char key) {
        Command command = Command.getWaitCommand(key);
        state = State.PLAY;

        return command;
    }

}
