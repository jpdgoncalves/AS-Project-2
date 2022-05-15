package UC2.PConsumer;

import UC2.GUI.NewGui;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class in which consumers are managed
 */
public class PConsumer{

    private static NewGui gui = new NewGui("Consumer GUI", 6);

    /**
     * The main method invoked starting the PConsumer process
     * @param args Arguments passed through the command line.
     */
    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //records can be reprocessed
        props.put("auto.commit.interval.ms", "1000");
        gui.setVisible(true);


        /**
         * Starting 6 consumer threads as requested in the assignment
         */
        TConsumer consumers[] = new TConsumer[6];
        for (int i=0; i<6; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, gui);
            consumers[i].start();
        }
    }
}
