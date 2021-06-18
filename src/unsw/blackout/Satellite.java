package unsw.blackout;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class Satellite {

    private String id;
    private String type;
    private double height;
    private double position;
    private double velocity;


    private List<String> possibleConnections = new ArrayList<String>();
    private List<String> devicesSupported = new ArrayList<String>();
    private List<connection> connections = new ArrayList<connection>();


    public Satellite(String id, String type, double height, double position) {
        this.id = id;
        this.type = type;
        this.height = height;
        this.position = position;
    }
    
    public double calcAngularVelocity(){
        return velocity/height;
    }

    /**
    * checks if device is in list of supported devices
    * @param d Device    
    */
    public boolean isSupported(Device d){
        for (String s : devicesSupported){                       
            if (s.equals(d.getType())){               
                return true;
            }
        }        
        return false;
    }

    /**
    * Checks if device is visible to satellite using math helper
    * @param d Device    
    */
    public boolean isVisible (Device d) {
        if (MathsHelper.satelliteIsVisibleFromDevice(position, height, d.getPosition())){                                               
            return true;
            
        }
        return false;
    }

    /**
    * appends device id to list of possible connections
    * @param d Device    
    */
    public void addPossibleConnection(Device d){
        if (possibleConnections.contains(d.getId()) == false) {
            possibleConnections.add(d.getId());
            Collections.sort(possibleConnections);
        }        
    }

    public void removePossibleConnection(Device d) {
        if (possibleConnections.contains(d.getId()) == true) {
            possibleConnections.remove(d.getId());
            Collections.sort(possibleConnections);
        }        
    }
    


    public void addSupportedDevice(String string) {
        devicesSupported.add(string);
    }

    /*
    Getters and setters
    */


    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public void setPosition(double position) {
        this.position = position;
    }
    public double getVelocity() {
        return velocity;
    }
    public String getId(){
        return id;
    }
    public String getType(){
        return type;
    }
    public double getHeight(){
        return height;
    }
    public double getPosition(){
        return position;
    }
    // Think this is needed for showWorldState
    public List<String> getPossibleConnections(){
        return possibleConnections;
    }
    // Think this is needed for showWorldState
    public List<connection> getConnections(){
        return connections;
    }

    public void updatePosition() {
        position += calcAngularVelocity();
        position %= 360;
    }

    // Overidden function
    public boolean canConnect(Device d) {
        return true;
    }
    
    public void addConnection(connection c) {
        connections.add(c);
        //sortConnections(connections);
    }

    private void sortConnections(List<connection> connections) {
        connections.sort(Comparator.comparing(connection::getStart));
    }

    // overriden function
    public int calcTimetoConnect(int timeToConnect) {
        return timeToConnect;
    }




    public void disconnect(Device d) {
        for (connection c : connections) {
            if (c.validateConnection(d.getId())) {
                c.endConnection();
            }
        }
        d.disconnect();
    }

    public void validateConnections(LocalTime currentTime) {
    }

    public void connect(LocalTime currentTime, Device d, int timeToConnect) {
    }

    public void updateActiveConnections() {
        if (!Objects.isNull(connections)) {
            for (connection c : connections) {
                if (Objects.isNull(c.getEnd())) {
                    c.updateConnection();
                }
            }
        }
    }

    



}
