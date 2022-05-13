package UC6.PSource;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class serves as a bounded buffer to store the data read by the
 * {@link TReader} thread before it is taken by a {@link TSender}.
 * This buffer works like a FIFO. The first measure to be put is the
 * first to be taken. Threads reading or writing to here will block
 * if the buffer is respectively empty or full.
 * thread
 */
public class MSensorDataBuffer {
    private final ReentrantLock monitor = new ReentrantLock(true);
    private final Condition waitNotEmpty = monitor.newCondition();
    private final Condition waitNotFull = monitor.newCondition();

    private boolean done = false;

    private final int capacity = 1000;
    private final Queue<SensorData> dataQueue = new ArrayDeque<>(capacity);

    /**
     * Method used to put sensor data into the buffer. If the buffer
     * is full the thread will block and wait until there is free space,
     * or it is interrupted by any reason.
     * @param data The data to add into the buffer.
     * @throws InterruptedException If the thread is interrupted.
     */
    public void putData(SensorData data) throws InterruptedException {
        monitor.lockInterruptibly();

        while (dataQueue.size() >= capacity) {
            waitNotFull.await();
        }

        dataQueue.add(data);
        waitNotEmpty.signal();

        monitor.unlock();
    }

    /**
     * Gets the oldest measure to be put into the buffer if there
     * is any or null if there isn't any to read and the {@link TReader}
     * has signaled that it has finished reading all the data.
     *
     * If the buffer is empty the thread will wait until there is something
     * to read or until it is interrupted.
     * @return The oldest measure put into the buffer or null
     * @throws InterruptedException If the thread is interrupted.
     */
    public SensorData getData() throws InterruptedException {
        SensorData data;

        monitor.lockInterruptibly();

        while (dataQueue.isEmpty() && !done) {
            waitNotEmpty.await();
        }

        if (done && dataQueue.isEmpty()) return null;

        data = dataQueue.poll();
        waitNotFull.signal();

        monitor.unlock();

        return data;
    }

    /**
     * Tells the {@link TSender} thread that the
     * {@link TReader} is done reading the data.
     */
    public void markAsDone() {
        monitor.lock();
        done = true;
        waitNotEmpty.signalAll();
        monitor.unlock();
    }
}
