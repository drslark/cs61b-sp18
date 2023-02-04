package hw4.puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import edu.princeton.cs.introcs.In;

public class Word implements WorldState {

    private static Set<String> words;
    private static final String WORD_FILE = "input/words10000.txt";
    private static final Map<String, Map<String, Integer>> CACHED_DISTANCE = new HashMap<>();

    private final String word;
    private final String goal;

    /**
     * Reads the word-file specified by the WORD_FILE variable.
     */
    private void readWords() {
        words = new HashSet<String>();

        In in = new In(WORD_FILE);
        while (!in.isEmpty()) {
            words.add(in.readString());
        }
    }

    /**
     * Creates a new Word.
     */
    public Word(String w, String g) {
        /* If words hasn't been read yet, read it. */
        if (words == null) {
            readWords();
        }

        if (!words.contains(w)) {
            throw new IllegalArgumentException("Invalid word: " + w);
        }

        if (!words.contains(g)) {
            throw new IllegalArgumentException("Invalid goal: " + g);
        }

        word = w;
        goal = g;
    }

    /**
     * Computes the edit distance between a and b. From
     * https://rosettacode.org/wiki/Levenshtein_distance.
     */
    private static int editDistance(String a, String b) {
        if (CACHED_DISTANCE.get(a) != null && CACHED_DISTANCE.get(a).get(b) != null) {
            return CACHED_DISTANCE.get(a).get(b);
        }

        if (CACHED_DISTANCE.get(b) != null && CACHED_DISTANCE.get(b).get(a) != null) {
            return CACHED_DISTANCE.get(b).get(a);
        }

        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                         a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }

        CACHED_DISTANCE.putIfAbsent(a, new HashMap<>());
        CACHED_DISTANCE.get(a).put(b, costs[b.length()]);
        return CACHED_DISTANCE.get(a).get(b);
    }


    @Override
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbourWords = new HashSet<>();

        String neighbourWord;
        for (int i = 0; i < word.length(); i++) {
            neighbourWord = word.substring(0, i) + word.substring(i + 1);
            if (words.contains(neighbourWord)) {
                neighbourWords.add(new Word(neighbourWord, goal));
            }

            for (char c = 'a'; c <= 'z'; c++) {
                if (word.charAt(i) == c) {
                    continue;
                }

                neighbourWord = word.substring(0, i) + c + word.substring(i + 1);
                if (words.contains(neighbourWord)) {
                    neighbourWords.add(new Word(neighbourWord, goal));
                }
            }
        }

        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                neighbourWord = word.substring(0, i) + c + word.substring(i);
                if (words.contains(neighbourWord)) {
                    neighbourWords.add(new Word(neighbourWord, goal));
                }
            }
        }

        return neighbourWords;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return editDistance(this.word, goal);
    }

    @Override
    public String toString() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word oWorld = (Word) o;

        if (word != null ? !word.equals(oWorld.word) : oWorld.word != null) {
            return false;
        }
        return goal != null ? goal.equals(oWorld.goal) : oWorld.goal == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        return result;
    }
}
