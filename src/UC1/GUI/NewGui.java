package UC1.GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class NewGui extends JFrame {

    private final HashMap<String, JPanel> textPanes = new HashMap<>();
    private final HashMap<String, JScrollPane> scrollPanes = new HashMap<>();

    public NewGui(int sensorCount) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());
        setSize(1000,750);

        for (int i = 1; i <= sensorCount; i++) {
            addSensorPane(i);
        }

    }

    public void sendInfo(String sensorId, String info) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                textPanes.get(sensorId).add(new JLabel(info));
                JScrollPane scrollPane = scrollPanes.get(sensorId);
                scrollPane.revalidate();
                scrollPane.repaint();
                JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void addSensorPane(int id) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        String sensorId = "00000" + id;
        JLabel title = new JLabel("Sensor " + id);
        JPanel logPanel = createLogPanel();
        JScrollPane scrollPane = new JScrollPane(logPanel);

        textPanes.put(sensorId, logPanel);
        scrollPanes.put(sensorId, scrollPane);

        panel.add(title);
        panel.add(scrollPane);

        add(panel);
    }

    private JPanel createLogPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

}

