package unsw.blackout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Satellite {

    private String id;
    private String type;
    private double height;
    private double position;
    private double velocity;
    private List<String> possibleConnections = new ArrayList<String>();
    private List<String> devicesSupported = new ArrayList<String>();
    private List<String> connections = new ArrayList<String>();


    public Satellite(String id, String type, double height, double position) {
        this.id = id;
        this.type = type;
        this.height = height;
        this.position = position;
    }
    public void addSupportedDevice(String string) {
        devicesSupported.add(string);
    }
    // THIS NEEDS TO BE REDONE
    public boolean checkConnection(Device d){
        for (String s : devicesSupported){
            // System.out.print(d.getType() + "    ");            
            if (s.equals(d.getType())){               
                if (MathsHelper.satelliteIsVisibleFromDevice(position, height, d.getPosition())){  
                    addPossibleConnection(d);                                      
                    return true;
                }
                else {                   
                    return false;
                }
            }
        }
        System.out.print("FUCKS");
        return false;
    }
    public void addPossibleConnection(Device d){
        if (possibleConnections.contains(d.getId())== false) {
            possibleConnections.add(d.getId());
            Collections.sort(possibleConnections);
        }
        
    }
    /*
    Getters and setters
    */
    public double getvelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
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
    public List<String> getPossibleConnections(){
        return possibleConnections;
    }
    public List<String> getConnections(){
        return connections;
    }
}
