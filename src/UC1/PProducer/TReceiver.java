package UC1.PProducer;

import UC1.PSource.SensorData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

public class TReceiver extends Thread{
    private PProducer producer;

    private Properties properties = new Properties();
    private String topicName = "sensor";
    private String key = "key";
    private String value = "";

    private ObjectInputStream in;

    public TReceiver(Properties properties, ObjectInputStream newIn){
        this.properties = properties;
        this.in = newIn;
    }

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
