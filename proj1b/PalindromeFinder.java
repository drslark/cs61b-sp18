/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {

    private static void printAllPalindromes() {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
                System.out.println(word);
            }
        }
    }

    private static void printAllPalindromes(CharacterComparator cc) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                System.out.println(word);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("All Palindromes:");
        printAllPalindromes();
        System.out.println();

        System.out.println("All OffOne Palindromes:");
        printAllPalindromes(new OffByOne());
        System.out.println();
    }

}
