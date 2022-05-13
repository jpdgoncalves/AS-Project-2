package UC3.PSource;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MSensorDataBuffer {
    private final ReentrantLock monitor = new ReentrantLock(true);
    private final Condition waitNotEmpty = monitor.newCondition();
    private final Condition waitNotFull = monitor.newCondition();

    private boolean done = false;

    private final int capacity = 1000;
    private final Queue<SensorData> dataQueue = new ArrayDeque<>(capacity);

    public void putData(SensorData data) throws InterruptedException {
        monitor.lockInterruptibly();

        while (dataQueue.size() >= capacity) {
            waitNotFull.await();
        }

        dataQueue.add(data);
        waitNotEmpty.signal();

        monitor.unlock();
    }

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

    public void markAsDone() {
        monitor.lock();
        done = true;
        waitNotEmpty.signalAll();
        monitor.unlock();
    }
}
