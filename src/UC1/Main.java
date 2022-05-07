package UC1;

import UC1.PProducer.PProducer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        PProducer producer = new PProducer();
//        producer.start();
        PConsumer consumer = new PConsumer();
        consumer.start();
//        PSource source = new PSource();

    }
}
