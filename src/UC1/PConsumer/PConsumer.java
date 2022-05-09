package UC1.PConsumer;

import UC1.PProducer.TProducer;
import org.apache.kafka.common.TopicPartition;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PConsumer{

    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";
        int partition = 0;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        TopicPartition topicPartition = new TopicPartition(topicName, partition);
        List<TopicPartition> asList = Arrays.asList(topicPartition);

        TConsumer consumer = new TConsumer(props/*, topicName*/, asList);
        consumer.start();
    }


}
