package UC6.PConsumer;

import UC6.GUI.NewGui;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Class in which consumers are managed
 */
public class PConsumer {

    /**
     * Each group put the minimum temperature found at the position corresponding to his number
     */
    private static float [] results_min;

    /**
     * Each group put the maximum temperature found at the position corresponding to his number
     */
    private static float [] results_max;

    /**
     * Number of groups having put their max and min temperature
     */
    private static int added = 0;

    private static final NewGui gui = new NewGui("Consumer GUI", 6);

    public static void main(String[] args) {
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        gui.setVisible(true);

        /**
         * Starting 3 consumers
         */
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, 0, gui);
            consumers[i].start();
        }
    }
}
