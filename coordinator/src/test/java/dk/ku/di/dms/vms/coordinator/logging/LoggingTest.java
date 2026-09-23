package dk.ku.di.dms.vms.coordinator.logging;

import dk.ku.di.dms.vms.modb.common.logging.ILoggingHandler;
import dk.ku.di.dms.vms.modb.common.logging.LoggingHandlerBuilder;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LoggingTest {

    @Test
    public void testLogging() throws IOException {
        ILoggingHandler compressedLoggingHandler = LoggingHandlerBuilder.build("test");

        var writeBuffer = ByteBuffer.allocateDirect(1024);
        writeBuffer.put( "TEST".getBytes(StandardCharsets.UTF_8) );
        writeBuffer.flip();

        compressedLoggingHandler.log( writeBuffer );

        compressedLoggingHandler.close();

        var fileName = compressedLoggingHandler.getFileName();
        Path path = Paths.get(fileName);

        var fileChannel = FileChannel.open(path,
                StandardOpenOption.READ);

        var readBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(readBuffer);
        readBuffer.flip();
        String readString = new String(readBuffer.array(), 0, readBuffer.limit(), StandardCharsets.UTF_8);

        assert readString.equals("TEST");
    }

}
