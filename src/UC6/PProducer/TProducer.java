package UC6.PProducer;

import UC6.GUI.NewGui;
import UC6.GUI.UpdateGUI;
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

    /**
     * The properties of the TProducer
     */
    private Properties properties;

    /**
     * The topicName to which the TProducer will write
     */
    private String topicName = "sensor";

    private String key = "key";
    private String value = "";

    private ObjectInputStream in;

    // private UpdateGUI producergui;
    private final NewGui gui;

    /**
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

        /*try {
            producergui = new UpdateGUI("P");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

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
                // producergui.sendInfo("id=" + sensorData.getSensorId() + " temp=" + sensorData.getTemperature() + " time=" + sensorData.getTimestamp());
                gui.sendInfo(value);
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
