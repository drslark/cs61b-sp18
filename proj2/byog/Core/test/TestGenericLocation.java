package byog.Core.test;

import java.util.HashSet;
import java.util.Set;

import byog.Core.coordinate.GenericLocation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test the function of the GenericLocation.
 */
public class TestGenericLocation {

    private static final int[] SHAPE = {5, 5, 5};

    /**
     * Test the function to get neighbours.
     */
    @Test
    public void testNeighbours() {
        GenericLocation.initialize(SHAPE);

        GenericLocation location = GenericLocation.locateAt(1, 1, 1);
        Set<GenericLocation> neighbours = new HashSet<>(location.getNeighbours());

        // there are 26 neighbours
        assertEquals(26, neighbours.size());

        // check all neighbours
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 0, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 0, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 0, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 1, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 1, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 1, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 2, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 2, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(0, 2, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 0, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 0, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 0, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 1, 0)));
        assertFalse(neighbours.contains(GenericLocation.locateAt(1, 1, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 1, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 2, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 2, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(1, 2, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 0, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 0, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 0, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 1, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 1, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 1, 2)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 2, 0)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 2, 1)));
        assertTrue(neighbours.contains(GenericLocation.locateAt(2, 2, 2)));

    }

    /**
     * Test the function to get close neighbours.
     */
    @Test
    public void testCloseNeighbours() {
        GenericLocation.initialize(SHAPE);

        GenericLocation location = GenericLocation.locateAt(1, 2, 3);
        Set<GenericLocation> closeNeighbours = new HashSet<>(location.getCloseNeighbours());

        // there are 6 close neighbours
        assertEquals(6, closeNeighbours.size());

        // check all close neighbours
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(0, 2, 3)));
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(2, 2, 3)));
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(1, 1, 3)));
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(1, 3, 3)));
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(1, 2, 2)));
        assertTrue(closeNeighbours.contains(GenericLocation.locateAt(1, 2, 4)));
    }

    /**
     * Test the function to get content.
     */
    @Test
    public void testContent() {
        GenericLocation.initialize(SHAPE);

        GenericLocation origin = GenericLocation.origin();
        Set<GenericLocation> content = origin.traverseContent(new int[]{3, 3, 3});

        // there are 27 locations in content
        assertEquals(27, content.size());

        // check all in content
        assertTrue(content.contains(GenericLocation.locateAt(0, 0, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 0, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 0, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 1, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 1, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 1, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 2, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 2, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(0, 2, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 0, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 0, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 0, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 1, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 1, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 1, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 2, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 2, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(1, 2, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 0, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 0, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 0, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 1, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 1, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 1, 2)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 2, 0)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 2, 1)));
        assertTrue(content.contains(GenericLocation.locateAt(2, 2, 2)));

    }

    /**
     * Test the function to get border.
     */
    @Test
    public void testBorder() {
        GenericLocation.initialize(SHAPE);

        GenericLocation origin = GenericLocation.origin();
        Set<GenericLocation> border = origin.traverseBorder(new int[]{3, 3, 3});

        // there are 26 locations in border
        assertEquals(26, border.size());

        // check all in border
        assertTrue(border.contains(GenericLocation.locateAt(0, 0, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 0, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 0, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 1, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 1, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 1, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 2, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 2, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(0, 2, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 0, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 0, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 0, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 1, 0)));
        assertFalse(border.contains(GenericLocation.locateAt(1, 1, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 1, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 2, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 2, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(1, 2, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 0, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 0, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 0, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 1, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 1, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 1, 2)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 2, 0)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 2, 1)));
        assertTrue(border.contains(GenericLocation.locateAt(2, 2, 2)));

    }

}
