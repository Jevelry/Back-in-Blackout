package unsw.blackout;

public class Device {
    private String id;
    private String type;
    private double position;
    private boolean isConnected;

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
    }
}
