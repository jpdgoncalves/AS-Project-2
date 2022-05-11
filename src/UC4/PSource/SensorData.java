package UC4.PSource;

import java.io.Serializable;

public class SensorData implements Serializable {
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

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId='" + sensorId + '\'' +
                ", temperature='" + temperature + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}