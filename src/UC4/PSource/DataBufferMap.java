package UC4.PSource;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class DataBufferMap {
    private final HashMap<String, MSensorDataBuffer> buffers = new HashMap<>();
    private final Queue<String> sensorIds = new ArrayDeque<>(6);

    public DataBufferMap() {
        for (int i = 1; i <= 6; i++) {
            String sensorId1 = "00000" + i;
            MSensorDataBuffer buffer = new MSensorDataBuffer();

            buffers.put(sensorId1, buffer);

            sensorIds.add(sensorId1);
        }
    }

    public synchronized String getSensorId() {
        return sensorIds.poll();
    }

    public void putData(SensorData data) throws InterruptedException {
        buffers.get(data.getSensorId()).putData(data);
    }

    public SensorData getData(String sensorId) throws InterruptedException {
        return buffers.get(sensorId).getData();
    }

    public void markAllAsDone() {
        for (MSensorDataBuffer buffer : buffers.values()) {
            buffer.markAsDone();
        }
    }
}
