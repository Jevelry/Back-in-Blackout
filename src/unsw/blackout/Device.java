package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class Device {
    private String id;
    private String type;
    private double position;
    private boolean isConnected;
    private List<activationPeriod> activationPeriods = new ArrayList<activationPeriod>();

    public Device(String id, String type, double position) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isConnected = false;
    }

    /*
     * Getters and setters. Some of these aren't used but needed to generate JSONObject
     */
////////////////////////
    public boolean getIsConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public List<activationPeriod> getActivationPeriods() {
        return activationPeriods;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPosition() {
        return position;
    }

    public void setPos(double newPos) {
        position = newPos;
        position %= 360;
    }
//////////////////////////
    /**
     * adds activation period to activationperiods and sorts list by start time
     */
    public void addActivationPeriod(LocalTime start, int durationInMinutes) {
        activationPeriods.add(new activationPeriod(start, durationInMinutes));
        sortActivationPeriods();
    }
    /**
     * Sorts list by start time
     */
    private void sortActivationPeriods() {
        Comparator<activationPeriod> compareByStart = (activationPeriod o1, activationPeriod o2) -> o1.getStartTime()
                .compareTo(o2.getStartTime());
        Collections.sort(activationPeriods, compareByStart);

    }
    /**
     * Checks if device is still active
     * @param currentTime
     * @return true or false
     */
    public boolean inActivationPeriod(LocalTime currentTime) {
        if (!Objects.isNull(activationPeriods)) {
            for (activationPeriod a : activationPeriods) {
                if (a.inPeriod(currentTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * disconnects device
     * @param s
     */
    public void disconnect(Satellite s) {
        isConnected = false;
    }

    /**
     * Goes through list of possible connections and connects device to Satellite if possible
     * @param possibleConnections List of possible satellites device can connect to
     * @param currentTime  
     */
    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {
    }

}
