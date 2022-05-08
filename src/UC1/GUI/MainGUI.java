package UC1.GUI;

import java.io.IOException;

public class MainGUI {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
       TUpdateGUIinfo producergui = new TUpdateGUIinfo("P");
        producergui.start();

       TUpdateGUIinfo consumergui = new TUpdateGUIinfo("C");
        consumergui.start();


       //test code
       while (true){

           //bit of code needed to send new info
           producergui.sendInfo("NO");
           consumergui.sendInfo("hey");
           Thread.sleep(1000);

       }

    }

}
