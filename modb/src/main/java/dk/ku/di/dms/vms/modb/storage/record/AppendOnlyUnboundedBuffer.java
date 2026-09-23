package dk.ku.di.dms.vms.modb.storage.record;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Make this class substitute the default logging handler in the future so to allow for reading logical logging through this class
 */
public class AppendOnlyUnboundedBuffer {

    private final FileChannel fileChannel;
    private final String fileName;

    public AppendOnlyUnboundedBuffer(FileChannel fileChannel, String fileName) {
        this.fileChannel = fileChannel;
        this.fileName = fileName;
    }

    public void append(ByteBuffer byteBuffer) throws IOException {
        do {
            this.fileChannel.write(byteBuffer);
        } while(byteBuffer.hasRemaining());
    }

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
