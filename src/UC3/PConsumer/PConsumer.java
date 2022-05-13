package UC3.PConsumer;

import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
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

        //records can be reprocessed but avoiding some degree of reprocessing
        props.put("auto.commit.interval.ms", "5000");


        /**
         * Starting 3 consumer threads as requested in the assignment
         */
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList);
            consumers[i].start();
        }
    }
}
