package dk.ku.di.dms.vms.modb.common.schema.network.control;

import dk.ku.di.dms.vms.modb.common.schema.network.Constants;
import dk.ku.di.dms.vms.modb.common.schema.network.node.IdentifiableNode;
import dk.ku.di.dms.vms.modb.common.serdes.IVmsSerdesProxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public final class ConsumerSet {

    public static void write(ByteBuffer buffer, String mapStr) throws IOException {
        buffer.put(Constants.CONSUMER_SET);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeflaterOutputStream deflater = new DeflaterOutputStream(out);
        byte[] in = mapStr.getBytes(StandardCharsets.UTF_8);
        deflater.write(in);
        deflater.flush();
        deflater.close();
        byte[] compressed = out.toByteArray();
        buffer.putInt(compressed.length);
        buffer.put(compressed);
    }

    public static Map<String, List<IdentifiableNode>> read(ByteBuffer buffer,
                                                           IVmsSerdesProxy proxy) throws IOException {
        int size = buffer.getInt();
        if(size <= 0) {
            return Map.of();
        }
        byte[] in = new byte[size];
        for(int i = 0; i < size; i++){
            in[i] = buffer.get();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream infl = new InflaterOutputStream(out);
        infl.write(in);
        infl.flush();
        infl.close();
        String consumerSet = out.toString(StandardCharsets.UTF_8);
        if(consumerSet.isEmpty()) {
            return Map.of();
        }
        return proxy.deserializeConsumerSet(consumerSet);
    }

}
