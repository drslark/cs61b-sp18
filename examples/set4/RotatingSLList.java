/* SLList, but with additional rotateRight() method. */
public class RotatingSLList<GloopGlop> extends SLList<GloopGlop> {

    /**
     * Rotates every element one spot to the right, moving the last item to the front of the list.
     */
    public void rotateRight() {
        GloopGlop item = removeLast();
        addFirst(item);
    }

    public static void main(String[] args) {
        RotatingSLList<Integer> rsl = new RotatingSLList<>();
        /* Original: [10, 11, 12,13] */
        rsl.addLast(10);
        rsl.addLast(11);
        rsl.addLast(12);
        rsl.addLast(13);

        /* Should be: [13, 10, 11, 12] */
        rsl.rotateRight();
        rsl.print();
    }
}
