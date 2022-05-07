package UC1.PProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TReceiver extends Thread{
    private PProducer producer;

    public TReceiver(PProducer newPProducer){
        producer = newPProducer;
    }

    @Override
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(this.producer.getProperties());
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.producer.getTopicName(), this.producer.getKey(), this.producer.getValue());
        producer.send(producerRecord);
        System.out.println("producer sent record to topic !!");
        producer.close();
    }
}
