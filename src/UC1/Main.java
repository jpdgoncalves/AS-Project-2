package UC1;

import UC1.PConsumer.PConsumer;
import UC1.PProducer.PProducer;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        PProducer producer = new PProducer();
        //producer.main();

        //test code - must be done to all of the groups that need to be implemented
        Properties properties = new Properties();
        Properties [] test = {properties};

        String groupName = "firstGroup";

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", groupName);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");



        PConsumer consumer = new PConsumer(test,3);

//        PSource source = new PSource();

    }
}
