package dk.ku.di.dms.vms.web_common.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class JdkAsyncChannel implements IChannel {

    private final AsynchronousSocketChannel channel;

    public JdkAsyncChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    public static JdkAsyncChannel create(// the group for the socket channel
                                         AsynchronousChannelGroup group) {
        try {
            return new JdkAsyncChannel(AsynchronousSocketChannel.open(group));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Future<Integer> write(ByteBuffer src){
        return this.channel.write(src);
    }

    @Override
    public <A> void write(ByteBuffer src, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {
        this.channel.write(src, timeout, unit, attachment, handler);
    }

    @Override
    public <A> void read(ByteBuffer dst, A attachment, CompletionHandler<Integer, ? super A> handler) {
        this.channel.read(dst, attachment, handler);
    }

    @Override
    public Future<Void> connect(InetSocketAddress inetSocketAddress) {
        return this.channel.connect(inetSocketAddress);
    }

    public void close() {
        try { this.channel.close(); } catch (IOException ignored) { }
    }

}
