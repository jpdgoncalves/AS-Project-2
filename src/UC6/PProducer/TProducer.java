package UC6.PProducer;

import UC6.PSource.SensorData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

public class TProducer extends Thread{
    private Properties properties;
    private String topicName = "sensor";
    private String key = "key";
    private String value = "";

    private ObjectInputStream in;

    public TProducer(Properties properties, ObjectInputStream newIn){
        this.properties = properties;
        this.in = newIn;
    }

    @Override
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(this.properties);

        System.out.println("producer "+ this.getId() +" sent record to topic !!");

        SensorData sensorData;
        try {
            while(true) {
                sensorData = (SensorData) in.readObject();
                value = "id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp();
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.topicName, this.key, this.value);
                producer.send(producerRecord);
                System.out.println("producer "+ this.getId() + "sent id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("I reached end of file and will respectfully inform the consumer of that fact");
            value = "EOF";
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.topicName, this.key, this.value);
            producer.send(producerRecord);

            throw new RuntimeException(e);
        }finally {
            producer.close();
        }

    }
}