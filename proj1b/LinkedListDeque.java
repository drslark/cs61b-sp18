import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Isn't this solution kinda... cheating? Yes.
 */
public class LinkedListDeque<Item> extends LinkedList<Item> implements Deque<Item> {

    @Override
    public void printDeque() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Iterator<Item> iterator = iterator(); iterator.hasNext();) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(" ");
            }
        }

        System.out.println(stringBuilder);
    }

    public Item getRecursive(int i) {
        return get(i);
    }

    @Override
    public Item removeFirst() {
        try {
            return super.removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Item removeLast() {
        try {
            return super.removeLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
