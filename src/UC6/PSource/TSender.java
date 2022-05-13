package UC6.PSource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TSender extends Thread {

    private final ObjectOutputStream out;
    private final SensorReader sensorReader;

    public TSender(Socket sendSocket, SensorReader sensorReader) throws IOException {
        this.sensorReader = sensorReader;
        this.out = new ObjectOutputStream(sendSocket.getOutputStream());

        setDaemon(true);
    }

    @Override
    public void run() {
        this.log("Started!");

        try {
            String sensorId = sensorReader.getSensorId();
            SensorData data;

            if (sensorId == null) {
                this.log("No more sensorIds left. Stopping!");
                return;
            }


            while ((data = sensorReader.getData(sensorId)) != null) {
                this.log("Sending " + data);
                this.out.writeObject(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }

        this.log("Finished!");
    }

    private void cleanup() {
        try {
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.log("Finished cleanup!");
    }

    private void log(String msg) {
        System.out.println("Sender thread " + this.getId() + ": " + msg);
    }
}
