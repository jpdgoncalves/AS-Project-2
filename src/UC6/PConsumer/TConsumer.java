package UC6.PConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;

import static java.lang.Float.parseFloat;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private List<TopicPartition> topicPartitions;

    private boolean stillRunning = true;

    private float sum_temps = 0;
    private int count_temps = 0;
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

                    sum_temps += curr_temp;
                    count_temps ++;

                } else {



                    stillRunning = false;

                    sum_temps = sum_temps / count_temps;
                    System.out.println("The average temperature is " + sum_temps);

                }

            }

        }

    }
}
