package dk.ku.di.dms.vms.web_common.meta;

import java.nio.channels.AsynchronousSocketChannel;

public class ConnectionMetadata {

    /**
     * generic, serves for both servers and VMSs, although the key
     * may change across different classes (e.g., use of vms name or <host+port>)
     */
    public final int key;

    public final NodeType nodeType;

    // unfortunately cannot set to final because of the election protocol in coordinator
    // see processServerPresentationMessage
    public final AsynchronousSocketChannel channel;

    public enum NodeType {
        SERVER,
        VMS,
        HTTP_CLIENT
    }

    public ConnectionMetadata(int key, NodeType nodeType, AsynchronousSocketChannel channel) {
        this.key = key;
        this.nodeType = nodeType;
        this.channel = channel;
    }

}
