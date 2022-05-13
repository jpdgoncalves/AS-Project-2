package UC5.PSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Thread responsible for reading all the
 * sensor data into a buffer.
 */
public class TReader extends Thread {

    private BufferedReader fileReader;
    private final DataBufferMap dataBuffer;

    /**
     * Creates a new reader thread for the specified
     * sensor data file.
     * @param fileName The name of the file to read.
     * @param dataBuffer The buffer into which the data will be feed.
     * @throws IOException When by any reason the data file could not be opened.
     */
    public TReader(String fileName, DataBufferMap dataBuffer) throws IOException {
        fileReader = new BufferedReader(new FileReader(fileName));
        this.dataBuffer = dataBuffer;

        setDaemon(true);
    }

    /**
     * The thread's routine to read the file. It will
     * end when either the thread is interrupted or
     * there is no more data to read.
     */
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

    /**
     * Private method responsible for reading from the file
     * and parsing the data into a {@link SensorData} instance
     * @return The instance of {@link SensorData} read from the file
     * or null if there is nothing left to read or an error occurred.
     */
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

    /**
     * Private routine called to free resources used
     * by this thread.
     */
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

    /**
     * Utility method to log a message
     * @param msg The message to log
     */
    private void log(String msg) {
        System.out.println("[Reader thread " + this.getId() + "]: " + msg);
    }
}
