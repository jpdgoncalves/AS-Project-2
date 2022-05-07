package UC1.GUI;

import javax.swing.*;
import java.io.IOException;

public class ProducerGUI {

    private JPanel contentPane;
    private JTextArea textArea;

    private JFrame frame;

    public ProducerGUI(){

        setContent("PLACEHOLDER");

        frame = new JFrame("Producer GUI");
        frame.setSize(1000,400 );
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

    }

    public void open() throws IOException, ClassNotFoundException {
        frame.setVisible(true);
    }

    public void setContent(String content){

        textArea.setText(textArea.getText() + "\n" + content);
        textArea.updateUI();

    }


}
