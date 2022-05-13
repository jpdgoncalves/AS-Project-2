package UC4.PSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PSource {
    private static final int PSOURCE_PORT = 13000;
    private static final int producerCount = 6;

    public static void main(String[] args) throws IOException, InterruptedException {

        DataBufferMap buffers = new DataBufferMap();
        ServerSocket serverSocket = new ServerSocket(PSOURCE_PORT);
        TReader tReader = new TReader("sensor.txt", buffers);
        TSender[] tSenders = new TSender[producerCount];

        System.out.println("PSource process is running!");
        tReader.start();

        for (int i = 0; i < producerCount; i++) {
            Socket socket = serverSocket.accept();
            System.out.println("Accepted a new socket connection! Starting thread...");
            TSender tSender = new TSender(socket, buffers);
            tSender.start();
            tSenders[i] = tSender;
        }

        for (int i = 0; i < producerCount; i++) {
            tSenders[i].join();
        }

        tReader.join();

        System.out.println("Finished");
    }
}