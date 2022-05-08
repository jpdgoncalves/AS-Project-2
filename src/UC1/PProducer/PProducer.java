package UC1.PProducer;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class PProducer{


    public static final int PSOURCE_PORT = 13000;
    public static final String IP_ADDRESS = "127.0.0.1";


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Socket attributes
        Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //records can be lost :
        props.put("acks", "0");
        //to make performance better since records can be lost
        props.put("retries", "0");

        (new TProducer(props, in)).start();

    }


}
