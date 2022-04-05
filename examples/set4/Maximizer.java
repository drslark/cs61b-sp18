public class Maximizer {

    public static Comparable max(Comparable[] items) {
        if (items.length <= 0) {
            return null;
        }

        int maxIdx = 0;
        for (int i = 1; i < items.length; i++) {
            if ((items[i]).compareTo(items[maxIdx]) > 0) {
                maxIdx = i;
            }
        }
        return items[maxIdx];
    }

    public static void main(String[] args) {
        Dog[] dogs = {new Dog("Elyse", 3), new Dog("Sture", 9), new Dog("Artemesios", 15)};
        Dog maxDog = (Dog) max(dogs);
        maxDog.bark();
    }

}
