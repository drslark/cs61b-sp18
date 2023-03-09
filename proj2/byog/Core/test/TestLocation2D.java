package byog.Core.test;

import java.util.HashSet;
import java.util.Set;

import byog.Core.coordinate.Location2D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test the function of the Location2D.
 */
public class TestLocation2D {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;

    private static final int[] SHAPE = {WIDTH, HEIGHT};

    /**
     * Test the function to get neighbours.
     */
    @Test
    public void testNeighbours() {
        Location2D.initialize(SHAPE);

        Location2D location = Location2D.locateAt(1, 2);
        Set<Location2D> neighbours = new HashSet<>(location.getNeighbours());

        // there are 8 neighbours
        assertEquals(8, neighbours.size());

        // check all neighbours
        assertTrue(neighbours.contains(Location2D.locateAt(0, 1)));
        assertTrue(neighbours.contains(Location2D.locateAt(0, 3)));
        assertTrue(neighbours.contains(Location2D.locateAt(0, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 1)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 3)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(1, 1)));
        assertFalse(neighbours.contains(Location2D.locateAt(1, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(1, 3)));
    }

    /**
     * Test the function to get close neighbours.
     */
    @Test
    public void testCloseNeighbours() {
        Location2D.initialize(SHAPE);

        Location2D location = Location2D.locateAt(1, 2);
        Set<Location2D> closeNeighbours = new HashSet<>(location.getCloseNeighbours());

        // there are 4 close neighbours
        assertEquals(4, closeNeighbours.size());

        // check all close neighbours
        assertTrue(closeNeighbours.contains(Location2D.locateAt(0, 2)));
        assertTrue(closeNeighbours.contains(Location2D.locateAt(2, 2)));
        assertTrue(closeNeighbours.contains(Location2D.locateAt(1, 1)));
        assertTrue(closeNeighbours.contains(Location2D.locateAt(1, 3)));
    }

    /**
     * Test the function to get content.
     */
    @Test
    public void testContent() {
        Location2D.initialize(SHAPE);

        Location2D location = Location2D.locateAt(0, 1);
        Set<Location2D> content = location.traverseContent(new int[]{3, 3});

        // there are 9 locations in content
        assertEquals(9, content.size());

        // check all in content
        assertTrue(content.contains(Location2D.locateAt(0, 1)));
        assertTrue(content.contains(Location2D.locateAt(0, 3)));
        assertTrue(content.contains(Location2D.locateAt(0, 2)));
        assertTrue(content.contains(Location2D.locateAt(2, 1)));
        assertTrue(content.contains(Location2D.locateAt(2, 3)));
        assertTrue(content.contains(Location2D.locateAt(2, 2)));
        assertTrue(content.contains(Location2D.locateAt(1, 1)));
        assertTrue(content.contains(Location2D.locateAt(1, 2)));
        assertTrue(content.contains(Location2D.locateAt(1, 3)));

    }

    /**
     * Test the function to get border.
     */
    @Test
    public void testBorder() {
        Location2D.initialize(SHAPE);

        Location2D location = Location2D.locateAt(0, 1);
        Set<Location2D> neighbours = location.traverseBorder(new int[]{3, 3});

        // there are 8 locations in border
        assertEquals(8, neighbours.size());

        // check all in border
        assertTrue(neighbours.contains(Location2D.locateAt(0, 1)));
        assertTrue(neighbours.contains(Location2D.locateAt(0, 3)));
        assertTrue(neighbours.contains(Location2D.locateAt(0, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 1)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 3)));
        assertTrue(neighbours.contains(Location2D.locateAt(2, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(1, 1)));
        assertFalse(neighbours.contains(Location2D.locateAt(1, 2)));
        assertTrue(neighbours.contains(Location2D.locateAt(1, 3)));

    }


}
