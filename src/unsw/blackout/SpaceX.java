package unsw.blackout;

public class SpaceX extends Satellite{
    public SpaceX(String id, String type, double height, double position) {
        super(id, type, height, position);
        setVelocity(55.5);
        addSupportedDevice("HandheldDevice");
    }


}
