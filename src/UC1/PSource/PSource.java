package UC1.PSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSource {
    public static final int PSOURCE_PORT = 13000;

    public static void main(String[] args) throws IOException, InterruptedException {

        SensorReader sensorReader = new SensorReader("sensor.txt");
        ServerSocket serverSocket = new ServerSocket(PSOURCE_PORT);

        Socket socket = serverSocket.accept();
        TSender senderThread = new TSender(socket, sensorReader);

        senderThread.start();
        senderThread.join();
    }
}
