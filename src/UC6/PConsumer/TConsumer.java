package UC6.PConsumer;

import UC6.GUI.NewGui;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;

import static java.lang.Float.parseFloat;
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

    private float sum_temps = 0;
    private int count_temps = 0;
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
                gui.sendInfo(record.value());

                if (record.value().split(" ").length != 1) {
                    float curr_temp = parseFloat(record.value().split(" ")[1].split("=")[1]);

                    sum_temps += curr_temp;
                    count_temps ++;

                } else {
                    stillRunning = false;

                    sum_temps = sum_temps / count_temps;
                    System.out.println("The average temperature is " + sum_temps);
                    gui.sendInfo("id=000006 The average temperature is " + sum_temps);

                }
            }
            consumer.commitAsync();
        }
        consumer.commitSync();
    }
}
