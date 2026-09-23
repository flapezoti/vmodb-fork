package dk.ku.di.dms.vms.modb.common.logging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class DefaultLoggingHandler implements ILoggingHandler {

    protected final FileChannel fileChannel;
    protected final String fileName;

    public DefaultLoggingHandler(FileChannel channel, String fileName) {
        this.fileChannel = channel;
        this.fileName = fileName;
    }

    @Override
    public final void close(){
        try {
            this.fileChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void log(ByteBuffer byteBuffer) throws IOException {
        do {
            this.fileChannel.write(byteBuffer);
        } while(byteBuffer.hasRemaining());
    }

    @Override
    public final void force(){
        try {
            this.fileChannel.force(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final String getFileName() {
        return this.fileName;
    }
}
