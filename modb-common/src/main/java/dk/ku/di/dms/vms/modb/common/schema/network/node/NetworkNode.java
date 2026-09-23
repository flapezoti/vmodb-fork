package dk.ku.di.dms.vms.modb.common.schema.network.node;

import dk.ku.di.dms.vms.modb.common.schema.network.meta.NetworkAddress;

/**
 * In distributed systems, nodes can fail.
 * When they come back, their network address may change,
 * but not their logical representation.
 * A transition: off -> on -> off
 * Immutable object for host and port.
 */
public class NetworkNode extends NetworkAddress {

    // whether this node is active
    private volatile transient boolean active;

    public NetworkNode(){}

    public NetworkNode(String host, int port) {
        super(host, port);
        this.active = false;
    }

    public boolean isActive(){
        return this.active;
    }

    public void on(){
        this.active = true;
    }

    public void off(){
        this.active = false;
    }

}
