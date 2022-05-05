package UC1.PSource;

import java.io.BufferedReader;
import java.io.IOException;

public class SensorData {
    private final String sensorId;
    private final String temperature;
    private final String timestamp;

    public SensorData(String sensorId, String temperature, String timestamp) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
