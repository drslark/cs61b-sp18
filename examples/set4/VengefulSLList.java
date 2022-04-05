/* SLList with additional operation printLostItems() which prints all items that have ever been deleted. */
public class VengefulSLList<GloopGlop> extends SLList<GloopGlop> {

    SLList<GloopGlop> deletedItems;

    public VengefulSLList() {
        super();
        deletedItems = new SLList<>();
    }

    public VengefulSLList(GloopGlop item) {
        super(item);
        deletedItems = new SLList<>();
    }

    /* Prints deleted items */
    public void printLostItems() {
        deletedItems.print();
    }

    @Override
    public GloopGlop removeLast() {
        GloopGlop item = super.removeLast();
        deletedItems.addFirst(item);
        return item;
    }

    public static void main(String[] args) {

        VengefulSLList<Integer> vs = new VengefulSLList<>();

        vs.addLast(1);
        vs.addLast(5);
        vs.addLast(10);
        vs.addLast(13);

        vs.removeLast();
        vs.removeLast();

        // Should print out the numbers of the fallen, namely 10 and 13.
        System.out.println("tye fallen are: ");
        vs.printLostItems();
    }
}
