package dk.ku.di.dms.vms.modb.common.schema.network.transaction;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;

import java.nio.ByteBuffer;

/**
 * An abort request is sent from a VMS to the leader.
 * The leader is then responsible for issuing abort request to all VMS participating in the given transaction.
 * The transaction must be stored in memory and discarded when the batch is committed.
 * This is necessary to get the participating VMSs.
 */
public final class TransactionAbort {

    public static final int SIZE = 1 + (2 * Long.BYTES);

    public static void write(ByteBuffer buffer, Payload payload) {
        buffer.put(Constants.TX_ABORT);
        buffer.putLong(payload.batch);
        buffer.putLong(payload.tid);
    }

    public static void write(ByteBuffer buffer, long batch, long tid){
        buffer.put(Constants.TX_ABORT);
        buffer.putLong(batch);
        buffer.putLong(tid);
    }

    public static Payload read(ByteBuffer buffer){
        long batch = buffer.getLong();
        long tid = buffer.getLong();
        return new Payload(batch, tid);
    }

    // a leader cannot issue new events (and batches of course) without receiving batch ACKs from all vms involved
    // so no need for further information in the payload
    public record Payload(
            long batch, long tid
    ) {
        @Override
        public String toString() {
            return "{"
                    + "\"batch\":\"" + batch + "\""
                    + ",\"tid\":\"" + tid + "\""
                    + "}";
        }
    }

}