package UC2.PConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import java.util.List;
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
     * The list of partitions from which the consumer is going to read
     */
    private List<TopicPartition> topicPartitions;

    /**
     * Constructor
     * @param properties The properties of the TConsumer we create
     * @param topicPartitions The list of partitions from which the consumer is going to read
     */
    public TConsumer(Properties properties, List <TopicPartition> topicPartitions){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        this.topicPartitions = topicPartitions;
    }

    /**
     * The routine that will be done by each Consumer thread
     */
    @Override
    public void run() {
        consumer.assign(topicPartitions);

        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }
    }
}
