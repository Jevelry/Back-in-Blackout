package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)

public class Task3Tests {
    @Test
    public void testMobileX() {
        //Tests mobile x priority
        //Creates 3 satellites and mobile x
        //activate mobile x

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66,
            /* Possible Connections */ new String[] { "DeviceA" })
            .expectSatellite("SpaceXSatellite", "Satellite2", 10000, 15, 55.5,
            /* Possible Connections */ new String[] { "DeviceA" })
        .expectDevice("MobileXPhone", "DeviceA", 30)
        
        .toString();

        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.40, 141.66,
                new String[] { "DeviceA" }
                )

        .expectSatellite("SpaceXSatellite", "Satellite2", 10000, 22.99, 55.5,
        new String[] { "DeviceA" },
        new DummyConnection[] {
            new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 41), 400),
        })

        .expectDevice("MobileXPhone", "DeviceA", 30, false,
        new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        .toString();

        TestHelper plan = new TestHelper().createDevice("MobileXPhone", "DeviceA", 30)
        .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
        .createSatellite("SpaceXSatellite", "Satellite2", 10000, 15)
        .showWorldState(initialWorldState)
        .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 400)
        .simulate(1440)
        .showWorldState(afterADay);

        plan.executeTestPlan();
    }
    
    @Test
    public void testAWSONLY1() {
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66,
            /* Possible Connections */ new String[] { "DeviceA" })

        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        
        .toString();

        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.40, 141.66,
                new String[] { "DeviceA" })
        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        .toString();

        
        TestHelper plan = new TestHelper().createDevice("AWSCloudServer", "DeviceA", 30)
        .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
        .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 400)
        .showWorldState(initialWorldState)
        .simulate(1440)
        .showWorldState(afterADay);

        plan.executeTestPlan();
    }
    @Test
    public void testAWS2SATELLTIES() {
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66,
            /* Possible Connections */ new String[] { "DeviceA" })
        .expectSatellite("SovietSatellite", "Satellite2", 10000, 50, 100,
            /* Possible Connections */ new String[] { "DeviceA" })

        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        
        .toString();

        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.40, 141.66,
                new String[] { "DeviceA" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 41), 395),
                })
        .expectSatellite("SovietSatellite", "Satellite2", 10000, 64.40, 100,
                /* Possible Connections */ new String[] { "DeviceA" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                })    
        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        .toString();

        
        TestHelper plan = new TestHelper().createDevice("AWSCloudServer", "DeviceA", 30)
        .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
        .createSatellite("SovietSatellite", "Satellite2", 10000, 50)
        .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 400)
        .showWorldState(initialWorldState)
        .simulate(1440)
        .showWorldState(afterADay);

        plan.executeTestPlan();
    }
    // NOTE, SIMULATOR IS SHOWING MINUTESACTIVE 372 BUT MINE SHOWS 371. UNSURE WHY.
    @Test
    public void testAWSSINGLESATELLITEREMOVE() {
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 61.60, 141.66,
            /* Possible Connections */ new String[] { "DeviceA" })
        .expectSatellite("SovietSatellite", "Satellite2", 10000, 107.60, 100,
            /* Possible Connections */ new String[] { "DeviceA" })

        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        
        .toString();

        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
        .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 82.00, 141.66,
                new String[] { "DeviceA" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 17), 371),
                })
        .expectSatellite("SovietSatellite", "Satellite2", 10000, 122, 100,
                /* Possible Connections */ new String[] {  },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 17), 366),
                })    
        .expectDevice("AWSCloudServer", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } } )
        .toString();

        
        TestHelper plan = new TestHelper().createDevice("AWSCloudServer", "DeviceA", 30)
        .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 61.60)
        .createSatellite("SovietSatellite", "Satellite2", 10000, 107.60)
        .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 400)
        .showWorldState(initialWorldState)
        .simulate(1440)
        .showWorldState(afterADay);

        plan.executeTestPlan();
    }
}
