package UC1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class PProducer {
    public PProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ProducerRecord producerRecord = new ProducerRecord("topic", "key", "Working on it");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        kafkaProducer.send(producerRecord);
        kafkaProducer.close();
    }
}
