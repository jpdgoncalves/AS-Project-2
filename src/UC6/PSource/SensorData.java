package UC6.PSource;

import java.io.Serializable;

/**
 * Class representing a measure from some particular
 * sensor at a particular time.
 */
public class SensorData implements Serializable {
    private final String sensorId;
    private final String temperature;
    private final String timestamp;

    /**
     * @param sensorId The sensor id this measure was taken from.
     * @param temperature The measured temperature.
     * @param timestamp The time when the measure was taken.
     */
    public SensorData(String sensorId, String temperature, String timestamp) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    /**
     * Returns the id of the sensor this measure came from.
     * @return The id of the sensor that originated this measure.
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * Returns the measured temperature.
     * @return The measured temperature.
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Returns the timestamp of when the measure was made.
     * @return The timestamp of when the measure was made.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Representation of this measure as a string
     * @return The string representation of this measure
     */
    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId='" + sensorId + '\'' +
                ", temperature='" + temperature + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}