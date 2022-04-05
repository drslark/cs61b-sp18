import java.util.Comparator;

/**
 * The class that drives Dog to run.
 */
public class DogLauncher {

    public static void main(String[] args) {
        Dog dog1 = new Dog("Elyse", 3);
        Dog dog2 = new Dog("Sture", 9);
        Dog dog3 = new Dog("Artemesios", 15);
        Dog biggerDog = Dog.getBiggerDog(dog1, dog2);
        biggerDog.bark();

        Comparator<Dog> nc = Dog.getNameComparator();
        if (nc.compare(dog1, dog3) > 0) { // if dog1 comes later than dog3 in alphabet
            dog1.bark();
        } else {
            dog3.bark();
        }
    }

}
