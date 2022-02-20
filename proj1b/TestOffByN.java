import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualChars() {
        assertTrue(new OffByN(1).equalChars('a', 'b'));
        assertTrue(new OffByN(5).equalChars('a', 'f'));
        assertTrue(new OffByN(5).equalChars('F', 'A'));
        assertTrue(new OffByN(25).equalChars('a', 'z'));
        assertTrue(new OffByN(25).equalChars('Z', 'A'));

        assertFalse(new OffByN(1).equalChars('a', 'e'));
        assertFalse(new OffByN(5).equalChars('f', 'h'));
        assertFalse(new OffByN(5).equalChars('X', 'Z'));
        assertFalse(new OffByN(25).equalChars('a', 'x'));
        assertFalse(new OffByN(25).equalChars('X', 'A'));
    }

}
