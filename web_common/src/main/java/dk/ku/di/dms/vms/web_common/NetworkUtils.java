package dk.ku.di.dms.vms.web_common;

import dk.ku.di.dms.vms.web_common.channel.IChannel;
import jdk.net.ExtendedSocketOptions;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.net.StandardSocketOptions.*;

public final class NetworkUtils {

    private static final System.Logger LOGGER = System.getLogger(NetworkUtils.class.getName());

    public static void configure(AsynchronousSocketChannel channel, int soBufferSize) throws IOException {
        channel.setOption(TCP_NODELAY, true);
        channel.setOption(SO_KEEPALIVE, true);
        if (channel.supportedOptions().contains(ExtendedSocketOptions.TCP_QUICKACK)) {
            channel.setOption(ExtendedSocketOptions.TCP_QUICKACK, false);
        }
        if (soBufferSize > 0) {
            LOGGER.log(DEBUG, "Configuring channel " + channel + " with " + soBufferSize + " as size of SO_SNDBUF and SO_RCVBUF");
            channel.setOption(SO_SNDBUF, soBufferSize);
            channel.setOption(SO_RCVBUF, soBufferSize);
        }
    }

    public static void configure(IChannel channel_, int soBufferSize) throws IOException {
        if(channel_ instanceof AsynchronousSocketChannel channel) {
            configure(channel, soBufferSize);
        }
    }

    public static void configureForFastAck(AsynchronousSocketChannel channel) {
        try {
            channel.setOption(TCP_NODELAY, true);
            if(channel.supportedOptions().contains(ExtendedSocketOptions.TCP_QUICKACK)) {
                channel.setOption(ExtendedSocketOptions.TCP_QUICKACK, true);
            }
        } catch (IOException e) {
            LOGGER.log(ERROR, "Error: "+e.getMessage(), e);

        }
    }

}
