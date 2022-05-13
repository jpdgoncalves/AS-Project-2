package UC6.PConsumer;

import UC6.GUI.UpdateGUI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static java.lang.Float.parseFloat;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private List<TopicPartition> topicPartitions;

    private boolean stillRunning = true;

    private float sum_temps = 0;
    private int count_temps = 0;
    private int aux = 0;

    private UpdateGUI consumergui;

    private int groupNumber;


    private Set<String> data = new LinkedHashSet<String>();



    private String topicName;

    public TConsumer(Properties properties/*, String newTopic*/, List <TopicPartition> topicPartitions, int groupNumber, UpdateGUI consumergui){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        this.topicPartitions = topicPartitions;
        this.groupNumber = groupNumber;
        this.consumergui = consumergui;


    }

    @Override
    public void run() {

        consumer.assign(topicPartitions);

        System.out.println("consumer begins !");
        while(stillRunning){

            ConsumerRecords<String,String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Receive message : " + record.value());
                consumergui.sendInfo(record.value());



                if (record.value().split(" ").length != 1) {
                    float curr_temp = parseFloat(record.value().split(" ")[1].split("=")[1]);

                    sum_temps += curr_temp;
                    count_temps ++;

                    if (data.contains(record.value().split(" ")[2].split("=")[1]) == false){
                        data.add(record.value().split(" ")[2].split("=")[1]);
                    } else {

                        System.out.println("I AM RECEIVING DUPLICATES PLZ FIX");
                        aux ++;
                    }

                } else {

                    stillRunning = false;

                    sum_temps = sum_temps / count_temps;
                    System.out.println("The average temperature is " + sum_temps);
                    consumergui.sendInfo("The average temperature is " + sum_temps);

                    System.out.println("Aux - " + aux);
                    System.out.println("nr - " + count_temps);

                }

            }

            consumer.commitAsync();

        }
        consumer.commitSync();

    }
}
