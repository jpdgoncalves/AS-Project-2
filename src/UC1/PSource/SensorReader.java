package UC1.PSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class SensorReader {
    private final ReentrantLock monitor = new ReentrantLock();

    private BufferedReader fileReader;

    public SensorReader(String fileName) throws IOException {
        fileReader = new BufferedReader(new FileReader(fileName));
    }

    public SensorData readData() throws InterruptedException{
        String sensorId, temperature, timestamp;

        monitor.lockInterruptibly();

        if (fileReader == null) return null;

        try {
            if ((sensorId = fileReader.readLine()) == null) return null;
            if ((temperature = fileReader.readLine()) == null) return null;
            if ((timestamp = fileReader.readLine()) == null) return null;
        } catch (IOException e) {
            e.printStackTrace();
            cleanup();
            return null;
        }

        monitor.unlock();

        return new SensorData(sensorId, temperature, timestamp);
    }

    public void close() {
        monitor.lock();
        cleanup();
        monitor.unlock();
    }

    private void cleanup() {
        try {
            fileReader.close();
            fileReader = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
