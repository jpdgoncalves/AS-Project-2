package UC4.GUI;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UpdateGUI extends Thread{

    private ProducerGUI guiP;
    private ConsumerGUI guiC;
    private final String option;



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
