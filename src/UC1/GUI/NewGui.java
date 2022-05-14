package UC1.GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewGui extends JFrame {

    private final HashMap<String, ArrayList<String>> recordsMap = new HashMap<>();
    private final HashMap<String, ArrayList<String>> bufferMap = new HashMap<>();
    private final int updateDelay = 1000;

    // GUI Variables
    private final JLabel totalRecords = new JLabel("Total Records: 0");
    private final HashMap<String, JPanel> textPanes = new HashMap<>();
    private final HashMap<String, JScrollPane> scrollPanes = new HashMap<>();

    public NewGui(int sensorCount) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());
        setSize(1000,750);

        for (int i = 1; i <= sensorCount; i++) {
            addSensorPane(i);
            recordsMap.put("00000" + i, new ArrayList<>());
            bufferMap.put("00000" + i, new ArrayList<>());
        }

    }

    public void sendInfo(String sensorId, String info) {
        try {
            SwingUtilities.invokeAndWait(() -> updateRoutine(sensorId, info));
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void updateRoutine(String sensorId, String info) {
        ArrayList<String> buffer = bufferMap.get(sensorId);
        ArrayList<String> records = recordsMap.get(sensorId);
        buffer.add(info);

        if (buffer.size() >= updateDelay) {
            records.addAll(buffer);
            updateGui(sensorId, buffer);
            buffer.clear();
        }
    }

    private void updateGui(String sensorId, ArrayList<String> buffer) {
        JScrollPane scrollPane = scrollPanes.get(sensorId);
        JPanel textPane = textPanes.get(sensorId);

        for (String record: buffer) {
            textPane.add(new JLabel(record));
        }

        scrollPane.revalidate();
        scrollPane.repaint();
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
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

