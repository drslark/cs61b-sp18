import org.junit.Test;

import static org.junit.Assert.*;

public class FlikTest {

    /**
     * Performs a few arbitrary tests to see if the sum method is correct
     */
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(1, 1));
        assertTrue(Flik.isSameNumber(500, 500));
    }
}
