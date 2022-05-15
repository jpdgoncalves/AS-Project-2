package UC3.PConsumer;

import UC3.GUI.NewGui;
import UC3.GUI.UpdateGUI;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class in which consumers are managed
 */
public class PConsumer{

    // private static UpdateGUI consumergui;
    private static final NewGui gui = new NewGui("Consumer GUI", 6);

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

        /*try {
            consumergui = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        gui.setVisible(true);


        /**
         * Starting 3 consumer threads as requested in the assignment
         */
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, gui);
            consumers[i].start();
        }
    }
}
