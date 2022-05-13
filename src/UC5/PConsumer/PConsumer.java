package UC5.PConsumer;

import org.apache.kafka.common.TopicPartition;

import java.util.*;


/**
 * Class in which consumers are managed
 */
public class PConsumer{

    /**
     * Each group put the minimum temperature found at the position corresponding to his number
     */
    private static float [] results_min;

    /**
     * Each group put the maximum temperature found at the position corresponding to his number
     */
    private static float [] results_max;

    /**
     * Number of groups having put their max and min temperature
     */
    private static int added = 0;

    public static void main(String[] args) {
        /**
         * First group properties
         */
        String topicName = "sensor";
        String groupName = "firstGroup";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupName);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props.put("auto.commit.interval.ms", "5000");


        /**
         * Second group properties
         */
        String topicName2 = "sensor";
        String groupName2 = "sndGroup";

        Properties props2 = new Properties();
        props2.put("bootstrap.servers", "localhost:9092");
        props2.put("group.id", groupName2);
        props2.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props2.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props2.put("auto.commit.interval.ms", "5000");


        /**
         * Third group properties
         */
        String topicName3 = "sensor";
        String groupName3 = "trdGroup";

        Properties props3 = new Properties();
        props3.put("bootstrap.servers", "localhost:9092");
        props3.put("group.id", groupName3);
        props3.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props3.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //records can be reprocessed :
        props3.put("auto.commit.interval.ms", "2000");


        /**
         * Adding consumers in group 1
         */
        TConsumer consumers[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition = new TopicPartition(topicName, i);
            List<TopicPartition> asList = Arrays.asList(topicPartition);

            consumers[i] = new TConsumer(props, asList, 0);
            consumers[i].start();
        }

        /**
         * Adding consumers in group 2
         */
        TConsumer consumers2[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition2 = new TopicPartition(topicName2, i);
            List<TopicPartition> asList2 = Arrays.asList(topicPartition2);

            consumers2[i] = new TConsumer(props2, asList2, 1);
            consumers2[i].start();
        }

        /**
         * Adding consumers in group 3
         */
        TConsumer consumers3[] = new TConsumer[3];
        for (int i=0; i<3; i++){
            TopicPartition topicPartition3 = new TopicPartition(topicName3, i);
            List<TopicPartition> asList3 = Arrays.asList(topicPartition3);

            consumers3[i] = new TConsumer(props3, asList3, 2);
            consumers3[i].start();
        }



    }

    /**
     * To write the maximum and minimum temperature voted on votingSystem()
     * @param groupNumber the group number on whoch the values are going to be added
     * @param result_min the minimum temperature value found
     * @param result_max the maximum temperature value found
     */
    public static void writeResults(int groupNumber, float result_min, float result_max){
        results_min[groupNumber] = result_min;

        results_max[groupNumber] = result_max;

        added ++;
        if (added == 3){
            System.out.println("Going to vote right now");
            votingSystem();
        }
    }

    /**
     * To vote on the maximum and minimum temperature found
     */
    private static void votingSystem(){
        float finalResultMin = -1;
        float finalResultMax = -1;

        for (int i = 0; i < results_min.length; i++){
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
