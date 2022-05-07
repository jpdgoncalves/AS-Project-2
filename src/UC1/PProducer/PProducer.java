package UC1.PProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

public class PProducer{
    private Properties properties = new Properties();
    private String topicName = "test";
    private String key = "key";
    private String value = "My name is Matthieu";

    public static final int PSOURCE_PORT = 13000;
    public static final String IP_ADDRESS = "127.0.0.1";

    public PProducer(){
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    public static void main(String[] args) throws IOException {
        //Socket attributes
        Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line;
        while(true)
            if(!(line = String.valueOf(in.read())).equals("-1"))
                System.out.println(line);
            else
                break;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
