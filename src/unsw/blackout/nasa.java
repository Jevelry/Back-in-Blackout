package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class nasa extends Satellite{
    private int Connections;
    private List<Device> connectedDevices = new ArrayList<Device>();
    
    public nasa(String id, String type, double height, double position){
        super(id, type, height, position);
        setVelocity(85);
        addSupportedDevice("HandheldDevice");
        addSupportedDevice("DesktopDevice");
        addSupportedDevice("LaptopDevice");
    }
    @Override
    public void validateConnections(LocalTime currentTime) {
        for (Device d : connectedDevices) {
            if (!d.inActivationPeriod(currentTime) || !isVisible(d)) {
                disconnect(d);
                Connections--; 
            }
        }
    }

    @Override
    public boolean canConnect(Device d) {    
        if (Connections < 6) {
            return true;
        }
        if (Connections == 6) {
            if (d.getPosition() >= 30 && d.getPosition() <= 40){
                for (Device i : connectedDevices) {
                    if (i.getPosition() <= 30 || i.getPosition() >= 40) {
                        return true;
                    }
                }
            }        

        }
        return false;
    }

    public void connect(LocalTime currentTime, Device d, int timeToConnect) {
        int ttc = calcTimetoConnect(timeToConnect);
        connection newConnection = new connection(d.getId(), currentTime, getId(), ttc);
        addConnection(newConnection);
        if (Connections == 6) {
            dropOldestConnection();
        }
        connectedDevices.add(d);
        Connections++;
    }

    private void dropOldestConnection() {
        for (Device d : connectedDevices) {
            if (d.getPosition() >= 30 && d.getPosition() <= 40){
                disconnect(d);
                connectedDevices.remove(d);
                break;
            }
        }
        Connections--;
        
    }

    @Override
    public int calcTimetoConnect(int timeToConnect) {
        return 10;

    }


    
}
