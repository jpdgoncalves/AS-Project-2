package UC6.PSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TReader extends Thread {

    private BufferedReader fileReader;
    private final DataBufferMap dataBuffer;

    public TReader(String fileName, DataBufferMap dataBuffer) throws IOException {
        fileReader = new BufferedReader(new FileReader(fileName));
        this.dataBuffer = dataBuffer;

        setDaemon(true);
    }

    @Override
    public void run() {
        this.log("Started!");
        try {
            SensorData data;

            while ((data = readData()) != null) {
                this.log(data.toString());
                dataBuffer.putData(data);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }

        this.log("Stopped!");
    }

    private SensorData readData() {
        String sensorId, temperature, timestamp;

        if (fileReader == null) return null;

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

    private void cleanup() {
        dataBuffer.markAllAsDone();

        try {
            fileReader.close();
            fileReader = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.log("Finished cleaning up!");
    }

    private void log(String msg) {
        System.out.println("[Reader thread " + this.getId() + "]: " + msg);
    }
}
