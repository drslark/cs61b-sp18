import static org.junit.Assert.*;
import org.junit.Test;

/** Tests the StudentArrayDeque by comparing it with ArrayDequeSolution. */
public class TestArrayDequeGold {

    private static final int LOOP_SIZE = 300;

    private static final int BATCH_LIMIT = 30;

    private static final double LEFT = 0.0;

    private static final double RIGHT = 1.01;

    private static final double RANDOM = 0.5;

    /** Adds an item to the front or last randomly. */
    private void randomAdd(
            ArrayDequeSolution<Integer> arrayDequeSolution,
            StudentArrayDeque<Integer> studentArrayDeque,
            Integer item,
            ArrayDequeSolution<String> errMsgs,
            double mode
    ) {
        if (StdRandom.uniform() < mode) {
            arrayDequeSolution.addFirst(item);
            studentArrayDeque.addFirst(item);
            errMsgs.addLast("addFirst(" + item.toString() + ")");
        } else {
            arrayDequeSolution.addLast(item);
            studentArrayDeque.addLast(item);
            errMsgs.addLast("addLast(" + item.toString() + ")");
        }
    }

    /** Removes an item from the front or last randomly. */
    private void randomRemove(
        ArrayDequeSolution<Integer> arrayDequeSolution,
        StudentArrayDeque<Integer> studentArrayDeque,
        ArrayDequeSolution<String> errMsgs,
        double mode
    ) {
        Integer itemSolution;
        Integer itemStudent;
        if (StdRandom.uniform() < mode) {
            itemSolution = arrayDequeSolution.removeFirst();
            itemStudent = studentArrayDeque.removeFirst();
            errMsgs.addLast("removeFirst()");
        } else {
            itemSolution = arrayDequeSolution.removeLast();
            itemStudent = studentArrayDeque.removeLast();
            errMsgs.addLast("removeLast()");
        }
        assertEquals(String.join("\n", errMsgs), itemSolution, itemStudent);
    }

    /** Loops between adding and removing randomly. */
    private void randomLoop(
            ArrayDequeSolution<Integer> arrayDequeSolution,
            StudentArrayDeque<Integer> studentArrayDeque,
            ArrayDequeSolution<String> errMsgs,
            double addMode,
            double removeMode,
            boolean clear
    ) {
        for (int i = 0; i < LOOP_SIZE; i++) {
            int sizeOfAdd = clear ? BATCH_LIMIT : StdRandom.uniform(1, BATCH_LIMIT + 1);
            for (int j = 0; j < sizeOfAdd; j++) {
                randomAdd(arrayDequeSolution, studentArrayDeque, j, errMsgs, addMode);
            }

            int size = arrayDequeSolution.size();
            int sizeOfRemove = clear ? BATCH_LIMIT : StdRandom.uniform(0, size + 1);
            for (int j = 0; j < sizeOfRemove; j++) {
                randomRemove(arrayDequeSolution, studentArrayDeque, errMsgs, removeMode);
            }
        }
    }

    /** Tests by comparing two kinds of ArrayDeques. */
    @Test
    public void testCompareArrayDeques() {
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();

        ArrayDequeSolution<String> errMsgs = new ArrayDequeSolution<>();

        randomLoop(arrayDequeSolution, studentArrayDeque, errMsgs, RANDOM, RANDOM, true);
        randomLoop(arrayDequeSolution, studentArrayDeque, errMsgs, RANDOM, LEFT, false);
        randomLoop(arrayDequeSolution, studentArrayDeque, errMsgs, RIGHT, RANDOM, false);
    }

}
