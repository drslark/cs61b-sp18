package synthesizer;

import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {

    /* Invariant: data are all in range [first, last). */

    /* Index for the next dequeue or peek. */
    private int first;

    /* Index for the next enqueue. */
    private int last;

    /* Array for storing the buffer data. */
    private final T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];

        first = 0;
        last = 0;

        this.capacity = capacity;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }

        rb[last] = x;

        fillCount += 1;
        last = last == capacity - 1 ? 0 : last + 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }

        T x = rb[first];
        rb[first] = null;

        fillCount -= 1;
        first = first == capacity - 1 ? 0 : first + 1;

        return x;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        return rb[first];
    }

    /**
     * Supports the ability of iterating.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator<>(this);
    }

    private static class ArrayRingBufferIterator<E> implements Iterator<E> {

        private final ArrayRingBuffer<E> arrayRingBuffer;

        private int index;

        ArrayRingBufferIterator(ArrayRingBuffer<E> arrayRingBuffer) {
            this.arrayRingBuffer = arrayRingBuffer;
            index = arrayRingBuffer.first;
        }

        @Override
        public boolean hasNext() {
            return index != arrayRingBuffer.last;
        }

        public E next() {
            E item = arrayRingBuffer.rb[index];
            index = index == arrayRingBuffer.capacity - 1 ? 0 : index + 1;
            return item;
        }
    }
}
