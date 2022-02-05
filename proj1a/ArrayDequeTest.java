import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    /* Utility class for generating random item. */
    public static class RandomItemGenerator<T> {

        private final T[] candidates;

        public RandomItemGenerator(T[] items) {
            candidates = items;
        }

        public T generate() {
            return candidates[(int) (Math.random() * candidates.length)];
        }
    }

    /* Utility method for getting private constructor. */
    @SuppressWarnings("rawtypes")
    private static Constructor<ArrayDeque> getArrayDequeDeclaredConstructor() {
        Class<ArrayDeque> clazz = ArrayDeque.class;
        try {
            Constructor<ArrayDeque> declaredConstructor = clazz.getDeclaredConstructor(clazz);
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    /* Utility method for checking resize ability. */
    @SuppressWarnings("unchecked")
    public static <T> ArrayDeque<T> getArrayDequeFromOther(ArrayDeque<T> other) {
        try {
            return (ArrayDeque<T>)
                    getArrayDequeDeclaredConstructor().newInstance(other);
        } catch (
                InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException ex
        ) {
            throw new RuntimeException(ex);
        }
    }

    /* Utility method for adding some random items. */
    public static <T> void addSomeRandomItems(
            ArrayDeque<T> arrayDeque, RandomItemGenerator<T> randomGenerator, int num
    ) {
        for (int i = 0; i < num; i++) {
            if (Math.random() < 0.5) {
                arrayDeque.addFirst(randomGenerator.generate());
            } else {
                arrayDeque.addLast(randomGenerator.generate());
            }
        }
    }

    /* Utility method for removing some random items. */
    public static <T> void removeSomeRandomItems(ArrayDeque<T> arrayDeque, int num) {
        for (int i = 0; i < num; i++) {
            if (Math.random() < 0.5) {
                arrayDeque.removeFirst();
            } else {
                arrayDeque.removeLast();
            }
        }
    }
    
    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed. 
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct, 
      * finally printing the results. 
      *
      * && is the "and" operation. */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");
        System.out.println(
                "Make sure to uncomment the lines below (and delete this print statement)."
        );

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");
        
        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
        
    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        System.out.println(
                "Make sure to uncomment the lines below (and delete this print statement)."
        );

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty 
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty 
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty 
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);

    }

    /** Adds some items, then removes them, after that, adds some items,
     * copy items to another deque
     * check the size and print deque. */
    public static void addResizeTest() {
        System.out.println("Running resize test.");

        System.out.println(
                "Make sure to uncomment the lines below (and delete this print statement)."
        );

        RandomItemGenerator<Integer> generator = new RandomItemGenerator<>(
                new Integer[]{1, 2, 3, 4, 5, 6}
        );
        ArrayDeque<Integer> first = new ArrayDeque<>();

        addSomeRandomItems(first, generator, 16);
        boolean passed = checkSize(first.size(), 16);

        removeSomeRandomItems(first, 16);
        passed = checkSize(first.size(), 0) && passed;

        addSomeRandomItems(first, generator, 3);
        passed = checkSize(first.size(), 3) && passed;

        ArrayDeque<Integer> second = getArrayDequeFromOther(first);
        passed = checkSize(second.size(), 3) && passed;

        first.printDeque();
        second.printDeque();

        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        addResizeTest();
    }
}
