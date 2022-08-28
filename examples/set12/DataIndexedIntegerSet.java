import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataIndexedIntegerSet {

    private boolean[] present;

    public DataIndexedIntegerSet() {
        this(2000000);
    }

    public DataIndexedIntegerSet(int size) {
        present = new boolean[size];
    }

    public void add(int i) {
        present[i] = true;
    }

    public boolean contains(int i) {
        return present[i];
    }

    public static void main(String[] args) {
        DataIndexedIntegerSet dataIndexedIntegerSet = new DataIndexedIntegerSet();
        dataIndexedIntegerSet.add(999);
        dataIndexedIntegerSet.add(1024);
        assertFalse(dataIndexedIntegerSet.contains(1));
        assertTrue(dataIndexedIntegerSet.contains(1024));
    }

}
