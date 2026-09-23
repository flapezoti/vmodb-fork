package dk.ku.di.dms.vms.coordinator.election;

public final class Constants {

    /**
     * Message identifiers
     */

    // a server requesting a vote
    public static final byte VOTE_REQUEST = 1;

    // a server responding a request
    public static final byte VOTE_RESPONSE = 2;

    // a server claims to be the leader
    public static final byte LEADER_REQUEST = 3;

}
