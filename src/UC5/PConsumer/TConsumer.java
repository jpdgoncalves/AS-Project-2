package UC5.PConsumer;

import UC5.GUI.NewGui;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;

import static java.lang.Float.*;

/**
 * Consumer thread generated from PProducer
 */
public class TConsumer extends Thread{

    /**
     * The properties of the TConsumer
     */
    private Properties properties;

    /**
     * The Kafka TConsumer
     */
    private KafkaConsumer<String, String> consumer;

    /**
     * The list of partitions from which the consumer is going to read
     */
    private List<TopicPartition> topicPartitions;

    private boolean stillRunning = true;

    private float min_temp = 0;
    private float max_temp = 0;
    private int groupNumber;

    private final NewGui gui;

    /**
     * Constructor
     * @param properties The properties of the TConsumer we create
     * @param topicPartitions The list of partitions from which the consumer is going to read
     */
    public TConsumer(Properties properties, List <TopicPartition> topicPartitions, int groupNumber, NewGui gui){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        this.topicPartitions = topicPartitions;
        this.groupNumber = groupNumber;
        this.gui = gui;
    }

    /**
     * The routine that will be done by each Consumer thread
     */
    @Override
    public void run() {
        consumer.assign(topicPartitions);

        System.out.println("consumer begins !");
        while(stillRunning){

            ConsumerRecords<String,String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Receive message : " + record.value());
                // consumergui.sendInfo(record.value());
                gui.sendInfo(record.value());

                if (record.value().split(" ").length != 1) {
                    float curr_temp = parseFloat(record.value().split(" ")[1].split("=")[1]);

                    if (curr_temp < min_temp || min_temp == 0) {
                        min_temp = curr_temp;
                    }
                    if (curr_temp > max_temp || min_temp == 0) {
                        max_temp = curr_temp;
                    }
                } else {
                    stillRunning = false;
                }
            }
        }

        System.out.println(groupNumber + " - Min temp is " + min_temp);
        System.out.println("Max temp is " + max_temp);
        gui.sendInfo("id=00000" + (groupNumber + 1) + " The maximum temperature recorded was " + max_temp);
        gui.sendInfo("id=00000" + (groupNumber + 1) + "The minimum temperature recorded was " + min_temp);

        PConsumer.writeResults(groupNumber, min_temp, max_temp);
    }
}
