import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArraySet<T> implements Iterable<T> {

    private static final int CAPACITY = 100;

    private final T[] keys;

    private int size; // The next element to be added will be at position size

    @SuppressWarnings("unchecked")
    public ArraySet() {
        keys = (T[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     * Returns true if this set contains a specific key.
     */
    public boolean contains(T key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a key to this set.
     * Throws an IllegalArgumentException if the key is null.
     */
    public void add(T key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be null!");
        }

        if (size == CAPACITY) {
            throw new RuntimeException("The set is full!");
        }

        if (contains(key)) {
            return;
        }

        keys[size] = key;
        size++;
    }

    /**
     * Returns the number of key in this set.
     */
    public int size() {
        return size;
    }

    /*
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        for (Iterator<T> iterator = iterator(); iterator.hasNext();) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
    */

    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();

        for (T item : this) {
            listOfItems.add(item.toString());
        }

        return "{" + String.join(", ", listOfItems) + "}";
    }

    public static <Glerp> ArraySet<Glerp> of(Glerp... items) {
        ArraySet<Glerp> arraySet = new ArraySet<>();

        for (Glerp item : items) {
            arraySet.add(item);
        }

        return arraySet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ArraySet)) {
            return false;
        }

        ArraySet<T> otherSet = (ArraySet<T>)other;
        if (size != otherSet.size) {
            return false;
        }

        for (T key : this) {
            if (!otherSet.contains(key)) {
                return false;
            }
        }

        return true;
    }

    /** Returns an iterator (a.k.a seer) into me. */
    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator<>(this);
    }

    private static class ArraySetIterator<E> implements Iterator<E> {

        private int currIndex;

        private final ArraySet<E> arraySet;

        public ArraySetIterator(ArraySet<E> arraySet) {
            currIndex = 0;
            this.arraySet = arraySet;
        }

        @Override
        public boolean hasNext() {
            return currIndex < arraySet.size;
        }

        @Override
        public E next() {
            E key = arraySet.keys[currIndex];
            currIndex++;
            return key;
        }
    }

    public static void main(String[] args) {
        ArraySet<String> arraySet = new ArraySet<>();
        try {
            arraySet.add(null);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        arraySet.add("horse");
        arraySet.add("fish");
        arraySet.add("house");
        arraySet.add("fish");
        System.out.println(arraySet.contains("horse"));
        System.out.println(arraySet.size());

        // toString
        System.out.println(arraySet);

        // equals
        ArraySet<String> theOtherArraySet = new ArraySet<>();
        theOtherArraySet.add("fish");
        theOtherArraySet.add("horse");
        theOtherArraySet.add("house");

        System.out.println(arraySet.equals(theOtherArraySet));
        System.out.println(arraySet.equals(null));
        System.out.println(arraySet.equals("fish"));

        ArraySet<String> anotherArraySet = ArraySet.of("hi", "i'm", "here");
        System.out.println(anotherArraySet);
    }

}
