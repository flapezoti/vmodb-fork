package dk.ku.di.dms.vms.modb.common.logging;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Interface for logging implementations
 */
public interface ILoggingHandler {

    default void log(ByteBuffer byteBuffer) throws IOException { }

    default void force() { }

    default void close() { }

    default String getFileName() { return ""; }
}
