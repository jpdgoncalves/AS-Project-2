package UC2.PConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private List<TopicPartition> topicPartitions;

    private String topicName;

    public TConsumer(Properties properties/*, String newTopic*/, List <TopicPartition> topicPartitions){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        //this.topicName = newTopic;
        this.topicPartitions = topicPartitions;

    }

    @Override
    public void run() {
        //consumer.subscribe(Arrays.asList(topicName));
        consumer.assign(topicPartitions);

        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }
    }
}
