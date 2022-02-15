import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the sort class.
 */
public class TestSort {

    /**
     * Tests the Sort.sort method.
     */
    @Test
    public void testSort() {
        String[] input = new String[]{"i", "have", "an", "egg"};
        String[] expected = new String[]{"an", "egg", "have", "i"};

        Sort.sort(input);

        assertEquals(expected.length, input.length);
        assertArrayEquals(expected, input);
    }

    /** Test the Sort.findSmallest method.*/
    @Test
    public void testFindSmallest() {
        String[] input = new String[]{"i", "have", "an", "egg"};
        int expected = 2;

        int actual = Sort.findSmallest(input, 0);
        assertEquals(expected, actual);

        input = new String[]{"there", "are", "many", "pigs"};
        expected = 1;

        actual = Sort.findSmallest(input, 0);
        assertEquals(expected, actual);
    }

    /**
     * Tests the Sort.swap method.
     */
    @Test
    public void testSwap() {
        String[] input = new String[]{"i", "have", "an", "egg"};
        String[] expected = new String[]{"an", "have", "i", "egg"};

        int i = 0;
        int j = 2;
        Sort.swap(input, i, j);

        assertEquals(expected.length, input.length);
        assertArrayEquals(expected, input);
    }

}
