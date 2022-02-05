public class SpeedTest {

    /**
     * Tests the performance of a list.
     *
     * @param list    The tested list.
     * @param testNum The test times.
     */
    private static void printTimeCost(List<Integer> list, int testNum) {
        long timeStart, timeEnd;

        timeStart = System.currentTimeMillis();
        for (int i = 0; i < testNum; i++) {
            list.addLast(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("%s add %d items costs %3.4fs%n", list.getClass().getName(), testNum, (timeEnd - timeStart) / 1000.0);
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Integer>[] lists = (List<Integer>[]) new List<?>[]{new SLList<>(), new DLList<>(), new AList<>()};
        int[] testNums = {1000, 10000, 100000};
        for (List<Integer> list : lists) {
            for (int testNum : testNums) {
                printTimeCost(list, testNum);
            }
        }
    }
}
