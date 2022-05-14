package UC1.PConsumer;

import UC1.GUI.NewGui;
import UC1.GUI.UpdateGUI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
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

    //private UpdateGUI consumergui;
    private NewGui gui;

    /**
     * Constructor
     * @param properties The properties of the TConsumer we create
     * @param newTopic The name of the topic the consumer will subscribe to
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
        /*try {
            consumergui = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.println("Receive message : " + record.value());
                gui.sendInfo("000001", record.value());
            }
        }
    }
}
