package UC1.GUI;

import javax.swing.*;
import java.io.IOException;

public class ProducerGUI {

    private JPanel contentPane;
    private JTextArea textArea;

    private JFrame frame;

    public ProducerGUI(){

        frame = new JFrame("Producer GUI");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000,800 );

        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        frame.add(scroll);

    }

    public void open() throws IOException, ClassNotFoundException {
        frame.setVisible(true);
    }

    public void setContent(String content){

        textArea.setText(textArea.getText() + "\n" + content);
        textArea.updateUI();

    }


}
