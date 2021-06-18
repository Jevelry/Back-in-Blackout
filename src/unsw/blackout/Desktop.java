package unsw.blackout;

import java.time.LocalTime;
import java.util.ArrayList;

public class Desktop extends Device{
    private int timeToConnect;

    public Desktop(String id, String type, double position) {
        super(id, type, position);
        timeToConnect = 5;
    }
    @Override
    public void processOptions(ArrayList<Satellite> possibleConnections, LocalTime currentTime) {
        if (inActivationPeriod(currentTime) && !getIsConnected()) {
            for (Satellite s : possibleConnections) {
                if (s.canConnect(this)) {
                    s.connect(currentTime, this, timeToConnect);
                    setConnected(true);
                }
            }
        }
    }
    public int getTimeToConnect() {
        return timeToConnect;
    }
}
