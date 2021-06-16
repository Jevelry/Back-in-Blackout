package unsw.blackout;

public class nasa extends Satellite{
    public nasa(String id, String type, double height, double position){
        super(id, type, height, position);
        setVelocity(85);
        addSupportedDevice("HandheldDevice");
        addSupportedDevice("DesktopDevice");
        addSupportedDevice("LaptopDevice");
    }






}
