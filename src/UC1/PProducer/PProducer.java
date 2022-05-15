package UC1.PProducer;

import UC1.GUI.NewGui;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * Class in which producers are managed
 */
public class PProducer{

    /**
     * The port on which we retrieve data from PSource
     */
    public static final int PSOURCE_PORT = 13000;

    /**
     * The address on which we retrieve data from PSource
     */
    public static final String IP_ADDRESS = "127.0.0.1";


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NewGui gui = new NewGui("Producer GUI", 6);
        gui.setVisible(true);

        //Socket attributes
        Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //records can be lost :
        props.put("acks", "0");

        //to make performance better since records can be lost
        props.put("retries", "0");

        //to make sure they are ordered
        props.put("max.in.flight.requests.per.connection", "1");

        //Start the producer thread
        (new TProducer(props, in, gui)).start();

    }


}
