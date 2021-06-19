package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Satellite {

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

    /*
     * Getters and setters
     */
////////////////////////////
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getHeight() {
        return height;
    }

    public double getPosition() {
        return position;
    }

    // Think this is needed for showWorldState
    public List<String> getPossibleConnections() {
        return possibleConnections;
    }

    // Think this is needed for showWorldState
    public List<connection> getConnections() {
        return connections;
    }
////////////////////////////
    /**
     * updates position of satellite
     */
    public void updatePosition() {
        position += calcAngularVelocity();
        position %= 360;
    }

    private double calcAngularVelocity() {
        return velocity / height;
    }

    /**
     * checks if device is in list of supported devices
     * @param d Device
     * @return true or false
     */
    public boolean isSupported(Device d) {
        for (String s : devicesSupported) {
            if (s.equals(d.getType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if device is visible to satellite using math helper
     * @param d Device
     * @return true or false
     */
    public boolean isVisible(Device d) {
        if (MathsHelper.satelliteIsVisibleFromDevice(position, height, d.getPosition())) {
            return true;
        }
        return false;
    }

    /**
     * appends device to list of possible connections
     * @param d Device
     */
    public void addPossibleConnection(Device d) {
        if (possibleConnections.contains(d.getId()) == false) {
            possibleConnections.add(d.getId());
            Collections.sort(possibleConnections);
        }
    }
    /**
     * removes device from list of possible connections
     * @param d
     */
    public void removePossibleConnection(Device d) {
        if (possibleConnections.contains(d.getId()) == true) {
            possibleConnections.remove(d.getId());
            Collections.sort(possibleConnections);
        }
    }
    /**
     * Adds device to supported devices
     * @param string
     */
    public void addSupportedDevice(String string) {
        devicesSupported.add(string);
    }

    /**
     * checks if device can connect to this satellite. This is default method
     * @param d
     * @return
     */
    public boolean canConnect(Device d) {
        return true;
    }
    /**
     * adds connection to list of connections
     * @param c
     */
    public void addConnection(connection c) {
        connections.add(c);
    }

    /**
     * calculates time to connect for device, this is default method
     * @param timeToConnect
     * @return
     */
    public int calcTimetoConnect(int timeToConnect) {
        return timeToConnect;
    }
    /**
     * disconnects device from satellite and ends connection
     * @param d
     * @param currentTime
     * @param s
     */
    public void disconnect(Device d, LocalTime currentTime, Satellite s) {
        for (connection c : connections) {
            if (c.validateConnection(d.getId())) {
                c.endConnection(currentTime);
            }
        }
        d.disconnect(s);
    }
    /**
     * Goes through list of devices actively connected and checks if still valid connection
     * @param currentTime
     */
    public void validateConnections(LocalTime currentTime) {
    }
    /**
     * Goes through list of devices actively connected and checks if still valid connection
     * @param currentTime
     */
    public void connect(LocalTime currentTime, Device d, int timeToConnect) {
    }

    /**
     * updates minutes active of connections that are still going on           
     */
    public void updateActiveConnections() {
        if (!Objects.isNull(connections)) {
            for (connection c : connections) {
                if (Objects.isNull(c.getEndTime())) {
                    c.updateConnection();
                }
            }
        }
    }

}
