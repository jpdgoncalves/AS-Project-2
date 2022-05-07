package UC1.PProducer;

import UC1.PSource.SensorData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectInputStream;

public class TReceiver extends Thread{
    private PProducer producer;

    private ObjectInputStream in;

    public TReceiver(PProducer newPProducer){
        producer = newPProducer;
    }

    public TReceiver(ObjectInputStream newIn){
        this.in = newIn;
    }

    @Override
    public void run() {
//        Producer<String, String> producer = new KafkaProducer<>(this.producer.getProperties());
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.producer.getTopicName(), this.producer.getKey(), this.producer.getValue());
//        producer.send(producerRecord);
//        System.out.println("producer sent record to topic !!");
//        producer.close();

        SensorData sensorData;
        while(true) {
            try {
                if((sensorData = (SensorData) in.readObject()) != null)
                    System.out.println("id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp());
                else
                    break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
