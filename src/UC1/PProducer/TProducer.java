package UC1.PProducer;

import UC1.GUI.NewGui;
import UC1.PSource.SensorData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

/**
 * Producer thread generated from PProducer
 */
public class TProducer extends Thread{

    /**
     * The properties of the TProducer
     */
    private Properties properties;

    /**
     * The topicName to which the TProducer will write
     */
    private String topicName = "sensor";

    private final NewGui gui;

    private String key = "key";
    private String value = "";

    private ObjectInputStream in;

    /**
     * Constructor
     * @param properties the properties of the TProducer we create
     * @param newIn the ObjectInputStream used for reading the sensor data from PSource
     */
    public TProducer(Properties properties, ObjectInputStream newIn, NewGui gui){
        this.properties = properties;
        this.in = newIn;
        this.gui = gui;
    }

    /**
     * The routine that will be done by each Producer thread
     */
    @Override
    public void run() {
        Producer<String, String> producer = new KafkaProducer<>(this.properties);

        System.out.println("producer sent record to topic !!");

        SensorData sensorData;
        while(true) {
            try {
                if((sensorData = (SensorData) in.readObject()) != null){
                    value = "id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp();
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.topicName, this.key, this.value);
                    producer.send(producerRecord);
                    System.out.println("id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp());
                    gui.sendInfo(value);
                }
                else
                    break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        producer.close();
    }
}
