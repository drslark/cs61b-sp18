package byog.Core.test;

import java.util.HashSet;
import java.util.Set;

import byog.Core.coordinates.Location2D;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the function of the Location2D.
 */
public class TestLocation2D {

    /**
     * Test the function to get neighbours.
     */
    @Test
    public void testNeighbours() {
        Location2D location = new Location2D(1, 2);
        Set<Location2D> neighbours = new HashSet<>(location.getNeighbours(1));

        // there are 8 neighbours
        assertEquals(8, neighbours.size());

        // check all neighbours
        assertTrue(neighbours.contains(new Location2D(0, 1)));
        assertTrue(neighbours.contains(new Location2D(0, 3)));
        assertTrue(neighbours.contains(new Location2D(0, 2)));
        assertTrue(neighbours.contains(new Location2D(2, 1)));
        assertTrue(neighbours.contains(new Location2D(2, 3)));
        assertTrue(neighbours.contains(new Location2D(2, 2)));
        assertTrue(neighbours.contains(new Location2D(1, 1)));
        assertTrue(neighbours.contains(new Location2D(1, 3)));
    }

    /**
     * Test the function to get close neighbours.
     */
    @Test
    public void testCloseNeighbours() {
        Location2D location = new Location2D(1, 2);
        Set<Location2D> closeNeighbours = new HashSet<>(location.getCloseNeighbours(1));

        // there are 4 close neighbours
        assertEquals(4, closeNeighbours.size());

        // check all close neighbours
        assertTrue(closeNeighbours.contains(new Location2D(0, 2)));
        assertTrue(closeNeighbours.contains(new Location2D(2, 2)));
        assertTrue(closeNeighbours.contains(new Location2D(1, 1)));
        assertTrue(closeNeighbours.contains(new Location2D(1, 3)));
    }

}
