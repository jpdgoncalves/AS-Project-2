package UC1.PConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private String topicName;

    public TConsumer(Properties properties, String newTopic){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        this.topicName = newTopic;
    }

    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }
    }
}
