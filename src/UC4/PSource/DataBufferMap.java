package UC4.PSource;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

/**
 * A Data buffer designed to create a single buffer for each sensor id.
 * This allows for a thread to read data for only some particular
 * buffer containing only {@link SensorData} instances with a certain
 * sensor id.
 */
public class DataBufferMap {
    private final HashMap<String, MSensorDataBuffer> buffers = new HashMap<>();
    private final Queue<String> sensorIds = new ArrayDeque<>(6);

    /**
     * Create an instance of this buffer.
     */
    public DataBufferMap() {
        for (int i = 1; i <= 6; i++) {
            String sensorId1 = "00000" + i;
            MSensorDataBuffer buffer = new MSensorDataBuffer();

            buffers.put(sensorId1, buffer);

            sensorIds.add(sensorId1);
        }
    }

    /**
     * Gets an id that can be used to access a particular buffer.
     * @return An id to access some particular buffer.
     */
    public synchronized String getSensorId() {
        return sensorIds.poll();
    }

    /**
     * Puts data into some particular buffer.
     * @param data The data to put into the buffer.
     * @throws InterruptedException If the thread is interrupted.
     */
    public void putData(SensorData data) throws InterruptedException {
        buffers.get(data.getSensorId()).putData(data);
    }

    /**
     * Gets a measure ({@link SensorData}) from a particular buffer
     * or null if there is nothing left to read.
     * @param sensorId The id of the buffer.
     * @return An instance of {@link SensorData} with the id specified by
     * the sensorId parameter.
     * @throws InterruptedException If the thread is interrupted.
     */
    public SensorData getData(String sensorId) throws InterruptedException {
        return buffers.get(sensorId).getData();
    }

    /**
     * Tells of the {@link TSender} threads reading from this file
     * that the {@link TReader} thread is done reading the file.
     */
    public void markAllAsDone() {
        for (MSensorDataBuffer buffer : buffers.values()) {
            buffer.markAsDone();
        }
    }
}
