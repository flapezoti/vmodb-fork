package dk.ku.di.dms.vms.modb.common.schema.network.meta;

import java.net.InetSocketAddress;
import java.util.Objects;

public class NetworkAddress {

    public String host;
    public int port;

    public NetworkAddress(){}

    public NetworkAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    public InetSocketAddress asInetSocketAddress(){
        return new InetSocketAddress(this.host, this.port);
    }

    // mutable since the VMS can crash
    @Override
    public int hashCode() {
        return Objects.hash(this.host, this.port);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NetworkAddress o_ && this.host.contentEquals(o_.host) && this.port == o_.port;
    }

}
