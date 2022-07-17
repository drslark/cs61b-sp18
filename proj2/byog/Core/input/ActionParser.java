package byog.Core.input;

import java.util.HashSet;
import java.util.Set;

/**
 * Provide the function to parse input.
 */
public class ActionParser {

    private final char startAction;
    private final char endAction;
    private final long seed;

    private static final Set<Character> END_SIGNS = new HashSet<Character>() {
        {
            add('Q');
            add('S');
        }
    };

    /**
     * Constructor to parse the input.
     * @param input The input string to play with.
     */
    public ActionParser(String input) {
        if (input == null || input.length() == 0) {
            throw new RuntimeException("The input should not be empty!");
        }

        startAction = input.charAt(0);
        StringBuilder stringBuilder = new StringBuilder();

        int i = 1;
        for (; i < input.length(); i++) {
            char currChar = input.charAt(i);
            if (END_SIGNS.contains(Character.toUpperCase(currChar))) {
                break;
            }
            stringBuilder.append(currChar);
        }

        try {
            seed = Long.parseLong(stringBuilder.toString());
        } catch (NumberFormatException exception) {
            throw new RuntimeException("Invalid seed in input!");
        }

        try {
            endAction = input.charAt(i);
        } catch (StringIndexOutOfBoundsException exception) {
            throw new RuntimeException("No end action found!");
        }

    }

    /**
     * Gets the seed.
     * @return The seed.
     */
    public Long getSeed() {
        return seed;
    }

}
