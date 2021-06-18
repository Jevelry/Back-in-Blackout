package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Soviet extends Satellite{
    private int Connections;
    private List<Device> connectedDevices = new ArrayList<Device>();


    public Soviet(String id, String type, double height, double position) {
        super(id, type, height, position);
        setVelocity(100);
        addSupportedDevice("LaptopDevice"); 
        addSupportedDevice("DesktopDevice");

    }
    @Override
    public void updatePosition() {
        if (getPosition() > 190 && getPosition() < 345){
            setVelocity(-100);
        }
        if (getPosition() < 140 && getPosition() >= 345){
            setVelocity(100);
        }        
        super.updatePosition();
    }
    public void validateConnections(LocalTime currentTime) {
        for (Device d : connectedDevices) {
            if (!d.inActivationPeriod(currentTime) || !isVisible(d)) {
                disconnect(d, currentTime);
                Connections--;
                
            }

        }
    }
    public void connect(LocalTime currentTime, Device d, int timeToConnect) {
        int ttc = calcTimetoConnect(timeToConnect);

        connection newConnection = new connection(d.getId(), currentTime, getId(), ttc);
        addConnection(newConnection);
        if (Connections == 10) {
            dropOldestConnection(currentTime);
        }
        connectedDevices.add(d);
        Connections++;
    }
    private void dropOldestConnection(LocalTime currentTime) {
        disconnect(connectedDevices.get(0), currentTime);
        connectedDevices.remove(0);
        Connections--;
    }

    @Override
    public int calcTimetoConnect(int timeToConnect) {
        return 2 * timeToConnect;

    }


}
