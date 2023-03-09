package byog.Core.utils;

import java.util.Random;

/**
 * Utils to help process random.
 */
public class RandomUtils {

    /**
     * Generates a random positive long value.
     *
     * @param random A pseudorandom generator.
     * @return A random positive long value.
     */
    public static long nextPositiveLong(Random random) {
        long randomValue = random.nextLong();
        return randomValue < 0 ? randomValue - Long.MIN_VALUE : randomValue;
    }

}
