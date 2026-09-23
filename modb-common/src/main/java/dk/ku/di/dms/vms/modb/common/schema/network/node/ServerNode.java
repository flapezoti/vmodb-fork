package dk.ku.di.dms.vms.modb.common.schema.network.node;

/**
 * Basic data of a server running for leader
 */
public final class ServerNode extends NetworkNode {

    // maybe volatile?
    public long lastOffset;

    // maybe also last batch?

    public ServerNode(String host, int port) {
        super(host, port);
        this.lastOffset = 0L;
    }

    public ServerNode(String host, int port, long lastOffset) {
        super(host,port);
        this.lastOffset = lastOffset;
    }

}
