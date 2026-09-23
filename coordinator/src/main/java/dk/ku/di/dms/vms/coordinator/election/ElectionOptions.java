package dk.ku.di.dms.vms.coordinator.election;

/**
 *
 */
public class ElectionOptions {

    private double roundDeltaIncrease = 0.25; // 25 % by default

    private long initRoundTimeout = 60000; // a minute by default

    public long getInitRoundTimeout() {
        return initRoundTimeout;
    }

    public void withInitRoundTimeout(long initRoundTimeout) {
        this.initRoundTimeout = initRoundTimeout;
    }

    public void withRoundDeltaIncrease(double delta){
        this.roundDeltaIncrease = roundDeltaIncrease;
    }

    public double getRoundDeltaIncrease(){
        return this.roundDeltaIncrease;
    }

}
