import java.util.Comparator;

/**
 * The Dog class.
 */
public class Dog implements OurComparable, Comparable<Dog> {

    public String name;

    public int size;

    /**
     * Constructor for dogs.
     *
     * @param z The size of a dog.
     */
    public Dog(int z) {
        name = "unknown";
        size = z;
    }

    /**
     * Constructor for dogs.
     *
     * @param z The size of a dog.
     */
    public Dog(String n, int z) {
        name = n;
        size = z;
    }

    /**
     * Dog barks!
     */
    public void bark() {
        System.out.printf("%s(%d) bark!\n", name, size);
    }

    /** Returns negative number if this dog is less than the dog pointed by o, and so forth. */
    @Override
    public int compareTo(OurComparable o) {
        if (!(o instanceof Dog)) {
            throw new RuntimeException("A dog can only be compared with another dog!");
        }

        Dog uddaDog = (Dog) o;

        return size - uddaDog.size;
    }

    /** Returns negative number if this dog is less than the dog pointed by uddaDog, and so forth. */
    @Override
    public int compareTo(Dog uddaDog) {
        return size - uddaDog.size;
    }

    private static class NameComparator implements Comparator<Dog> {

        @Override
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }

    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }

    /**
     * Get the bigger dog.
     *
     * @param dog1 The first dog.
     * @param dog2 The second dog.
     * @return The dog with a bigger size.
     */
    public static Dog getBiggerDog(Dog dog1, Dog dog2) {
        if (dog1.size > dog2.size) {
            return dog1;
        }
        return dog2;
    }

}
