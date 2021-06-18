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
    private LocalTime currentTime = LocalTime.of(0,0);
    
    /**
    * Creates new device and appends to list of devices
    * @param  id  unique identifier for device
    * @param  type specifies type of device (i.e destop, phone)
    * @param  position measured in degrees relative to the x-axis, rotating anti-clockwise.      
    */
    public void createDevice(String id, String type, double position) {
        if (type.equals("HandheldDevice")) {            
            Handheld newDevice = new Handheld(id, type, position);
            devices.add(newDevice);
        }
        if (type.equals("LaptopDevice")) {            
            Laptop newDevice = new Laptop(id, type, position);
            devices.add(newDevice);
        }
        if (type.equals("DesktopDevice")) {            
            Desktop newDevice = new Desktop(id, type, position);
            devices.add(newDevice);
        }
   
    }

    /**
    * Creates new satellite and appends to list of satellite
    * @param  id  unique identifier for satellite
    * @param  type specifies type of sattelite (i.e soviet, NASA)
    * @param  position measured in degrees relative to the x-axis, rotating anti-clockwise.
    * @param  height measured from centre of planet, so it??ll include the radius of the ring      
    */
    public void createSatellite(String id, String type, double height, double position) {
        

        if (type.equals("NasaSatellite")) {            
            nasa newSatellite = new nasa(id, type, height, position);
            satellites.add(newSatellite);
        }
        if (type.equals("SovietSatellite")) {
            Soviet newSatellite = new Soviet(id, type, height, position);
            satellites.add(newSatellite); 
        }
        if (type.equals("BlueOriginSatellite")) {
            BlueOrigin newSatellite = new BlueOrigin(id, type, height, position);  
            satellites.add(newSatellite);
        }
        if (type.equals("SpaceXSatellite")) {
            SpaceX newSatellite = new SpaceX(id, type, height, position);
            satellites.add(newSatellite); 
        }
         
    }

    
    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {

        traceIdDevice(deviceId).addActivationPeriod(start, durationInMinutes);

    }

    /**
    * Removes satellite from list array of satellite
    * @param  id  unique identifier for satellite     
    */
    public void removeSatellite(String id) {    
        satellites.remove(traceIdSatellite(id));
    }

    /**
    * Removes device from list array of devices
    * @param  id  unique identifier for device     
    */
    public void removeDevice(String id) {        
        devices.remove(traceIdDevice(id));
    }

    /**
    * Moves Device to new specified position
    * @param  id  unique identifier for device   
    * @param  newPos  new position (measured in degrees from x axis counter clockwise / anti-clockwise). 
    */
    public void moveDevice(String id, double newPos) {
        traceIdDevice(id).setPos(newPos);        
    }

    /**
    * Finds satellite with ID
    * @param  id  unique identifier for Satellite 
    * @return  d  Satellite with coressponding id    
    */
    private Satellite traceIdSatellite(String id) {
        for (Satellite d: satellites){
            if (d.getId().equals(id)){
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
            if (s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }
    /**
    * updates world state by forcing Satellite to check connections   
    */
    public void updatePossibleConnections() {
        
        for (Satellite s : satellites) {
            for (Device d : devices) {
                if (s.isSupported(d) && s.isVisible(d)){
                    s.addPossibleConnection(d);
                } else {
                    s.removePossibleConnection(d);
                }
            }
        }

    }
    public JSONObject showWorldState() {
        updatePossibleConnections();
        // used to sort devices and satellites by id alphabetically
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

        result.put("currentTime", currentTime);
        return result;
    }
    
    public void simulate(int tickDurationInMinutes) {
        // TODO:
        // for each step of the simulation:
        //     update the position of each satellite
        //     then process disconnections => then connections
        
        for (int i = tickDurationInMinutes; i > 0; i--) {
            updatePossibleConnections();
            updateSatellitePositions();
            updateExistingConnections();
            processDisconnections();
            processConnections();
            currentTime = currentTime.plusMinutes(1);
        }
        
    }

    private void updateExistingConnections() {
        for (Satellite s : satellites) {
            s.updateActiveConnections();
        }
    }



    private void processConnections() {
        Comparator<Satellite> compareByPosition = (Satellite o1, Satellite o2) -> Double.compare(o1.getPosition(),(o2.getPosition()) );
        Collections.sort(satellites, compareByPosition);
        
        
        for (Device d : devices) {
            ArrayList<Satellite> possibleConnections = new ArrayList<Satellite>();
            findPossibleConnections(d, possibleConnections);
            d.processOptions(possibleConnections, currentTime);
        }
        Comparator<Satellite> compareByIds = (Satellite o1, Satellite o2) -> o1.getId().compareTo( o2.getId() );
        Collections.sort(satellites, compareByIds);
    }

    private void findPossibleConnections(Device d, ArrayList<Satellite> possibleConnections) {
        for (Satellite s : satellites) {
            if (s.isSupported(d) && s.isVisible(d)){
                possibleConnections.add(s);
            }
        }
    }

    private void processDisconnections() {
        
        for (Satellite s : satellites) {
            s.validateConnections(currentTime); 
        } 
    }

    private void updateSatellitePositions() {
        for (Satellite s : satellites) {
            s.updatePosition();
        }
    }
}
