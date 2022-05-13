package UC1.PConsumer;

import java.util.Properties;

/**
 * Class in which consumers are managed
 */
public class PConsumer{

    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        TConsumer consumer = new TConsumer(props, topicName);
        consumer.start();
    }
}
