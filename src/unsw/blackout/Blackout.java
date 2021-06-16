package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class Blackout {
    private ArrayList<Satellite> satellites = new ArrayList<Satellite>();
    private List<Device> devices = new ArrayList<Device>();   
    
    /**
    * Creates new device and appends to list of devices
    * @param  id  unique identifier for device
    * @param  type specifies type of device (i.e destop, phone)
    * @param  position measured in degrees relative to the x-axis, rotating anti-clockwise.      
    */
    public void createDevice(String id, String type, double position) {
        devices.add(new Device(id, type, position));        
    }

    /**
    * Creates new satellite and appends to list of satellite
    * @param  id  unique identifier for satellite
    * @param  type specifies type of sattelite (i.e soviet, NASA)
    * @param  position measured in degrees relative to the x-axis, rotating anti-clockwise.
    * @param  height measured from centre of planet, so it??ll include the radius of the ring      
    */
    public void createSatellite(String id, String type, double height, double position) {
        Satellite newSatellite;
        newSatellite = new BlueOrigin(id, type, height, position);
        
        if (type == "nasaSatellite")            
            newSatellite = new nasa(id, type, height, position);
        if (type == "SovietSatellite") 
            newSatellite = new Soviet(id, type, height, position); 
        if (type == "BlueOriginSatellite") 
            newSatellite = new BlueOrigin(id, type, height, position);                                    
        if (type == "SpaceXSatellite") 
            newSatellite = new SpaceX(id, type, height, position);
            
        satellites.add(newSatellite); 
    }


    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        // TODO:
    }

    /**
    * Removes satellite from list array of satellite
    * @param  id  unique identifier for satellite     
    */
    public void removeSatellite(String id) {
        Satellite s = traceIdSatellite(id);
        satellites.remove(s);
    }

    /**
    * Removes device from list array of devices
    * @param  id  unique identifier for device     
    */
    public void removeDevice(String id) {
        Device d = traceIdDevice(id); 
        devices.remove(d);
    }

    /**
    * Moves Device to new specified position
    * @param  id  unique identifier for device   
    * @param  newPos  new position (measured in degrees from x axis counter clockwise / anti-clockwise). 
    */
    public void moveDevice(String id, double newPos) {
        Device d = traceIdDevice(id);
        d.setPos(newPos);
    }

    /**
    * Finds satellite with ID
    * @param  id  unique identifier for Satellite 
    * @return  d  Satellite with coressponding id    
    */
    private Satellite traceIdSatellite(String id) {
        for (Satellite d: satellites){
            if (d.getId() == id){
                return d;
            }
        }
        return null;
    }

    /**
    * Finds device with ID
    * @param  id  unique identifier for device 
    * @return  d  device with coressponding id    
    */
    private Device traceIdDevice(String id) {
        for (Device s: devices){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }
    public void updateWorld() {
        for (Satellite s : satellites) {
            for (Device d : devices) {
                s.checkConnection(d);
            }
        }

    }
    public JSONObject showWorldState() {
        updateWorld();
        Comparator<Device> compareById = (Device o1, Device o2) -> o1.getId().compareTo( o2.getId() );
        Comparator<Satellite> compareByIds = (Satellite o1, Satellite o2) -> o1.getId().compareTo( o2.getId() );
        Collections.sort(devices, compareById);
        Collections.sort(satellites, compareByIds);

        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray(this.devices);
        JSONArray satellites = new JSONArray(this.satellites);

        // TODO:

        result.put("devices", devices);
        result.put("satellites", satellites);
        // TODO: you'll want to replace this for Task2
        result.put("currentTime", LocalTime.of(0, 0));
        return result;
    }

    public void simulate(int tickDurationInMinutes) {
        // TODO:
    }
}
