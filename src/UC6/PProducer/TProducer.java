package UC6.PProducer;

import UC6.PSource.SensorData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Producer thread generated from PProducer
 */
public class TProducer extends Thread{

    private Properties properties; //the properties of the TProducer

    private String topicName = "sensor"; //the topicName to which the TProducer will write

    private String key = "key";
    private String value = "";

    private ObjectInputStream in; // The ObjectInputStream used for reading the sensor data from PSource

    /**
     * @param properties the properties of the TProducer we create
     * @param newIn the ObjectInputStream used for reading the sensor data from PSource
     */
    public TProducer(Properties properties, ObjectInputStream newIn){
        this.properties = properties;
        this.in = newIn;
    }

    /**
     * The routine that will be done by each Producer thread
     */
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

        }finally {
            producer.close();
        }
    }
}
