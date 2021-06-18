package unsw.blackout;

import java.time.LocalTime;

public class activationPeriod {
    private LocalTime startTime;
    private LocalTime endTime;


    public activationPeriod(LocalTime startTime,  int durationInMinutes) {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(durationInMinutes);
    }

    public boolean inPeriod(LocalTime time){
        return (startTime.isBefore(time) || startTime.equals(time)) && (endTime.isAfter(time) || endTime.equals(time));
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }


    public LocalTime getEndTime() {
        return this.endTime;
    }


}
