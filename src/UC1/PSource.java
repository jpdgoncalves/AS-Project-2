package UC1;

import java.io.*;

public class PSource {

    public PSource(){
//        this.file = new FileWriter("src/HC/Logger/logger.txt");
    }
    public void readFromFile() throws IOException {
        File file = new File("src/sensor.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            System.out.println(st);

    }
}
