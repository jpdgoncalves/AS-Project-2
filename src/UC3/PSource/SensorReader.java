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

        while ((data = readData(fileReader)) != null)
            putData(data, dataBySensorId);

        fileReader.close();

        sensorIds.addAll(dataBySensorId.keySet());
    }

    public String getSensorId() {
        String sensorId;

        sensorsIdRegion.lock();
        sensorId = sensorIds.poll();
        sensorsIdRegion.unlock();

        return sensorId;
    }

    public SensorData getData(String sensorId) {
        return dataBySensorId.get(sensorId).poll();
    }

    private static void putData(SensorData data, HashMap<String, Queue<SensorData>> dataBySensorId) {
        if (!dataBySensorId.containsKey(data.getSensorId()))
            dataBySensorId.put(data.getSensorId(), new ArrayDeque<>());

        dataBySensorId.get(data.getSensorId()).add(data);
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