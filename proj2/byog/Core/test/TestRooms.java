package byog.Core.test;

import byog.Core.context.Context;
import byog.Core.coordinate.Location2D;
import byog.Core.terrain.concrete.RectangularRoom;
import byog.Core.terrain.view.Room;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the function of Rooms.
 */
public class TestRooms {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int HUD_HEIGHT = 0;

    public static final int[] SHAPE = {WIDTH, HEIGHT};

    /**
     * Test the function isOverlap.
     */
    @Test
    public void testOverlap() {
        Location2D.initialize(SHAPE);

        Context<Location2D> context = new Context<>(
            WIDTH, HEIGHT, HUD_HEIGHT, Location2D.origin(), SHAPE
        );
        Context.setDefault(context);

        Room<Location2D> thisRoom = new RectangularRoom<>(
            Location2D.locateAt(5, 5), new int[]{10, 10}
        );
        Room<Location2D> thatRoom = new RectangularRoom<>(
            Location2D.locateAt(10, 10), new int[]{6, 6}
        );
        assertTrue(thisRoom.isOverlap(thatRoom));
    }

    /**
     * Test the function isMargin.
     */
    @Test
    public void testMargin() {
        Location2D.initialize(SHAPE);

        Context<Location2D> context = new Context<>(
            WIDTH, HEIGHT, HUD_HEIGHT, Location2D.origin(), SHAPE
        );
        Context.setDefault(context);

        Room<Location2D> thisRoom = new RectangularRoom<>(
            Location2D.locateAt(5, 5), new int[]{10, 10}
        );
        Room<Location2D> thatRoom = new RectangularRoom<>(
            Location2D.locateAt(10, 15), new int[]{6, 6}
        );
        assertTrue(thisRoom.isMargin(thatRoom));

        thisRoom = new RectangularRoom<>(Location2D.locateAt(5, 5), new int[]{10, 10});
        thatRoom = new RectangularRoom<>(Location2D.locateAt(15, 15), new int[]{6, 6});
        assertFalse(thisRoom.isMargin(thatRoom));

        thisRoom = new RectangularRoom<>(Location2D.locateAt(5, 5), new int[]{10, 1});
        thatRoom = new RectangularRoom<>(Location2D.locateAt(5, 10), new int[]{6, 1});
        assertFalse(thisRoom.isMargin(thatRoom));
    }

}
