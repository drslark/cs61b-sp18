import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DemoCollections {

    /** Returns a lower case version of string with
     * all characters except letters removed. */
    public static String cleanString(String s) {
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }

    /** Gets a list of all words in the file. */
    public static List<String> getWords(String inputFileName) {
        List<String> words = new ArrayList<>();
        In in = new In(inputFileName);

        while (!in.isEmpty()) {
            String nextWord = cleanString(in.readString());
            words.add(nextWord);
        }

        return words;
    }

    /** Returns the count of the number of the unique words in words. */
    public static int countUniqueWords(List<String> words) {
        Set<String> uniqueWords = new HashSet<>();

        for (String word : words) {
            uniqueWords.add(word);
        }

        return uniqueWords.size();
    }

    /** Returns a map (a.k.a. dictionary) that tracks the count of
     * all target words in words */
    public static Map<String, Integer> collectWordCount(List<String> words, List<String> targets) {
        Map<String, Integer> wordCounts = new HashMap<>();

        /* Make a note that we have seen none of the words. */
        for (String target : targets) {
            wordCounts.put(target, 0);
        }

        for (String word : words) {
            if (wordCounts.containsKey(word)) {
                int count = wordCounts.get(word);
                wordCounts.put(word, count + 1);
            }
        }

        return wordCounts;
    }

    public static void main(String[] args) {
        List<String> words = getWords("libraryOfBabylon.txt");
        System.out.println(countUniqueWords(words));

        List<String> targets = new ArrayList<>();
        targets.add("lottery");
        targets.add("the");
        targets.add("babylon");
        System.out.println(collectWordCount(words, targets));
    }
}
