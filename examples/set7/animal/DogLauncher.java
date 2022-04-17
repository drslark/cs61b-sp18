package animal;

import animal.Dog;

/** Created by someone. */
public class DogLauncher {

    Dog dog = new Dog("Jack", 1);

    animal.Dog theOtherDog = new animal.Dog("Mike", 2);

    public static void main(String[] args) {
        DogLauncher dogLauncher = new DogLauncher();
        dogLauncher.dog.bark();
        System.out.println("age: " + dogLauncher.dog.age);
        dogLauncher.theOtherDog.bark();
        System.out.println("age: " + dogLauncher.theOtherDog.age);
    }

}
