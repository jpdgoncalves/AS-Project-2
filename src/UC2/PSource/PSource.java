package UC2.PSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSource {
    public static final int PSOURCE_PORT = 13000;

    public static void main(String[] args) throws IOException {

        DataBufferMap buffers = new DataBufferMap();
        ServerSocket serverSocket = new ServerSocket(PSOURCE_PORT);

        System.out.println("PSource process is running!");
        TReader tReader = new TReader("sensor.txt", buffers);
        tReader.start();

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Accepted a new socket connection! Starting thread...");
            (new TSender(socket, buffers)).start();
        }

    }
}