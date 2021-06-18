package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Device {
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
    Getters and setters
    */



    public boolean getIsConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
    // Only used for showWorldState
    public List<activationPeriod> getActivationPeriods() {
        return activationPeriods;
    }
    public String getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public double getPosition(){
        return position;
    }
    public void setPos(double newPos) {
        position = newPos;
        position %= 360;
    }
    public void addActivationPeriod(LocalTime start, int durationInMinutes){    
        activationPeriods.add(new activationPeriod(start, durationInMinutes));
        sortActivationPeriods();
    }


    private void sortActivationPeriods() {
        Comparator<activationPeriod> compareByStart = (activationPeriod o1, activationPeriod o2) -> o1.getStartTime().compareTo( o2.getStartTime() );
        Collections.sort(activationPeriods, compareByStart);

    }


    public boolean inActivationPeriod(LocalTime currentTime) {
        if (!Objects.isNull(activationPeriods)){
            for (activationPeriod a : activationPeriods){
                if (a.inPeriod(currentTime)) {
                    return true;
                }
            }
            
        }
        return false;
    }


    // public void connect(LocalTime currentTime, Satellite s) {
    //     timeToConnect = s.calcTimetoConnect(type, timeToConnect);
    //     connection newConnection = new connection(this, currentTime, s.getId(), timeToConnect);
    //     isConnected = true;
    //     s.addConnection(newConnection);
    // }


    public void disconnect() {
        isConnected = false;
    }


    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {

    }







}
