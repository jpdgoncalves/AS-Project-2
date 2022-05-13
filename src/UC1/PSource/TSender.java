package UC1.PSource;

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
    private final MSensorDataBuffer sensorReader;

    public TSender(Socket sendSocket, MSensorDataBuffer sensorReader) throws IOException {
        this.sensorReader = sensorReader;
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
            SensorData data;
            while ((data = sensorReader.getData()) != null) {
                this.log("Sending " + data.toString());
                this.out.writeObject(data);
            }
        } catch (InterruptedException ignored){

        } catch (IOException e) {
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

        this.log("Finished cleaning up!");
    }

    /**
     * Utility method to log a message
     * @param msg The message to log
     */
    private void log(String msg) {
        System.out.println("[Sender thread " + this.getId() + "]: " + msg);
    }
}
