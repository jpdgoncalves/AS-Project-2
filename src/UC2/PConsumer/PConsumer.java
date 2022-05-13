package UC2.PConsumer;

import UC2.GUI.UpdateGUI;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;



public class PConsumer{
    private static UpdateGUI consumergui;
    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);

        //records can be reprocessed
        props.put("auto.commit.interval.ms", "1000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try {
            consumergui = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        TConsumer consumers[] = new TConsumer[6];
        for (int i=0; i<6; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, consumergui);
            consumers[i].start();
        }

    }


}
