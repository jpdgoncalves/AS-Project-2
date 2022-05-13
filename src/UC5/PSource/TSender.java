package UC5.PSource;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TSender extends Thread {

    private final ObjectOutputStream out;
    private final DataBufferMap buffers;

    public TSender(Socket sendSocket, DataBufferMap dataBuffer) throws IOException {
        this.buffers = dataBuffer;
        this.out = new ObjectOutputStream(sendSocket.getOutputStream());

        setDaemon(true);
    }

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

    private void cleanup() {
        try {
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.log("Finished cleanup!");
    }

    private void log(String msg) {
        System.out.println("[Sender thread " + this.getId() + "]: " + msg);
    }
}
