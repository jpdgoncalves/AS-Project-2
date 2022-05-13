package UC2.PProducer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

public class PProducer{


    public static final int PSOURCE_PORT = 13000;
    public static final String IP_ADDRESS = "127.0.0.1";


    public static void main(String[] args) throws IOException, ClassNotFoundException {



        TProducer producers[] = new TProducer[6];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //some records can be lost, but minimize the possibility of losing all records of a sensor ID
        props.put("acks", "1");

        //to minimize the possibility of losing all records of a sensor ID without deteriorating the performance and minimize the latency
        props.put("retries", "1");

        //starting 6 producers
        for (int i=0; i<6; i++){
            //Socket attributes
            Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            producers[i] = (new TProducer(props, in));
            producers[i].start();
        }

    }


}
