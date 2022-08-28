import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataIndexedStringSet {

    private boolean[] present;

    public DataIndexedStringSet() {
        this(2000000);
    }

    public DataIndexedStringSet(int size) {
        present = new boolean[size];
    }

    public void add(String s) {
        present[asciiToInt(s)] = true;
    }

    public boolean contains(String s) {
        return present[asciiToInt(s)];
    }

    private static int asciiToInt(String s) {
        int intRep = 0;
        for (int i = 0; i < s.length(); i++) {
            intRep = intRep * 126;
            intRep = intRep + s.charAt(i);
        }
        return intRep;
    }

    public static void main(String[] args) {
        DataIndexedStringSet dataIndexedStringSet = new DataIndexedStringSet();
        dataIndexedStringSet.add("cat");
        dataIndexedStringSet.add("dog");
        assertFalse(dataIndexedStringSet.contains("z"));
        assertTrue(dataIndexedStringSet.contains("dog"));
    }

}
