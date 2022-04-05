public class SpeedTest {

    /**
     * Tests the performance of a list.
     *
     * @param lastAdder    The tested list.
     * @param testNum The test times.
     */
    private static void printTimeCost(LastAdder<Integer> lastAdder, int testNum) {
        long timeStart, timeEnd;

        timeStart = System.currentTimeMillis();
        for (int i = 0; i < testNum; i++) {
            lastAdder.addLast(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("%s add %d items costs %3.4fs%n", lastAdder.getClass().getName(), testNum, (timeEnd - timeStart) / 1000.0);
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        LastAdder<Integer>[] lastAdders = (LastAdder<Integer>[]) new LastAdder<?>[]{new SLList<>(), new DLList<>(), new AList<>()};
        int[] testNums = {1000, 10000, 100000};
        for (LastAdder<Integer> lastAdder : lastAdders) {
            for (int testNum : testNums) {
                printTimeCost(lastAdder, testNum);
            }
        }
    }
}
