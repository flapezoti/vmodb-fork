package dk.ku.di.dms.vms.coordinator.options;

/**
 * Initialized with default values
 */
public class FollowerOptions {

    private int heartbeatSlack = 1000;

    // timeout to keep track when to send heartbeats to followers
    private long heartbeatTimeout = 20000;

    private int maximumLeaderConnectionAttempt;

    public int getMaximumLeaderConnectionAttempt() {
        return maximumLeaderConnectionAttempt;
    }

    public FollowerOptions withMaximumLeaderConnectionAttempt(int maximumLeaderConnectionAttempt) {
        this.maximumLeaderConnectionAttempt = maximumLeaderConnectionAttempt;
        return this;
    }

    public int getHeartbeatSlack() {
        return heartbeatSlack;
    }

    public FollowerOptions withHeartbeatSlack(int heartbeatSlack) {
        this.heartbeatSlack = heartbeatSlack;
        return this;
    }

    public long getHeartbeatTimeout() {
        return heartbeatTimeout;
    }

    public FollowerOptions withHeartbeatTimeout(long heartbeatTimeout) {
        this.heartbeatTimeout = heartbeatTimeout;
        return this;
    }
}
