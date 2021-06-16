package unsw.blackout;

public class BlueOrigin extends Satellite{
    public BlueOrigin(String id, String type, double height, double position){
        super(id, type, height, position);
        setVelocity(141.67);
        addSupportedDevice("HandheldDevice");
        addSupportedDevice("DesktopDevice");
        addSupportedDevice("LaptopDevice");
    }
}
