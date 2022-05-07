package UC1.GUI;

import javax.swing.*;
import java.io.IOException;

public class TUpdateGUIinfo extends Thread{

    private ProducerGUI gui;

    public TUpdateGUIinfo() throws IOException, ClassNotFoundException {
        this.gui = new ProducerGUI();
        gui.open();

    }

    @Override
    public void run() {

        final Runnable doHelloWorld = new Runnable() {
            public void run() {
                gui.setContent("hi");
            }
        };

        while (true){
            try {
                SwingUtilities.invokeAndWait(doHelloWorld);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
