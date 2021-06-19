package unsw.blackout;

import java.time.LocalTime;
import java.util.Objects;

public class connection {

    private String deviceId;
    private LocalTime startTime;
    private LocalTime endTime;
    private int minutesActive;
    private String satelliteId;

    public connection(String deviceId, LocalTime startTime, String satelliteId, int timeToConnect) {
        this.startTime = startTime;
        this.deviceId = deviceId;
        this.satelliteId = satelliteId;
        minutesActive = 0 - timeToConnect;
    }

    public void updateConnection() {
        minutesActive++;
    }

    public void endConnection(LocalTime currentTime) {
        endTime = currentTime;
        minutesActive -= 1;
    }

    /*
     * getters and setter, not all are used but i think are needed for showWorldState
     */
//////////////////////////
    public String getDeviceId() {
        return this.deviceId;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public int getMinutesActive() {
        return this.minutesActive;
    }

    public String getSatelliteId() {
        return this.satelliteId;
    }
////////////////////////////
    /**
     * checks if connection is still active
     * @param id
     * @return true or false
     */
    public boolean validateConnection(String id) {
        if (Objects.isNull(endTime) && deviceId.equals(id)) {
            return true;
        }
        return false;
    }

}
