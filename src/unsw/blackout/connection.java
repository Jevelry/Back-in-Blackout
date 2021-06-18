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



    public void updateConnection(){
        minutesActive++;
    }


    public void endConnection(){
        endTime = startTime.plusMinutes(minutesActive + 1);
    }
    
    /*
    getters and setter, not used but i think are needed for showWorldState
    */

    public String getDeviceID() {
        return this.deviceId;
    }

    public LocalTime getStart() {
        return this.startTime;
    }

    public LocalTime getEnd() {
        return this.endTime;
    }

    public int getMinutesActive() {
        return this.minutesActive;
    }

    public String getSatelliteID() {
        return this.satelliteId;
    }

    public boolean validateConnection(String id) {
        if (Objects.isNull(endTime) && deviceId.equals(id)){
            return true;
        }
        return false;
    }


    
}
