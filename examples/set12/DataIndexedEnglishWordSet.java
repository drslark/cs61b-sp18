import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataIndexedEnglishWordSet {

    private boolean[] present;

    public DataIndexedEnglishWordSet() {
        this(300000000);
    }

    public DataIndexedEnglishWordSet(int size) {
        present = new boolean[size];
    }

    public void add(String s) {
        present[englishToInt(s)] = true;
    }

    public boolean contains(String s) {
        return present[englishToInt(s)];
    }

    private static int englishToInt(String s) {
        int intRep = 0;
        for (int i = 0; i < s.length(); i++) {
            intRep = intRep * 27;
            intRep = intRep + letterNum(s.charAt(i));
        }
        return intRep;
    }

    private static int letterNum(char l) {
        if (l < 'a' || l > 'z') {
            throw new IllegalArgumentException("Invalid letter!");
        }
        return l - 'a' + 1;
    }

    public static void main(String[] args) {
        assertEquals(1, englishToInt("a"));
        assertEquals(1598, englishToInt("bee"));
        assertEquals(237949071, englishToInt("potato"));
        DataIndexedEnglishWordSet dataIndexedEnglishWordSet = new DataIndexedEnglishWordSet();
        dataIndexedEnglishWordSet.add("cat");
        dataIndexedEnglishWordSet.add("dog");
        dataIndexedEnglishWordSet.add("potato");
        assertFalse(dataIndexedEnglishWordSet.contains("z"));
        assertTrue(dataIndexedEnglishWordSet.contains("dog"));
    }

}
