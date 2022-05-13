package UC3.PSource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Thread responsible for reading data from the buffer
 * and sending that data to the socket to which some producer
 * is connected to.
 */
public class TSender extends Thread {

    private final ObjectOutputStream out;
    private final DataBufferMap buffers;

    public TSender(Socket sendSocket, DataBufferMap dataBuffer) throws IOException {
        this.buffers = dataBuffer;
        this.out = new ObjectOutputStream(sendSocket.getOutputStream());

        setDaemon(true);
    }

    /**
     * Routine run by this thread. Until either there
     * is nothing left to read or the thread gets
     * interrupted.
     */
    @Override
    public void run() {
        this.log("Started!");

        try {
            String sensorId = buffers.getSensorId();
            SensorData data;

            if (sensorId == null) {
                this.log("No more sensorIds left. Stopping!");
                return;
            }


            while ((data = buffers.getData(sensorId)) != null) {
                this.log("Sending " + data);
                this.out.writeObject(data);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }

        this.log("Ended!");
    }

    /**
     * Internal routine to clean up the resources
     * used by this thread (aka close the socket).
     */
    private void cleanup() {
        try {
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.log("Finished cleanup!");
    }

    /**
     * Utility method to log a message
     * @param msg The message to log
     */
    private void log(String msg) {
        System.out.println("[Sender thread " + this.getId() + "]: " + msg);
    }
}
