package UC6.PSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSource {
    public static final int PSOURCE_PORT = 13000;

    public static void main(String[] args) throws IOException {

        SensorReader sensorReader = new SensorReader("sensor.txt");
        ServerSocket serverSocket = new ServerSocket(PSOURCE_PORT);

        System.out.println("PSource process is running!");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Accepted a new socket connection! Starting thread...");
            (new TSender(socket, sensorReader)).start();
        }

    }
}