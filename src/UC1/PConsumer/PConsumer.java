package UC1.PConsumer;

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

public class PConsumer{
    //Properties properties = new Properties();
    private String topicName = "test";
    private String groupName = "firstGroup";

    private Properties [] propertiesList;
    private int nr_consumers;

    public PConsumer(Properties [] propertiesList, int nr_consumers){
        /*properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", groupName);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        */
        this.propertiesList = propertiesList;
        this.nr_consumers = nr_consumers;

        createConsumers();

    }

    public void createConsumers() {
        TConsumer [][] listConsumers = new TConsumer[propertiesList.length][nr_consumers];

        //create nr_consumers amount of consumers for each group
        for (int i = 0; i < propertiesList.length; i++){

            for (int j = 0; j < nr_consumers; j++){

                TConsumer consumer = new TConsumer(propertiesList[i]);
                consumer.receiveNewTopic(topicName);
                consumer.start();
                listConsumers[i][j] = consumer;

            }

        }

        /*KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.println("Receive message : " + record.value());
        }*/
    }
}
