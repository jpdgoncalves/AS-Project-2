package UC3.PSource;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class DataBufferMap {
    private final HashMap<String, MSensorDataBuffer> buffers = new HashMap<>();
    private final Queue<String> sensorIds = new ArrayDeque<>(6);

    public DataBufferMap() {
        for (int i = 1; i <= 6; i+=2) {
            String sensorId1 = "00000" + i;
            String sensorId2 = "00000" + (i + 1);
            MSensorDataBuffer buffer = new MSensorDataBuffer();

            buffers.put(sensorId1, buffer);
            buffers.put(sensorId2, buffer);

            sensorIds.add(sensorId1);
            sensorIds.add(sensorId2);
        }
    }

    public synchronized String getSensorId() {
        String sensorId = sensorIds.poll();
        sensorIds.poll();
        return sensorId;
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
