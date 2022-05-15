package UC6.GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class NewGui extends JFrame {

    private final HashMap<String, ArrayList<String>> recordsMap = new HashMap<>();
    private int recordsCount = 0;

    /**
     * GUI element to place the total records
     */
    private final JLabel totalRecords = new JLabel("Total Records: 0");

    /**
     * Maps the sensor titles to a sensor id.
     */
    private final HashMap<String, JLabel> sensorTitles = new HashMap<>();

    /**
     * Queue containing the gui labels. Each label displays a single
     * record. When one wants to display the next record take out the first
     * element from the queue, remove it from the gui, set the text, add it
     * back to the queue and add it back into the gui.
     */
    private final HashMap<String, Queue<JLabel>> labelsQueue = new HashMap<>();

    /**
     * Contains the panes to display the records mapped by sensor id
     */
    private final HashMap<String, JPanel> textPanes = new HashMap<>();
    /**
     * Used to map the scroll pane to a sensor id so we can update it
     * when a record is added.
     */
    private final HashMap<String, JScrollPane> scrollPanes = new HashMap<>();

    public NewGui(String title, int sensorCount) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, sensorCount));
        setSize(1000,750);
        setTitle(title);

        for (int i = 1; i <= sensorCount; i++) {
            addSensorPane(i);
            recordsMap.put("00000" + i, new ArrayList<>());
        }

        addTotalRecordsPanel(sensorCount);
    }

    public void sendInfo(String info) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                String sensorId = parseSensorId(info);
                updateRoutine(sensorId, info);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void updateRoutine(String sensorId, String info) {
        JLabel title = sensorTitles.get(sensorId);
        JPanel panel = textPanes.get(sensorId);
        JScrollPane scrollPane = scrollPanes.get(sensorId);
        Queue<JLabel> lQueue = labelsQueue.get(sensorId);
        JLabel nextLabel = lQueue.poll();

        recordsMap.get(sensorId).add(info);
        recordsCount++;

        nextLabel.setText(info);
        title.setText(String.format(
                "Sensor %s: (%d)",
                sensorId.replace("0", ""),
                recordsMap.get(sensorId).size()
        ));
        totalRecords.setText("Total Records: " + recordsCount);

        panel.remove(nextLabel);
        panel.add(nextLabel);
        lQueue.add(nextLabel);

        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void addSensorPane(int id) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        String sensorId = "00000" + id;
        JLabel title = new JLabel("Sensor " + id + ": (0)");
        JPanel logPanel = createLogPanel(sensorId);
        JScrollPane scrollPane = new JScrollPane(logPanel);

        sensorTitles.put(sensorId, title);
        textPanes.put(sensorId, logPanel);
        scrollPanes.put(sensorId, scrollPane);

        panel.add(title);
        panel.add(scrollPane);

        add(panel);
    }

    private JPanel createLogPanel(String sensorId) {
        JPanel panel = new JPanel();
        Queue<JLabel> lQueue = new ArrayDeque<>();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        labelsQueue.put(sensorId, lQueue);

        for (int i = 0; i < 100; i++) {
            JLabel label = new JLabel(" ");
            panel.add(label);
            lQueue.add(label);
        }

        return panel;
    }

    private void addTotalRecordsPanel(int sensorCount) {
        add(totalRecords);
    }

    private String parseSensorId(String data) {
        String unparsedId = data.split(" ")[0];

        return unparsedId.replace("id=", "");
    }
}

