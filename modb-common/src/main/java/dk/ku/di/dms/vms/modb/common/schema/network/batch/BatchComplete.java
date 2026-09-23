package dk.ku.di.dms.vms.modb.common.schema.network.batch;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;
import dk.ku.di.dms.vms.modb.common.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A prepare request payload
 * Used to inform coordinator
 * Using name because network address can change within a batch
 */
public final class BatchComplete {

    public static Payload read(ByteBuffer buffer){
        long batch = buffer.getLong();
        int size = buffer.getInt();
        String vms = ByteUtils.extractStringFromByteBuffer(buffer, size);
        return new Payload(batch, vms);
    }

    public static void write(ByteBuffer buffer, Payload payload) {
        buffer.put(Constants.BATCH_COMPLETE);
        buffer.putLong(payload.batch());
        buffer.putInt( payload.vms().length() );
        buffer.put( payload.vms().getBytes(StandardCharsets.UTF_8) );
    }

    public static Payload of(long batch, String vms){
        return new Payload(batch, vms);
    }

    public record Payload(
        long batch, String vms
    ){
        @Override
        public String toString() {
            return "{"
                    + "\"batch\":\"" + batch + "\""
                    + ",\"vms\":\"" + vms + "\""
                    + "}";
        }
    }

}
