package UC5.PConsumer;

import UC5.GUI.UpdateGUI;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.*;

public class PConsumer{

    private static float [] results_min = {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
    private static float [] results_max = {-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};

    private static int added = 0;

    private static UpdateGUI consumergui0;
    private static UpdateGUI consumergui1;
    private static UpdateGUI consumergui2;


    public static void main(String[] args) {
        String topicName = "sensor";

        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props.put("auto.commit.interval.ms", "5000");

        try {
            consumergui0 = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String groupName2 = "sndGroup";

        Properties props2 = new Properties();
        props2.put("bootstrap.servers", "localhost:9092");
        props2.put("group.id", groupName2);
        props2.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props2.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props2.put("auto.commit.interval.ms", "5000");

        try {
            consumergui1 = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String groupName3 = "trdGroup";

        Properties props3 = new Properties();
        props3.put("bootstrap.servers", "localhost:9092");
        props3.put("group.id", groupName3);
        props3.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props3.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props3.put("auto.commit.interval.ms", "2000");

        try {
            consumergui2 = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        //Group 1
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, 0, consumergui0);
            consumers[i].start();
        }

        //Group 2
        TConsumer consumers2[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition2 = new TopicPartition(topicName, i);
            List<TopicPartition> asList2 = Arrays.asList(topicPartition2);

            consumers2[i] = new TConsumer(props2, asList2, 1, consumergui1);
            consumers2[i].start();
        }

        //Group 3
        TConsumer consumers3[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition3 = new TopicPartition(topicName, i);
            List<TopicPartition> asList3 = Arrays.asList(topicPartition3);

            consumers3[i] = new TConsumer(props3, asList3, 2, consumergui2);
            consumers3[i].start();
        }



    }

    public static void writeResults(int groupNumber, float result_min, float result_max){
        //System.out.println("hello");
        //if (results_min[groupNumber] > result_min ){
            results_min[groupNumber] = result_min;
        //}

        //if (results_max[groupNumber] < result_max){
            results_max[groupNumber] = result_max;
        //}

        added ++;
        //System.out.println("Added ++  -" + added);
        if (added == 3){
            System.out.println("Going to vote rn");
            votingSystem();
        }


    }

    private static void votingSystem(){

        float finalResultMin = -1;
        float finalResultMax = -1;


        for (int i = 0; i < results_min.length; i++){

            //System.out.println("results min - "+ results_min[i]);

            int equals = 0;

            for (int j = 0; j < results_min.length; j++){
                if (results_min[i] == results_min[j]) {
                    equals ++;
                }
            }

            if (equals >= 2){
                finalResultMin = results_min[i];

            }

        }

        for (int i = 0; i < results_max.length; i++){

            int equals = 0;

            for (int j = 0; j < results_max.length; j++){
                if (results_max[i] == results_max[j]) {
                    equals ++;
                }
            }

            if (equals >= 2){
                finalResultMax = results_max[i];

            }

        }

        System.out.println("Final results (if -1 -> error)");
        System.out.println("Min - " + finalResultMin);
        System.out.println("Max - " + finalResultMax);




    }


}
