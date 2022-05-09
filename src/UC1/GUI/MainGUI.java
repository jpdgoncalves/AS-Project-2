package UC1.GUI;

import java.io.IOException;

public class MainGUI {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

       UpdateGUI producergui = new UpdateGUI("P");

       UpdateGUI consumergui = new UpdateGUI("C");

       //test code
       while (true){

           //bit of code needed to send new info
           producergui.sendInfo("NO");
           consumergui.sendInfo("hey");
           Thread.sleep(1000);

       }

    }

}
