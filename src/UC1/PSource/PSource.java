package UC1.PSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSource {
    public static final int PSOURCE_PORT = 13000;

    public static void main(String[] args) throws IOException, InterruptedException {

        MSensorDataBuffer dataBuffer = new MSensorDataBuffer();
        ServerSocket serverSocket = new ServerSocket(PSOURCE_PORT);

        System.out.println("Server has started...");

        Socket socket = serverSocket.accept();
        TSender senderThread = new TSender(socket, dataBuffer);
        TReader readerThread = new TReader("sensor.txt", dataBuffer);

        senderThread.start();
        readerThread.start();

        senderThread.join();
        readerThread.join();
    }
}
