package UC1.PSource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TSender extends Thread {

    private final ObjectOutputStream out;
    private final MSensorDataBuffer sensorReader;

    public TSender(Socket sendSocket, MSensorDataBuffer sensorReader) throws IOException {
        this.sensorReader = sensorReader;
        this.out = new ObjectOutputStream(sendSocket.getOutputStream());

        setDaemon(true);
    }

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

    private void cleanup() {
        try {
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.log("Finished cleaning up!");
    }

    private void log(String msg) {
        System.out.println("[Sender thread " + this.getId() + "]: " + msg);
    }
}
