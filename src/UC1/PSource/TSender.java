package UC1.PSource;

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
        try {
            SensorData data;
            while ((data = sensorReader.readData()) != null) {
                this.out.writeObject(data);
            }
        } catch (InterruptedException ignored){

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        try {
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sensorReader.close();
    }
}
