package UC1.GUI;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TUpdateGUIinfo extends Thread{

    private ProducerGUI guiP;
    private ConsumerGUI guiC;
    private boolean isAlive = true;
    private final ReentrantLock rl = new ReentrantLock();
    private final Condition newData;
    private boolean hasDataBeenRead;
    private final String option;


    private String toWrite;

    //TODO
    //Do I like this solution of thread monitor like thingy? No.
    //Does it work? Yes.

    public TUpdateGUIinfo(String guiOption) throws IOException, ClassNotFoundException {

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


        this.newData = rl.newCondition();
        this.hasDataBeenRead = false;
    }

    public void killThread (){
        isAlive = false;
    }

    public void sendInfo(String data){
        rl.lock();

        toWrite = data;
        hasDataBeenRead = false;
        newData.signal();

        rl.unlock();
    }

    @Override
    public void run() {

        rl.lock();

        final Runnable writeToGUI = new Runnable() {
            public void run() {
                switch (option){
                    case "P":
                        guiP.setContent(toWrite);
                        break;
                    case "C":
                        guiC.setContent(toWrite);
                        break;
                }

            }
        };

        while (isAlive){ //boolean that can be set by whoever creates the thread
            try {

                while (hasDataBeenRead == true || toWrite == null) {  // no new data to read
                    newData.await();
                }

                SwingUtilities.invokeAndWait(writeToGUI);
                hasDataBeenRead = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        rl.unlock();

    }

}
