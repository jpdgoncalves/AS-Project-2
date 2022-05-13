package UC6.PConsumer;

import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PConsumer{

    private static float [] results_min = {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
    private static float [] results_max = {-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};

    private static int added = 0;

    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //in case a kafka consumer crashes (=consumer idle more than 10 seconds), connection is closed
        props.put("connections.max.idle.ms", "10000");
        //records can be reprocessed :
        props.put("auto.commit.interval.ms", "2000");




        //Group 1
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, 0);
            consumers[i].start();
        }


    }




}
