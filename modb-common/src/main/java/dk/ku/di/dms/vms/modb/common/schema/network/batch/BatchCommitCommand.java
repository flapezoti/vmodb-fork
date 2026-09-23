package dk.ku.di.dms.vms.modb.common.schema.network.batch;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;

import java.nio.ByteBuffer;

/**
 * A batch-commit request payload.
 * Sent to non-terminal nodes in a batch.
 * Sent after all terminal nodes have replied a BATCH_COMPLETE event
 */
public final class BatchCommitCommand {

    public static final int SIZE = 1 + (2 * Long.BYTES) + Integer.BYTES;

    public static void write(ByteBuffer buffer, BatchCommitCommand.Payload payload){
        buffer.put(Constants.BATCH_COMMIT_COMMAND);
        buffer.putLong(payload.batch);
        buffer.putLong(payload.previousBatch);
        buffer.putInt(payload.numberOfTIDsBatch);
    }

    public static Payload read(ByteBuffer buffer){
        long batch = buffer.getLong();
        long previousBatch = buffer.getLong();
        int numberOfTIDsBatch = buffer.getInt();
        return new Payload(batch, previousBatch, numberOfTIDsBatch);
    }

    public record Payload(long batch, long previousBatch, int numberOfTIDsBatch){
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
