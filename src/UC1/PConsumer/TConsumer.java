package UC1.PConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    String topicName;

    public TConsumer(Properties properties){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(properties);

    }

    public void receiveNewTopic(String topicName){
        this.topicName = topicName;
        consumer.subscribe(Arrays.asList(topicName));
    }

    @Override
    public void run() {
        System.out.println("consumer begins with topic " + topicName + " and group id " + properties.getProperty("group.id"));
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }
    }
}
