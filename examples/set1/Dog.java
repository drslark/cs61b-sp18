/**
 * The Dog class.
 */
public class Dog {

    public int size;

    /**
     * Constructor for dogs.
     *
     * @param z The size of a dog.
     */
    public Dog(int z) {
        size = z;
    }

    /**
     * Dog barks!
     */
    public void bark() {
        System.out.println("bark!");
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
