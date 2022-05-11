package UC3.PSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class SensorReader {
    private final ReentrantLock sensorsIdRegion = new ReentrantLock();

    private final HashMap<String, Queue<SensorData>> dataBySensorId = new HashMap<>();
    private final Queue<String> sensorIds = new ArrayDeque<>(6);

    public SensorReader(String fileName) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        SensorData data;

        createKeys(sensorIds, dataBySensorId);

        while ( (data = readData(fileReader)) != null ) {
            dataBySensorId.get(data.getSensorId()).add(data);
        }

        fileReader.close();

        sensorIds.addAll(dataBySensorId.keySet());
    }

    public String getSensorId() {
        String sensorId;

        sensorsIdRegion.lock();
        sensorId = sensorIds.poll();
        sensorIds.poll();
        sensorsIdRegion.unlock();

        return sensorId;
    }

    public SensorData getData(String sensorId) {
        return dataBySensorId.get(sensorId).poll();
    }

    private static void createKeys(Queue<String> sensorIds , HashMap<String, Queue<SensorData>> dataBySensorId) {
        for (int i = 1; i < 6; i += 2) {
            Queue<SensorData> queue = new ArrayDeque<>();

            sensorIds.add("00000" + i);
            sensorIds.add("00000" + (i + 1));
            dataBySensorId.put("00000" + i, queue);
            dataBySensorId.put("00000" + (i + 1), queue);
        }
    }

    private static SensorData readData(BufferedReader fileReader) {
        String sensorId, temperature, timestamp;

        try {
            if ((sensorId = fileReader.readLine()) == null) return null;
            if ((temperature = fileReader.readLine()) == null) return null;
            if ((timestamp = fileReader.readLine()) == null) return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new SensorData(sensorId, temperature, timestamp);
    }
}