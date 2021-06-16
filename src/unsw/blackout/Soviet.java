package unsw.blackout;

public class Soviet extends Satellite{
    public Soviet(String id, String type, double height, double position) {
        super(id, type, height, position);
        setVelocity(75);
        addSupportedDevice("LaptopDevice"); 
        addSupportedDevice("DesktopDevice");

    }
}
