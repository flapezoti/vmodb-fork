package dk.ku.di.dms.vms.modb.common.schema.network.control;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;
import dk.ku.di.dms.vms.modb.common.schema.network.node.ServerNode;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * The payload of a heartbeat
 */
public final class Heartbeat {

    // type | port | size | <host address is variable>
    private static final int headerSize = Byte.BYTES + Integer.BYTES + Integer.BYTES;

    public static void write(ByteBuffer buffer, ServerNode serverIdentifier){
        byte[] hostBytes = serverIdentifier.host.getBytes();
        buffer.put( Constants.HEARTBEAT );
        buffer.putInt(serverIdentifier.port );
        buffer.putInt( hostBytes.length );
        buffer.put( hostBytes );
    }

    public static HeartbeatPayload read(ByteBuffer buffer){
        HeartbeatPayload payload = new HeartbeatPayload();
        payload.port = buffer.getInt();
        int size = buffer.getInt();
        payload.host = new String(buffer.array(), headerSize, size, StandardCharsets.UTF_8 );
        return payload;
    }

    public static class HeartbeatPayload {
        public String host;
        public int port;
    }

}
