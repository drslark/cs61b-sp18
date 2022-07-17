package byog.Core.test;

import byog.Core.coordinates.Location2D;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.Core.terrain.Room;
import byog.Core.terrain.Rooms;

/**
 * Test the function of Rooms.
 */
public class TestRooms {

    /**
     * Test the function isOverlap.
     */
    @Test
    public void testOverlap() {
        Room thisRoom = new Room(new Location2D(5, 5), 10, 10);
        Room thatRoom = new Room(new Location2D(10, 10), 6, 6);
        assertTrue(Rooms.isOverlap(thisRoom, thatRoom));
    }

    /**
     * Test the function isMargin.
     */
    @Test
    public void testMargin() {
        Room thisRoom = new Room(new Location2D(5, 5), 10, 10);
        Room thatRoom = new Room(new Location2D(10, 15), 6, 6);
        assertTrue(Rooms.isMargin(thisRoom, thatRoom));

        thisRoom = new Room(new Location2D(5, 5), 10, 10);
        thatRoom = new Room(new Location2D(15, 15), 6, 6);
        assertFalse(Rooms.isMargin(thisRoom, thatRoom));

        thisRoom = new Room(new Location2D(5, 5), 10, 1);
        thatRoom = new Room(new Location2D(5, 10), 6, 1);
        assertFalse(Rooms.isMargin(thisRoom, thatRoom));
    }

}
