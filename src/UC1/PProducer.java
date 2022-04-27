package UC1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class PProducer extends Thread{
    Properties properties = new Properties();
    private String topicName = "test";
    private String key = "key";
    private String value = "My name is Matthieu";

    public PProducer(){
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Override
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, key, value);
        producer.send(producerRecord);
        System.out.println("producer working !!");
        producer.close();
    }
}
