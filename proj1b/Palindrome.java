public class Palindrome {

    /**
     * Converts a string to a deque of characters.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /**
     * Whether a word is palindrome or not.
     */
    public boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    private boolean isPalindrome(Deque<Character> wordQueue) {
        Character first = wordQueue.removeFirst();
        Character last = wordQueue.removeLast();
        if (first == null || last == null) {
            return true;
        }

        if (!first.equals(last)) {
            return false;
        }

        return isPalindrome(wordQueue);
    }

    /**
     * Whether a word is palindrome or not.
     * The characters' equality is defined in a given CharacterComparator.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    private boolean isPalindrome(Deque<Character> wordQueue, CharacterComparator cc) {
        Character first = wordQueue.removeFirst();
        Character last = wordQueue.removeLast();
        if (first == null || last == null) {
            return true;
        }

        if (!cc.equalChars(first, last)) {
            return false;
        }

        return isPalindrome(wordQueue, cc);
    }

}
