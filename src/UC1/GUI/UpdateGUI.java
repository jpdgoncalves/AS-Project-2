package UC1.GUI;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Class to update the GUI
 */
public class UpdateGUI extends Thread{

    /**
     * The producer GUI
     */
    private ProducerGUI guiP;

    /**
     * The consumer GUI
     */
    private ConsumerGUI guiC;
    private final String option;


    /**
     * Constructor
     * @param guiOption P if it is a Producer and C  if it is a Consumer
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public UpdateGUI(String guiOption) throws IOException, ClassNotFoundException {
        option = guiOption;

        switch (option){
            case "P":
                this.guiP = new ProducerGUI();
                guiP.open();
                break;
            case "C":
                this.guiC = new ConsumerGUI();
                guiC.open();
                break;

        }

    }


    /**
     * Send the new data to the GUI in function of the entity calling
     * @param data data to be sent to the GUI
     */
    public void sendInfo(String data){
        final Runnable writeToGUI = new Runnable() {
            public void run() {
                switch (option){
                    case "P":
                        guiP.setContent(data);
                        break;
                    case "C":
                        guiC.setContent(data);
                        break;
                }
            }
        };

        try {
            SwingUtilities.invokeAndWait(writeToGUI);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
