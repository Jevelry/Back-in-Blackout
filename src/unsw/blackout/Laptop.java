package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

public class Laptop extends Device {

    public Laptop(String id, String type, double position) {
        super(id, type, position);

    }

    @Override
    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {
        if (inActivationPeriod(currentTime) && !getIsConnected()) {
            for (Satellite s : possibleConnections) {
                if (s.canConnect(this)) {
                    s.connect(currentTime, this, 2);
                    setConnected(true);
                    break;
                }
            }
        }
    }

}
