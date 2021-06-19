package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AWSCloudServer extends Desktop {
    private int activeConnections;
    private List<Satellite> connectedSatellites = new ArrayList<Satellite>();

    public AWSCloudServer(String id, String type, double position) {
        super(id, type, position);
    }

    @Override
    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {
        if (inActivationPeriod(currentTime) && activeConnections == 1) {
            for (Satellite s : possibleConnections) {
                if (s.canConnect(this) && !connectedSatellites.contains(s)) {
                    s.connect(currentTime, this, 5);
                    setConnected(true);
                    activeConnections++;
                    return;
                }
            }
            if (activeConnections == 1) {
                connectedSatellites.get(0).disconnect(this, currentTime, connectedSatellites.get(0));
            }
        }

        if (inActivationPeriod(currentTime) && activeConnections == 0) {
            int counter = 0;
            for (Satellite s : possibleConnections) {
                if (s.canConnect(this)) {
                    counter++;
                }
            }
            if (counter >= 2)
                for (Satellite s : possibleConnections) {
                    if (activeConnections < 2) {
                        if (s.canConnect(this)) {
                            s.connect(currentTime, this, 5);
                            connectedSatellites.add(s);
                            activeConnections++;
                        }
                    }
                }
            setConnected(true);
        }
    }

    @Override
    public void disconnect(Satellite s) {
        activeConnections--;
        if (activeConnections == 0) {
            setConnected(false);
        }
        connectedSatellites.remove(s);
    }
}
