package UC5.PConsumer;

import UC5.PConsumer.PConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;

import static java.lang.Float.*;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private List<TopicPartition> topicPartitions;

    private boolean stillRunning = true;

    private float min_temp = 0;
    private float max_temp = 0;
    private int groupNumber;


    private String topicName;

    public TConsumer(Properties properties/*, String newTopic*/, List <TopicPartition> topicPartitions, int groupNumber){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        //this.topicName = newTopic;
        this.topicPartitions = topicPartitions;
        this.groupNumber = groupNumber;

    }

    @Override
    public void run() {
        //consumer.subscribe(Arrays.asList(topicName));
        consumer.assign(topicPartitions);



        System.out.println("consumer begins !");
        while(stillRunning){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Receive message : " + record.value());

                //System.out.println("nr 3???? - " + record.value().split(" ")[1].split("=")[1]);


                if (record.value().split(" ").length != 1) {
                    float curr_temp = parseFloat(record.value().split(" ")[1].split("=")[1]);

                    if (curr_temp < min_temp || min_temp == 0) {
                        min_temp = curr_temp;
                    }
                    if (curr_temp > max_temp || min_temp == 0) {
                        max_temp = curr_temp;
                    }
                } else {



                    stillRunning = false;

                }

            }

        }

        System.out.println("Min temp is " + min_temp);
        System.out.println("Max temp is " + max_temp);

        PConsumer.writeResults(groupNumber, min_temp, max_temp);
    }
}
