package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlueOrigin extends Satellite {
    private int Laptops;
    private int Desktops;
    private int Connections;
    private List<Device> connectedDevices = new ArrayList<Device>();

    public BlueOrigin(String id, String type, double height, double position) {
        super(id, type, height, position);
        setVelocity(8500 / 60.0);
        addSupportedDevice("HandheldDevice");
        addSupportedDevice("DesktopDevice");
        addSupportedDevice("LaptopDevice");
        addSupportedDevice("MobileXPhone");
        addSupportedDevice("AWSCloudServer");

    }

    @Override
    public void validateConnections(LocalTime currentTime) {
        Iterator<Device> itr = connectedDevices.iterator();
        while (itr.hasNext()) {
            Device d = itr.next();
            if (!d.inActivationPeriod(currentTime) || !isVisible(d)) {
                disconnect(d, currentTime, this);
                itr.remove();
                if (d.getType().equals("DesktopDevice")) {
                    Desktops--;
                    Connections--;
                } else if (d.getType().equals("LaptopDevice")) {
                    Laptops--;
                    Connections--;
                } else if (d.getType().equals("HandheldDevice")) {
                    Connections--;
                } else {
                    Connections--;
                }
            }
        }
    }

    @Override
    public boolean canConnect(Device d) {

        if (d.getType().equals("DesktopDevice")) {
            if (Desktops < 2 && Connections < 10) {
                return true;
            }
        } else if (d.getType().equals("LaptopDevice")) {
            if (Laptops < 5 && Connections < 10) {
                return true;
            }
        } else if (d.getType().equals("HandheldDevice")) {
            if (Connections < 10) {
                return true;
            }
        } else {
            if (Connections < 10) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void connect(LocalTime currentTime, Device d, int timeToConnect) {
        int ttc = calcTimetoConnect(timeToConnect);
        connection newConnection = new connection(d.getId(), currentTime, getId(), ttc);
        addConnection(newConnection);
        connectedDevices.add(d);
        Connections++;
        if (d.getType().equals("LaptopDevice")) {
            Laptops++;
        }
        if (d.getType().equals(("DesktopDevice"))) {
            Desktops++;
        }
    }
}
