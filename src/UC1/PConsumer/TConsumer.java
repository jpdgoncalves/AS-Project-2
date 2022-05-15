package UC1.PConsumer;

import UC1.GUI.NewGui;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

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
     * The name of the topic the consumer will subscribe to
     */
    private String topicName;

    /**
     * Consumer thread GUI
     */
    private final NewGui gui;

    /**
     * Constructor
     * @param properties The properties of the TConsumer we create
     * @param newTopic The name of the topic the consumer will subscribe to
     * @param gui The gui where the records will be displayed.
     */
    public TConsumer(Properties properties, String newTopic, NewGui gui){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        this.topicName = newTopic;
        this.gui = gui;
    }

    /**
     * The routine that will be done by each Consumer thread
     */
    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.println("Receive message : " + record.value());
                gui.sendInfo(record.value());
            }
        }
    }
}
