package UC3.PProducer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

public class PProducer{


    public static final int PSOURCE_PORT = 13000;
    public static final String IP_ADDRESS = "127.0.0.1";


    public static void main(String[] args) throws IOException, ClassNotFoundException {



        TProducer producers[] = new TProducer[3];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //minimize the possibility of losing records but while minimizing the impact on the overall performance
        props.put("acks", "1");
        //minimize the possibility of losing records but while minimizing the impact on the overall performance
        props.put("retries", "2");

        //high throughput
        props.put("max.in.flight.requests.per.connection", "10");

        //starting 3 producers
        for (int i=0; i<3; i++){
            //Socket attributes
            Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

//            TProducer producer = (new TProducer(props, in));
//            producer.start();
            producers[i] = (new TProducer(props, in));
            producers[i].start();
        }

    }


}
