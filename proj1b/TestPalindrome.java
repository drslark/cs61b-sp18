import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        StringBuilder actual = new StringBuilder();
        for (int i = 0; i < "persiflage".length(); i++) {
            actual.append(d.removeFirst());
        }
        assertEquals("persiflage", actual.toString());
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("b"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));

        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("cat"));
    }

    @Test
    public void testIsOffByOnePalindrome() {
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("bBabAa", new OffByOne()));
        assertTrue(palindrome.isPalindrome("cgKJhd", new OffByOne()));
        assertTrue(palindrome.isPalindrome("OotvwspP", new OffByOne()));

        assertFalse(palindrome.isPalindrome("horse", new OffByOne()));
        assertFalse(palindrome.isPalindrome("noon", new OffByOne()));
        assertFalse(palindrome.isPalindrome("racecar", new OffByOne()));
        assertFalse(palindrome.isPalindrome("cat", new OffByOne()));
    }

    @Test
    public void testIsOffByNPalindrome() {
        assertTrue(palindrome.isPalindrome("flake", new OffByN(1)));
        assertTrue(palindrome.isPalindrome("ha", new OffByN(7)));
        assertTrue(palindrome.isPalindrome("AZ", new OffByN(25)));

        assertFalse(palindrome.isPalindrome("horse", new OffByN(1)));
        assertFalse(palindrome.isPalindrome("written", new OffByN(3)));
        assertFalse(palindrome.isPalindrome("blood", new OffByN(2)));
    }

}
