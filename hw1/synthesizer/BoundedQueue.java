package synthesizer;

import java.util.Iterator;


public interface BoundedQueue<T> extends Iterable<T> {

    /**
     * Returns size of the buffer.
     */
    int capacity();

    /**
     * Returns number of the items of te buffer.
     */
    int fillCount();

    /**
     * Adds item x to the end.
     */
    void enqueue(T x);

    /**
     * Deletes and returns item from the front.
     */
    T dequeue();

    /**
     * Returns (but not deletes) item from the front.
     */
    T peek();

    /**
     * Returns an iterator that is able to iterate through the BoundedQueue.
     */
    @Override
    Iterator<T> iterator();

    /**
     * Is the buffer empty (fillCount equals zero)?
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * Is the buffer full (fillCount is same sa capacity)?
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }

}
