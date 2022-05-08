package UC1.GUI;

import javax.swing.*;
import java.io.IOException;

public class TUpdateGUIinfo extends Thread{

    private ProducerGUI gui;

    private String toWrite;

    public TUpdateGUIinfo() throws IOException, ClassNotFoundException {
        this.gui = new ProducerGUI();
        gui.open();

    }

    @Override
    public void run() {

        final Runnable writeToGUI = new Runnable() {
            public void run() {
                gui.setContent(toWrite);
            }
        };

        while (true){
            try {
                //TODO - missing receiving info from producer
                toWrite = "hi";
                SwingUtilities.invokeAndWait(writeToGUI);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
