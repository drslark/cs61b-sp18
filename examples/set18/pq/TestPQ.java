package pq;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for ExtrinsicUniqueMinPQ.
 */
public class TestPQ {

    /**
     * Test if minPQ property holds.
     */
    @Test
    public void testMinPQ() {
        int loops = 50;
        int items = 100000;

        Random random = new Random();
        Map<Integer, Double> itemToPriority = new HashMap<>();

        ExtrinsicUniqueMinPQ<Integer> minPQ = new ExtrinsicUniqueMinPQ<>();

        for (int i = 0; i < loops; i++) {
            for (int item = 0; item < items; item++) {
                double priority = random.nextDouble();
                minPQ.add(item, priority);
                itemToPriority.put(item, priority);
            }

            double prevPriority = -1.0;
            while (minPQ.size() > 0) {
                int item = minPQ.removeMin();
                double priority = itemToPriority.remove(item);

                assertTrue(prevPriority <= priority);

                prevPriority = priority;
            }
        }
    }

    /**
     * Test if changePriority works.
     */
    @Test
    public void testChangePriority() {
        int loops = 50;
        int items = 100000;
        int changes = 10000;

        Random random = new Random();
        Map<Integer, Double> itemToPriority = new HashMap<>();

        ExtrinsicUniqueMinPQ<Integer> minPQ = new ExtrinsicUniqueMinPQ<>();

        for (int i = 0; i < loops; i++) {
            for (int item = 0; item < items; item++) {
                double priority = random.nextDouble();
                minPQ.add(item, priority);
                itemToPriority.put(item, priority);
            }

            for (int j = 0; j < changes; j++) {
                int item = random.nextInt(items);
                double priority = random.nextDouble();
                itemToPriority.replace(item, priority);
                minPQ.changePriority(item, priority);
            }

            double prevPriority = -1.0;
            while (minPQ.size() > 0) {
                int item = minPQ.removeMin();
                double priority = itemToPriority.remove(item);

                assertTrue(prevPriority <= priority);

                prevPriority = priority;
            }
        }
    }

}
