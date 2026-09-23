package dk.ku.di.dms.vms.modb.common.schema.network.batch;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;

import java.nio.ByteBuffer;

/**
 * Payload that carries batch-commit information.
 * It may come appended with the batch of events
 * (as the last event) or may come alone.
 * Only sent to terminal nodes in a batch.
 */
public final class BatchCommitInfo {

    // message type + 3 longs + 1 int
    public static final int SIZE = 1 + (2 * Long.BYTES) + Integer.BYTES;

    public static void write(ByteBuffer buffer, long batch,
                             long previousBatch, int numberOfTIDsBatch){
        buffer.put(Constants.BATCH_COMMIT_INFO);
        buffer.putLong( batch );
        buffer.putLong( previousBatch );
        buffer.putInt( numberOfTIDsBatch );
    }

    public static void write(ByteBuffer buffer, BatchCommitInfo.Payload payload){
        buffer.put(Constants.BATCH_COMMIT_INFO);
        buffer.putLong( payload.batch );
        buffer.putLong(payload.previousBatch );
        buffer.putInt(payload.numberOfTIDsBatch);
    }

    public static Payload read(ByteBuffer buffer){
        long batch = buffer.getLong();
        long previousBatch = buffer.getLong();
        int numberOfTIDsBatch = buffer.getInt();
        return new Payload(batch, previousBatch, numberOfTIDsBatch);
    }

    public static Payload of(long batch, long previousBatch, int numberOfTIDsBatch){
        return new Payload(batch, previousBatch, numberOfTIDsBatch);
    }

    public record Payload(
            long batch, long previousBatch, int numberOfTIDsBatch
    ){
        @Override
        public String toString() {
            return "{"
                    + "\"batch\":\"" + batch + "\""
                    + ",\"previousBatch\":\"" + previousBatch + "\""
                    + ",\"numberOfTIDsBatch\":\"" + numberOfTIDsBatch + "\""
                    + "}";
        }
    }

}
