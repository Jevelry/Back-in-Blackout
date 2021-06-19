package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

public class MobileX extends Handheld {

    public MobileX(String id, String type, double position) {
        super(id, type, position);

    }

    @Override
    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {
        if (inActivationPeriod(currentTime) && !getIsConnected()) {
            for (Satellite s : possibleConnections) {
                if (s.getType().equals("SpaceXSatellite")) {
                    s.connect(currentTime, this, 1);
                    setConnected(true);
                    return;
                }
            }
            for (Satellite s : possibleConnections) {
                if (s.canConnect(this)) {
                    s.connect(currentTime, this, 1);
                    setConnected(true);
                    return;
                }
            }
        }
    }

}
