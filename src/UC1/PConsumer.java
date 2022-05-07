package UC1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class PConsumer extends Thread{
    Properties properties = new Properties();
    private String topicName = "sensor";
    private String groupName = "firstGroup";
    public PConsumer(){
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", groupName);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }
    }
}
