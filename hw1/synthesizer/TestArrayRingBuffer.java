package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        testFullAndEmpty();
        testEnqueueAndDequeue();
        testIterator();
        testRuntimeException();
    }

    private void testFullAndEmpty() {
        BoundedQueue<Double> bq = new ArrayRingBuffer<>(4);

        assertTrue(bq.isEmpty());
        assertFalse(bq.isFull());
        assertEquals(4, bq.capacity());
        assertEquals(0, bq.fillCount());

        bq.enqueue(9.3);
        bq.enqueue(15.1);

        assertEquals(4, bq.capacity());
        assertEquals(2, bq.fillCount());

        bq.enqueue(31.2);
        bq.enqueue(-3.1);

        assertTrue(bq.isFull());
        assertFalse(bq.isEmpty());
        assertEquals(4, bq.capacity());
        assertEquals(4, bq.fillCount());

        bq.dequeue();
        bq.dequeue();

        assertFalse(bq.isFull());
        assertFalse(bq.isEmpty());
        assertEquals(4, bq.capacity());
        assertEquals(2, bq.fillCount());

    }

    private void testEnqueueAndDequeue() {
        BoundedQueue<Double> bq = new ArrayRingBuffer<>(4);

        bq.enqueue(33.1);
        bq.enqueue(44.8);
        bq.enqueue(62.3);
        bq.enqueue(-3.4);

        assertEquals((Double) 33.1, bq.dequeue());
        assertEquals((Double) 44.8, bq.dequeue());
        assertEquals((Double) 62.3, bq.dequeue());
        assertEquals((Double) (-3.4), bq.dequeue());
        
        bq.enqueue(33.1);
        bq.enqueue(44.8);

        assertEquals((Double) 33.1, bq.dequeue());
        assertEquals((Double) 44.8, bq.dequeue());

        bq.enqueue(62.3);
        bq.enqueue(-3.4);

        assertEquals((Double) 62.3, bq.dequeue());
        assertEquals((Double) (-3.4), bq.dequeue());

    }

    private void testIterator() {
        BoundedQueue<Double> bq = new ArrayRingBuffer<>(4);
        Double[] items = {33.1, 44.8, 62.3, -3.1};

        for (Double item : items) {
            bq.enqueue(item);
        }

        int index = 0;
        for (Double item : bq) {
            assertEquals(items[index], item);
            index++;
        }
    }

    private void testRuntimeException() {
        BoundedQueue<Double> bq = new ArrayRingBuffer<>(4);

        try {
            bq.dequeue();
        } catch (RuntimeException ex) {
            assertEquals("Ring Buffer Underflow", ex.getMessage());
        }

        bq.enqueue(33.1);
        bq.enqueue(44.8);
        bq.enqueue(62.3);
        bq.enqueue(-3.4);

        try {
            bq.enqueue(9.9);
        } catch (RuntimeException ex) {
            assertEquals("Ring Buffer Overflow", ex.getMessage());
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
