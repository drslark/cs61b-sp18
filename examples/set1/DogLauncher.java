/**
 * The class that drives Dog to run.
 */
public class DogLauncher {

    public static void main(String[] args) {
        Dog dog1 = new Dog(3);
        Dog dog2 = new Dog(5);
        Dog biggerDog = Dog.getBiggerDog(dog1, dog2);
        biggerDog.bark();
    }

}
